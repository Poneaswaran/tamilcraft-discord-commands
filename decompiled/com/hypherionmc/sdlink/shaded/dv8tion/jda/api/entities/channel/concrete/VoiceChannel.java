/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IAgeRestrictedChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.ISlowmodeChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IVoiceStatusChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IWebhookContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.StandardGuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.concrete.VoiceChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ChannelAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface VoiceChannel
extends StandardGuildChannel,
GuildMessageChannel,
AudioChannel,
IWebhookContainer,
IAgeRestrictedChannel,
ISlowmodeChannel,
IVoiceStatusChannel {
    public static final int MAX_USERLIMIT = 99;

    @Nonnull
    @CheckReturnValue
    public ChannelAction<VoiceChannel> createCopy(@Nonnull Guild var1);

    @Nonnull
    @CheckReturnValue
    default public ChannelAction<VoiceChannel> createCopy() {
        return this.createCopy(this.getGuild());
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public VoiceChannelManager getManager();
}

