/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.invite;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GenericGuildEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GenericGuildInviteEvent
extends GenericGuildEvent {
    private final String code;
    private final GuildChannel channel;

    public GenericGuildInviteEvent(@Nonnull JDA api, long responseNumber, @Nonnull String code, @Nonnull GuildChannel channel) {
        super(api, responseNumber, channel.getGuild());
        this.code = code;
        this.channel = channel;
    }

    @Nonnull
    public String getCode() {
        return this.code;
    }

    @Nonnull
    public String getUrl() {
        return "https://discord.gg/" + this.code;
    }

    @Nonnull
    public GuildChannelUnion getChannel() {
        return (GuildChannelUnion)this.channel;
    }

    @Nonnull
    public ChannelType getChannelType() {
        return this.channel.getType();
    }
}

