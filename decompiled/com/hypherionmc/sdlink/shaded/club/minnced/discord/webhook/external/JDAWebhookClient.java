/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.external;

import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.WebhookClient;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.WebhookClientBuilder;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.receive.ReadonlyMessage;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.AllowedMentions;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.WebhookEmbed;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.util.ThreadPools;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Webhook;
import com.hypherionmc.sdlink.shaded.okhttp3.OkHttpClient;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Matcher;
import org.jetbrains.annotations.NotNull;

public class JDAWebhookClient
extends WebhookClient {
    public JDAWebhookClient(long id, String token, boolean parseMessage, OkHttpClient client, ScheduledExecutorService pool, AllowedMentions mentions) {
        this(id, token, parseMessage, client, pool, mentions, 0L);
    }

    public JDAWebhookClient(long id, String token, boolean parseMessage, OkHttpClient client, ScheduledExecutorService pool, AllowedMentions mentions, long threadId) {
        super(id, token, parseMessage, client, pool, mentions, threadId);
    }

    protected JDAWebhookClient(JDAWebhookClient parent, long threadId) {
        super(parent, threadId);
    }

    @NotNull
    public static JDAWebhookClient from(@NotNull Webhook webhook) {
        return WebhookClientBuilder.fromJDA(webhook).buildJDA();
    }

    @NotNull
    public static JDAWebhookClient withId(long id, @NotNull String token) {
        Objects.requireNonNull(token, "Token");
        ScheduledExecutorService pool = ThreadPools.getDefaultPool(id, null, false);
        return new JDAWebhookClient(id, token, true, new OkHttpClient(), pool, AllowedMentions.all());
    }

    @NotNull
    public static JDAWebhookClient withUrl(@NotNull String url) {
        Objects.requireNonNull(url, "URL");
        Matcher matcher = WebhookClientBuilder.WEBHOOK_PATTERN.matcher(url);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Failed to parse webhook URL");
        }
        return JDAWebhookClient.withId(Long.parseUnsignedLong(matcher.group(1)), matcher.group(2));
    }

    @Override
    @NotNull
    public JDAWebhookClient onThread(long threadId) {
        return new JDAWebhookClient(this, threadId);
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull Message message) {
        return this.send(WebhookMessageBuilder.fromJDA(message).build());
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull MessageEmbed embed) {
        return this.send(WebhookEmbedBuilder.fromJDA(embed).build(), new WebhookEmbed[0]);
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(long messageId, @NotNull Message message) {
        return this.edit(messageId, WebhookMessageBuilder.fromJDA(message).build());
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(long messageId, @NotNull MessageEmbed embed) {
        return this.edit(messageId, WebhookEmbedBuilder.fromJDA(embed).build(), new WebhookEmbed[0]);
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(@NotNull String messageId, @NotNull Message message) {
        return this.edit(messageId, WebhookMessageBuilder.fromJDA(message).build());
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(@NotNull String messageId, @NotNull MessageEmbed embed) {
        return this.edit(messageId, WebhookEmbedBuilder.fromJDA(embed).build(), new WebhookEmbed[0]);
    }
}

