/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class MessageBulkDeleteEvent
extends Event {
    protected final GuildMessageChannel channel;
    protected final List<String> messageIds;

    public MessageBulkDeleteEvent(@Nonnull JDA api, long responseNumber, @Nonnull GuildMessageChannel channel, @Nonnull List<String> messageIds) {
        super(api, responseNumber);
        this.channel = channel;
        this.messageIds = Collections.unmodifiableList(messageIds);
    }

    @Nonnull
    public GuildMessageChannelUnion getChannel() {
        return (GuildMessageChannelUnion)this.channel;
    }

    @Nonnull
    public Guild getGuild() {
        return this.channel.getGuild();
    }

    @Nonnull
    public List<String> getMessageIds() {
        return this.messageIds;
    }
}

