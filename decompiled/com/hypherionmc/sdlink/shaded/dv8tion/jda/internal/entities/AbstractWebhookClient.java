/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.WebhookClient;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ActionRow;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookMessageDeleteAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookMessageEditAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.AttachedFile;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileUpload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessagePollData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.WebhookMessageCreateActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.WebhookMessageDeleteActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.WebhookMessageEditActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractWebhookClient<T>
implements WebhookClient<T> {
    protected final long id;
    protected final JDA api;
    protected String token;

    protected AbstractWebhookClient(long webhookId, String webhookToken, JDA api) {
        this.id = webhookId;
        this.token = webhookToken;
        this.api = api;
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    @Nonnull
    public JDA getJDA() {
        return this.api;
    }

    public abstract WebhookMessageCreateActionImpl<T> sendRequest();

    public abstract WebhookMessageEditActionImpl<T> editRequest(String var1);

    @Override
    @Nonnull
    public WebhookMessageCreateAction<T> sendMessage(@Nonnull String content) {
        return (WebhookMessageCreateAction)this.sendRequest().setContent(content);
    }

    @Override
    @Nonnull
    public WebhookMessageCreateAction<T> sendMessageEmbeds(@Nonnull Collection<? extends MessageEmbed> embeds) {
        return (WebhookMessageCreateAction)this.sendRequest().addEmbeds(embeds);
    }

    @Override
    @Nonnull
    public WebhookMessageCreateAction<T> sendMessageComponents(@Nonnull Collection<? extends LayoutComponent> components) {
        return (WebhookMessageCreateAction)this.sendRequest().setComponents(components);
    }

    @Override
    @Nonnull
    public WebhookMessageCreateAction<T> sendMessage(@Nonnull MessageCreateData message) {
        return (WebhookMessageCreateAction)this.sendRequest().applyData(message);
    }

    @Override
    @Nonnull
    public WebhookMessageCreateAction<T> sendMessagePoll(@Nonnull MessagePollData poll) {
        Checks.notNull(poll, "Message Poll");
        return (WebhookMessageCreateAction)this.sendRequest().setPoll(poll);
    }

    @Override
    @Nonnull
    public WebhookMessageCreateAction<T> sendFiles(@Nonnull Collection<? extends FileUpload> files) {
        return (WebhookMessageCreateAction)this.sendRequest().addFiles(files);
    }

    @Override
    @Nonnull
    public WebhookMessageEditActionImpl<T> editMessageById(@Nonnull String messageId, @Nonnull String content) {
        return (WebhookMessageEditActionImpl)this.editRequest(messageId).setContent(content);
    }

    @Override
    @Nonnull
    public WebhookMessageEditAction<T> editMessageComponentsById(@Nonnull String messageId, @Nonnull Collection<? extends LayoutComponent> components) {
        Checks.noneNull(components, "Components");
        if (components.stream().anyMatch(x -> !(x instanceof ActionRow))) {
            throw new UnsupportedOperationException("The provided component layout is not supported");
        }
        List actionRows = components.stream().map(ActionRow.class::cast).collect(Collectors.toList());
        return (WebhookMessageEditAction)this.editRequest(messageId).setComponents(actionRows);
    }

    @Override
    @Nonnull
    public WebhookMessageEditActionImpl<T> editMessageEmbedsById(@Nonnull String messageId, @Nonnull Collection<? extends MessageEmbed> embeds) {
        return (WebhookMessageEditActionImpl)this.editRequest(messageId).setEmbeds(embeds);
    }

    @Override
    @Nonnull
    public WebhookMessageEditActionImpl<T> editMessageById(@Nonnull String messageId, @Nonnull MessageEditData message) {
        return (WebhookMessageEditActionImpl)this.editRequest(messageId).applyData(message);
    }

    @Override
    @Nonnull
    public WebhookMessageEditActionImpl<T> editMessageAttachmentsById(@Nonnull String messageId, @Nonnull Collection<? extends AttachedFile> attachments) {
        return (WebhookMessageEditActionImpl)this.editRequest(messageId).setAttachments(attachments);
    }

    @Override
    @Nonnull
    public WebhookMessageDeleteAction deleteMessageById(@Nonnull String messageId) {
        if (!"@original".equals(messageId)) {
            Checks.isSnowflake(messageId);
        }
        Route.CompiledRoute route = Route.Webhooks.EXECUTE_WEBHOOK_DELETE.compile(Long.toUnsignedString(this.id), this.token, messageId);
        return new WebhookMessageDeleteActionImpl(this.api, route);
    }
}

