/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileUpload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.AbstractMessageBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.AllowedMentionsData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateRequest;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessagePollData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.IOUtil;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class MessageCreateBuilder
extends AbstractMessageBuilder<MessageCreateData, MessageCreateBuilder>
implements MessageCreateRequest<MessageCreateBuilder> {
    private final List<FileUpload> files = new ArrayList<FileUpload>(10);
    private MessagePollData poll;
    private boolean tts;

    @Nonnull
    public static MessageCreateBuilder from(@Nonnull MessageCreateData data) {
        return (MessageCreateBuilder)new MessageCreateBuilder().applyData(data);
    }

    @Nonnull
    public static MessageCreateBuilder fromEditData(@Nonnull MessageEditData data) {
        return (MessageCreateBuilder)new MessageCreateBuilder().applyEditData(data);
    }

    @Nonnull
    public static MessageCreateBuilder fromMessage(@Nonnull Message message) {
        return (MessageCreateBuilder)new MessageCreateBuilder().applyMessage(message);
    }

    @Override
    @Nonnull
    public MessageCreateBuilder addContent(@Nonnull String content) {
        Checks.notNull(content, "Content");
        Checks.check(Helpers.codePointLength(this.content) + Helpers.codePointLength(content) <= 2000, "Cannot have content longer than %d characters", (Object)2000);
        this.content.append(content);
        return this;
    }

    @Override
    @Nonnull
    public MessageCreateBuilder addEmbeds(@Nonnull Collection<? extends MessageEmbed> embeds) {
        Checks.noneNull(embeds, "Embeds");
        Checks.check(this.embeds.size() + embeds.size() <= 10, "Cannot add more than %d embeds", (Object)10);
        this.embeds.addAll(embeds);
        return this;
    }

    @Override
    @Nonnull
    public MessageCreateBuilder addComponents(@Nonnull Collection<? extends LayoutComponent> components) {
        Checks.noneNull(components, "ComponentLayouts");
        for (LayoutComponent layoutComponent : components) {
            Checks.check(layoutComponent.isMessageCompatible(), "Provided component layout is invalid for messages!");
        }
        Checks.check(this.components.size() + components.size() <= 5, "Cannot add more than %d component layouts", (Object)5);
        this.components.addAll(components);
        return this;
    }

    @Override
    @Nonnull
    public MessageCreateBuilder setFiles(@Nullable Collection<? extends FileUpload> files) {
        if (files != null) {
            Checks.noneNull(files, "Files");
        }
        this.files.clear();
        if (files != null) {
            this.files.addAll(files);
            this.setVoiceMessageIfApplicable(files);
        }
        return this;
    }

    @Override
    @Nonnull
    public List<FileUpload> getAttachments() {
        return Collections.unmodifiableList(this.files);
    }

    @Override
    @Nullable
    public MessagePollData getPoll() {
        return this.poll;
    }

    @Override
    @Nonnull
    public MessageCreateBuilder setPoll(@Nullable MessagePollData poll) {
        this.poll = poll;
        return this;
    }

    @Override
    @Nonnull
    public MessageCreateBuilder addFiles(@Nonnull Collection<? extends FileUpload> files) {
        Checks.noneNull(files, "Files");
        this.files.addAll(files);
        this.setVoiceMessageIfApplicable(files);
        return this;
    }

    @Override
    @Nonnull
    public MessageCreateBuilder setTTS(boolean tts) {
        this.tts = tts;
        return this;
    }

    @Override
    @Nonnull
    public MessageCreateBuilder setSuppressedNotifications(boolean suppressed) {
        this.messageFlags = suppressed ? (this.messageFlags |= Message.MessageFlag.NOTIFICATIONS_SUPPRESSED.getValue()) : (this.messageFlags &= ~Message.MessageFlag.NOTIFICATIONS_SUPPRESSED.getValue());
        return this;
    }

    @Override
    @Nonnull
    public MessageCreateBuilder setVoiceMessage(boolean voiceMessage) {
        this.messageFlags = voiceMessage ? (this.messageFlags |= Message.MessageFlag.IS_VOICE_MESSAGE.getValue()) : (this.messageFlags &= ~Message.MessageFlag.IS_VOICE_MESSAGE.getValue());
        return this;
    }

    @Override
    public boolean isEmpty() {
        return Helpers.isBlank(this.content) && this.embeds.isEmpty() && this.files.isEmpty() && this.components.isEmpty() && this.poll == null;
    }

    @Override
    public boolean isValid() {
        return !this.isEmpty() && this.embeds.size() <= 10 && this.components.size() <= 5 && Helpers.codePointLength(this.content) <= 2000;
    }

    @Override
    @Nonnull
    public MessageCreateData build() {
        String content = this.content.toString().trim();
        ArrayList<MessageEmbed> embeds = new ArrayList<MessageEmbed>(this.embeds);
        ArrayList<FileUpload> files = new ArrayList<FileUpload>(this.files);
        ArrayList<LayoutComponent> components = new ArrayList<LayoutComponent>(this.components);
        AllowedMentionsData mentions = this.mentions.copy();
        if (content.isEmpty() && embeds.isEmpty() && files.isEmpty() && components.isEmpty() && this.poll == null) {
            throw new IllegalStateException("Cannot build an empty message. You need at least one of content, embeds, components, poll, or files");
        }
        int length = Helpers.codePointLength(content);
        if (length > 2000) {
            throw new IllegalStateException("Message content is too long! Max length is 2000 characters, provided " + length);
        }
        if (embeds.size() > 10) {
            throw new IllegalStateException("Cannot build message with over 10 embeds, provided " + embeds.size());
        }
        if (components.size() > 5) {
            throw new IllegalStateException("Cannot build message with over 5 component layouts, provided " + components.size());
        }
        return new MessageCreateData(content, embeds, files, components, mentions, this.poll, this.tts, this.messageFlags);
    }

    @Override
    @Nonnull
    public MessageCreateBuilder clear() {
        super.clear();
        this.files.clear();
        this.tts = false;
        return this;
    }

    @Override
    @Nonnull
    public MessageCreateBuilder closeFiles() {
        this.files.forEach(IOUtil::silentClose);
        this.files.clear();
        return this;
    }

    private void setVoiceMessageIfApplicable(@NotNull Collection<? extends FileUpload> files) {
        if (files.stream().anyMatch(FileUpload::isVoiceMessage)) {
            this.setVoiceMessage(true);
        }
    }
}

