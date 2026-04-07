/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IAgeRestrictedChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IThreadContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IWebhookContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.StandardGuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.middleman.StandardGuildMessageChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ChannelAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface StandardGuildMessageChannel
extends StandardGuildChannel,
GuildMessageChannel,
IThreadContainer,
IWebhookContainer,
IAgeRestrictedChannel {
    public static final int MAX_TOPIC_LENGTH = 1024;

    @Override
    @Nonnull
    @CheckReturnValue
    public StandardGuildMessageChannelManager<?, ?> getManager();

    @Nullable
    public String getTopic();

    @Nonnull
    @CheckReturnValue
    public ChannelAction<? extends StandardGuildMessageChannel> createCopy(@Nonnull Guild var1);

    @Nonnull
    @CheckReturnValue
    public ChannelAction<? extends StandardGuildMessageChannel> createCopy();
}

