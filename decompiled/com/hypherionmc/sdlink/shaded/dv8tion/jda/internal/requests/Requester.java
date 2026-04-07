/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.MDC
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestConfig;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestRateLimiter;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.IOUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.config.AuthorizationConfig;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.MediaType;
import com.hypherionmc.sdlink.shaded.okhttp3.OkHttpClient;
import com.hypherionmc.sdlink.shaded.okhttp3.Request;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import com.hypherionmc.sdlink.shaded.okhttp3.Response;
import com.hypherionmc.sdlink.shaded.okhttp3.internal.http.HttpMethod;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.function.Consumer;
import javax.net.ssl.SSLPeerUnverifiedException;
import org.slf4j.Logger;
import org.slf4j.MDC;

public class Requester {
    private static final int[] RETRY_ERROR_CODES = new int[]{502, 503, 504, 520, 521, 522, 523, 524, 529};
    public static final Logger LOG = JDALogger.getLog(Requester.class);
    public static final RequestBody EMPTY_BODY = RequestBody.create(null, new byte[0]);
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_OCTET = MediaType.parse("application/octet-stream; charset=utf-8");
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    public static final MediaType MEDIA_TYPE_GIF = MediaType.parse("image/gif");
    protected final JDAImpl api;
    protected final AuthorizationConfig authConfig;
    private final RestRateLimiter rateLimiter;
    private final String baseUrl;
    private final String userAgent;
    private final Consumer<? super Request.Builder> customBuilder;
    private final OkHttpClient httpClient;
    private boolean isContextReady = false;
    private ConcurrentMap<String, String> contextMap = null;
    private volatile boolean retryOnTimeout = false;

    public Requester(JDA api, AuthorizationConfig authConfig, RestConfig config, RestRateLimiter rateLimiter) {
        if (authConfig == null) {
            throw new NullPointerException("Provided config was null!");
        }
        this.authConfig = authConfig;
        this.api = (JDAImpl)api;
        this.rateLimiter = rateLimiter;
        this.baseUrl = config.getBaseUrl();
        this.userAgent = config.getUserAgent();
        this.customBuilder = config.getCustomBuilder();
        this.httpClient = this.api.getHttpClient();
    }

    public void setContextReady(boolean ready) {
        this.isContextReady = ready;
    }

    public void setContext() {
        if (!this.isContextReady) {
            return;
        }
        if (this.contextMap == null) {
            this.contextMap = this.api.getContextMap();
        }
        this.contextMap.forEach(MDC::put);
    }

    public JDAImpl getJDA() {
        return this.api;
    }

    public <T> void request(Request<T> apiRequest) {
        if (this.rateLimiter.isStopped()) {
            throw new RejectedExecutionException("The Requester has been stopped! No new requests can be requested!");
        }
        if (apiRequest.shouldQueue()) {
            this.rateLimiter.enqueue(new WorkTask(apiRequest));
        } else {
            this.execute(new WorkTask(apiRequest), true);
        }
    }

    private static boolean isRetry(Throwable e) {
        return e instanceof SocketException || e instanceof SocketTimeoutException || e instanceof SSLPeerUnverifiedException;
    }

    public Response execute(WorkTask task) {
        return this.execute(task, false);
    }

