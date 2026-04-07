/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.javacord.api.entity.message.Message
 *  org.javacord.api.entity.message.embed.Embed
 *  org.javacord.api.entity.webhook.Webhook
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
import com.hypherionmc.sdlink.shaded.okhttp3.OkHttpClient;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Matcher;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.webhook.Webhook;
import org.jetbrains.annotations.NotNull;

public class JavacordWebhookClient
extends WebhookClient {
    public JavacordWebhookClient(long id, String token, boolean parseMessage, OkHttpClient client, ScheduledExecutorService pool, AllowedMentions mentions) {
        this(id, token, parseMessage, client, pool, mentions, 0L);
    }

    public JavacordWebhookClient(long id, String token, boolean parseMessage, OkHttpClient client, ScheduledExecutorService pool, AllowedMentions mentions, long threadId) {
        super(id, token, parseMessage, client, pool, mentions, threadId);
    }

    protected JavacordWebhookClient(JavacordWebhookClient parent, long threadId) {
        super(parent, threadId);
    }

    @NotNull
    public static JavacordWebhookClient from(@NotNull Webhook webhook) {
        return WebhookClientBuilder.fromJavacord(webhook).buildJavacord();
    }

    @NotNull
    public static JavacordWebhookClient withId(long id, @NotNull String token) {
        Objects.requireNonNull(token, "Token");
        ScheduledExecutorService pool = ThreadPools.getDefaultPool(id, null, false);
        return new JavacordWebhookClient(id, token, true, new OkHttpClient(), pool, AllowedMentions.all());
    }

    @NotNull
    public static JavacordWebhookClient withUrl(@NotNull String url) {
        Objects.requireNonNull(url, "URL");
        Matcher matcher = WebhookClientBuilder.WEBHOOK_PATTERN.matcher(url);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Failed to parse webhook URL");
        }
        return JavacordWebhookClient.withId(Long.parseUnsignedLong(matcher.group(1)), matcher.group(2));
    }

    @Override
    @NotNull
    public JavacordWebhookClient onThread(long threadId) {
        return new JavacordWebhookClient(this, threadId);
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull Message message) {
        return this.send(WebhookMessageBuilder.fromJavacord(message).build());
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull Embed embed) {
        return this.send(WebhookEmbedBuilder.fromJavacord(embed).build(), new WebhookEmbed[0]);
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(long messageId, @NotNull Message message) {
        return this.edit(messageId, WebhookMessageBuilder.fromJavacord(message).build());
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(long messageId, @NotNull Embed embed) {
        return this.edit(messageId, WebhookEmbedBuilder.fromJavacord(embed).build(), new WebhookEmbed[0]);
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(@NotNull String messageId, @NotNull Message message) {
        return this.edit(messageId, WebhookMessageBuilder.fromJavacord(message).build());
    }

    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(@NotNull String messageId, @NotNull Embed embed) {
        return this.edit(messageId, WebhookEmbedBuilder.fromJavacord(embed).build(), new WebhookEmbed[0]);
    }
}

