/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.WebhookClient;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.Interaction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookMessageEditAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.AttachedFile;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.InteractionHookImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;

public interface InteractionHook
extends WebhookClient<Message> {
    @Nonnull
    public Interaction getInteraction();

    public long getExpirationTimestamp();

    default public boolean isExpired() {
        return System.currentTimeMillis() > this.getExpirationTimestamp();
    }

    @Nonnull
    public InteractionHook setEphemeral(boolean var1);

    @Override
    @Nonnull
    public JDA getJDA();

    @Nonnull
    @CheckReturnValue
    default public RestAction<Message> retrieveOriginal() {
        return this.retrieveMessageById("@original");
    }

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<Message> editOriginal(@Nonnull String content) {
        return this.editMessageById("@original", content);
    }

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<Message> editOriginalComponents(@Nonnull Collection<? extends LayoutComponent> components) {
        return this.editMessageComponentsById("@original", components);
    }

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<Message> editOriginalComponents(LayoutComponent ... components) {
        return this.editMessageComponentsById("@original", components);
    }

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<Message> editOriginalEmbeds(@Nonnull Collection<? extends MessageEmbed> embeds) {
        return this.editMessageEmbedsById("@original", embeds);
    }

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<Message> editOriginalEmbeds(MessageEmbed ... embeds) {
        return this.editMessageEmbedsById("@original", embeds);
    }

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<Message> editOriginal(@Nonnull MessageEditData message) {
        return this.editMessageById("@original", message);
    }

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<Message> editOriginalFormat(@Nonnull String format, Object ... args2) {
        Checks.notNull(format, "Format String");
        return this.editOriginal(String.format(format, args2));
    }

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<Message> editOriginalAttachments(@Nonnull Collection<? extends AttachedFile> attachments) {
        return this.editMessageAttachmentsById("@original", attachments);
    }

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<Message> editOriginalAttachments(AttachedFile ... attachments) {
        Checks.noneNull(attachments, "Attachments");
        return this.editOriginalAttachments(Arrays.asList(attachments));
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> deleteOriginal() {
        return this.deleteMessageById("@original");
    }

    @Nonnull
    public static InteractionHook from(@Nonnull JDA jda, @Nonnull String token) {
        Checks.notNull(jda, "JDA");
        Checks.notBlank(token, "Token");
        return new InteractionHookImpl(jda, token);
    }
}