    public Response execute(WorkTask task, boolean handleOnRateLimit) {
        return this.execute(task, false, handleOnRateLimit);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Response execute(WorkTask task, boolean retried, boolean handleOnRatelimit) {
        Route.CompiledRoute route = task.getRoute();
        Request.Builder builder = new Request.Builder();
        String url = this.baseUrl + route.getCompiledRoute();
        builder.url(url);
        Request apiRequest = task.request;
        this.applyBody(apiRequest, builder);
        this.applyHeaders(apiRequest, builder);
        if (this.customBuilder != null) {
            try {
                this.customBuilder.accept(builder);
            }
            catch (Exception e) {
                LOG.error("Custom request builder caused exception", (Throwable)e);
            }
        }
        com.hypherionmc.sdlink.shaded.okhttp3.Request request = builder.build();
        LinkedHashSet<String> rays = new LinkedHashSet<String>();
        Response[] responses = new Response[4];
        Response lastResponse = null;
        try {
            LOG.trace("Executing request {} {}", (Object)task.getRoute().getMethod(), (Object)url);
            int code = 0;
            for (int attempt = 0; attempt < responses.length; ++attempt) {
                if (apiRequest.isSkipped()) {
                    Response[] responseArray = null;
                    return responseArray;
                }
                Response[] call = this.httpClient.newCall(request);
                lastResponse = call.execute();
                code = lastResponse.code();
                responses[attempt] = lastResponse;
                String cfRay = lastResponse.header("CF-RAY");
                if (cfRay != null) {
                    rays.add(cfRay);
                }
                if (!Requester.shouldRetry(code)) break;
                LOG.debug("Requesting {} -> {} returned status {}... retrying (attempt {})", new Object[]{apiRequest.getRoute().getMethod(), url, code, attempt + 1});
                try {
                    Thread.sleep(500 << attempt);
                    continue;
                }
                catch (InterruptedException ignored) {
                    break;
                }
            }
            LOG.trace("Finished Request {} {} with code {}", new Object[]{route.getMethod(), lastResponse.request().url(), code});
            if (Requester.shouldRetry(code)) {
                task.handleResponse(lastResponse, -1L, rays);
                Response attempt = null;
                return attempt;
            }
            if (!rays.isEmpty()) {
                LOG.debug("Received response with following cf-rays: {}", rays);
            }
            if (handleOnRatelimit && code == 429) {
                long retryAfter = this.parseRetry(lastResponse);
                task.handleResponse(lastResponse, retryAfter, rays);
            } else if (code != 429) {
                task.handleResponse(lastResponse, (Set<String>)rays);
            } else if (Requester.getContentType(lastResponse).startsWith("application/json")) {
                try (InputStream body = IOUtil.getBody(lastResponse);){
                    long retryAfterBody = (long)Math.ceil(DataObject.fromJson(body).getDouble("retry_after", 0.0));
                    long retryAfterHeader = Long.parseLong(lastResponse.header("Retry-After"));
                    lastResponse = lastResponse.newBuilder().header("Retry-After", Long.toString(Math.max(retryAfterHeader, retryAfterBody))).build();
                }
                catch (Exception e) {
                    LOG.warn("Failed to parse retry-after response body", (Throwable)e);
                }
            }
            Response response = lastResponse;
            return response;
        }
        catch (UnknownHostException e) {
            LOG.error("DNS resolution failed: {}", (Object)e.getMessage());
            task.handleResponse(e, (Set<String>)rays);
            Response response = null;
            return response;
        }
        catch (IOException e) {
            if (this.retryOnTimeout && !retried && Requester.isRetry(e)) {
                Response response = this.execute(task, true, handleOnRatelimit);
                return response;
            }
            LOG.error("There was an I/O error while executing a REST request: {}", (Object)e.getMessage());
            task.handleResponse(e, (Set<String>)rays);
            Response response = null;
            return response;
        }
        catch (Exception e) {
            LOG.error("There was an unexpected error while executing a REST request", (Throwable)e);
            task.handleResponse(e, (Set<String>)rays);
            Response response = null;
            return response;
        }
        finally {
            for (Response r : responses) {
                if (r == null) break;
                r.close();
            }
        }
    }

    private void applyBody(Request<?> apiRequest, Request.Builder builder) {
        String method = apiRequest.getRoute().getMethod().toString();
        RequestBody body = apiRequest.getBody();
        if (body == null && HttpMethod.requiresRequestBody(method)) {
            body = EMPTY_BODY;
        }
        builder.method(method, body);
        if (apiRequest.getRawBody() != null) {
            LOG.trace("Sending request on route {}/{} with body\n{}", new Object[]{method, apiRequest.getRoute().getCompiledRoute(), apiRequest.getRawBody()});
        }
    }

    private void applyHeaders(Request<?> apiRequest, Request.Builder builder) {
        builder.header("user-agent", this.userAgent).header("accept-encoding", "gzip").header("authorization", this.authConfig.getToken()).header("x-ratelimit-precision", "millisecond");
        if (apiRequest.getHeaders() != null) {
            for (Map.Entry header : apiRequest.getHeaders().entrySet()) {
                builder.header((String)header.getKey(), (String)header.getValue());
            }
        }
    }

    public OkHttpClient getHttpClient() {
        return this.httpClient;
    }

    public RestRateLimiter getRateLimiter() {
        return this.rateLimiter;
    }

    public void setRetryOnTimeout(boolean retryOnTimeout) {
        this.retryOnTimeout = retryOnTimeout;
    }

    public void stop(boolean shutdown, Runnable callback) {
        this.rateLimiter.stop(shutdown, callback);
    }

    private static boolean shouldRetry(int code) {
        if (code < RETRY_ERROR_CODES[0] || code > RETRY_ERROR_CODES[RETRY_ERROR_CODES.length - 1]) {
            return false;
        }
        for (int retryCode : RETRY_ERROR_CODES) {
            if (retryCode != code) continue;
            return true;
        }
        return false;
    }

    private long parseRetry(Response response) {
        String retryAfter = response.header("Retry-After", "0");
        return (long)(Double.parseDouble(retryAfter) * 1000.0);
    }

    private static String getContentType(Response response) {
        String type = response.header("content-type");
        return type == null ? "" : type.toLowerCase(Locale.ROOT);
    }

    private class WorkTask
    implements RestRateLimiter.Work {
        private final Request<?> request;
        private boolean done;

        private WorkTask(Request<?> request) {
            this.request = request;
        }

        @Override
        @Nonnull
        public Route.CompiledRoute getRoute() {
            return this.request.getRoute();
        }

        @Override
        @Nonnull
        public JDA getJDA() {
            return this.request.getJDA();
        }

        @Override
        @Nullable
        public Response execute() {
            return Requester.this.execute(this);
        }

        @Override
        public boolean isSkipped() {
            return this.request.isSkipped();
        }

        @Override
        public boolean isDone() {
            return this.isSkipped() || this.done;
        }

        @Override
        public boolean isPriority() {
            return this.request.isPriority();
        }

        @Override
        public boolean isCancelled() {
            return this.request.isCancelled();
        }

        @Override
        public void cancel() {
            this.request.cancel();
        }

        private void handleResponse(Response response, Set<String> rays) {
            this.done = true;
            this.request.handleResponse(new com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response(response, -1L, rays));
        }

        private void handleResponse(Exception error, Set<String> rays) {
            this.done = true;
            this.request.handleResponse(new com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response(error, rays));
        }

        private void handleResponse(Response response, long retryAfter, Set<String> cfRays) {
            this.done = true;
            this.request.handleResponse(new com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response(response, retryAfter, cfRays));
        }
    }
}

