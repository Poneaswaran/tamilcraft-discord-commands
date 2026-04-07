/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.WebhookClient;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.InteractionHook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.MessageEditAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ReceivedMessage;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.message.MessageEditBuilderMixin;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.function.BooleanSupplier;

public class MessageEditActionImpl
extends RestActionImpl<Message>
implements MessageEditAction,
MessageEditBuilderMixin<MessageEditAction> {
    private final String messageId;
    private final Guild guild;
    private final MessageChannel channel;
    private final MessageEditBuilder builder = new MessageEditBuilder();
    private WebhookClient<Message> webhook;

    public MessageEditActionImpl(@Nonnull JDA jda, @Nullable Guild guild, @Nonnull String channelId, @Nonnull String messageId) {
        super(jda, Route.Messages.EDIT_MESSAGE.compile(channelId, messageId));
        this.channel = null;
        this.guild = guild;
        this.messageId = messageId;
    }

    public MessageEditActionImpl(@Nonnull MessageChannel channel, @Nonnull String messageId) {
        super(channel.getJDA(), Route.Messages.EDIT_MESSAGE.compile(channel.getId(), messageId));
        this.channel = channel;
        this.guild = channel instanceof GuildChannel ? ((GuildChannel)((Object)channel)).getGuild() : null;
        this.messageId = messageId;
    }

    public MessageEditActionImpl withHook(WebhookClient<Message> hook) {
        this.webhook = hook;
        return this;
    }

    @Override
    public MessageEditBuilder getBuilder() {
        return this.builder;
    }

    @Override
    protected Route.CompiledRoute finalizeRoute() {
        if (!(this.webhook == null || this.webhook instanceof InteractionHook && ((InteractionHook)this.webhook).isExpired())) {
            return Route.Webhooks.EXECUTE_WEBHOOK_EDIT.compile(this.webhook.getId(), this.webhook.getToken(), this.messageId);
        }
        return super.finalizeRoute();
    }

    @Override
    protected RequestBody finalizeData() {
        try (MessageEditData data = this.builder.build();){
            RequestBody requestBody = this.getMultipartBody(data.getFiles(), data.toData());
            return requestBody;
        }
    }

    @Override
    protected void handleSuccess(Response response, Request<Message> request) {
        EntityBuilder entityBuilder = this.api.getEntityBuilder();
        DataObject json = response.getObject();
        ReceivedMessage message = entityBuilder.createMessageBestEffort(json, this.channel, this.guild);
        request.onSuccess(message.withHook(this.webhook));
    }

    @Override
    @Nonnull
    public MessageEditAction setCheck(BooleanSupplier checks) {
        return (MessageEditAction)super.setCheck(checks);
    }

    @Override
    @Nonnull
    public MessageEditAction deadline(long timestamp) {
        return (MessageEditAction)super.deadline(timestamp);
    }
}

