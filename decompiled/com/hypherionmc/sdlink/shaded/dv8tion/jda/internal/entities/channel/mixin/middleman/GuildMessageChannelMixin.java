/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.TimeUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.GuildChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.MessageChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.MessageCreateActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collection;

public interface GuildMessageChannelMixin<T extends GuildMessageChannelMixin<T>>
extends GuildMessageChannel,
GuildMessageChannelUnion,
GuildChannelMixin<T>,
MessageChannelMixin<T> {
    @Override
    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> deleteMessagesByIds(@Nonnull Collection<String> messageIds) {
        this.checkCanAccess();
        this.checkPermission(Permission.MESSAGE_MANAGE, "Must have MESSAGE_MANAGE in order to bulk delete messages in this channel regardless of author.");
        if (messageIds.size() < 2 || messageIds.size() > 100) {
            throw new IllegalArgumentException("Must provide at least 2 or at most 100 messages to be deleted.");
        }
        long twoWeeksAgo = TimeUtil.getDiscordTimestamp(System.currentTimeMillis() - 1209600000L);
        for (String id : messageIds) {
            Checks.check(MiscUtil.parseSnowflake(id) > twoWeeksAgo, "Message Id provided was older than 2 weeks. Id: " + id);
        }
        return this.bulkDeleteMessages(messageIds);
    }

    @Override
    @Nonnull
    default public RestAction<Void> removeReactionById(@Nonnull String messageId, @Nonnull Emoji emoji, @Nonnull User user) {
        Checks.isSnowflake(messageId, "Message ID");
        Checks.notNull(emoji, "Emoji");
        Checks.notNull(user, "User");
        this.checkCanAccess();
        if (!this.getJDA().getSelfUser().equals(user)) {
            this.checkPermission(Permission.MESSAGE_MANAGE);
        }
        String targetUser = user.equals(this.getJDA().getSelfUser()) ? "@me" : user.getId();
        Route.CompiledRoute route = Route.Messages.REMOVE_REACTION.compile(this.getId(), messageId, emoji.getAsReactionCode(), targetUser);
        return new RestActionImpl<Void>(this.getJDA(), route);
    }

    @Override
    @Nonnull
    default public RestAction<Void> clearReactionsById(@Nonnull String messageId) {
        Checks.isSnowflake(messageId, "Message ID");
        this.checkCanAccess();
        this.checkPermission(Permission.MESSAGE_MANAGE);
        Route.CompiledRoute route = Route.Messages.REMOVE_ALL_REACTIONS.compile(this.getId(), messageId);
        return new RestActionImpl<Void>(this.getJDA(), route);
    }

    @Override
    @Nonnull
    default public RestAction<Void> clearReactionsById(@Nonnull String messageId, @Nonnull Emoji emoji) {
        Checks.notNull(messageId, "Message ID");
        Checks.notNull(emoji, "Emoji");
        this.checkCanAccess();
        this.checkPermission(Permission.MESSAGE_MANAGE);
        Route.CompiledRoute route = Route.Messages.CLEAR_EMOJI_REACTIONS.compile(this.getId(), messageId, emoji.getAsReactionCode());
        return new RestActionImpl<Void>(this.getJDA(), route);
    }

    @Override
    @Nonnull
    default public MessageCreateAction sendStickers(@Nonnull Collection<? extends StickerSnowflake> stickers) {
        this.checkCanSendMessage();
        Checks.notEmpty(stickers, "Stickers");
        Checks.noneNull(stickers, "Stickers");
        return new MessageCreateActionImpl(this).setStickers(stickers);
    }

    @Override
    default public void checkCanSendMessage() {
        this.checkCanAccess();
        if (this.getType().isThread()) {
            this.checkPermission(Permission.MESSAGE_SEND_IN_THREADS);
        } else {
            this.checkPermission(Permission.MESSAGE_SEND);
        }
    }

    @Override
    default public void checkCanSendMessageEmbeds() {
        this.checkCanAccess();
        this.checkPermission(Permission.MESSAGE_EMBED_LINKS);
    }

    @Override
    default public void checkCanSendFiles() {
        this.checkCanAccess();
        this.checkPermission(Permission.MESSAGE_ATTACH_FILES);
    }

    @Override
    default public void checkCanViewHistory() {
        this.checkCanAccess();
        this.checkPermission(Permission.MESSAGE_HISTORY);
    }

    @Override
    default public void checkCanAddReactions() {
        this.checkCanAccess();
        this.checkPermission(Permission.MESSAGE_ADD_REACTION);
        this.checkPermission(Permission.MESSAGE_HISTORY, "You need MESSAGE_HISTORY to add reactions to a message");
    }

    @Override
    default public void checkCanRemoveReactions() {
        this.checkCanAccess();
        this.checkPermission(Permission.MESSAGE_HISTORY, "You need MESSAGE_HISTORY to remove reactions from a message");
    }

    @Override
    default public void checkCanControlMessagePins() {
        this.checkCanAccess();
        this.checkPermission(Permission.MESSAGE_MANAGE, "You need MESSAGE_MANAGE to pin or unpin messages.");
    }

    @Override
    default public boolean canDeleteOtherUsersMessages() {
        return this.hasPermission(Permission.MESSAGE_MANAGE);
    }
}

