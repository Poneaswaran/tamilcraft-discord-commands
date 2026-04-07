/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Entitlement;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.DiscordLocale;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.InteractionType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.ChannelUtil;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.List;

public interface Interaction
extends ISnowflake {
    public int getTypeRaw();

    @Nonnull
    default public InteractionType getType() {
        return InteractionType.fromKey(this.getTypeRaw());
    }

    @Nonnull
    public String getToken();

    @Nullable
    public Guild getGuild();

    default public boolean isFromGuild() {
        return this.getGuild() != null;
    }

    @Nonnull
    default public ChannelType getChannelType() {
        Channel channel = this.getChannel();
        return channel != null ? channel.getType() : ChannelType.UNKNOWN;
    }

    @Nonnull
    public User getUser();

    @Nullable
    public Member getMember();

    public boolean isAcknowledged();

    @Nullable
    public Channel getChannel();

    public long getChannelIdLong();

    @Nullable
    default public String getChannelId() {
        long id = this.getChannelIdLong();
        return id != 0L ? Long.toUnsignedString(this.getChannelIdLong()) : null;
    }

    @Nonnull
    default public GuildChannel getGuildChannel() {
        return ChannelUtil.safeChannelCast(this.getChannel(), GuildChannel.class);
    }

    @Nonnull
    default public MessageChannel getMessageChannel() {
        return ChannelUtil.safeChannelCast(this.getChannel(), MessageChannel.class);
    }

    @Nonnull
    public DiscordLocale getUserLocale();

    @Nonnull
    default public DiscordLocale getGuildLocale() {
        if (!this.isFromGuild()) {
            throw new IllegalStateException("This interaction did not happen in a guild");
        }
        return this.getGuild().getLocale();
    }

    @Nonnull
    public List<Entitlement> getEntitlements();

    @Nonnull
    public JDA getJDA();
}

