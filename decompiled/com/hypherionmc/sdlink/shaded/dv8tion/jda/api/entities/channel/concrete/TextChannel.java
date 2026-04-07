/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.ISlowmodeChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.StandardGuildMessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.concrete.TextChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ChannelAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface TextChannel
extends StandardGuildMessageChannel,
ISlowmodeChannel {
    @Nonnull
    @CheckReturnValue
    public ChannelAction<TextChannel> createCopy(@Nonnull Guild var1);

    @Nonnull
    @CheckReturnValue
    default public ChannelAction<TextChannel> createCopy() {
        return this.createCopy(this.getGuild());
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public TextChannelManager getManager();
}

