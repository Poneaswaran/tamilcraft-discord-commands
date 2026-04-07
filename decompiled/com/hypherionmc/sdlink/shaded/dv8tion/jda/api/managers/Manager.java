/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.ManagerBase;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public interface Manager<M extends Manager<M>>
extends AuditableRestAction<Void> {
    public static void setPermissionChecksEnabled(boolean enable) {
        ManagerBase.setPermissionChecksEnabled(enable);
    }

    public static boolean isPermissionChecksEnabled() {
        return ManagerBase.isPermissionChecksEnabled();
    }

    @Nonnull
    @CheckReturnValue
    public M setCheck(BooleanSupplier var1);

    @Nonnull
    @CheckReturnValue
    public M timeout(long var1, @Nonnull TimeUnit var3);

    @Nonnull
    @CheckReturnValue
    public M deadline(long var1);

    @Nonnull
    @CheckReturnValue
    public M reset(long var1);

    @Nonnull
    @CheckReturnValue
    public M reset(long ... var1);

    @Nonnull
    @CheckReturnValue
    public M reset();
}

