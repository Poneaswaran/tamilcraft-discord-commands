/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.PermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.ReactionPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination.ReactionPaginationActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Objects;

public class MessageReaction {
    private final JDA jda;
    private final EmojiUnion emoji;
    private final MessageChannel channel;
    private final long channelId;
    private final long messageId;
    private final boolean[] self;
    private final int[] counts;

    public MessageReaction(@Nonnull JDA jda, @Nullable MessageChannel channel, @Nonnull EmojiUnion emoji, long channelId, long messageId, boolean[] self, int[] counts) {
        this.jda = jda;
        this.emoji = emoji;
        this.channel = channel;
        this.channelId = channelId;
        this.messageId = messageId;
        this.self = self;
        this.counts = counts;
    }

    @Nonnull
    public JDA getJDA() {
        return this.jda;
    }

    public boolean isSelf() {
        return this.self[0] || this.self[1];
    }

    public boolean isSelf(@Nonnull ReactionType type) {
        Checks.notNull((Object)type, "Type");
        return this.self[type == ReactionType.NORMAL ? 0 : 1];
    }

    public boolean hasCount() {
        return this.counts != null;
    }

    public boolean hasChannel() {
        return this.channel != null;
    }

    public int getCount() {
        if (!this.hasCount()) {
            throw new IllegalStateException("Cannot retrieve count for this MessageReaction!");
        }
        return this.counts[0];
    }

    public int getCount(@Nonnull ReactionType type) {
        if (!this.hasCount()) {
            throw new IllegalStateException("Cannot retrieve count for this MessageReaction!");
        }
        Checks.notNull((Object)type, "Type");
        return this.counts[type == ReactionType.NORMAL ? 1 : 2];
    }

    @Nonnull
    public ChannelType getChannelType() {
        return this.channel != null ? this.channel.getType() : ChannelType.UNKNOWN;
    }

    public boolean isFromType(@Nonnull ChannelType type) {
        return this.getChannelType() == type;
    }

    @Nonnull
    public Guild getGuild() {
        return this.getGuildChannel().getGuild();
    }

    @Nonnull
    public MessageChannelUnion getChannel() {
        if (this.channel != null) {
            return (MessageChannelUnion)this.channel;
        }
        throw new IllegalStateException("Cannot provide channel instance for this reaction! Use getChannelId() instead.");
    }

    @Nonnull
    public GuildMessageChannelUnion getGuildChannel() {
        return (GuildMessageChannelUnion)this.getChannel().asGuildMessageChannel();
    }

    public long getChannelIdLong() {
        return this.channelId;
    }

    @Nonnull
    public String getChannelId() {
        return Long.toUnsignedString(this.channelId);
    }

    @Nonnull
    public EmojiUnion getEmoji() {
        return this.emoji;
    }

    @Nonnull
    public String getMessageId() {
        return Long.toUnsignedString(this.messageId);
    }

    public long getMessageIdLong() {
        return this.messageId;
    }

    @Nonnull
    @CheckReturnValue
    public ReactionPaginationAction retrieveUsers() {
        return new ReactionPaginationActionImpl(this);
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> removeReaction() {
        return this.removeReaction(this.getJDA().getSelfUser());
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> removeReaction(@Nonnull User user) {
        Checks.notNull(user, "User");
        boolean self = user.equals(this.getJDA().getSelfUser());
        if (!self && this.channel != null) {
            if (!this.channel.getType().isGuild()) {
                throw new PermissionException("Unable to remove Reaction of other user in non-guild channels!");
            }
            GuildChannel guildChannel = (GuildChannel)((Object)this.channel);
            if (!guildChannel.getGuild().getSelfMember().hasPermission(guildChannel, Permission.MESSAGE_MANAGE)) {
                throw new InsufficientPermissionException(guildChannel, Permission.MESSAGE_MANAGE);
            }
        }
        String code = this.emoji.getAsReactionCode();
        String target = self ? "@me" : user.getId();
        Route.CompiledRoute route = Route.Messages.REMOVE_REACTION.compile(this.getChannelId(), this.getMessageId(), code, target);
        return new RestActionImpl<Void>(this.getJDA(), route);
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> clearReactions() {
        if (this.channel == null) {
            Route.CompiledRoute route = Route.Messages.CLEAR_EMOJI_REACTIONS.compile(this.getChannelId(), this.getMessageId(), this.emoji.getAsReactionCode());
            return new RestActionImpl<Void>(this.jda, route);
        }
        if (!this.getChannelType().isGuild()) {
            throw new UnsupportedOperationException("Cannot clear reactions on a message sent from a private channel");
        }
        GuildMessageChannel guildChannel = Objects.requireNonNull(this.getGuildChannel());
        return guildChannel.clearReactionsById(this.getMessageId(), (Emoji)this.emoji);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MessageReaction)) {
            return false;
        }
        MessageReaction r = (MessageReaction)obj;
        return r.emoji.equals(this.emoji) && r.isSelf() == this.isSelf() && r.messageId == this.messageId;
    }

    public String toString() {
        return new EntityString(this).addMetadata("channelId", this.channelId).addMetadata("messageId", this.messageId).addMetadata("emoji", this.emoji).toString();
    }

    public static enum ReactionType {
        NORMAL,
        SUPER;

    }
}

