/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.ChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GenericChannelEvent
extends Event {
    protected final Channel channel;

    public GenericChannelEvent(@Nonnull JDA api, long responseNumber, Channel channel) {
        super(api, responseNumber);
        this.channel = channel;
    }

    public boolean isFromGuild() {
        return this.getChannelType().isGuild();
    }

    @Nonnull
    public ChannelType getChannelType() {
        return this.channel.getType();
    }

    public boolean isFromType(ChannelType type) {
        return this.getChannelType() == type;
    }

    @Nonnull
    public ChannelUnion getChannel() {
        return (ChannelUnion)this.channel;
    }

    @Nonnull
    public Guild getGuild() {
        if (!this.isFromGuild()) {
            throw new IllegalStateException("This channel event did not happen in a guild");
        }
        return ((GuildChannel)this.channel).getGuild();
    }
}

