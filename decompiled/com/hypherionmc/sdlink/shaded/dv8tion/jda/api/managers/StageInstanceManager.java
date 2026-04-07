/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.annotations.ForRemoval;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.StageInstance;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.Manager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface StageInstanceManager
extends Manager<StageInstanceManager> {
    public static final long TOPIC = 1L;
    @Deprecated
    @ForRemoval(deadline="5.3.0")
    public static final long PRIVACY_LEVEL = 2L;

    @Override
    @Nonnull
    @CheckReturnValue
    public StageInstanceManager reset(long var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public StageInstanceManager reset(long ... var1);

    @Nonnull
    public StageInstance getStageInstance();

    @Nonnull
    @CheckReturnValue
    public StageInstanceManager setTopic(@Nullable String var1);
}

