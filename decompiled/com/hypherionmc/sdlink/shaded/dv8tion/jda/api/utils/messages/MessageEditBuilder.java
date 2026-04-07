/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.AttachedFile;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileUpload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.AbstractMessageBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.AllowedMentionsData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditRequest;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.IOUtil;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MessageEditBuilder
extends AbstractMessageBuilder<MessageEditData, MessageEditBuilder>
implements MessageEditRequest<MessageEditBuilder> {
    protected static final int CONTENT = 1;
    protected static final int EMBEDS = 2;
    protected static final int COMPONENTS = 4;
    protected static final int ATTACHMENTS = 8;
    protected static final int MENTIONS = 16;
    protected static final int FLAGS = 32;
    private boolean replace = false;
    private int configuredFields = 0;
    private final List<AttachedFile> attachments = new ArrayList<AttachedFile>(10);

    @Nonnull
    public static MessageEditBuilder from(@Nonnull MessageEditData data) {
        return new MessageEditBuilder().applyData(data);
    }

    @Nonnull
    public static MessageEditBuilder fromCreateData(@Nonnull MessageCreateData data) {
        return (MessageEditBuilder)new MessageEditBuilder().applyCreateData(data);
    }

    @Nonnull
    public static MessageEditBuilder fromMessage(@Nonnull Message message) {
        return (MessageEditBuilder)new MessageEditBuilder().applyMessage(message);
    }

    @Override
    @Nonnull
    public MessageEditBuilder mentionRepliedUser(boolean mention) {
        super.mentionRepliedUser(mention);
        this.configuredFields |= 0x10;
        return this;
    }

    @Override
    @Nonnull
    public MessageEditBuilder setAllowedMentions(@Nullable Collection<Message.MentionType> allowedMentions) {
        super.setAllowedMentions((Collection)allowedMentions);
        this.configuredFields |= 0x10;
        return this;
    }

    @Override
    @Nonnull
    public MessageEditBuilder mention(@Nonnull Collection<? extends IMentionable> mentions) {
        super.mention((Collection)mentions);
        this.configuredFields |= 0x10;
        return this;
    }

    @Override
    @Nonnull
    public MessageEditBuilder mentionUsers(@Nonnull Collection<String> userIds) {
        super.mentionUsers((Collection)userIds);
        this.configuredFields |= 0x10;
        return this;
    }

    @Override
    @Nonnull
    public MessageEditBuilder mentionRoles(@Nonnull Collection<String> roleIds) {
        super.mentionRoles((Collection)roleIds);
        this.configuredFields |= 0x10;
        return this;
    }

    @Override
    @Nonnull
    public MessageEditBuilder setAttachments(@Nullable Collection<? extends AttachedFile> attachments) {
        this.attachments.clear();
        this.configuredFields |= 8;
        if (attachments != null) {
            this.attachments.addAll(attachments);
        }
        return this;
    }

    @Override
    @Nonnull
    public List<? extends AttachedFile> getAttachments() {
        return Collections.unmodifiableList(this.attachments);
    }

    @Override
    @Nonnull
    public MessageEditBuilder setReplace(boolean isReplace) {
        this.replace = isReplace;
        return this;
    }

    @Override
    public boolean isReplace() {
        return this.replace;
    }

    @Override
    @Nonnull
    public MessageEditBuilder setContent(@Nullable String content) {
        super.setContent(content);
        this.configuredFields |= 1;
        return this;
    }

    @Override
    @Nonnull
    public MessageEditBuilder setEmbeds(@Nonnull Collection<? extends MessageEmbed> embeds) {
        super.setEmbeds((Collection)embeds);
        this.configuredFields |= 2;
        return this;
    }

    @Override
    @Nonnull
    public MessageEditBuilder setComponents(@Nonnull Collection<? extends LayoutComponent> components) {
        super.setComponents((Collection)components);
        this.configuredFields |= 4;
        return this;
    }

    @Override
    @Nonnull
    public MessageEditBuilder setSuppressEmbeds(boolean suppress) {
        super.setSuppressEmbeds(suppress);
        this.configuredFields |= 0x20;
        return this;
    }

    @Override
    @Nonnull
    public MessageEditBuilder applyData(@Nonnull MessageEditData data) {
        Checks.notNull(data, "Data");
        this.configuredFields |= data.getConfiguredFields();
        this.replace |= data.isReplace();
        if (data.isSet(1)) {
            this.setContent(data.getContent());
        }
        if (data.isSet(2)) {
            this.setEmbeds(data.getEmbeds());
        }
        if (data.isSet(4)) {
            List layoutComponents = data.getComponents().stream().map(LayoutComponent::createCopy).collect(Collectors.toList());
            this.setComponents((Collection)layoutComponents);
        }
        if (data.isSet(8)) {
            this.setAttachments(data.getAttachments());
        }
        if (data.isSet(16)) {
            this.mentions = data.mentions.copy();
        }
        if (data.isSet(32)) {
            this.messageFlags = data.getFlags();
        }
        return this;
    }

    @Override
    public boolean isEmpty() {
        return !this.replace && this.configuredFields == 0;
    }

    @Override
    public boolean isValid() {
        if (this.isSet(2) && this.embeds.size() > 10) {
            return false;
        }
        if (this.isSet(4) && this.components.size() > 5) {
            return false;
        }
        return !this.isSet(1) || Helpers.codePointLength(this.content) <= 2000;
    }

    private boolean isSet(int flag) {
        return this.replace || (this.configuredFields & flag) != 0;
    }

    @Override
    @Nonnull
    public MessageEditData build() {
        int length;
        String content = this.content.toString().trim();
        ArrayList<MessageEmbed> embeds = new ArrayList<MessageEmbed>(this.embeds);
        ArrayList<AttachedFile> attachments = new ArrayList<AttachedFile>(this.attachments);
        ArrayList<LayoutComponent> components = new ArrayList<LayoutComponent>(this.components);
        AllowedMentionsData mentions = this.mentions.copy();
        int n = length = this.isSet(1) ? Helpers.codePointLength(content) : 0;
        if (length > 2000) {
            throw new IllegalStateException("Message content is too long! Max length is 2000 characters, provided " + length);
        }
        if (this.isSet(2) && embeds.size() > 10) {
            throw new IllegalStateException("Cannot build message with over 10 embeds, provided " + embeds.size());
        }
        if (this.isSet(4) && components.size() > 5) {
            throw new IllegalStateException("Cannot build message with over 5 component layouts, provided " + components.size());
        }
        return new MessageEditData(this.configuredFields, this.messageFlags, this.replace, content, embeds, attachments, components, mentions);
    }

    @Override
    @Nonnull
    public MessageEditBuilder clear() {
        this.configuredFields = 0;
        this.attachments.clear();
        return (MessageEditBuilder)super.clear();
    }

    @Override
    @Nonnull
    public MessageEditBuilder closeFiles() {
        this.attachments.forEach(IOUtil::silentClose);
        this.attachments.removeIf(FileUpload.class::isInstance);
        return this;
    }
}

