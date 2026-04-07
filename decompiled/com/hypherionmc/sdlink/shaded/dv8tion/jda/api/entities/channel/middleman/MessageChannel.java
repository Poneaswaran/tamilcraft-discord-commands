/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageHistory;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ParsingException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ActionRow;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.MessageEditAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.MessagePaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.PollVotersPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.ReactionPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.AttachedFile;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileUpload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessagePollData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ReceivedMessage;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.MessageCreateActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.MessageEditActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination.MessagePaginationActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination.PollVotersPaginationActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination.ReactionPaginationActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formattable;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Unmodifiable;

public interface MessageChannel
extends Channel,
Formattable {
    @Nonnull
    default public String getLatestMessageId() {
        return Long.toUnsignedString(this.getLatestMessageIdLong());
    }

    public long getLatestMessageIdLong();

    public boolean canTalk();

    @Nonnull
    default public List<CompletableFuture<Void>> purgeMessagesById(@Nonnull List<String> messageIds) {
        if (messageIds == null || messageIds.isEmpty()) {
            return Collections.emptyList();
        }
        long[] ids = new long[messageIds.size()];
        for (int i = 0; i < ids.length; ++i) {
            ids[i] = MiscUtil.parseSnowflake(messageIds.get(i));
        }
        return this.purgeMessagesById(ids);
    }

    @Nonnull
    default public List<CompletableFuture<Void>> purgeMessagesById(String ... messageIds) {
        if (messageIds == null || messageIds.length == 0) {
            return Collections.emptyList();
        }
        return this.purgeMessagesById(Arrays.asList(messageIds));
    }

    @Nonnull
    default public List<CompletableFuture<Void>> purgeMessages(Message ... messages) {
        if (messages == null || messages.length == 0) {
            return Collections.emptyList();
        }
        return this.purgeMessages(Arrays.asList(messages));
    }

    @Nonnull
    default public List<CompletableFuture<Void>> purgeMessages(@Nonnull List<? extends Message> messages) {
        if (messages == null || messages.isEmpty()) {
            return Collections.emptyList();
        }
        return this.purgeMessagesById(messages.stream().filter(m -> m.getType().canDelete()).mapToLong(ISnowflake::getIdLong).toArray());
    }

    @Nonnull
    default public List<CompletableFuture<Void>> purgeMessagesById(long ... messageIds) {
        if (messageIds == null || messageIds.length == 0) {
            return Collections.emptyList();
        }
        ArrayList<CompletableFuture<Void>> list = new ArrayList<CompletableFuture<Void>>(messageIds.length);
        TreeSet sortedIds = new TreeSet(Comparator.reverseOrder());
        for (long messageId : messageIds) {
            sortedIds.add(messageId);
        }
        Object object = sortedIds.iterator();
        while (object.hasNext()) {
            long messageId = (Long)object.next();
            list.add(this.deleteMessageById(messageId).submit());
        }
        return list;
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction sendMessage(@Nonnull CharSequence text) {
        Checks.notNull(text, "Content");
        return (MessageCreateAction)new MessageCreateActionImpl(this).setContent(text.toString());
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction sendMessage(@Nonnull MessageCreateData msg) {
        Checks.notNull(msg, "Message");
        return (MessageCreateAction)new MessageCreateActionImpl(this).applyData(msg);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction sendMessageFormat(@Nonnull String format, Object ... args2) {
        Checks.notEmpty(format, "Format");
        return this.sendMessage(String.format(format, args2));
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction sendMessageEmbeds(@Nonnull MessageEmbed embed, MessageEmbed ... other) {
        Checks.notNull(embed, "MessageEmbeds");
        Checks.noneNull(other, "MessageEmbeds");
        ArrayList<MessageEmbed> embeds = new ArrayList<MessageEmbed>(1 + other.length);
        embeds.add(embed);
        Collections.addAll(embeds, other);
        return (MessageCreateAction)new MessageCreateActionImpl(this).setEmbeds(embeds);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction sendMessageEmbeds(@Nonnull Collection<? extends MessageEmbed> embeds) {
        return (MessageCreateAction)new MessageCreateActionImpl(this).setEmbeds(embeds);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction sendMessageComponents(@Nonnull LayoutComponent component, LayoutComponent ... other) {
        Checks.notNull(component, "LayoutComponents");
        Checks.noneNull(other, "LayoutComponents");
        ArrayList<LayoutComponent> components = new ArrayList<LayoutComponent>(1 + other.length);
        components.add(component);
        Collections.addAll(components, other);
        return (MessageCreateAction)new MessageCreateActionImpl(this).setComponents(components);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction sendMessageComponents(@Nonnull Collection<? extends LayoutComponent> components) {
        return (MessageCreateAction)new MessageCreateActionImpl(this).setComponents(components);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction sendMessagePoll(@Nonnull MessagePollData poll) {
        Checks.notNull(poll, "Poll");
        return (MessageCreateAction)new MessageCreateActionImpl(this).setPoll(poll);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction sendFiles(@Nonnull Collection<? extends FileUpload> files) {
        Checks.notEmpty(files, "File Collection");
        Checks.noneNull(files, "Files");
        return (MessageCreateAction)new MessageCreateActionImpl(this).addFiles(files);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction sendFiles(FileUpload ... files) {
        Checks.notEmpty(files, "File Collection");
        Checks.noneNull(files, "Files");
        return this.sendFiles(Arrays.asList(files));
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Message> retrieveMessageById(@Nonnull String messageId) {
        Checks.isSnowflake(messageId, "Message ID");
        JDAImpl jda = (JDAImpl)this.getJDA();
        Route.CompiledRoute route = Route.Messages.GET_MESSAGE.compile(this.getId(), messageId);
        return new RestActionImpl<Message>((JDA)jda, route, (response, request) -> jda.getEntityBuilder().createMessageWithChannel(response.getObject(), this, false));
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Message> retrieveMessageById(long messageId) {
        return this.retrieveMessageById(Long.toUnsignedString(messageId));
    }

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Void> deleteMessageById(@Nonnull String messageId) {
        Checks.isSnowflake(messageId, "Message ID");
        Route.CompiledRoute route = Route.Messages.DELETE_MESSAGE.compile(this.getId(), messageId);
        return new AuditableRestActionImpl<Void>(this.getJDA(), route);
    }

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Void> deleteMessageById(long messageId) {
        return this.deleteMessageById(Long.toUnsignedString(messageId));
    }

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Message> endPollById(@Nonnull String messageId) {
        Checks.isSnowflake(messageId, "Message ID");
        return new AuditableRestActionImpl<Message>(this.getJDA(), Route.Messages.END_POLL.compile(this.getId(), messageId), (response, request) -> {
            JDAImpl jda = (JDAImpl)this.getJDA();
            return jda.getEntityBuilder().createMessageWithChannel(response.getObject(), this, false);
        });
    }

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Message> endPollById(long messageId) {
        return this.endPollById(Long.toUnsignedString(messageId));
    }

    @Nonnull
    @CheckReturnValue
    default public PollVotersPaginationAction retrievePollVotersById(@Nonnull String messageId, long answerId) {
        return new PollVotersPaginationActionImpl(this.getJDA(), this.getId(), messageId, answerId);
    }

    @Nonnull
    @CheckReturnValue
    default public PollVotersPaginationAction retrievePollVotersById(long messageId, long answerId) {
        return new PollVotersPaginationActionImpl(this.getJDA(), this.getId(), Long.toUnsignedString(messageId), answerId);
    }

    @Nonnull
    default public MessageHistory getHistory() {
        return new MessageHistory(this);
    }

    @Nonnull
    @CheckReturnValue
    default public MessagePaginationAction getIterableHistory() {
        return new MessagePaginationActionImpl(this);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageHistory.MessageRetrieveAction getHistoryAround(@Nonnull String messageId, int limit) {
        return MessageHistory.getHistoryAround(this, messageId).limit(limit);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageHistory.MessageRetrieveAction getHistoryAround(long messageId, int limit) {
        return this.getHistoryAround(Long.toUnsignedString(messageId), limit);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageHistory.MessageRetrieveAction getHistoryAround(@Nonnull Message message, int limit) {
        Checks.notNull(message, "Provided target message");
        return this.getHistoryAround(message.getId(), limit);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageHistory.MessageRetrieveAction getHistoryAfter(@Nonnull String messageId, int limit) {
        return MessageHistory.getHistoryAfter(this, messageId).limit(limit);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageHistory.MessageRetrieveAction getHistoryAfter(long messageId, int limit) {
        return this.getHistoryAfter(Long.toUnsignedString(messageId), limit);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageHistory.MessageRetrieveAction getHistoryAfter(@Nonnull Message message, int limit) {
        Checks.notNull(message, "Message");
        return this.getHistoryAfter(message.getId(), limit);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageHistory.MessageRetrieveAction getHistoryBefore(@Nonnull String messageId, int limit) {
        return MessageHistory.getHistoryBefore(this, messageId).limit(limit);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageHistory.MessageRetrieveAction getHistoryBefore(long messageId, int limit) {
        return this.getHistoryBefore(Long.toUnsignedString(messageId), limit);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageHistory.MessageRetrieveAction getHistoryBefore(@Nonnull Message message, int limit) {
        Checks.notNull(message, "Message");
        return this.getHistoryBefore(message.getId(), limit);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageHistory.MessageRetrieveAction getHistoryFromBeginning(int limit) {
        return MessageHistory.getHistoryFromBeginning(this).limit(limit);
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> sendTyping() {
        Route.CompiledRoute route = Route.Channels.SEND_TYPING.compile(this.getId());
        return new RestActionImpl<Void>(this.getJDA(), route);
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> addReactionById(@Nonnull String messageId, @Nonnull Emoji emoji) {
        Checks.isSnowflake(messageId, "Message ID");
        Checks.notNull(emoji, "Emoji");
        Route.CompiledRoute route = Route.Messages.ADD_REACTION.compile(this.getId(), messageId, emoji.getAsReactionCode(), "@me");
        return new RestActionImpl<Void>(this.getJDA(), route);
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> addReactionById(long messageId, @Nonnull Emoji emoji) {
        return this.addReactionById(Long.toUnsignedString(messageId), emoji);
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> removeReactionById(@Nonnull String messageId, @Nonnull Emoji emoji) {
        Checks.isSnowflake(messageId, "Message ID");
        Checks.notNull(emoji, "Emoji");
        Route.CompiledRoute route = Route.Messages.REMOVE_REACTION.compile(this.getId(), messageId, emoji.getAsReactionCode(), "@me");
        return new RestActionImpl<Void>(this.getJDA(), route);
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> removeReactionById(long messageId, @Nonnull Emoji emoji) {
        return this.removeReactionById(Long.toUnsignedString(messageId), emoji);
    }

    @Nonnull
    @CheckReturnValue
    default public ReactionPaginationAction retrieveReactionUsersById(@Nonnull String messageId, @Nonnull Emoji emoji) {
        Checks.isSnowflake(messageId, "Message ID");
        Checks.notNull(emoji, "Emoji");
        return new ReactionPaginationActionImpl(this, messageId, emoji.getAsReactionCode());
    }

    @Nonnull
    @CheckReturnValue
    default public ReactionPaginationAction retrieveReactionUsersById(long messageId, @Nonnull Emoji emoji) {
        return this.retrieveReactionUsersById(Long.toUnsignedString(messageId), emoji);
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> pinMessageById(@Nonnull String messageId) {
        Checks.isSnowflake(messageId, "Message ID");
        Route.CompiledRoute route = Route.Messages.ADD_PINNED_MESSAGE.compile(this.getId(), messageId);
        return new RestActionImpl<Void>(this.getJDA(), route);
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> pinMessageById(long messageId) {
        return this.pinMessageById(Long.toUnsignedString(messageId));
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> unpinMessageById(@Nonnull String messageId) {
        Checks.isSnowflake(messageId, "Message ID");
        Route.CompiledRoute route = Route.Messages.REMOVE_PINNED_MESSAGE.compile(this.getId(), messageId);
        return new RestActionImpl<Void>(this.getJDA(), route);
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> unpinMessageById(long messageId) {
        return this.unpinMessageById(Long.toUnsignedString(messageId));
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<@Unmodifiable List<Message>> retrievePinnedMessages() {
        JDAImpl jda = (JDAImpl)this.getJDA();
        Route.CompiledRoute route = Route.Messages.GET_PINNED_MESSAGES.compile(this.getId());
        return new RestActionImpl<List<Message>>((JDA)jda, route, (response, request) -> {
            EntityBuilder builder = jda.getEntityBuilder();
            DataArray pins = response.getArray();
            ArrayList<ReceivedMessage> pinnedMessages = new ArrayList<ReceivedMessage>(pins.length());
            for (int i = 0; i < pins.length(); ++i) {
                try {
                    pinnedMessages.add(builder.createMessageWithChannel(pins.getObject(i), this, false));
                    continue;
                }
                catch (ParsingException | NullPointerException e) {
                    JDALogger.getLog(this.getClass()).error("Failed to parse pinned message", (Throwable)e);
                }
            }
            return Collections.unmodifiableList(pinnedMessages);
        });
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageById(@Nonnull String messageId, @Nonnull CharSequence newContent) {
        Checks.isSnowflake(messageId, "Message ID");
        Checks.notEmpty(newContent, "Provided message content");
        Checks.check(newContent.length() <= 2000, "Provided newContent length must be %d or less characters.", (Object)2000);
        return (MessageEditAction)new MessageEditActionImpl(this, messageId).setContent(newContent.toString());
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageById(long messageId, @Nonnull CharSequence newContent) {
        return this.editMessageById(Long.toUnsignedString(messageId), newContent);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageById(@Nonnull String messageId, @Nonnull MessageEditData data) {
        Checks.isSnowflake(messageId, "Message ID");
        Checks.notNull(data, "message");
        return (MessageEditAction)new MessageEditActionImpl(this, messageId).applyData(data);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageById(long messageId, @Nonnull MessageEditData data) {
        return this.editMessageById(Long.toUnsignedString(messageId), data);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageFormatById(@Nonnull String messageId, @Nonnull String format, Object ... args2) {
        Checks.notBlank(format, "Format String");
        return this.editMessageById(messageId, (CharSequence)String.format(format, args2));
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageFormatById(long messageId, @Nonnull String format, Object ... args2) {
        Checks.notBlank(format, "Format String");
        return this.editMessageById(messageId, (CharSequence)String.format(format, args2));
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageEmbedsById(@Nonnull String messageId, MessageEmbed ... newEmbeds) {
        Checks.noneNull(newEmbeds, "MessageEmbeds");
        return this.editMessageEmbedsById(messageId, Arrays.asList(newEmbeds));
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageEmbedsById(long messageId, MessageEmbed ... newEmbeds) {
        return this.editMessageEmbedsById(Long.toUnsignedString(messageId), newEmbeds);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageEmbedsById(@Nonnull String messageId, @Nonnull Collection<? extends MessageEmbed> newEmbeds) {
        Checks.isSnowflake(messageId, "Message ID");
        return (MessageEditAction)new MessageEditActionImpl(this, messageId).setEmbeds(newEmbeds);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageEmbedsById(long messageId, @Nonnull Collection<? extends MessageEmbed> newEmbeds) {
        return this.editMessageEmbedsById(Long.toUnsignedString(messageId), newEmbeds);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageComponentsById(@Nonnull String messageId, @Nonnull Collection<? extends LayoutComponent> components) {
        Checks.isSnowflake(messageId, "Message ID");
        Checks.noneNull(components, "Components");
        if (components.stream().anyMatch(x -> !(x instanceof ActionRow))) {
            throw new UnsupportedOperationException("The provided component layout is not supported");
        }
        List actionRows = components.stream().map(ActionRow.class::cast).collect(Collectors.toList());
        return (MessageEditAction)new MessageEditActionImpl(this, messageId).setComponents(actionRows);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageComponentsById(long messageId, @Nonnull Collection<? extends LayoutComponent> components) {
        return this.editMessageComponentsById(Long.toUnsignedString(messageId), components);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageComponentsById(@Nonnull String messageId, LayoutComponent ... components) {
        Checks.noneNull(components, "Components");
        return this.editMessageComponentsById(messageId, Arrays.asList(components));
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageComponentsById(long messageId, LayoutComponent ... components) {
        Checks.noneNull(components, "Components");
        return this.editMessageComponentsById(messageId, Arrays.asList(components));
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageAttachmentsById(@Nonnull String messageId, @Nonnull Collection<? extends AttachedFile> attachments) {
        Checks.isSnowflake(messageId, "Message ID");
        return (MessageEditAction)new MessageEditActionImpl(this, messageId).setAttachments(attachments);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageAttachmentsById(@Nonnull String messageId, AttachedFile ... attachments) {
        Checks.noneNull(attachments, "Attachments");
        return this.editMessageAttachmentsById(messageId, Arrays.asList(attachments));
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageAttachmentsById(long messageId, @Nonnull Collection<? extends AttachedFile> attachments) {
        return this.editMessageAttachmentsById(Long.toUnsignedString(messageId), attachments);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditAction editMessageAttachmentsById(long messageId, AttachedFile ... attachments) {
        return this.editMessageAttachmentsById(Long.toUnsignedString(messageId), attachments);
    }
}

