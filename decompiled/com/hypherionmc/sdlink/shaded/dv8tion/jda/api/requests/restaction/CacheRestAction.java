/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public interface CacheRestAction<T>
extends RestAction<T> {
    @Override
    @Nonnull
    @CheckReturnValue
    public CacheRestAction<T> setCheck(@Nullable BooleanSupplier var1);

    @Override
    @Nonnull
    @CheckReturnValue
    default public CacheRestAction<T> addCheck(@Nonnull BooleanSupplier checks) {
        return (CacheRestAction)RestAction.super.addCheck(checks);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public CacheRestAction<T> timeout(long timeout2, @Nonnull TimeUnit unit) {
        return (CacheRestAction)RestAction.super.timeout(timeout2, unit);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public CacheRestAction<T> deadline(long timestamp) {
        return (CacheRestAction)RestAction.super.deadline(timestamp);
    }

    @Nonnull
    @CheckReturnValue
    public CacheRestAction<T> useCache(boolean var1);
}

