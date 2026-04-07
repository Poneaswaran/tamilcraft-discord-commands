/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ActionRow;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.MessageEditCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.AttachedFile;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.interactions.MessageEditCallbackActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface IMessageEditCallback
extends IDeferrableCallback {
    @Nonnull
    @CheckReturnValue
    public MessageEditCallbackAction deferEdit();

    @Nonnull
    @CheckReturnValue
    default public MessageEditCallbackAction editMessage(@Nonnull MessageEditData message) {
        Checks.notNull(message, "Message");
        MessageEditCallbackActionImpl action = (MessageEditCallbackActionImpl)this.deferEdit();
        return (MessageEditCallbackAction)action.applyData(message);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditCallbackAction editMessage(@Nonnull String content) {
        Checks.notNull(content, "Content");
        return (MessageEditCallbackAction)this.deferEdit().setContent(content);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditCallbackAction editComponents(@Nonnull Collection<? extends LayoutComponent> components) {
        Checks.noneNull(components, "Components");
        if (components.stream().anyMatch(it -> !(it instanceof ActionRow))) {
            throw new UnsupportedOperationException("The provided component layout is not supported");
        }
        List actionRows = components.stream().map(ActionRow.class::cast).collect(Collectors.toList());
        return (MessageEditCallbackAction)this.deferEdit().setComponents(actionRows);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditCallbackAction editComponents(LayoutComponent ... components) {
        Checks.noneNull(components, "LayoutComponents");
        return this.editComponents(Arrays.asList(components));
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditCallbackAction editMessageEmbeds(@Nonnull Collection<? extends MessageEmbed> embeds) {
        Checks.noneNull(embeds, "MessageEmbed");
        return (MessageEditCallbackAction)this.deferEdit().setEmbeds(embeds);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditCallbackAction editMessageEmbeds(MessageEmbed ... embeds) {
        Checks.noneNull(embeds, "MessageEmbed");
        return (MessageEditCallbackAction)this.deferEdit().setEmbeds(embeds);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditCallbackAction editMessageFormat(@Nonnull String format, Object ... args2) {
        Checks.notNull(format, "Format String");
        return this.editMessage(String.format(format, args2));
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditCallbackAction editMessageAttachments(@Nonnull Collection<? extends AttachedFile> attachments) {
        Checks.noneNull(attachments, "Attachments");
        return (MessageEditCallbackAction)this.deferEdit().setAttachments(attachments);
    }

    @Nonnull
    @CheckReturnValue
    default public MessageEditCallbackAction editMessageAttachments(AttachedFile ... attachments) {
        Checks.noneNull(attachments, "Attachments");
        return (MessageEditCallbackAction)this.deferEdit().setAttachments(attachments);
    }
}

