/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookMessageDeleteAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AbstractWebhookMessageActionImpl;

public class WebhookMessageDeleteActionImpl
extends AbstractWebhookMessageActionImpl<Void, WebhookMessageDeleteActionImpl>
implements WebhookMessageDeleteAction {
    public WebhookMessageDeleteActionImpl(JDA api, Route.CompiledRoute route) {
        super(api, route);
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

