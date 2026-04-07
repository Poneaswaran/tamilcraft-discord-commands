/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Contract
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.operator;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.RateLimitedException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.operator.RestActionOperator;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.Contract;

public class FlatMapErrorRestAction<T>
extends RestActionOperator<T, T> {
    private final Predicate<? super Throwable> check;
    private final Function<? super Throwable, ? extends RestAction<? extends T>> map;

    public FlatMapErrorRestAction(RestAction<T> action, Predicate<? super Throwable> check2, Function<? super Throwable, ? extends RestAction<? extends T>> map) {
        super(action);
        this.check = check2;
        this.map = map;
    }

    @Override
    public void queue(@Nullable Consumer<? super T> success, @Nullable Consumer<? super Throwable> failure) {
        Consumer<Throwable> contextFailure = this.contextWrap(failure);
        this.action.queue(success, this.contextWrap(error -> {
            try {
                if (this.check.test((Throwable)error)) {
                    RestAction<T> then = this.map.apply((Throwable)error);
                    if (then == null) {
                        FlatMapErrorRestAction.doFailure(failure, new IllegalStateException("FlatMapError operand is null", (Throwable)error));
                    } else {
                        then.queue(success, contextFailure);
                    }
                } else {
                    FlatMapErrorRestAction.doFailure(failure, error);
                }
            }
            catch (Throwable e) {
                FlatMapErrorRestAction.doFailure(failure, Helpers.appendCause(e, error));
            }
        }));
    }

    @Override
    public T complete(boolean shouldQueue) throws RateLimitedException {
        try {
            return this.action.complete(shouldQueue);
        }
        catch (Throwable error) {
            try {
                if (this.check.test(error)) {
                    RestAction<T> then = this.map.apply(error);
                    if (then == null) {
                        throw new IllegalStateException("FlatMapError operand is null", error);
                    }
                    return then.complete(shouldQueue);
                }
            }
            catch (Throwable e) {
                if (e instanceof IllegalStateException && e.getCause() == error) {
                    throw (IllegalStateException)e;
                }
                if (e instanceof RateLimitedException) {
                    throw (RateLimitedException)Helpers.appendCause(e, error);
                }
                this.fail(Helpers.appendCause(e, error));
            }
            this.fail(error);
            throw new AssertionError((Object)"Unreachable");
        }
    }

    @Override
    @Nonnull
    public CompletableFuture<T> submit(boolean shouldQueue) {
        return ((CompletableFuture)this.action.submit(shouldQueue).handle((result, error) -> {
            if (this.check.test((Throwable)error)) {
                return this.map.apply((Throwable)error).submit(shouldQueue).thenApply(x -> x);
            }
            return CompletableFuture.completedFuture(result);
        })).thenCompose(Function.identity());
    }

    @Contract(value="_ -> fail")
    private void fail(Throwable error) {
        if (error instanceof RuntimeException) {
            throw (RuntimeException)error;
        }
        if (error instanceof Error) {
            throw (Error)error;
        }
        throw new RuntimeException(error);
    }
}

