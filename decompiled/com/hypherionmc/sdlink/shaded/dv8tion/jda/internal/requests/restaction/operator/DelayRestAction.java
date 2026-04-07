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
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class DelayRestAction<T>
extends RestActionOperator<T, T> {
    private final TimeUnit unit;
    private final long delay;
    private final ScheduledExecutorService scheduler;

    public DelayRestAction(RestAction<T> action, TimeUnit unit, long delay, ScheduledExecutorService scheduler) {
        super(action);
        this.unit = unit;
        this.delay = delay;
        this.scheduler = scheduler == null ? action.getJDA().getRateLimitPool() : scheduler;
    }

    @Override
    public void queue(@Nullable Consumer<? super T> success, @Nullable Consumer<? super Throwable> failure) {
        this.handle(this.action, failure, result -> this.scheduler.schedule(() -> DelayRestAction.doSuccess(success, result), this.delay, this.unit));
    }

    @Override
    public T complete(boolean shouldQueue) throws RateLimitedException {
        Object result = this.action.complete(shouldQueue);
        try {
            this.unit.sleep(this.delay);
            return result;
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Nonnull
    public CompletableFuture<T> submit(boolean shouldQueue) {
        CompletableFuture future = new CompletableFuture();
        this.queue(future::complete, future::completeExceptionally);
        return future;
    }
}

