/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.GenericUserEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.OffsetDateTime;

public class UserTypingEvent
extends GenericUserEvent {
    private final Member member;
    private final MessageChannel channel;
    private final OffsetDateTime timestamp;

    public UserTypingEvent(@Nonnull JDA api, long responseNumber, @Nonnull User user, @Nonnull MessageChannel channel, @Nonnull OffsetDateTime timestamp, @Nullable Member member) {
        super(api, responseNumber, user);
        this.member = member;
        this.channel = channel;
        this.timestamp = timestamp;
    }

    @Nonnull
    public OffsetDateTime getTimestamp() {
        return this.timestamp;
    }

    @Nonnull
    public MessageChannelUnion getChannel() {
        return (MessageChannelUnion)this.channel;
    }

    public boolean isFromType(@Nonnull ChannelType type) {
        return this.channel.getType() == type;
    }

    @Nonnull
    public ChannelType getType() {
        return this.channel.getType();
    }

    @Nullable
    public Guild getGuild() {
        return this.getType().isGuild() ? this.member.getGuild() : null;
    }

    @Nullable
    public Member getMember() {
        return this.member;
    }
}

