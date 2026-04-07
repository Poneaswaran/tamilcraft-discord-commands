/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.StageInstance;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public interface StageInstanceAction
extends RestAction<StageInstance> {
    @Nonnull
    @CheckReturnValue
    public StageInstanceAction setCheck(@Nullable BooleanSupplier var1);

    @Nonnull
    @CheckReturnValue
    public StageInstanceAction timeout(long var1, @Nonnull TimeUnit var3);

    @Nonnull
    @CheckReturnValue
    public StageInstanceAction deadline(long var1);

    @Nonnull
    @CheckReturnValue
    public StageInstanceAction setTopic(@Nonnull String var1);
}

