/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.util;

import com.hypherionmc.sdlink.api.messaging.MessageDestination;
import com.hypherionmc.sdlink.api.messaging.MessageType;
import com.hypherionmc.sdlink.core.config.impl.MessageChannelConfig;
import com.hypherionmc.sdlink.core.managers.ChannelManager;
import com.hypherionmc.sdlink.core.managers.WebhookManager;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.WebhookClient;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import org.jetbrains.annotations.Nullable;

public final class DestinationHolder {
    private final GuildMessageChannel channel;
    private final WebhookClient webhookClient;
    private final MessageChannelConfig.DestinationObject destination;

    private DestinationHolder(MessageChannelConfig.DestinationObject destination, MessageType type) {
        this.channel = ChannelManager.getOverride(type) != null ? ChannelManager.getOverride(type) : ChannelManager.getDestinationChannel(destination.channel);
        this.webhookClient = WebhookManager.getOverride(type) != null ? WebhookManager.getOverride(type) : WebhookManager.getWebhookClient(destination.channel);
        this.destination = destination;
    }

    public static DestinationHolder of(MessageChannelConfig.DestinationObject destination, MessageType type) {
        return new DestinationHolder(destination, type);
    }

    @Nullable
    public GuildMessageChannel channel() {
        return this.channel;
    }

    @Nullable
    public WebhookClient webhook() {
        return this.webhookClient;
    }

    public MessageDestination destination() {
        return this.destination.channel;
    }

    public boolean useEmbed() {
        return this.destination.useEmbed;
    }

    public String embedLayout() {
        return this.destination.embedLayout;
    }

    public boolean hasWebhook() {
        return this.webhookClient != null;
    }
}

