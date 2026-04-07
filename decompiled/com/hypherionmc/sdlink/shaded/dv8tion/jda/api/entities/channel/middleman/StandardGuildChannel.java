/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.ICategorizableChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.ICopyableChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IInviteContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IMemberContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPositionableChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.middleman.StandardGuildChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ChannelAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface StandardGuildChannel
extends GuildChannel,
IPermissionContainer,
IPositionableChannel,
ICopyableChannel,
IMemberContainer,
IInviteContainer,
ICategorizableChannel {
    @Override
    @Nonnull
    @CheckReturnValue
    public StandardGuildChannelManager<?, ?> getManager();

    @Nonnull
    @CheckReturnValue
    public ChannelAction<? extends StandardGuildChannel> createCopy(@Nonnull Guild var1);

    @Nonnull
    @CheckReturnValue
    public ChannelAction<? extends StandardGuildChannel> createCopy();
}

