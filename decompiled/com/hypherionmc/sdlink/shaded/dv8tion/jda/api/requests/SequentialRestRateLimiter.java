/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestRateLimiter;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.Headers;
import com.hypherionmc.sdlink.shaded.okhttp3.Response;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;

public final class SequentialRestRateLimiter
implements RestRateLimiter {
    private static final Logger log = JDALogger.getLog(RestRateLimiter.class);
    private static final String UNINIT_BUCKET = "uninit";
    private final CompletableFuture<?> shutdownHandle = new CompletableFuture();
    private final Future<?> cleanupWorker;
    private final RestRateLimiter.RateLimitConfig config;
    private boolean isStopped;
    private boolean isShutdown;
    private final ReentrantLock lock = new ReentrantLock();
    private final Set<Route> hitRatelimit = new HashSet<Route>(5);
    private final Map<Route, String> hashes = new HashMap<Route, String>();
    private final Map<String, Bucket> buckets = new HashMap<String, Bucket>();
    private final Map<Bucket, Future<?>> rateLimitQueue = new HashMap();

    public SequentialRestRateLimiter(@Nonnull RestRateLimiter.RateLimitConfig config) {
        this.config = config;
        this.cleanupWorker = config.getScheduler().scheduleAtFixedRate(this::cleanup, 30L, 30L, TimeUnit.SECONDS);
    }

    @Override
    public void enqueue(@Nonnull RestRateLimiter.Work task) {
        MiscUtil.locked(this.lock, () -> {
            Bucket bucket = this.getBucket(task.getRoute());
            bucket.enqueue(task);
            this.runBucket(bucket);
        });
    }

    @Override
    public void stop(boolean shutdown, @Nonnull Runnable callback) {
        MiscUtil.locked(this.lock, () -> {
            boolean doShutdown = shutdown;
            if (!this.isStopped) {
                this.isStopped = true;
                this.shutdownHandle.thenRun(callback);
                if (!doShutdown) {
                    int count = this.buckets.values().stream().mapToInt(bucket -> bucket.getRequests().size()).sum();
                    if (count > 0) {
                        log.info("Waiting for {} requests to finish.", (Object)count);
                    }
                    boolean bl = doShutdown = count == 0;
                }
            }
            if (doShutdown && !this.isShutdown) {
                this.shutdown();
            }
        });
    }

    @Override
    public boolean isStopped() {
        return this.isStopped;
    }

    @Override
    public int cancelRequests() {
        return MiscUtil.locked(this.lock, () -> {
            int cancelled = (int)this.buckets.values().stream().map(Bucket::getRequests).flatMap(Collection::stream).filter(request -> !request.isPriority() && !request.isCancelled()).peek(RestRateLimiter.Work::cancel).count();
            if (cancelled == 1) {
                log.warn("Cancelled 1 request!");
            } else if (cancelled > 1) {
                log.warn("Cancelled {} requests!", (Object)cancelled);
            }
            return cancelled;
        });
    }

    private void shutdown() {
        this.isShutdown = true;
        this.cleanupWorker.cancel(false);
        this.cleanup();
        this.shutdownHandle.complete(null);
    }

    private void cleanup() {
        MiscUtil.locked(this.lock, () -> {
            int size = this.buckets.size();
            Iterator<Map.Entry<String, Bucket>> entries = this.buckets.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, Bucket> entry = entries.next();
                Bucket bucket = entry.getValue();
                if (this.isShutdown) {
                    bucket.requests.forEach(RestRateLimiter.Work::cancel);
                }
                bucket.requests.removeIf(RestRateLimiter.Work::isSkipped);
                if (!bucket.requests.isEmpty() || this.rateLimitQueue.containsKey(bucket)) continue;
                if (bucket.isUninit()) {
                    entries.remove();
                    continue;
                }
                if (bucket.reset <= this.getNow()) {
                    entries.remove();
                    continue;
                }
                if (!this.isStopped) continue;
                entries.remove();
            }
            if ((size -= this.buckets.size()) > 0) {
                log.debug("Removed {} expired buckets", (Object)size);
            } else if (this.isStopped && !this.isShutdown) {
                this.shutdown();
            }
        });
    }

    private String getRouteHash(Route route) {
        return this.hashes.getOrDefault(route, "uninit+" + route);
    }

    private Bucket getBucket(Route.CompiledRoute route) {
        return MiscUtil.locked(this.lock, () -> {
            String hash = this.getRouteHash(route.getBaseRoute());
            String bucketId = hash + ":" + route.getMajorParameters();
            return this.buckets.computeIfAbsent(bucketId, id -> {
                if (route.getBaseRoute().isInteractionBucket()) {
                    return new InteractionBucket((String)id);
                }
                return new ClassicBucket((String)id);
            });
        });
    }

    private void scheduleElastic(Bucket bucket) {
        block7: {
            if (this.isShutdown) {
                return;
            }
            ExecutorService elastic = this.config.getElastic();
            ScheduledExecutorService scheduler = this.config.getScheduler();
            try {
                if (elastic == scheduler) {
                    bucket.run();
                } else {
                    elastic.execute(bucket);
                }
            }
            catch (RejectedExecutionException ex) {
                if (!this.isShutdown) {
                    log.error("Failed to execute bucket worker", (Throwable)ex);
                }
            }
            catch (Throwable t) {
                log.error("Caught throwable in bucket worker", t);
                if (!(t instanceof Error)) break block7;
                throw t;
            }
        }
    }

    private void runBucket(Bucket bucket) {
        if (this.isShutdown) {
            return;
        }
        MiscUtil.locked(this.lock, () -> this.rateLimitQueue.computeIfAbsent(bucket, k -> this.config.getScheduler().schedule(() -> this.scheduleElastic(bucket), bucket.getRateLimit(), TimeUnit.MILLISECONDS)));
    }

    private long parseLong(String input) {
        return input == null ? 0L : Long.parseLong(input);
    }

    private long parseDouble(String input) {
        return input == null ? 0L : (long)(Double.parseDouble(input) * 1000.0);
    }

    private long getNow() {
        return System.currentTimeMillis();
    }

    private Bucket updateBucket(Route.CompiledRoute route, Response response) {
        return MiscUtil.locked(this.lock, () -> {
            try {
                Bucket bucket = this.getBucket(route);
                Headers headers = response.headers();
                boolean global = headers.get("X-RateLimit-Global") != null;
                boolean cloudflare = headers.get("via") == null;
                String hash = headers.get("X-RateLimit-Bucket");
                String scope = headers.get("X-RateLimit-Scope");
                long now = this.getNow();
                Route baseRoute = route.getBaseRoute();
                if (hash != null) {
                    if (!this.hashes.containsKey(baseRoute)) {
                        this.hashes.put(baseRoute, hash);
                        log.debug("Caching bucket hash {} -> {}", (Object)baseRoute, (Object)hash);
                    }
                    bucket = this.getBucket(route);
                }
                if (response.code() == 429) {
                    String retryAfterHeader = headers.get("Retry-After");
                    long retryAfter = this.parseLong(retryAfterHeader) * 1000L;
                    if (global) {
                        this.config.getGlobalRateLimit().setClassic(now + retryAfter);
                        log.error("Encountered global rate limit! Retry-After: {} ms Scope: {}", (Object)retryAfter, (Object)scope);
                    } else if (cloudflare) {
                        this.config.getGlobalRateLimit().setCloudflare(now + retryAfter);
                        log.error("Encountered cloudflare rate limit! Retry-After: {} s", (Object)(retryAfter / 1000L));
                    } else {
                        boolean firstHit = this.hitRatelimit.add(baseRoute) && retryAfter < 60000L;
                        bucket.remaining = 0;
                        bucket.reset = now + retryAfter;
                        if (firstHit) {
                            log.debug("Encountered 429 on route {} with bucket {} Retry-After: {} ms Scope: {}", new Object[]{baseRoute, bucket.bucketId, retryAfter, scope});
                        } else {
                            log.warn("Encountered 429 on route {} with bucket {} Retry-After: {} ms Scope: {}", new Object[]{baseRoute, bucket.bucketId, retryAfter, scope});
                        }
                    }
                    log.trace("Updated bucket {} to retry after {}", (Object)bucket.bucketId, (Object)(bucket.reset - now));
                    return bucket;
                }
                if (hash == null) {
                    return bucket;
                }
                String limitHeader = headers.get("X-RateLimit-Limit");
                String remainingHeader = headers.get("X-RateLimit-Remaining");
                String resetAfterHeader = headers.get("X-RateLimit-Reset-After");
                String resetHeader = headers.get("X-RateLimit-Reset");
                bucket.remaining = (int)this.parseLong(remainingHeader);
                bucket.reset = this.config.isRelative() ? now + this.parseDouble(resetAfterHeader) : this.parseDouble(resetHeader);
                log.trace("Updated bucket {} to ({}/{}, {})", new Object[]{bucket.bucketId, bucket.remaining, limitHeader, bucket.reset - now});
                return bucket;
            }
            catch (Exception e) {
                Bucket bucket = this.getBucket(route);
                log.error("Encountered Exception while updating a bucket. Route: {} Bucket: {} Code: {} Headers:\n{}", new Object[]{route.getBaseRoute(), bucket, response.code(), response.headers(), e});
                return bucket;
            }
        });
    }

    private abstract class Bucket
    implements Runnable {
        protected final String bucketId;
        protected final Deque<RestRateLimiter.Work> requests = new ConcurrentLinkedDeque<RestRateLimiter.Work>();
        protected long reset = 0L;
        protected int remaining = 1;

        public Bucket(String bucketId) {
            this.bucketId = bucketId;
        }

        public boolean isUninit() {
            return this.bucketId.startsWith(SequentialRestRateLimiter.UNINIT_BUCKET);
        }

        public void enqueue(RestRateLimiter.Work request) {
            this.requests.addLast(request);
        }

        public void retry(RestRateLimiter.Work request) {
            if (!this.moveRequest(request)) {
                this.requests.addFirst(request);
            }
        }

        public long getReset() {
            return this.reset;
        }

        public int getRemaining() {
            return this.remaining;
        }

        public abstract long getGlobalRateLimit(long var1);

        public long getRateLimit() {
            long now = SequentialRestRateLimiter.this.getNow();
            long global = this.getGlobalRateLimit(now);
            if (this.reset <= now) {
                this.remaining = 1;
            }
            return Math.max(global, this.remaining < 1 ? this.reset - now : 0L);
        }

        protected boolean isGlobalRateLimit() {
            return this.getGlobalRateLimit(SequentialRestRateLimiter.this.getNow()) > 0L;
        }

        protected void backoff() {
            MiscUtil.locked(SequentialRestRateLimiter.this.lock, () -> {
                SequentialRestRateLimiter.this.rateLimitQueue.remove(this);
                if (!this.requests.isEmpty()) {
                    SequentialRestRateLimiter.this.runBucket(this);
                } else if (SequentialRestRateLimiter.this.isStopped) {
                    SequentialRestRateLimiter.this.buckets.remove(this.bucketId);
                }
                if (SequentialRestRateLimiter.this.isStopped && SequentialRestRateLimiter.this.buckets.isEmpty()) {
                    SequentialRestRateLimiter.this.shutdown();
                }
            });
        }

        @Nonnull
        public Queue<RestRateLimiter.Work> getRequests() {
            return this.requests;
        }

        protected boolean moveRequest(RestRateLimiter.Work request) {
            return MiscUtil.locked(SequentialRestRateLimiter.this.lock, () -> {
                Bucket bucket = SequentialRestRateLimiter.this.getBucket(request.getRoute());
                if (bucket != this) {
                    bucket.enqueue(request);
                    SequentialRestRateLimiter.this.runBucket(bucket);
                }
                return bucket != this;
            });
        }

        protected boolean execute(RestRateLimiter.Work request) {
            try {
                Response response = request.execute();
                if (response != null) {
                    SequentialRestRateLimiter.this.updateBucket(request.getRoute(), response);
                }
                if (!request.isDone()) {
                    this.retry(request);
                }
            }
            catch (Throwable ex) {
                log.error("Encountered exception trying to execute request", ex);
                if (ex instanceof Error) {
                    throw (Error)ex;
                }
                return true;
            }
            return false;
        }

        @Override
        public void run() {
            log.trace("Bucket {} is running {} requests", (Object)this.bucketId, (Object)this.requests.size());
            while (!this.requests.isEmpty()) {
                RestRateLimiter.Work request;
                long rateLimit = this.getRateLimit();
                if (rateLimit > 0L) {
                    String baseRoute;
                    request = this.requests.peekFirst();
                    String string = baseRoute = request != null ? request.getRoute().getBaseRoute().toString() : "N/A";
                    if (!this.isGlobalRateLimit() && rateLimit >= 1800000L) {
                        log.warn("Encountered long {} minutes Rate-Limit on route {}", (Object)TimeUnit.MILLISECONDS.toMinutes(rateLimit), (Object)baseRoute);
                    }
                    log.debug("Backing off {} ms for bucket {} on route {}", new Object[]{rateLimit, this.bucketId, baseRoute});
                    break;
                }
                request = this.requests.removeFirst();
                if (request.isSkipped() || this.isUninit() && this.moveRequest(request) || !this.execute(request)) continue;
                break;
            }
            this.backoff();
        }

        public String toString() {
            return this.bucketId;
        }

        public int hashCode() {
            return this.bucketId.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Bucket)) {
                return false;
            }
            return this.bucketId.equals(((Bucket)obj).bucketId);
        }
    }

    private class InteractionBucket
    extends Bucket {
        public InteractionBucket(String bucketId) {
            super(bucketId);
        }

        @Override
        public long getGlobalRateLimit(long now) {
            return SequentialRestRateLimiter.this.config.getGlobalRateLimit().getCloudflare() - now;
        }
    }

    private class ClassicBucket
    extends Bucket {
        public ClassicBucket(String bucketId) {
            super(bucketId);
        }

        @Override
        public long getGlobalRateLimit(long now) {
            RestRateLimiter.GlobalRateLimit holder = SequentialRestRateLimiter.this.config.getGlobalRateLimit();
            long global = Math.max(holder.getClassic(), holder.getCloudflare());
            return global - now;
        }
    }
}

