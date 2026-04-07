/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public interface MemberAction
extends RestAction<Void> {
    @Nonnull
    @CheckReturnValue
    public MemberAction setCheck(@Nullable BooleanSupplier var1);

    @Nonnull
    @CheckReturnValue
    public MemberAction timeout(long var1, @Nonnull TimeUnit var3);

    @Nonnull
    @CheckReturnValue
    public MemberAction deadline(long var1);

    @Nonnull
    public String getAccessToken();

    @Nonnull
    public String getUserId();

    @Nullable
    public User getUser();

    @Nonnull
    public Guild getGuild();

    @Nonnull
    @CheckReturnValue
    public MemberAction setNickname(@Nullable String var1);

    @Nonnull
    @CheckReturnValue
    public MemberAction setRoles(@Nullable Collection<Role> var1);

    @Nonnull
    @CheckReturnValue
    public MemberAction setRoles(Role ... var1);

    @Nonnull
    @CheckReturnValue
    public MemberAction setMute(boolean var1);

    @Nonnull
    @CheckReturnValue
    public MemberAction setDeafen(boolean var1);
}

