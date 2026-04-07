/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Icon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.SelfUser;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.Manager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface AccountManager
extends Manager<AccountManager> {
    public static final long NAME = 1L;
    public static final long AVATAR = 2L;
    public static final long BANNER = 4L;

    @Nonnull
    public SelfUser getSelfUser();

    @Override
    @Nonnull
    @CheckReturnValue
    public AccountManager reset(long var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public AccountManager reset(long ... var1);

    @Nonnull
    @CheckReturnValue
    public AccountManager setName(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    public AccountManager setAvatar(@Nullable Icon var1);

    @Nonnull
    @CheckReturnValue
    public AccountManager setBanner(@Nullable Icon var1);
}

