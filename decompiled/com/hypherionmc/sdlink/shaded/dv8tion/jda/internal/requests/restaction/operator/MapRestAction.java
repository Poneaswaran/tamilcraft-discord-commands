/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.operator;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.RateLimitedException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.operator.RestActionOperator;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public class MapRestAction<I, O>
extends RestActionOperator<I, O> {
    private final Function<? super I, ? extends O> function;

    public MapRestAction(RestAction<I> action, Function<? super I, ? extends O> function) {
        super(action);
        this.function = function;
    }

    @Override
    public void queue(@Nullable Consumer<? super O> success, @Nullable Consumer<? super Throwable> failure) {
        this.handle(this.action, failure, result -> MapRestAction.doSuccess(success, this.function.apply(result)));
    }

    @Override
    public O complete(boolean shouldQueue) throws RateLimitedException {
        return this.function.apply(this.action.complete(shouldQueue));
    }

    @Override
    @Nonnull
    public CompletableFuture<O> submit(boolean shouldQueue) {
        return this.action.submit(shouldQueue).thenApply(this.function);
    }
}

