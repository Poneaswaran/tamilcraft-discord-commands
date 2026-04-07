/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.react;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageReaction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.GenericMessageEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.CompletedRestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class GenericMessageReactionEvent
extends GenericMessageEvent {
    protected final long userId;
    protected User issuer;
    protected Member member;
    protected MessageReaction reaction;

    public GenericMessageReactionEvent(@Nonnull JDA api, long responseNumber, @Nullable User user, @Nullable Member member, @Nonnull MessageReaction reaction, long userId) {
        super(api, responseNumber, reaction.getMessageIdLong(), reaction.getChannel());
        this.userId = userId;
        this.issuer = user;
        this.member = member;
        this.reaction = reaction;
    }

    @Nonnull
    public String getUserId() {
        return Long.toUnsignedString(this.userId);
    }

    public long getUserIdLong() {
        return this.userId;
    }

    @Nullable
    public User getUser() {
        return this.issuer == null && this.isFromType(ChannelType.PRIVATE) ? this.getChannel().asPrivateChannel().getUser() : this.issuer;
    }

    @Nullable
    public Member getMember() {
        return this.member;
    }

    @Nonnull
    public MessageReaction getReaction() {
        return this.reaction;
    }

    @Nonnull
    public EmojiUnion getEmoji() {
        return this.reaction.getEmoji();
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<User> retrieveUser() {
        User user = this.getUser();
        if (user != null) {
            return new CompletedRestAction<User>(this.getJDA(), user);
        }
        return this.getJDA().retrieveUserById(this.getUserIdLong());
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<Member> retrieveMember() {
        if (this.member != null) {
            return new CompletedRestAction<Member>(this.getJDA(), this.member);
        }
        if (!this.getChannel().getType().isGuild()) {
            throw new IllegalStateException("Cannot retrieve member for a private reaction not from a guild");
        }
        return this.getGuild().retrieveMemberById(this.getUserIdLong());
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<Message> retrieveMessage() {
        return this.getChannel().retrieveMessageById(this.getMessageId());
    }
}

