/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Blocking
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.Response;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;
import org.jetbrains.annotations.Blocking;

public interface RestRateLimiter {
    public static final String RESET_AFTER_HEADER = "X-RateLimit-Reset-After";
    public static final String RESET_HEADER = "X-RateLimit-Reset";
    public static final String LIMIT_HEADER = "X-RateLimit-Limit";
    public static final String REMAINING_HEADER = "X-RateLimit-Remaining";
    public static final String GLOBAL_HEADER = "X-RateLimit-Global";
    public static final String HASH_HEADER = "X-RateLimit-Bucket";
    public static final String RETRY_AFTER_HEADER = "Retry-After";
    public static final String SCOPE_HEADER = "X-RateLimit-Scope";

    public void enqueue(@Nonnull Work var1);

    public void stop(boolean var1, @Nonnull Runnable var2);

    public boolean isStopped();

    public int cancelRequests();

    public static class RateLimitConfig {
        private final ScheduledExecutorService scheduler;
        private final ExecutorService elastic;
        private final GlobalRateLimit globalRateLimit;
        private final boolean isRelative;

        public RateLimitConfig(@Nonnull ScheduledExecutorService scheduler, @Nonnull GlobalRateLimit globalRateLimit, boolean isRelative) {
            this(scheduler, scheduler, globalRateLimit, isRelative);
        }

        public RateLimitConfig(@Nonnull ScheduledExecutorService scheduler, @Nonnull ExecutorService elastic, @Nonnull GlobalRateLimit globalRateLimit, boolean isRelative) {
            this.scheduler = scheduler;
            this.elastic = elastic;
            this.globalRateLimit = globalRateLimit;
            this.isRelative = isRelative;
        }

        @Nonnull
        public ScheduledExecutorService getScheduler() {
            return this.scheduler;
        }

        @Nonnull
        public ExecutorService getElastic() {
            return this.elastic;
        }

        @Nonnull
        public GlobalRateLimit getGlobalRateLimit() {
            return this.globalRateLimit;
        }

        public boolean isRelative() {
            return this.isRelative;
        }
    }

    public static interface GlobalRateLimit {
        public long getClassic();

        public void setClassic(long var1);

        public long getCloudflare();

        public void setCloudflare(long var1);

        @Nonnull
        public static GlobalRateLimit create() {
            return new GlobalRateLimit(){
                private final AtomicLong classic = new AtomicLong(-1L);
                private final AtomicLong cloudflare = new AtomicLong(-1L);

                @Override
                public long getClassic() {
                    return this.classic.get();
                }

                @Override
                public void setClassic(long timestamp) {
                    this.classic.set(timestamp);
                }

                @Override
                public long getCloudflare() {
                    return this.cloudflare.get();
                }

                @Override
                public void setCloudflare(long timestamp) {
                    this.cloudflare.set(timestamp);
                }
            };
        }
    }

    public static interface Work {
        @Nonnull
        public Route.CompiledRoute getRoute();

        @Nonnull
        public JDA getJDA();

        @Nullable
        @Blocking
        public Response execute();

        public boolean isSkipped();

        public boolean isDone();

        public boolean isPriority();

        public boolean isCancelled();

        public void cancel();
    }
}

