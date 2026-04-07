/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.message;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.AttachedFile;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileUpload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.AbstractMessageBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageRequest;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public interface AbstractMessageBuilderMixin<R extends MessageRequest<R>, B extends AbstractMessageBuilder<?, B>>
extends MessageRequest<R> {
    public B getBuilder();

    @Override
    @Nonnull
    default public R setContent(@Nullable String content) {
        ((AbstractMessageBuilder)this.getBuilder()).setContent(content);
        return (R)this;
    }

    @Override
    @Nonnull
    default public String getContent() {
        return ((AbstractMessageBuilder)this.getBuilder()).getContent();
    }

    @Override
    @Nonnull
    default public R setEmbeds(@Nonnull Collection<? extends MessageEmbed> embeds) {
        ((AbstractMessageBuilder)this.getBuilder()).setEmbeds((Collection)embeds);
        return (R)this;
    }

    @Override
    @Nonnull
    default public List<MessageEmbed> getEmbeds() {
        return ((AbstractMessageBuilder)this.getBuilder()).getEmbeds();
    }

    @Override
    @Nonnull
    default public R setComponents(@Nonnull Collection<? extends LayoutComponent> components) {
        ((AbstractMessageBuilder)this.getBuilder()).setComponents((Collection)components);
        return (R)this;
    }

    @Override
    @Nonnull
    default public List<LayoutComponent> getComponents() {
        return ((AbstractMessageBuilder)this.getBuilder()).getComponents();
    }

    @Override
    @Nonnull
    default public R setSuppressEmbeds(boolean suppress) {
        ((AbstractMessageBuilder)this.getBuilder()).setSuppressEmbeds(suppress);
        return (R)this;
    }

    @Override
    default public boolean isSuppressEmbeds() {
        return ((AbstractMessageBuilder)this.getBuilder()).isSuppressEmbeds();
    }

    @Override
    @Nonnull
    default public R setFiles(@Nullable Collection<? extends FileUpload> files) {
        this.getBuilder().setFiles(files);
        return (R)this;
    }

    @Override
    @Nonnull
    default public List<? extends AttachedFile> getAttachments() {
        return this.getBuilder().getAttachments();
    }

    @Override
    @Nonnull
    default public R mentionRepliedUser(boolean mention) {
        ((AbstractMessageBuilder)this.getBuilder()).mentionRepliedUser(mention);
        return (R)this;
    }

    @Override
    @Nonnull
    default public R setAllowedMentions(@Nullable Collection<Message.MentionType> allowedMentions) {
        ((AbstractMessageBuilder)this.getBuilder()).setAllowedMentions((Collection)allowedMentions);
        return (R)this;
    }

    @Override
    @Nonnull
    default public R mention(@Nonnull Collection<? extends IMentionable> mentions) {
        ((AbstractMessageBuilder)this.getBuilder()).mention((Collection)mentions);
        return (R)this;
    }

    @Override
    @Nonnull
    default public R mentionUsers(@Nonnull Collection<String> userIds) {
        ((AbstractMessageBuilder)this.getBuilder()).mentionUsers((Collection)userIds);
        return (R)this;
    }

    @Override
    @Nonnull
    default public R mentionRoles(@Nonnull Collection<String> roleIds) {
        ((AbstractMessageBuilder)this.getBuilder()).mentionRoles((Collection)roleIds);
        return (R)this;
    }

    @Override
    @Nonnull
    default public Set<String> getMentionedUsers() {
        return ((AbstractMessageBuilder)this.getBuilder()).getMentionedUsers();
    }

    @Override
    @Nonnull
    default public Set<String> getMentionedRoles() {
        return ((AbstractMessageBuilder)this.getBuilder()).getMentionedRoles();
    }

    @Override
    @Nonnull
    default public EnumSet<Message.MentionType> getAllowedMentions() {
        return ((AbstractMessageBuilder)this.getBuilder()).getAllowedMentions();
    }

    @Override
    default public boolean isMentionRepliedUser() {
        return ((AbstractMessageBuilder)this.getBuilder()).isMentionRepliedUser();
    }
}

