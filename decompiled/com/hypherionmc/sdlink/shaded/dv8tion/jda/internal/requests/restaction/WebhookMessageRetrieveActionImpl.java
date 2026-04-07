/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookMessageRetrieveAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AbstractWebhookMessageActionImpl;
import java.util.function.BiFunction;

public class WebhookMessageRetrieveActionImpl
extends AbstractWebhookMessageActionImpl<Message, WebhookMessageRetrieveActionImpl>
implements WebhookMessageRetrieveAction {
    public WebhookMessageRetrieveActionImpl(JDA api, Route.CompiledRoute route, BiFunction<Response, Request<Message>, Message> handler) {
        super(api, route, handler);
    }

    @Override
    protected Route.CompiledRoute finalizeRoute() {
        Route.CompiledRoute route = super.finalizeRoute();
        if (this.threadId != null) {
            route = route.withQueryParams("thread_id", this.threadId);
        }
        return route;
    }
}

