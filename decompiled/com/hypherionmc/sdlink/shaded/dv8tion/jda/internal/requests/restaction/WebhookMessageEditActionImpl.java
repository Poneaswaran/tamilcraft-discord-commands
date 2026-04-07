/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookMessageEditAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AbstractWebhookMessageActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.message.MessageEditBuilderMixin;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.function.Function;

public class WebhookMessageEditActionImpl<T>
extends AbstractWebhookMessageActionImpl<T, WebhookMessageEditActionImpl<T>>
implements WebhookMessageEditAction<T>,
MessageEditBuilderMixin<WebhookMessageEditAction<T>> {
    private final Function<DataObject, T> transformer;
    private final MessageEditBuilder builder = new MessageEditBuilder();

    public WebhookMessageEditActionImpl(JDA api, Route.CompiledRoute route, Function<DataObject, T> transformer) {
        super(api, route);
        this.transformer = transformer;
    }

    @Override
    public MessageEditBuilder getBuilder() {
        return this.builder;
    }

    @Override
    protected RequestBody finalizeData() {
        try (MessageEditData data = this.builder.build();){
            DataObject payload = data.toData();
            RequestBody requestBody = this.getMultipartBody(data.getFiles(), payload);
            return requestBody;
        }
    }

    @Override
    protected Route.CompiledRoute finalizeRoute() {
        Route.CompiledRoute route = super.finalizeRoute();
        if (this.threadId != null) {
            route = route.withQueryParams("thread_id", this.threadId);
        }
        return route;
    }

    @Override
    protected void handleSuccess(Response response, Request<T> request) {
        T message = this.transformer.apply(response.getObject());
        request.onSuccess(message);
    }
}

