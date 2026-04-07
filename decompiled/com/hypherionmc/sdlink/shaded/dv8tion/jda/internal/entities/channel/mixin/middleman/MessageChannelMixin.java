/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageHistory;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.MessageEditAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.MessagePaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.ReactionPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.AttachedFile;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileUpload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.TimeUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessagePollData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.ChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;

public interface MessageChannelMixin<T extends MessageChannelMixin<T>>
extends MessageChannel,
MessageChannelUnion,
ChannelMixin<T> {
    @Override
    @Nonnull
    default public List<CompletableFuture<Void>> purgeMessages(@Nonnull List<? extends Message> messages) {
        this.checkCanAccess();
        if (messages == null || messages.isEmpty()) {
            return Collections.emptyList();
        }
        if (!this.canDeleteOtherUsersMessages()) {
            for (Message message : messages) {
                if (message.getAuthor().equals(this.getJDA().getSelfUser())) continue;
                if (this.getType() == ChannelType.PRIVATE) {
                    throw new IllegalStateException("Cannot delete messages of other users in a private channel");
                }
                throw new InsufficientPermissionException((GuildChannel)((Object)this), Permission.MESSAGE_MANAGE, "Cannot delete messages of other users");
            }
        }
        return MessageChannelUnion.super.purgeMessages(messages);
    }

    @Override
    @Nonnull
    default public List<CompletableFuture<Void>> purgeMessagesById(long ... messageIds) {
        this.checkCanAccess();
        if (messageIds == null || messageIds.length == 0) {
            return Collections.emptyList();
        }
        if (!this.canDeleteOtherUsersMessages()) {
            return MessageChannelUnion.super.purgeMessagesById(messageIds);
        }
        LinkedList<CompletableFuture<Void>> list = new LinkedList<CompletableFuture<Void>>();
        TreeSet bulk = new TreeSet(Comparator.reverseOrder());
        TreeSet norm = new TreeSet(Comparator.reverseOrder());
        long twoWeeksAgo = TimeUtil.getDiscordTimestamp(System.currentTimeMillis() - 1209600000L + 10000L);
        for (long messageId : messageIds) {
            if (messageId > twoWeeksAgo) {
                bulk.add(messageId);
                continue;
            }
            norm.add(messageId);
        }
        if (!bulk.isEmpty()) {
            ArrayList<String> toDelete = new ArrayList<String>(100);
            while (!bulk.isEmpty()) {
                toDelete.clear();
                for (int i = 0; i < 100 && !bulk.isEmpty(); ++i) {
                    toDelete.add(Long.toUnsignedString((Long)bulk.pollLast()));
                }
                if (toDelete.size() == 1) {
                    list.add(this.deleteMessageById((String)toDelete.get(0)).submit());
                    continue;
                }
                if (toDelete.isEmpty()) continue;
                list.add(this.bulkDeleteMessages(toDelete).submit());
            }
        }
        if (!norm.isEmpty()) {
            Object object = norm.iterator();
            while (object.hasNext()) {
                long message = (Long)object.next();
                list.add(this.deleteMessageById(message).submit());
            }
        }
        return list;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction sendMessage(@Nonnull CharSequence text) {
        this.checkCanSendMessage();
        return MessageChannelUnion.super.sendMessage(text);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction sendMessageEmbeds(@Nonnull MessageEmbed embed, MessageEmbed ... other) {
        this.checkCanSendMessage();
        this.checkCanSendMessageEmbeds();
        return MessageChannelUnion.super.sendMessageEmbeds(embed, other);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction sendMessageEmbeds(@Nonnull Collection<? extends MessageEmbed> embeds) {
        this.checkCanSendMessage();
        this.checkCanSendMessageEmbeds();
        return MessageChannelUnion.super.sendMessageEmbeds(embeds);
    }

    @Override
    @NotNull
    default public MessageCreateAction sendMessageComponents(@NotNull LayoutComponent component, LayoutComponent ... other) {
        this.checkCanSendMessage();
        return MessageChannelUnion.super.sendMessageComponents(component, other);
    }

    @Override
    @Nonnull
    default public MessageCreateAction sendMessageComponents(@Nonnull Collection<? extends LayoutComponent> components) {
        this.checkCanSendMessage();
        return MessageChannelUnion.super.sendMessageComponents(components);
    }

    @Override
    @Nonnull
    default public MessageCreateAction sendMessagePoll(@Nonnull MessagePollData poll) {
        this.checkCanSendMessage();
        return MessageChannelUnion.super.sendMessagePoll(poll);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction sendMessage(@Nonnull MessageCreateData msg) {
        this.checkCanSendMessage();
        return MessageChannelUnion.super.sendMessage(msg);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction sendFiles(@Nonnull Collection<? extends FileUpload> files) {
        this.checkCanSendMessage();
        this.checkCanSendFiles();
        return MessageChannelUnion.super.sendFiles(files);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public RestAction<Message> retrieveMessageById(@Nonnull String messageId) {
        this.checkCanViewHistory();
        return MessageChannelUnion.super.retrieveMessageById(messageId);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Void> deleteMessageById(@Nonnull String messageId) {
        this.checkCanAccess();
        return MessageChannelUnion.super.deleteMessageById(messageId);
    }

    @Override
    @Nonnull
    default public MessageHistory getHistory() {
        this.checkCanViewHistory();
        return MessageChannelUnion.super.getHistory();
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public MessagePaginationAction getIterableHistory() {
        this.checkCanViewHistory();
        return MessageChannelUnion.super.getIterableHistory();
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public MessageHistory.MessageRetrieveAction getHistoryAround(@Nonnull String messageId, int limit) {
        this.checkCanViewHistory();
        return MessageChannelUnion.super.getHistoryAround(messageId, limit);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public MessageHistory.MessageRetrieveAction getHistoryAfter(@Nonnull String messageId, int limit) {
        this.checkCanViewHistory();
        return MessageChannelUnion.super.getHistoryAfter(messageId, limit);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public MessageHistory.MessageRetrieveAction getHistoryBefore(@Nonnull String messageId, int limit) {
        this.checkCanViewHistory();
        return MessageChannelUnion.super.getHistoryBefore(messageId, limit);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public MessageHistory.MessageRetrieveAction getHistoryFromBeginning(int limit) {
        this.checkCanViewHistory();
        return MessageHistory.getHistoryFromBeginning(this).limit(limit);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> sendTyping() {
        this.checkCanAccess();
        return MessageChannelUnion.super.sendTyping();
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> addReactionById(@Nonnull String messageId, @Nonnull Emoji emoji) {
        this.checkCanAddReactions();
        return MessageChannelUnion.super.addReactionById(messageId, emoji);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> removeReactionById(@Nonnull String messageId, @Nonnull Emoji emoji) {
        this.checkCanRemoveReactions();
        return MessageChannelUnion.super.removeReactionById(messageId, emoji);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public ReactionPaginationAction retrieveReactionUsersById(@Nonnull String messageId, @Nonnull Emoji emoji) {
        this.checkCanRemoveReactions();
        return MessageChannelUnion.super.retrieveReactionUsersById(messageId, emoji);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> pinMessageById(@Nonnull String messageId) {
        this.checkCanControlMessagePins();
        return MessageChannelUnion.super.pinMessageById(messageId);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> unpinMessageById(@Nonnull String messageId) {
        this.checkCanControlMessagePins();
        return MessageChannelUnion.super.unpinMessageById(messageId);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public RestAction<List<Message>> retrievePinnedMessages() {
        this.checkCanAccess();
        return MessageChannelUnion.super.retrievePinnedMessages();
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageById(@Nonnull String messageId, @Nonnull CharSequence newContent) {
        this.checkCanSendMessage();
        return MessageChannelUnion.super.editMessageById(messageId, newContent);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageById(@Nonnull String messageId, @Nonnull MessageEditData data) {
        this.checkCanSendMessage();
        return MessageChannelUnion.super.editMessageById(messageId, data);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageEmbedsById(@Nonnull String messageId, @Nonnull Collection<? extends MessageEmbed> newEmbeds) {
        this.checkCanSendMessage();
        this.checkCanSendMessageEmbeds();
        return MessageChannelUnion.super.editMessageEmbedsById(messageId, newEmbeds);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageComponentsById(@Nonnull String messageId, @Nonnull Collection<? extends LayoutComponent> components) {
        this.checkCanSendMessage();
        return MessageChannelUnion.super.editMessageComponentsById(messageId, components);
    }

    @Override
    @Nonnull
    default public MessageEditAction editMessageAttachmentsById(@Nonnull String messageId, @Nonnull Collection<? extends AttachedFile> attachments) {
        this.checkCanSendMessage();
        return MessageChannelUnion.super.editMessageAttachmentsById(messageId, attachments);
    }

    public T setLatestMessageIdLong(long var1);

    public void checkCanSendMessage();

    public void checkCanSendMessageEmbeds();

    public void checkCanSendFiles();

    public void checkCanViewHistory();

    public void checkCanAddReactions();

    public void checkCanRemoveReactions();

    public void checkCanControlMessagePins();

    public boolean canDeleteOtherUsersMessages();

    default public RestActionImpl<Void> bulkDeleteMessages(Collection<String> messageIds) {
        DataObject body = DataObject.empty().put("messages", messageIds);
        Route.CompiledRoute route = Route.Messages.DELETE_MESSAGES.compile(this.getId());
        return new RestActionImpl<Void>(this.getJDA(), route, body);
    }
}

