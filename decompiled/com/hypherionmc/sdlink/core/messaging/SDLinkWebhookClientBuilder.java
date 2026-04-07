/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.core.messaging;

import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.WebhookClientBuilder;

public final class SDLinkWebhookClientBuilder
extends WebhookClientBuilder {
    public SDLinkWebhookClientBuilder(String name, String url) {
        super(url);
        this.setThreadFactory(job -> {
            Thread thread = new Thread(job);
            thread.setName(name + " Webhook Thread");
            thread.setDaemon(true);
            return thread;
        });
        this.setWait(false);
    }

    public SDLinkWebhookClientBuilder setThreadChannelID(String id) {
        this.setThreadId(Long.parseLong(id));
        return this;
    }
}

