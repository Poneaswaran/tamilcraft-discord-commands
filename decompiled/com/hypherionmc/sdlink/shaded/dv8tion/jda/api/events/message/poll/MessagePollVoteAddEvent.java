/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.poll;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.poll.GenericMessagePollVoteEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class MessagePollVoteAddEvent
extends GenericMessagePollVoteEvent {
    public MessagePollVoteAddEvent(@Nonnull MessageChannel channel, long responseNumber, long messageId, long userId, long answerId) {
        super(channel, responseNumber, messageId, userId, answerId);
    }
}

