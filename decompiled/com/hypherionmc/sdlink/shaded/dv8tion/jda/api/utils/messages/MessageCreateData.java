/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileUpload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.SerializableData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.AllowedMentionsData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessagePollData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.IOUtil;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class MessageCreateData
implements MessageData,
AutoCloseable,
SerializableData {
    private final String content;
    private final List<MessageEmbed> embeds;
    private final List<FileUpload> files;
    private final List<LayoutComponent> components;
    private final AllowedMentionsData mentions;
    private final MessagePollData poll;
    private final boolean tts;
    private final int flags;

    protected MessageCreateData(String content, List<MessageEmbed> embeds, List<FileUpload> files, List<LayoutComponent> components, AllowedMentionsData mentions, MessagePollData poll, boolean tts, int flags) {
        this.content = content;
        this.embeds = Collections.unmodifiableList(embeds);
        this.files = Collections.unmodifiableList(files);
        this.components = Collections.unmodifiableList(components);
        this.mentions = mentions;
        this.poll = poll;
        this.tts = tts;
        this.flags = flags;
    }

    @Nonnull
    public static MessageCreateData fromContent(@Nonnull String content) {
        return ((MessageCreateBuilder)new MessageCreateBuilder().setContent(content)).build();
    }

    @Nonnull
    public static MessageCreateData fromEmbeds(@Nonnull Collection<? extends MessageEmbed> embeds) {
        return ((MessageCreateBuilder)new MessageCreateBuilder().setEmbeds((Collection)embeds)).build();
    }

    @Nonnull
    public static MessageCreateData fromEmbeds(MessageEmbed ... embeds) {
        return ((MessageCreateBuilder)new MessageCreateBuilder().setEmbeds(embeds)).build();
    }

    @Nonnull
    public static MessageCreateData fromFiles(@Nonnull Collection<? extends FileUpload> files) {
        return ((MessageCreateBuilder)new MessageCreateBuilder().setFiles((Collection)files)).build();
    }

    @Nonnull
    public static MessageCreateData fromFiles(FileUpload ... files) {
        return ((MessageCreateBuilder)new MessageCreateBuilder().setFiles(files)).build();
    }

    @Nonnull
    public static MessageCreateData fromMessage(@Nonnull Message message) {
        return ((MessageCreateBuilder)new MessageCreateBuilder().applyMessage(message)).build();
    }

    @Nonnull
    public static MessageCreateData fromEditData(@Nonnull MessageEditData data) {
        return ((MessageCreateBuilder)new MessageCreateBuilder().applyEditData(data)).build();
    }

    @Override
    @Nonnull
    public String getContent() {
        return this.content;
    }

    @Override
    @Nonnull
    public List<MessageEmbed> getEmbeds() {
        return this.embeds;
    }

    @Override
    @Nonnull
    public List<LayoutComponent> getComponents() {
        return this.components;
    }

    @Nonnull
    public List<? extends FileUpload> getAttachments() {
        return this.getFiles();
    }

    @Nullable
    public MessagePollData getPoll() {
        return this.poll;
    }

    @Override
    public boolean isSuppressEmbeds() {
        return (this.flags & Message.MessageFlag.EMBEDS_SUPPRESSED.getValue()) != 0;
    }

    public boolean isTTS() {
        return this.tts;
    }

    public boolean isSuppressedNotifications() {
        return (this.flags & Message.MessageFlag.NOTIFICATIONS_SUPPRESSED.getValue()) != 0;
    }

    public boolean isVoiceMessage() {
        return (this.flags & Message.MessageFlag.IS_VOICE_MESSAGE.getValue()) != 0;
    }

    @Override
    @Nonnull
    public Set<String> getMentionedUsers() {
        return this.mentions.getMentionedUsers();
    }

    @Override
    @Nonnull
    public Set<String> getMentionedRoles() {
        return this.mentions.getMentionedRoles();
    }

    @Override
    @Nonnull
    public EnumSet<Message.MentionType> getAllowedMentions() {
        return this.mentions.getAllowedMentions();
    }

    @Override
    public boolean isMentionRepliedUser() {
        return this.mentions.isMentionRepliedUser();
    }

    @Override
    @Nonnull
    public DataObject toData() {
        DataObject json = DataObject.empty();
        json.put("content", this.content);
        json.put("poll", this.poll);
        json.put("embeds", DataArray.fromCollection(this.embeds));
        json.put("components", DataArray.fromCollection(this.components));
        json.put("tts", this.tts);
        json.put("flags", this.flags);
        json.put("allowed_mentions", this.mentions);
        if (this.files != null && !this.files.isEmpty()) {
            DataArray attachments = DataArray.empty();
            json.put("attachments", attachments);
            for (int i = 0; i < this.files.size(); ++i) {
                attachments.add(this.files.get(i).toAttachmentData(i));
            }
        }
        return json;
    }

    @Nonnull
    public List<FileUpload> getFiles() {
        return this.files;
    }

    @Override
    public void close() {
        this.files.forEach(IOUtil::silentClose);
    }
}

