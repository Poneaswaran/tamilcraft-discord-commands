/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ContextException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class TriggerRestAction<T>
extends RestActionImpl<T> {
    private final ReentrantLock mutex = new ReentrantLock();
    private final List<Runnable> callbacks = new LinkedList<Runnable>();
    private volatile boolean isReady;
    private volatile Throwable exception;

    public TriggerRestAction(JDA api, Route.CompiledRoute route) {
        super(api, route);
    }

    public TriggerRestAction(JDA api, Route.CompiledRoute route, DataObject data) {
        super(api, route, data);
    }

    public TriggerRestAction(JDA api, Route.CompiledRoute route, RequestBody data) {
        super(api, route, data);
    }

    public TriggerRestAction(JDA api, Route.CompiledRoute route, BiFunction<Response, Request<T>, T> handler) {
        super(api, route, handler);
    }

    public TriggerRestAction(JDA api, Route.CompiledRoute route, DataObject data, BiFunction<Response, Request<T>, T> handler) {
        super(api, route, data, handler);
    }

    public TriggerRestAction(JDA api, Route.CompiledRoute route, RequestBody data, BiFunction<Response, Request<T>, T> handler) {
        super(api, route, data, handler);
    }

    public void run() {
        MiscUtil.locked(this.mutex, () -> {
            this.isReady = true;
            this.callbacks.forEach(Runnable::run);
        });
    }

    public void fail(Throwable throwable) {
        MiscUtil.locked(this.mutex, () -> {
            this.exception = throwable;
            this.callbacks.forEach(Runnable::run);
        });
    }

    public void onReady(Runnable callback) {
        MiscUtil.locked(this.mutex, () -> {
            if (this.isReady || this.exception != null) {
                callback.run();
            } else {
                this.callbacks.add(callback);
            }
        });
    }

    @Override
    public void queue(Consumer<? super T> success, Consumer<? super Throwable> failure) {
        if (this.isReady) {
            super.queue(success, failure);
            return;
        }
        Consumer<Throwable> onFailure = this.wrapContext(failure);
        this.onReady(() -> {
            if (this.exception != null) {
                onFailure.accept(this.exception);
            } else {
                super.queue(success, onFailure);
            }
        });
    }

    @Override
    @Nonnull
    public CompletableFuture<T> submit(boolean shouldQueue) {
        if (this.isReady) {
            return super.submit(shouldQueue);
        }
        CompletableFuture future = new CompletableFuture();
        Consumer<Throwable> onFailure = this.wrapContext(future::completeExceptionally);
        this.onReady(() -> {
            if (this.exception != null) {
                onFailure.accept(this.exception);
                return;
            }
            CompletableFuture handle = super.submit(shouldQueue);
            handle.whenComplete((success, error) -> {
                if (error != null) {
                    onFailure.accept((Throwable)error);
                } else {
                    future.complete(success);
                }
            });
            future.whenComplete((r, e) -> {
                if (future.isCancelled()) {
                    handle.cancel(false);
                }
            });
        });
        return future;
    }

    private Consumer<? super Throwable> wrapContext(Consumer<? super Throwable> failure) {
        Consumer<Throwable> consumer = failure = failure == null ? TriggerRestAction.getDefaultFailure() : failure;
        if (!TriggerRestAction.isPassContext() || failure instanceof ContextException.ContextConsumer) {
            return failure;
        }
        return ContextException.here(failure);
    }
}

