/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.map.CaseInsensitiveMap;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ErrorResponseException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.RateLimitedException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestFuture;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.AttachedFile;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.CallbackContext;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.ErrorMapper;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.Requester;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import org.slf4j.Logger;

public class RestActionImpl<T>
implements RestAction<T> {
    public static final Logger LOG = JDALogger.getLog(RestAction.class);
    private static Consumer<Object> DEFAULT_SUCCESS = o -> {};
    private static Consumer<? super Throwable> DEFAULT_FAILURE = t -> {
        if (t instanceof CancellationException || t instanceof TimeoutException) {
            LOG.debug(t.getMessage());
        } else if (LOG.isDebugEnabled() || !(t instanceof ErrorResponseException)) {
            LOG.error("RestAction queue returned failure", t);
        } else if (t.getCause() != null) {
            LOG.error("RestAction queue returned failure: [{}] {}", new Object[]{t.getClass().getSimpleName(), t.getMessage(), t.getCause()});
        } else {
            LOG.error("RestAction queue returned failure: [{}] {}", (Object)t.getClass().getSimpleName(), (Object)t.getMessage());
        }
    };
    protected static boolean passContext = true;
    protected static long defaultTimeout = 0L;
    protected final JDAImpl api;
    private final Route.CompiledRoute route;
    private final RequestBody data;
    private final BiFunction<Response, Request<T>, T> handler;
    private ErrorMapper errorMapper = null;
    private boolean priority = false;
    private long deadline = 0L;
    private Object rawData;
    private BooleanSupplier checks;

    public static void setPassContext(boolean enable) {
        passContext = enable;
    }

    public static boolean isPassContext() {
        return passContext;
    }

    public static void setDefaultFailure(Consumer<? super Throwable> callback) {
        DEFAULT_FAILURE = callback == null ? t -> {} : callback;
    }

    public static void setDefaultSuccess(Consumer<Object> callback) {
        DEFAULT_SUCCESS = callback == null ? t -> {} : callback;
    }

    public static void setDefaultTimeout(long timeout2, @Nonnull TimeUnit unit) {
        Checks.notNull((Object)unit, "TimeUnit");
        defaultTimeout = unit.toMillis(timeout2);
    }

    public static long getDefaultTimeout() {
        return defaultTimeout;
    }

    public static Consumer<? super Throwable> getDefaultFailure() {
        return DEFAULT_FAILURE;
    }

    public static Consumer<Object> getDefaultSuccess() {
        return DEFAULT_SUCCESS;
    }

    public RestActionImpl(JDA api, Route.CompiledRoute route) {
        this(api, route, (RequestBody)null, null);
    }

    public RestActionImpl(JDA api, Route.CompiledRoute route, DataObject data) {
        this(api, route, data, null);
    }

    public RestActionImpl(JDA api, Route.CompiledRoute route, RequestBody data) {
        this(api, route, data, null);
    }

    public RestActionImpl(JDA api, Route.CompiledRoute route, BiFunction<Response, Request<T>, T> handler) {
        this(api, route, (RequestBody)null, handler);
    }

    public RestActionImpl(JDA api, Route.CompiledRoute route, DataObject data, BiFunction<Response, Request<T>, T> handler) {
        this(api, route, data == null ? null : RequestBody.create(Requester.MEDIA_TYPE_JSON, data.toJson()), handler);
        this.rawData = data;
    }

    public RestActionImpl(JDA api, Route.CompiledRoute route, RequestBody data, BiFunction<Response, Request<T>, T> handler) {
        Checks.notNull(api, "api");
        this.api = (JDAImpl)api;
        this.route = route;
        this.data = data;
        this.handler = handler;
    }

    public void setErrorMapper(ErrorMapper errorMapper) {
        this.errorMapper = errorMapper;
    }

    public RestActionImpl<T> priority() {
        this.priority = true;
        return this;
    }

    @Override
    @Nonnull
    public JDA getJDA() {
        return this.api;
    }

    @Override
    @Nonnull
    public RestAction<T> setCheck(BooleanSupplier checks) {
        this.checks = checks;
        return this;
    }

    @Override
    @Nullable
    public BooleanSupplier getCheck() {
        return this.checks;
    }

    @Override
    @Nonnull
    public RestAction<T> deadline(long timestamp) {
        this.deadline = timestamp;
        return this;
    }

    @Override
    public void queue(Consumer<? super T> success, Consumer<? super Throwable> failure) {
        Route.CompiledRoute route = this.finalizeRoute();
        Checks.notNull(route, "Route");
        RequestBody data = this.finalizeData();
        CaseInsensitiveMap<String, String> headers = this.finalizeHeaders();
        CheckWrapper finisher = this.getFinisher();
        if (success == null) {
            success = DEFAULT_SUCCESS;
        }
        if (failure == null) {
            failure = DEFAULT_FAILURE;
        }
        this.api.getRequester().request(new Request<T>(this, success, failure, finisher, true, data, this.rawData, this.getDeadline(), this.priority, route, headers));
    }

    @Override
    @Nonnull
    public CompletableFuture<T> submit(boolean shouldQueue) {
        Route.CompiledRoute route = this.finalizeRoute();
        Checks.notNull(route, "Route");
        RequestBody data = this.finalizeData();
        CaseInsensitiveMap<String, String> headers = this.finalizeHeaders();
        CheckWrapper finisher = this.getFinisher();
        return new RestFuture(this, shouldQueue, finisher, data, this.rawData, this.getDeadline(), this.priority, route, headers);
    }

    @Override
    public T complete(boolean shouldQueue) throws RateLimitedException {
        if (CallbackContext.isCallbackContext()) {
            throw new IllegalStateException("Preventing use of complete() in callback threads! This operation can be a deadlock cause");
        }
        try {
            return this.submit(shouldQueue).join();
        }
        catch (CompletionException e) {
            if (e.getCause() != null) {
                Throwable cause = e.getCause();
                if (cause instanceof ErrorResponseException) {
                    throw (ErrorResponseException)cause.fillInStackTrace();
                }
                if (cause instanceof RateLimitedException) {
                    throw (RateLimitedException)cause.fillInStackTrace();
                }
                if (cause instanceof RuntimeException) {
                    throw (RuntimeException)cause;
                }
                if (cause instanceof Error) {
                    throw (Error)cause;
                }
            }
            throw e;
        }
    }

    protected RequestBody finalizeData() {
        return this.data;
    }

    protected Route.CompiledRoute finalizeRoute() {
        return this.route;
    }

    protected CaseInsensitiveMap<String, String> finalizeHeaders() {
        return null;
    }

    protected BooleanSupplier finalizeChecks() {
        return null;
    }

    protected RequestBody getRequestBody(DataObject object) {
        this.rawData = object;
        return object == null ? null : RequestBody.create(Requester.MEDIA_TYPE_JSON, object.toJson());
    }

    protected RequestBody getRequestBody(DataArray array) {
        this.rawData = array;
        return array == null ? null : RequestBody.create(Requester.MEDIA_TYPE_JSON, array.toJson());
    }

    @Nonnull
    protected RequestBody getMultipartBody(@Nonnull List<? extends AttachedFile> files, @Nonnull DataObject json) {
        RequestBody payloadJson = this.getRequestBody(json);
        if (files.isEmpty()) {
            return payloadJson;
        }
        return AttachedFile.createMultipartBody(files, payloadJson).build();
    }

    private CheckWrapper getFinisher() {
        BooleanSupplier pre = this.finalizeChecks();
        BooleanSupplier wrapped = this.checks;
        return pre != null || wrapped != null ? new CheckWrapper(wrapped, pre) : CheckWrapper.EMPTY;
    }

    public void handleResponse(Response response, Request<T> request) {
        if (response.isOk()) {
            this.handleSuccess(response, request);
        } else if (response.isRateLimit()) {
            request.onRateLimited(response);
        } else {
            Throwable mappedThrowable;
            ErrorResponseException exception = request.createErrorResponseException(response);
            Throwable throwable = mappedThrowable = this.errorMapper != null ? this.errorMapper.apply(response, request, exception) : null;
            if (mappedThrowable != null) {
                request.onFailure(mappedThrowable);
            } else {
                request.onFailure(exception);
            }
        }
    }

    protected void handleSuccess(Response response, Request<T> request) {
        if (this.handler == null) {
            request.onSuccess(null);
        } else {
            request.onSuccess(this.handler.apply(response, request));
        }
    }

    private long getDeadline() {
        return this.deadline > 0L ? this.deadline : (defaultTimeout > 0L ? System.currentTimeMillis() + defaultTimeout : 0L);
    }

    protected static class CheckWrapper
    implements BooleanSupplier {
        public static final CheckWrapper EMPTY = new CheckWrapper(null, null){

            @Override
            public boolean getAsBoolean() {
                return true;
            }
        };
        protected final BooleanSupplier pre;
        protected final BooleanSupplier wrapped;

        public CheckWrapper(BooleanSupplier wrapped, BooleanSupplier pre) {
            this.pre = pre;
            this.wrapped = wrapped;
        }

        public boolean pre() {
            return this.pre == null || this.pre.getAsBoolean();
        }

        public boolean test() {
            return this.wrapped == null || this.wrapped.getAsBoolean();
        }

        @Override
        public boolean getAsBoolean() {
            return this.pre() && this.test();
        }
    }
}

