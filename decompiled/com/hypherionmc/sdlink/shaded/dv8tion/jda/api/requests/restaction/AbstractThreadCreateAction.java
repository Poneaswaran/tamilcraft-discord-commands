/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface AbstractThreadCreateAction<T, R extends AbstractThreadCreateAction<T, R>>
extends RestAction<T> {
    @Nonnull
    public Guild getGuild();

    @Nonnull
    public ChannelType getType();

    @Nonnull
    @CheckReturnValue
    public R setName(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    public R setAutoArchiveDuration(@Nonnull ThreadChannel.AutoArchiveDuration var1);
}

