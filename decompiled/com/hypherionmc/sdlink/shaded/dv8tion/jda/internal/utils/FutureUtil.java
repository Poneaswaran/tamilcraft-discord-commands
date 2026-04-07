/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils;

import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class FutureUtil {
    @Nonnull
    public static <T, U> CompletableFuture<U> thenApplyCancellable(@Nonnull CompletableFuture<T> future, @Nonnull Function<T, U> applyFunction, @Nullable Runnable onCancel) {
        CompletableFuture cf = new CompletableFuture();
        ((CompletableFuture)future.thenAccept(t -> cf.complete(applyFunction.apply(t)))).exceptionally(throwable -> {
            cf.completeExceptionally((Throwable)throwable);
            return null;
        });
        cf.whenComplete((u, throwable) -> {
            if (cf.isCancelled()) {
                future.cancel(true);
                if (onCancel != null) {
                    onCancel.run();
                }
            }
        });
        return cf;
    }

    @Nonnull
    public static <T, U> CompletableFuture<U> thenApplyCancellable(@Nonnull CompletableFuture<T> future, @Nonnull Function<T, U> applyFunction) {
        return FutureUtil.thenApplyCancellable(future, applyFunction, null);
    }
}

