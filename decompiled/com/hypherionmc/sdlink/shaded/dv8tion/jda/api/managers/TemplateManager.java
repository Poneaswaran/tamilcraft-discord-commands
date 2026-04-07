/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.Manager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface TemplateManager
extends Manager<TemplateManager> {
    public static final long NAME = 1L;
    public static final long DESCRIPTION = 2L;

    @Override
    @Nonnull
    @CheckReturnValue
    public TemplateManager reset(long var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public TemplateManager reset(long ... var1);

    @Nonnull
    @CheckReturnValue
    public TemplateManager setName(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    public TemplateManager setDescription(@Nullable String var1);
}

