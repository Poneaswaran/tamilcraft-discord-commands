/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public interface FluentRestAction<T, R extends FluentRestAction<T, R>>
extends RestAction<T> {
    @Nonnull
    @CheckReturnValue
    public R setCheck(@Nullable BooleanSupplier var1);

    @Nonnull
    @CheckReturnValue
    default public R addCheck(@Nonnull BooleanSupplier checks) {
        return (R)((FluentRestAction)RestAction.super.addCheck(checks));
    }

    @Nonnull
    @CheckReturnValue
    default public R timeout(long timeout2, @Nonnull TimeUnit unit) {
        return (R)((FluentRestAction)RestAction.super.timeout(timeout2, unit));
    }

    @Nonnull
    @CheckReturnValue
    default public R deadline(long timestamp) {
        return (R)((FluentRestAction)RestAction.super.deadline(timestamp));
    }
}

