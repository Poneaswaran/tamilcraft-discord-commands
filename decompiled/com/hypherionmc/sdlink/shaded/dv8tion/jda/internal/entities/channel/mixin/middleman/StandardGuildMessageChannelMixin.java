/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.StandardGuildMessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IAgeRestrictedChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IThreadContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.ITopicChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IWebhookContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.GuildMessageChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.StandardGuildChannelMixin;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface StandardGuildMessageChannelMixin<T extends StandardGuildMessageChannelMixin<T>>
extends StandardGuildMessageChannel,
StandardGuildChannelMixin<T>,
GuildMessageChannelMixin<T>,
IThreadContainerMixin<T>,
IAgeRestrictedChannelMixin<T>,
IWebhookContainerMixin<T>,
ITopicChannelMixin<T> {
    @Override
    default public boolean canTalk(@Nonnull Member member) {
        if (!this.getGuild().equals(member.getGuild())) {
            throw new IllegalArgumentException("Provided Member is not from the Guild that this channel is part of.");
        }
        return member.hasPermission((GuildChannel)this, Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND);
    }
}

