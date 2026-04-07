/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public interface FluentAuditableRestAction<T, R extends FluentAuditableRestAction<T, R>>
extends AuditableRestAction<T> {
    @Nonnull
    @CheckReturnValue
    public R reason(@Nullable String var1);

    @Nonnull
    @CheckReturnValue
    public R setCheck(@Nullable BooleanSupplier var1);

    @Nonnull
    @CheckReturnValue
    default public R addCheck(@Nonnull BooleanSupplier checks) {
        return (R)((FluentAuditableRestAction)AuditableRestAction.super.addCheck(checks));
    }

    @Nonnull
    @CheckReturnValue
    default public R timeout(long timeout2, @Nonnull TimeUnit unit) {
        return (R)((FluentAuditableRestAction)AuditableRestAction.super.timeout(timeout2, unit));
    }

    @Nonnull
    @CheckReturnValue
    default public R deadline(long timestamp) {
        return (R)((FluentAuditableRestAction)AuditableRestAction.super.deadline(timestamp));
    }
}

