/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IncomingWebhookClient;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookMessageDeleteAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookMessageRetrieveAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.AbstractWebhookClient;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ReceivedMessage;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.WebhookMessageCreateActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.WebhookMessageDeleteActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.WebhookMessageEditActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.WebhookMessageRetrieveActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.function.Function;

public class IncomingWebhookClientImpl
extends AbstractWebhookClient<Message>
implements IncomingWebhookClient {
    public IncomingWebhookClientImpl(long webhookId, String webhookToken, JDA api) {
        super(webhookId, webhookToken, api);
    }

    @Override
    public WebhookMessageCreateActionImpl<Message> sendRequest() {
        Route.CompiledRoute route = Route.Webhooks.EXECUTE_WEBHOOK.compile(Long.toUnsignedString(this.id), this.token);
        route = route.withQueryParams("wait", "true");
        WebhookMessageCreateActionImpl<Message> action = new WebhookMessageCreateActionImpl<Message>(this.api, route, this.builder());
        action.run();
        action.setInteraction(false);
        return action;
    }

    @Override
    public WebhookMessageEditActionImpl<Message> editRequest(@Nonnull String messageId) {
        if (!"@original".equals(messageId)) {
            Checks.isSnowflake(messageId);
        }
        Route.CompiledRoute route = Route.Webhooks.EXECUTE_WEBHOOK_EDIT.compile(Long.toUnsignedString(this.id), this.token, messageId);
        route = route.withQueryParams("wait", "true");
        WebhookMessageEditActionImpl<Message> action = new WebhookMessageEditActionImpl<Message>(this.api, route, this.builder());
        action.run();
        return action;
    }

    @Override
    @Nonnull
    public WebhookMessageRetrieveAction retrieveMessageById(@Nonnull String messageId) {
        if (!"@original".equals(messageId)) {
            Checks.isSnowflake(messageId);
        }
        Route.CompiledRoute route = Route.Webhooks.EXECUTE_WEBHOOK_FETCH.compile(Long.toUnsignedString(this.id), this.token, messageId);
        WebhookMessageRetrieveActionImpl action = new WebhookMessageRetrieveActionImpl(this.api, route, (response, request) -> this.builder().apply(response.getObject()));
        action.run();
        return action;
    }

    @Override
    @Nonnull
    public WebhookMessageDeleteAction deleteMessageById(@Nonnull String messageId) {
        WebhookMessageDeleteActionImpl action = (WebhookMessageDeleteActionImpl)super.deleteMessageById(messageId);
        action.run();
        return action;
    }

    private Function<DataObject, Message> builder() {
        return data -> {
            JDAImpl jda = (JDAImpl)this.api;
            long channelId = data.getUnsignedLong("channel_id");
            MessageChannel channel = this.api.getChannelById(MessageChannel.class, channelId);
            EntityBuilder entityBuilder = jda.getEntityBuilder();
            ReceivedMessage message = entityBuilder.createMessageBestEffort((DataObject)data, channel, null);
            message.withHook(this);
            return message;
        };
    }
}

