/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.AttachedFile;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileUpload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.SerializableData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.AllowedMentionsData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.IOUtil;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class MessageEditData
implements MessageData,
AutoCloseable,
SerializableData {
    protected final AllowedMentionsData mentions;
    private final String content;
    private final List<MessageEmbed> embeds;
    private final List<AttachedFile> files;
    private final List<LayoutComponent> components;
    private final int messageFlags;
    private final boolean isReplace;
    private final int configuredFields;

    protected MessageEditData(int configuredFields, int messageFlags, boolean isReplace, String content, List<MessageEmbed> embeds, List<AttachedFile> files, List<LayoutComponent> components, AllowedMentionsData mentions) {
        this.content = content;
        this.embeds = Collections.unmodifiableList(embeds);
        this.files = Collections.unmodifiableList(files);
        this.components = Collections.unmodifiableList(components);
        this.mentions = mentions;
        this.messageFlags = messageFlags;
        this.isReplace = isReplace;
        this.configuredFields = configuredFields;
    }

    @Nonnull
    public static MessageEditData fromContent(@Nonnull String content) {
        return new MessageEditBuilder().setContent(content).build();
    }

    @Nonnull
    public static MessageEditData fromEmbeds(@Nonnull Collection<? extends MessageEmbed> embeds) {
        return ((MessageEditBuilder)new MessageEditBuilder().setEmbeds((Collection)embeds)).build();
    }

    @Nonnull
    public static MessageEditData fromEmbeds(MessageEmbed ... embeds) {
        return ((MessageEditBuilder)new MessageEditBuilder().setEmbeds(embeds)).build();
    }

    @Nonnull
    public static MessageEditData fromFiles(@Nonnull Collection<? extends FileUpload> files) {
        return ((MessageEditBuilder)new MessageEditBuilder().setFiles(files)).build();
    }

    @Nonnull
    public static MessageEditData fromFiles(FileUpload ... files) {
        return ((MessageEditBuilder)new MessageEditBuilder().setFiles(files)).build();
    }

    @Nonnull
    public static MessageEditData fromMessage(@Nonnull Message message) {
        return ((MessageEditBuilder)new MessageEditBuilder().applyMessage(message)).build();
    }

    @Nonnull
    public static MessageEditData fromCreateData(@Nonnull MessageCreateData data) {
        return ((MessageEditBuilder)new MessageEditBuilder().applyCreateData(data)).build();
    }

    protected boolean isReplace() {
        return this.isReplace;
    }

    protected int getConfiguredFields() {
        return this.configuredFields;
    }

    protected int getFlags() {
        return this.messageFlags;
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
    public List<AttachedFile> getAttachments() {
        return this.files;
    }

    @Override
    public boolean isSuppressEmbeds() {
        return this.isSet(Message.MessageFlag.EMBEDS_SUPPRESSED.getValue());
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
    public synchronized DataObject toData() {
        DataObject json = DataObject.empty();
        if (this.isSet(1)) {
            json.put("content", this.content);
        }
        if (this.isSet(2)) {
            json.put("embeds", DataArray.fromCollection(this.embeds));
        }
        if (this.isSet(4)) {
            json.put("components", DataArray.fromCollection(this.components));
        }
        if (this.isSet(16)) {
            json.put("allowed_mentions", this.mentions);
        }
        if (this.isSet(32)) {
            json.put("flags", this.messageFlags);
        }
        if (this.isSet(8)) {
            DataArray attachments = DataArray.empty();
            int fileUploadCount = 0;
            for (AttachedFile file : this.files) {
                attachments.add(file.toAttachmentData(fileUploadCount));
                if (!(file instanceof FileUpload)) continue;
                ++fileUploadCount;
            }
            json.put("attachments", attachments);
        }
        return json;
    }

    @Nonnull
    public synchronized List<FileUpload> getFiles() {
        return this.files.stream().filter(FileUpload.class::isInstance).map(FileUpload.class::cast).collect(Helpers.toUnmodifiableList());
    }

    @Override
    public synchronized void close() {
        this.files.forEach(IOUtil::silentClose);
    }

    protected boolean isSet(int flag) {
        return this.isReplace || (this.configuredFields & flag) != 0;
    }
}

