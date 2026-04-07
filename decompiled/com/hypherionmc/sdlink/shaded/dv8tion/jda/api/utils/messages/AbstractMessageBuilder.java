/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.AllowedMentionsData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageRequest;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractMessageBuilder<T, R extends AbstractMessageBuilder<T, R>>
implements MessageRequest<R> {
    protected final List<MessageEmbed> embeds = new ArrayList<MessageEmbed>(10);
    protected final List<LayoutComponent> components = new ArrayList<LayoutComponent>(5);
    protected final StringBuilder content = new StringBuilder(2000);
    protected AllowedMentionsData mentions = new AllowedMentionsData();
    protected int messageFlags;

    protected AbstractMessageBuilder() {
    }

    @Override
    @Nonnull
    public R setContent(@Nullable String content) {
        if (content != null) {
            content = content.trim();
            Checks.notLonger(content, 2000, "Content");
            this.content.setLength(0);
            this.content.append(content);
        } else {
            this.content.setLength(0);
        }
        return (R)this;
    }

    @Override
    @Nonnull
    public String getContent() {
        return this.content.toString();
    }

    @Override
    @Nonnull
    public R mentionRepliedUser(boolean mention) {
        this.mentions.mentionRepliedUser(mention);
        return (R)this;
    }

    @Override
    @Nonnull
    public R setAllowedMentions(@Nullable Collection<Message.MentionType> allowedMentions) {
        this.mentions.setAllowedMentions(allowedMentions);
        return (R)this;
    }

    @Override
    @Nonnull
    public R mention(@Nonnull Collection<? extends IMentionable> mentions) {
        this.mentions.mention(mentions);
        return (R)this;
    }

    @Override
    @Nonnull
    public R mentionUsers(@Nonnull Collection<String> userIds) {
        this.mentions.mentionUsers(userIds);
        return (R)this;
    }

    @Override
    @Nonnull
    public R mentionRoles(@Nonnull Collection<String> roleIds) {
        this.mentions.mentionRoles(roleIds);
        return (R)this;
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
    public R setEmbeds(@Nonnull Collection<? extends MessageEmbed> embeds) {
        Checks.noneNull(embeds, "Embeds");
        Checks.check(embeds.size() <= 10, "Cannot send more than %d embeds in a message!", (Object)10);
        this.embeds.clear();
        this.embeds.addAll(embeds);
        return (R)this;
    }

    @Override
    @Nonnull
    public List<MessageEmbed> getEmbeds() {
        return Collections.unmodifiableList(this.embeds);
    }

    @Override
    @Nonnull
    public R setComponents(@Nonnull Collection<? extends LayoutComponent> components) {
        Checks.noneNull(components, "ComponentLayouts");
        for (LayoutComponent layoutComponent : components) {
            Checks.check(layoutComponent.isMessageCompatible(), "Provided component layout is invalid for messages!");
        }
        Checks.check(components.size() <= 5, "Cannot send more than %d component layouts in a message!", (Object)5);
        this.components.clear();
        this.components.addAll(components);
        return (R)this;
    }

    @Override
    @Nonnull
    public List<LayoutComponent> getComponents() {
        return Collections.unmodifiableList(this.components);
    }

    @Override
    @Nonnull
    public R setSuppressEmbeds(boolean suppress) {
        int flag = Message.MessageFlag.EMBEDS_SUPPRESSED.getValue();
        this.messageFlags = suppress ? (this.messageFlags |= flag) : (this.messageFlags &= ~flag);
        return (R)this;
    }

    @Override
    public boolean isSuppressEmbeds() {
        return (this.messageFlags & Message.MessageFlag.EMBEDS_SUPPRESSED.getValue()) != 0;
    }

    public long getMessageFlagsRaw() {
        return this.messageFlags;
    }

    public abstract boolean isEmpty();

    public abstract boolean isValid();

    @Nonnull
    public abstract T build();

    @Nonnull
    public R clear() {
        this.embeds.clear();
        this.components.clear();
        this.content.setLength(0);
        this.mentions.clear();
        this.messageFlags = 0;
        return (R)this;
    }

    @Nonnull
    public abstract R closeFiles();
}

