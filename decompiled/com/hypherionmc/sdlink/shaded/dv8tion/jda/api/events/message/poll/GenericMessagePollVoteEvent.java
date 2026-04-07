/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.poll;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.GenericMessageEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GenericMessagePollVoteEvent
extends GenericMessageEvent {
    protected final long userId;
    protected final long messageId;
    protected final long answerId;

    public GenericMessagePollVoteEvent(@Nonnull MessageChannel channel, long responseNumber, long messageId, long userId, long answerId) {
        super(channel.getJDA(), responseNumber, messageId, channel);
        this.userId = userId;
        this.messageId = messageId;
        this.answerId = answerId;
    }

    @Nonnull
    public String getUserId() {
        return Long.toUnsignedString(this.userId);
    }

    public long getUserIdLong() {
        return this.userId;
    }

    public long getAnswerId() {
        return this.answerId;
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<User> retrieveUser() {
        return this.getJDA().retrieveUserById(this.getUserIdLong());
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<Member> retrieveMember() {
        if (!this.getChannel().getType().isGuild()) {
            throw new IllegalStateException("Cannot retrieve member for a vote that happened outside of a guild");
        }
        return this.getGuild().retrieveMemberById(this.getUserIdLong());
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<Message> retrieveMessage() {
        return this.getChannel().retrieveMessageById(this.getMessageId());
    }
}

