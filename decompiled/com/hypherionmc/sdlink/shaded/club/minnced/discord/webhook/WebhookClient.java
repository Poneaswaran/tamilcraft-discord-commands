/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Async$Execute
 *  org.jetbrains.annotations.Async$Schedule
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook;

import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.IOUtil;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.WebhookClientBuilder;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.exception.HttpException;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.receive.EntityFactory;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.receive.ReadonlyMessage;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.AllowedMentions;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.WebhookEmbed;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.WebhookMessage;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.util.ThreadPools;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.util.WebhookErrorHandler;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnegative;
import com.hypherionmc.sdlink.shaded.json.JSONException;
import com.hypherionmc.sdlink.shaded.json.JSONObject;
import com.hypherionmc.sdlink.shaded.okhttp3.OkHttpClient;
import com.hypherionmc.sdlink.shaded.okhttp3.Request;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import com.hypherionmc.sdlink.shaded.okhttp3.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import org.jetbrains.annotations.Async;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebhookClient
implements AutoCloseable {
    public static final String WEBHOOK_URL = "https://discord.com/api/v9/webhooks/%s/%s";
    public static final String USER_AGENT = "Webhook(https://github.com/MinnDevelopment/discord-webhooks, 0.8.4)";
    private static final Logger LOG = LoggerFactory.getLogger(WebhookClient.class);
    private static WebhookErrorHandler DEFAULT_ERROR_HANDLER = WebhookErrorHandler.DEFAULT;
    protected final WebhookClient parent;
    protected final String url;
    protected final long id;
    protected final long threadId;
    protected final OkHttpClient client;
    protected final ScheduledExecutorService pool;
    protected final Bucket bucket;
    protected final BlockingQueue<Request> queue;
    protected final boolean parseMessage;
    protected final AllowedMentions allowedMentions;
    protected long defaultTimeout;
    protected volatile boolean isQueued;
    protected boolean isShutdown;
    protected WebhookErrorHandler errorHandler = DEFAULT_ERROR_HANDLER;

    protected WebhookClient(long id, String token, boolean parseMessage, OkHttpClient client, ScheduledExecutorService pool, AllowedMentions mentions, long threadId) {
        this.client = client;
        this.id = id;
        this.threadId = threadId;
        this.parseMessage = parseMessage;
        this.url = String.format(Locale.ROOT, WEBHOOK_URL, Long.toUnsignedString(id), token);
        this.pool = pool;
        this.bucket = new Bucket();
        this.queue = new LinkedBlockingQueue<Request>();
        this.allowedMentions = mentions;
        this.parent = null;
        this.isQueued = false;
    }

    protected WebhookClient(WebhookClient parent, long threadId) {
        this.client = parent.client;
        this.id = parent.id;
        this.threadId = threadId;
        this.parseMessage = parent.parseMessage;
        this.url = parent.url;
        this.parent = parent;
        this.pool = parent.pool;
        this.bucket = parent.bucket;
        this.queue = parent.queue;
        this.allowedMentions = parent.allowedMentions;
        this.isQueued = false;
    }

    public static void setDefaultErrorHandler(@NotNull WebhookErrorHandler handler) {
        DEFAULT_ERROR_HANDLER = Objects.requireNonNull(handler, "Error Handler must not be null!");
    }

    @NotNull
    public static WebhookClient withId(long id, @NotNull String token) {
        Objects.requireNonNull(token, "Token");
        ScheduledExecutorService pool = ThreadPools.getDefaultPool(id, null, false);
        return new WebhookClient(id, token, true, new OkHttpClient(), pool, AllowedMentions.all(), 0L);
    }

    @NotNull
    public static WebhookClient withUrl(@NotNull String url) {
        Objects.requireNonNull(url, "URL");
        Matcher matcher = WebhookClientBuilder.WEBHOOK_PATTERN.matcher(url);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Failed to parse webhook URL");
        }
        return WebhookClient.withId(Long.parseUnsignedLong(matcher.group(1)), matcher.group(2));
    }

    @NotNull
    public WebhookClient onThread(long threadId) {
        return new WebhookClient(this, threadId);
    }

    public long getId() {
        return this.id;
    }

    public long getThreadId() {
        return this.threadId;
    }

    @NotNull
    public String getUrl() {
        return this.url;
    }

    public boolean isWait() {
        return this.parseMessage;
    }

    public boolean isShutdown() {
        return this.isShutdown;
    }

    @NotNull
    public WebhookClient setTimeout(@Nonnegative long millis) {
        if (millis < 0L) {
            throw new IllegalArgumentException("Cannot set a negative timeout");
        }
        this.defaultTimeout = millis;
        return this;
    }

    @NotNull
    public WebhookClient setErrorHandler(@NotNull WebhookErrorHandler handler) {
        this.errorHandler = Objects.requireNonNull(handler, "Error Handler must not be null!");
        return this;
    }

    public long getTimeout() {
        return this.defaultTimeout;
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull WebhookMessage message) {
        Objects.requireNonNull(message, "WebhookMessage");
        return this.execute(message.getBody());
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull File file) {
        Objects.requireNonNull(file, "File");
        return this.send(file, file.getName());
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull File file, @NotNull String fileName) {
        return this.send(new WebhookMessageBuilder().setAllowedMentions(this.allowedMentions).addFile(fileName, file).build());
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull byte[] data, @NotNull String fileName) {
        return this.send(new WebhookMessageBuilder().setAllowedMentions(this.allowedMentions).addFile(fileName, data).build());
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull InputStream data, @NotNull String fileName) {
        return this.send(new WebhookMessageBuilder().setAllowedMentions(this.allowedMentions).addFile(fileName, data).build());
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull WebhookEmbed first, WebhookEmbed ... embeds) {
        return this.send(WebhookMessage.embeds(first, embeds));
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull Collection<WebhookEmbed> embeds) {
        return this.send(WebhookMessage.embeds(embeds));
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull String content) {
        Objects.requireNonNull(content, "Content");
        content = content.trim();
        if (content.isEmpty()) {
            throw new IllegalArgumentException("Cannot send an empty message");
        }
        if (content.length() > 2000) {
            throw new IllegalArgumentException("Content may not exceed 2000 characters");
        }
        return this.execute(WebhookClient.newBody(this.newJson().put("content", content).toString()));
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(long messageId, @NotNull WebhookMessage message) {
        Objects.requireNonNull(message, "WebhookMessage");
        return this.execute(message.getBody(), Long.toUnsignedString(messageId), RequestType.EDIT);
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(long messageId, @NotNull WebhookEmbed first, WebhookEmbed ... embeds) {
        return this.edit(messageId, WebhookMessage.embeds(first, embeds));
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(long messageId, @NotNull Collection<WebhookEmbed> embeds) {
        return this.edit(messageId, WebhookMessage.embeds(embeds));
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(long messageId, @NotNull String content) {
        Objects.requireNonNull(content, "Content");
        content = content.trim();
        if (content.isEmpty()) {
            throw new IllegalArgumentException("Cannot send an empty message");
        }
        if (content.length() > 2000) {
            throw new IllegalArgumentException("Content may not exceed 2000 characters");
        }
        return this.edit(messageId, new WebhookMessageBuilder().setContent(content).build());
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(@NotNull String messageId, @NotNull WebhookMessage message) {
        Objects.requireNonNull(message, "WebhookMessage");
        return this.execute(message.getBody(), messageId, RequestType.EDIT);
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(@NotNull String messageId, @NotNull WebhookEmbed first, WebhookEmbed ... embeds) {
        return this.edit(messageId, WebhookMessage.embeds(first, embeds));
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(@NotNull String messageId, @NotNull Collection<WebhookEmbed> embeds) {
        return this.edit(messageId, WebhookMessage.embeds(embeds));
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(@NotNull String messageId, @NotNull String content) {
        Objects.requireNonNull(content, "Content");
        content = content.trim();
        if (content.isEmpty()) {
            throw new IllegalArgumentException("Cannot send an empty message");
        }
        if (content.length() > 2000) {
            throw new IllegalArgumentException("Content may not exceed 2000 characters");
        }
        return this.edit(messageId, new WebhookMessageBuilder().setContent(content).build());
    }

    @NotNull
    public CompletableFuture<Void> delete(long messageId) {
        return this.execute(null, Long.toUnsignedString(messageId), RequestType.DELETE).thenApply(v -> null);
    }

    @NotNull
    public CompletableFuture<Void> delete(@NotNull String messageId) {
        return this.execute(null, messageId, RequestType.DELETE).thenApply(v -> null);
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> get(long messageId) {
        return this.get(Long.toUnsignedString(messageId));
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> get(@NotNull String messageId) {
        return this.execute(null, messageId, RequestType.GET);
    }

    private JSONObject newJson() {
        JSONObject json = new JSONObject();
        json.put("allowed_mentions", this.allowedMentions);
        return json;
    }

    @Override
    public void close() {
        this.isShutdown = true;
        if (this.parent != null) {
            this.parent.close();
        }
        if (this.queue.isEmpty()) {
            this.pool.shutdown();
        }
    }

    protected void checkShutdown() {
        if (this.isShutdown) {
            throw new RejectedExecutionException("Cannot send to closed client!");
        }
    }

    @NotNull
    protected static RequestBody newBody(String object) {
        return RequestBody.create(IOUtil.JSON, object);
    }

    @NotNull
    protected CompletableFuture<ReadonlyMessage> execute(RequestBody body, @Nullable String messageId, @NotNull RequestType type) {
        this.checkShutdown();
        String endpoint = this.url;
        if (type != RequestType.SEND) {
            Objects.requireNonNull(messageId, "Message ID");
            endpoint = endpoint + "/messages/" + messageId;
        }
        ArrayList<String> query = new ArrayList<String>(2);
        if (this.parseMessage) {
            query.add("wait=true");
        }
        if (this.threadId != 0L) {
            query.add("thread_id=" + Long.toUnsignedString(this.threadId));
        }
        if (!query.isEmpty()) {
            endpoint = endpoint + "?" + String.join((CharSequence)"&", query);
        }
        return this.queueRequest(endpoint, type.method, body);
    }

    @NotNull
    protected CompletableFuture<ReadonlyMessage> execute(RequestBody body) {
        return this.execute(body, null, RequestType.SEND);
    }

    @NotNull
    protected static HttpException failure(Response response) throws IOException {
        InputStream stream = IOUtil.getBody(response);
        String responseBody = stream == null ? "" : new String(IOUtil.readAllBytes(stream));
        return new HttpException(response.code(), responseBody, response.headers());
    }

    @NotNull
    protected CompletableFuture<ReadonlyMessage> queueRequest(String url, String method, RequestBody body) {
        CompletableFuture<ReadonlyMessage> callback = new CompletableFuture<ReadonlyMessage>();
        Request req = new Request(callback, body, method, url);
        if (this.defaultTimeout > 0L) {
            req.deadline = System.currentTimeMillis() + this.defaultTimeout;
        }
        return this.parent == null ? this.schedule(callback, req) : this.parent.schedule(callback, req);
    }

    @NotNull
    protected CompletableFuture<ReadonlyMessage> schedule(@NotNull CompletableFuture<ReadonlyMessage> callback, @NotNull Request req) {
        this.enqueuePair(req);
        if (!this.isQueued) {
            this.backoffQueue();
        }
        this.isQueued = true;
        return callback;
    }

    @NotNull
    protected com.hypherionmc.sdlink.shaded.okhttp3.Request newRequest(Request request) {
        return new Request.Builder().url(request.url).method(request.method, request.body).header("accept-encoding", "gzip").header("user-agent", USER_AGENT).build();
    }

    protected void backoffQueue() {
        long delay = this.bucket.retryAfter();
        if (delay > 0L) {
            LOG.debug("Backing off queue for {}", (Object)delay);
        }
        this.pool.schedule(this::drainQueue, delay, TimeUnit.MILLISECONDS);
    }

    protected synchronized void drainQueue() {
        Request pair;
        boolean graceful = true;
        while (!this.queue.isEmpty() && (graceful = this.executePair(pair = (Request)this.queue.peek()))) {
        }
        boolean bl = this.isQueued = !graceful;
        if (this.isShutdown && graceful) {
            this.pool.shutdown();
        }
    }

    private boolean enqueuePair(@Async.Schedule Request pair) {
        return this.queue.add(pair);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean executePair(@Async.Execute Request req) {
        if (req.future.isDone()) {
            this.queue.poll();
            return true;
        }
        if (req.deadline > 0L && req.deadline < System.currentTimeMillis()) {
            req.future.completeExceptionally(new TimeoutException());
            this.queue.poll();
            return true;
        }
        com.hypherionmc.sdlink.shaded.okhttp3.Request request = this.newRequest(req);
        try (Response response = this.client.newCall(request).execute();){
            this.bucket.update(response);
            if (response.code() == 429) {
                this.backoffQueue();
                boolean bl = false;
                return bl;
            }
            if (!response.isSuccessful()) {
                HttpException exception = WebhookClient.failure(response);
                this.errorHandler.handle(this, "Sending a webhook message failed with non-OK http response", exception);
                ((Request)this.queue.poll()).future.completeExceptionally(exception);
                boolean bl = true;
                return bl;
            }
            ReadonlyMessage message = null;
            if (this.parseMessage && !"DELETE".equals(req.method)) {
                InputStream body = IOUtil.getBody(response);
                JSONObject json = IOUtil.toJSON(body);
                message = EntityFactory.makeMessage(json);
            }
            ((Request)this.queue.poll()).future.complete(message);
            if (!this.bucket.isRateLimit()) return true;
            this.backoffQueue();
            boolean bl = false;
            return bl;
        }
        catch (JSONException | IOException e) {
            this.errorHandler.handle(this, "There was some error while sending a webhook message", e);
            ((Request)this.queue.poll()).future.completeExceptionally(e);
        }
        return true;
    }

    private static final class Request {
        private final CompletableFuture<ReadonlyMessage> future;
        private final RequestBody body;
        private final String method;
        private final String url;
        private long deadline;

        public Request(CompletableFuture<ReadonlyMessage> future, RequestBody body, String method, String url) {
            this.future = future;
            this.body = body;
            this.method = method;
            this.url = url;
        }
    }

    protected final class Bucket {
        public static final int RATE_LIMIT_CODE = 429;
        public long resetTime;
        public int remainingUses;
        public int limit = Integer.MAX_VALUE;

        protected Bucket() {
        }

        public synchronized boolean isRateLimit() {
            if (this.retryAfter() <= 0L) {
                this.remainingUses = this.limit;
            }
            return this.remainingUses <= 0;
        }

        public synchronized long retryAfter() {
            return this.resetTime - System.currentTimeMillis();
        }

        private synchronized void handleRatelimit(Response response, long current) throws IOException {
            long delay;
            String retryAfter = response.header("Retry-After");
            String limitHeader = response.header("X-RateLimit-Limit", "5");
            if (retryAfter == null) {
                InputStream stream = IOUtil.getBody(response);
                if (stream == null) {
                    delay = 30000L;
                } else {
                    JSONObject body = IOUtil.toJSON(stream);
                    delay = (long)Math.ceil(body.getDouble("retry_after")) * 1000L;
                }
            } else {
                delay = Long.parseLong(retryAfter) * 1000L;
            }
            LOG.error("Encountered 429, retrying after {} ms", (Object)delay);
            this.resetTime = current + delay;
            this.remainingUses = 0;
            this.limit = Integer.parseInt(limitHeader);
        }

        private synchronized void update0(Response response) throws IOException {
            long current = System.currentTimeMillis();
            boolean is429 = response.code() == 429;
            String remainingHeader = response.header("X-RateLimit-Remaining");
            String limitHeader = response.header("X-RateLimit-Limit");
            String resetHeader = response.header("X-RateLimit-Reset-After");
            if (is429) {
                this.handleRatelimit(response, current);
                return;
            }
            if (remainingHeader == null || limitHeader == null || resetHeader == null) {
                LOG.debug("Failed to update buckets due to missing headers in response with code: {} and headers: \n{}", (Object)response.code(), (Object)response.headers());
                return;
            }
            this.remainingUses = Integer.parseInt(remainingHeader);
            this.limit = Integer.parseInt(limitHeader);
            long reset = (long)Math.ceil(Double.parseDouble(resetHeader));
            long delay = reset * 1000L;
            this.resetTime = current + delay;
        }

        public void update(Response response) {
            try {
                this.update0(response);
            }
            catch (Exception ex) {
                WebhookClient.this.errorHandler.handle(WebhookClient.this, "Could not read http response", ex);
            }
        }
    }

    static enum RequestType {
        SEND("POST"),
        EDIT("PATCH"),
        DELETE("DELETE"),
        GET("GET");

        private final String method;

        private RequestType(String method) {
            this.method = method;
        }

        public String getMethod() {
            return this.method;
        }
    }
}

