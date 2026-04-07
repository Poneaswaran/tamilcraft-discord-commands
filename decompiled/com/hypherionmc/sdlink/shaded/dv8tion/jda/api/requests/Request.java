/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.map.CaseInsensitiveMap;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audit.ThreadLocalReason;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.ExceptionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.http.HttpRequestEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ContextException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ErrorResponseException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.RateLimitedException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.ErrorResponse;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.CallbackContext;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.IOUtil;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.MultipartBody;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeoutException;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class Request<T> {
    private final JDAImpl api;
    private final RestActionImpl<T> restAction;
    private final Consumer<? super T> onSuccess;
    private final Consumer<? super Throwable> onFailure;
    private final BooleanSupplier checks;
    private final boolean shouldQueue;
    private final Route.CompiledRoute route;
    private final RequestBody body;
    private final Object rawBody;
    private final CaseInsensitiveMap<String, String> headers;
    private final long deadline;
    private final boolean priority;
    private final String localReason;
    private boolean done = false;
    private boolean isCancelled = false;

    public Request(RestActionImpl<T> restAction, Consumer<? super T> onSuccess, Consumer<? super Throwable> onFailure, BooleanSupplier checks, boolean shouldQueue, RequestBody body, Object rawBody, long deadline, boolean priority, Route.CompiledRoute route, CaseInsensitiveMap<String, String> headers) {
        this.deadline = deadline;
        this.priority = priority;
        this.restAction = restAction;
        this.onSuccess = onSuccess;
        this.onFailure = onFailure instanceof ContextException.ContextConsumer ? onFailure : (RestActionImpl.isPassContext() ? ContextException.here(onFailure) : onFailure);
        this.checks = checks;
        this.shouldQueue = shouldQueue;
        this.body = body;
        this.rawBody = rawBody;
        this.route = route;
        this.headers = headers;
        this.api = (JDAImpl)restAction.getJDA();
        this.localReason = ThreadLocalReason.getCurrent();
    }

    private void cleanup() {
        if (this.body instanceof MultipartBody) {
            MultipartBody multi = (MultipartBody)this.body;
            multi.parts().stream().map(MultipartBody.Part::body).filter(AutoCloseable.class::isInstance).map(AutoCloseable.class::cast).forEach(IOUtil::silentClose);
        } else if (this.body instanceof AutoCloseable) {
            IOUtil.silentClose((AutoCloseable)((Object)this.body));
        }
    }

    public void onSuccess(T successObj) {
        if (this.done) {
            return;
        }
        this.done = true;
        this.cleanup();
        RestActionImpl.LOG.trace("Scheduling success callback for request with route {}/{}", (Object)this.route.getMethod(), (Object)this.route.getCompiledRoute());
        this.api.getCallbackPool().execute(() -> {
            block14: {
                try (ThreadLocalReason.Closable __ = ThreadLocalReason.closable(this.localReason);
                     CallbackContext ___ = CallbackContext.getInstance();){
                    RestActionImpl.LOG.trace("Running success callback for request with route {}/{}", (Object)this.route.getMethod(), (Object)this.route.getCompiledRoute());
                    this.onSuccess.accept(successObj);
                }
                catch (Throwable t) {
                    RestActionImpl.LOG.error("Encountered error while processing success consumer", t);
                    if (!(t instanceof Error)) break block14;
                    this.api.handleEvent(new ExceptionEvent(this.api, t, true));
                    throw (Error)t;
                }
            }
        });
    }

    public void onFailure(Response response) {
        if (response.code == 429) {
            this.onRateLimited(response);
        } else {
            this.onFailure(this.createErrorResponseException(response));
        }
    }

    public void onRateLimited(Response response) {
        this.onFailure(new RateLimitedException(this.route, response.retryAfter));
    }

    @Nonnull
    public ErrorResponseException createErrorResponseException(@Nonnull Response response) {
        return ErrorResponseException.create(ErrorResponse.fromJSON(response.optObject().orElse(null)), response);
    }

    public void onFailure(Throwable failException) {
        if (this.done) {
            return;
        }
        this.done = true;
        this.cleanup();
        RestActionImpl.LOG.trace("Scheduling failure callback for request with route {}/{}", (Object)this.route.getMethod(), (Object)this.route.getCompiledRoute());
        this.api.getCallbackPool().execute(() -> {
            block15: {
                try (ThreadLocalReason.Closable __ = ThreadLocalReason.closable(this.localReason);
                     CallbackContext ___ = CallbackContext.getInstance();){
                    RestActionImpl.LOG.trace("Running failure callback for request with route {}/{}", (Object)this.route.getMethod(), (Object)this.route.getCompiledRoute());
                    this.onFailure.accept(failException);
                    if (failException instanceof Error) {
                        this.api.handleEvent(new ExceptionEvent(this.api, failException, false));
                    }
                }
                catch (Throwable t) {
                    RestActionImpl.LOG.error("Encountered error while processing failure consumer", t);
                    if (!(t instanceof Error)) break block15;
                    this.api.handleEvent(new ExceptionEvent(this.api, t, true));
                    throw (Error)t;
                }
            }
        });
    }

    public void onCancelled() {
        this.onFailure(new CancellationException("RestAction has been cancelled"));
    }

    public void onTimeout() {
        this.onFailure(new TimeoutException("RestAction has timed out"));
    }

    @Nonnull
    public JDAImpl getJDA() {
        return this.api;
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<T> getRestAction() {
        return this.restAction;
    }

    @Nonnull
    public Consumer<? super T> getOnSuccess() {
        return this.onSuccess;
    }

    @Nonnull
    public Consumer<? super Throwable> getOnFailure() {
        return this.onFailure;
    }

    public boolean isPriority() {
        return this.priority;
    }

    public boolean isSkipped() {
        if (this.isTimeout()) {
            this.onTimeout();
            return true;
        }
        boolean skip = this.runChecks();
        if (skip) {
            this.onCancelled();
        }
        return skip;
    }

    private boolean isTimeout() {
        return this.deadline > 0L && this.deadline < System.currentTimeMillis();
    }

    private boolean runChecks() {
        try {
            return this.isCancelled() || this.checks != null && !this.checks.getAsBoolean();
        }
        catch (Exception e) {
            this.onFailure(e);
            return true;
        }
    }

    @Nullable
    public CaseInsensitiveMap<String, String> getHeaders() {
        return this.headers;
    }

    @Nonnull
    public Route.CompiledRoute getRoute() {
        return this.route;
    }

    @Nullable
    public RequestBody getBody() {
        return this.body;
    }

    @Nullable
    public Object getRawBody() {
        return this.rawBody;
    }

    public boolean shouldQueue() {
        return this.shouldQueue;
    }

    public void cancel() {
        if (!this.isCancelled) {
            this.onCancelled();
        }
        this.isCancelled = true;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void handleResponse(@Nonnull Response response) {
        RestActionImpl.LOG.trace("Handling response for request with route {}/{} and code {}", new Object[]{this.route.getMethod(), this.route.getCompiledRoute(), response.code});
        this.restAction.handleResponse(response, this);
        this.api.handleEvent(new HttpRequestEvent(this, response));
    }
}

