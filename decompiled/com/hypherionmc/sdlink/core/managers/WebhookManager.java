/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.core.managers;

import com.hypherionmc.sdlink.api.messaging.MessageDestination;
import com.hypherionmc.sdlink.api.messaging.MessageType;
import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.core.config.impl.MessageChannelConfig;
import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.core.managers.CacheManager;
import com.hypherionmc.sdlink.core.messaging.SDLinkWebhookClientBuilder;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.WebhookClient;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.WebhookClientBuilder;
import com.hypherionmc.sdlink.util.EncryptionUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Nullable;

public final class WebhookManager {
    private static final HashMap<MessageDestination, WebhookClient> clientMap = new HashMap();
    private static final HashMap<MessageType, WebhookClient> overrides = new HashMap();
    private static WebhookClient chatWebhookClient;
    private static WebhookClient eventWebhookClient;
    private static WebhookClient consoleWebhookClient;
    private static final Pattern THREAD_PATTERN;

    public static void init() {
        clientMap.clear();
        overrides.clear();
        if (SDLinkConfig.INSTANCE == null || !SDLinkConfig.INSTANCE.channelsAndWebhooks.webhooks.enabled) {
            return;
        }
        if (!SDLinkConfig.INSTANCE.generalConfig.enabled) {
            return;
        }
        if (!SDLinkConfig.INSTANCE.channelsAndWebhooks.webhooks.chatWebhook.isEmpty()) {
            chatWebhookClient = WebhookManager.createClient("Chat", EncryptionUtil.INSTANCE.decrypt(SDLinkConfig.INSTANCE.channelsAndWebhooks.webhooks.chatWebhook));
            BotController.INSTANCE.getLogger().info("Using Webhook for Chat Messages");
        }
        if (!SDLinkConfig.INSTANCE.channelsAndWebhooks.webhooks.eventsWebhook.isEmpty()) {
            eventWebhookClient = WebhookManager.createClient("Events", EncryptionUtil.INSTANCE.decrypt(SDLinkConfig.INSTANCE.channelsAndWebhooks.webhooks.eventsWebhook));
            BotController.INSTANCE.getLogger().info("Using Webhook for Event Messages");
        }
        if (!SDLinkConfig.INSTANCE.channelsAndWebhooks.webhooks.consoleWebhook.isEmpty()) {
            consoleWebhookClient = WebhookManager.createClient("Console", EncryptionUtil.INSTANCE.decrypt(SDLinkConfig.INSTANCE.channelsAndWebhooks.webhooks.consoleWebhook));
            BotController.INSTANCE.getLogger().info("Using Webhook for Console Messages");
        }
        if (chatWebhookClient != null) {
            clientMap.put(MessageDestination.CHAT, chatWebhookClient);
        }
        clientMap.put(MessageDestination.EVENT, eventWebhookClient);
        clientMap.put(MessageDestination.CONSOLE, consoleWebhookClient);
        for (Map.Entry<MessageType, MessageChannelConfig.DestinationObject> d : CacheManager.messageDestinations.entrySet()) {
            String url = EncryptionUtil.INSTANCE.decrypt(d.getValue().override);
            if (!d.getValue().channel.isOverride() || d.getValue().override == null || !url.startsWith("http") || overrides.containsKey((Object)d.getKey())) continue;
            WebhookClient client = WebhookManager.createClient(d.getKey().name() + " override", url);
            BotController.INSTANCE.getLogger().info("Using Webhook override for {} Messages", (Object)d.getKey().name());
            overrides.put(d.getKey(), client);
        }
    }

    @Nullable
    public static WebhookClient getOverride(MessageType type) {
        if (overrides.get((Object)type) == null) {
            return null;
        }
        return overrides.get((Object)type);
    }

    public static WebhookClient getWebhookClient(MessageDestination destination) {
        return clientMap.get((Object)destination);
    }

    public static void shutdown() {
        if (chatWebhookClient != null) {
            chatWebhookClient.close();
        }
        if (eventWebhookClient != null) {
            eventWebhookClient.close();
        }
        if (consoleWebhookClient != null) {
            consoleWebhookClient.close();
        }
        overrides.forEach((k, v) -> {
            if (v != null) {
                v.close();
            }
        });
    }

    private static WebhookClient createClient(String name, String url) {
        Matcher threadMatcher = THREAD_PATTERN.matcher(url);
        Matcher webhookMatcher = WebhookClientBuilder.WEBHOOK_PATTERN.matcher(url);
        if (threadMatcher.find() && webhookMatcher.find()) {
            return new SDLinkWebhookClientBuilder(name, String.format("https://discord.com/api/webhooks/%s/%s", webhookMatcher.group(1), webhookMatcher.group(2))).setThreadChannelID(threadMatcher.group(1)).build();
        }
        if (webhookMatcher.matches()) {
            return new SDLinkWebhookClientBuilder(name, String.format("https://discord.com/api/webhooks/%s/%s", webhookMatcher.group(1), webhookMatcher.group(2))).build();
        }
        return new SDLinkWebhookClientBuilder(name, url).build();
    }

    public static boolean isAppWebhook(long id) {
        return clientMap.values().stream().filter(Objects::nonNull).anyMatch(c -> c.getId() == id) || overrides.values().stream().filter(Objects::nonNull).anyMatch(c -> c.getId() == id);
    }

    static {
        THREAD_PATTERN = Pattern.compile("thread_id=(\\d+)");
    }
}

