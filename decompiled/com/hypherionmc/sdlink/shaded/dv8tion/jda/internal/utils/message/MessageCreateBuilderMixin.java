/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.message;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileUpload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateRequest;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessagePollData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.message.AbstractMessageBuilderMixin;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public interface MessageCreateBuilderMixin<R extends MessageCreateRequest<R>>
extends AbstractMessageBuilderMixin<R, MessageCreateBuilder>,
MessageCreateRequest<R> {
    @Override
    @Nonnull
    default public R addContent(@Nonnull String content) {
        ((MessageCreateBuilder)this.getBuilder()).addContent(content);
        return (R)this;
    }

    @Override
    @Nonnull
    default public R addEmbeds(@Nonnull Collection<? extends MessageEmbed> embeds) {
        ((MessageCreateBuilder)this.getBuilder()).addEmbeds((Collection)embeds);
        return (R)this;
    }

    @Override
    @Nonnull
    default public R addComponents(@Nonnull Collection<? extends LayoutComponent> components) {
        ((MessageCreateBuilder)this.getBuilder()).addComponents((Collection)components);
        return (R)this;
    }

    @Override
    @Nonnull
    default public R addFiles(@Nonnull Collection<? extends FileUpload> files) {
        ((MessageCreateBuilder)this.getBuilder()).addFiles((Collection)files);
        return (R)this;
    }

    @Override
    @Nullable
    default public MessagePollData getPoll() {
        return ((MessageCreateBuilder)this.getBuilder()).getPoll();
    }

    @Override
    @Nonnull
    default public R setPoll(@Nullable MessagePollData poll) {
        ((MessageCreateBuilder)this.getBuilder()).setPoll(poll);
        return (R)this;
    }

    @Override
    @Nonnull
    default public R setTTS(boolean tts) {
        ((MessageCreateBuilder)this.getBuilder()).setTTS(tts);
        return (R)this;
    }

    @Override
    @Nonnull
    default public R setFiles(@Nullable Collection<? extends FileUpload> files) {
        ((MessageCreateBuilder)this.getBuilder()).setFiles((Collection)files);
        return (R)this;
    }

    @Override
    @Nonnull
    default public List<FileUpload> getAttachments() {
        return ((MessageCreateBuilder)this.getBuilder()).getAttachments();
    }

    @Override
    @Nonnull
    default public R setSuppressedNotifications(boolean suppressed) {
        ((MessageCreateBuilder)this.getBuilder()).setSuppressedNotifications(suppressed);
        return (R)this;
    }

    @Override
    @Nonnull
    default public R setVoiceMessage(boolean voiceMessage) {
        ((MessageCreateBuilder)this.getBuilder()).setVoiceMessage(voiceMessage);
        return (R)this;
    }
}

