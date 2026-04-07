/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  discord4j.core.object.entity.Webhook
 *  discord4j.core.spec.MessageCreateSpec
 *  discord4j.core.spec.MessageEditSpec
 *  org.jetbrains.annotations.NotNull
 *  reactor.core.publisher.Mono
 */
package com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.external;

import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.WebhookClient;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.WebhookClientBuilder;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.receive.ReadonlyMessage;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.AllowedMentions;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.WebhookMessage;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.util.ThreadPools;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.okhttp3.OkHttpClient;
import discord4j.core.object.entity.Webhook;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.core.spec.MessageEditSpec;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

public class D4JWebhookClient
extends WebhookClient {
    public D4JWebhookClient(long id, String token, boolean parseMessage, OkHttpClient client, ScheduledExecutorService pool, AllowedMentions mentions) {
        this(id, token, parseMessage, client, pool, mentions, 0L);
    }

    public D4JWebhookClient(long id, String token, boolean parseMessage, OkHttpClient client, ScheduledExecutorService pool, AllowedMentions mentions, long threadId) {
        super(id, token, parseMessage, client, pool, mentions, threadId);
    }

    protected D4JWebhookClient(D4JWebhookClient parent, long threadId) {
        super(parent, threadId);
    }

    @NotNull
    public static D4JWebhookClient from(@NotNull Webhook webhook) {
        return WebhookClientBuilder.fromD4J(webhook).buildD4J();
    }

    @NotNull
    public static D4JWebhookClient withId(long id, @NotNull String token) {
        Objects.requireNonNull(token, "Token");
        ScheduledExecutorService pool = ThreadPools.getDefaultPool(id, null, false);
        return new D4JWebhookClient(id, token, true, new OkHttpClient(), pool, AllowedMentions.all(), 0L);
    }

    @NotNull
    public static D4JWebhookClient withUrl(@NotNull String url) {
        Objects.requireNonNull(url, "URL");
        Matcher matcher = WebhookClientBuilder.WEBHOOK_PATTERN.matcher(url);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Failed to parse webhook URL");
        }
        return D4JWebhookClient.withId(Long.parseUnsignedLong(matcher.group(1)), matcher.group(2));
    }

    @Override
    @NotNull
    public D4JWebhookClient onThread(long threadId) {
        return new D4JWebhookClient(this, threadId);
    }

    @Deprecated
    @CheckReturnValue
    @NotNull
    public Mono<ReadonlyMessage> send(@NotNull Consumer<? super MessageCreateSpec> callback) {
        throw new UnsupportedOperationException("Cannot build messages via consumers in Discord4J 3.2.0! Please change to fromD4J(spec)");
    }

    @Deprecated
    @CheckReturnValue
    @NotNull
    public Mono<ReadonlyMessage> edit(long messageId, @NotNull Consumer<? super MessageCreateSpec> callback) {
        throw new UnsupportedOperationException("Cannot build messages via consumers in Discord4J 3.2.0! Please change to fromD4J(spec)");
    }

    @Deprecated
    @CheckReturnValue
    @NotNull
    public Mono<ReadonlyMessage> edit(@NotNull String messageId, @NotNull Consumer<? super MessageCreateSpec> callback) {
        throw new UnsupportedOperationException("Cannot build messages via consumers in Discord4J 3.2.0! Please change to fromD4J(spec)");
    }

    @CheckReturnValue
    @NotNull
    public Mono<ReadonlyMessage> send(@NotNull MessageCreateSpec spec) {
        WebhookMessage message = WebhookMessageBuilder.fromD4J(spec).build();
        return Mono.fromFuture(() -> this.send(message));
    }

    @CheckReturnValue
    @NotNull
    public Mono<ReadonlyMessage> edit(long messageId, @NotNull MessageEditSpec spec) {
        WebhookMessage message = WebhookMessageBuilder.fromD4J(spec).build();
        return Mono.fromFuture(() -> this.edit(messageId, message));
    }

    @CheckReturnValue
    @NotNull
    public Mono<ReadonlyMessage> edit(@NotNull String messageId, @NotNull MessageEditSpec spec) {
        WebhookMessage message = WebhookMessageBuilder.fromD4J(spec).build();
        return Mono.fromFuture(() -> this.edit(messageId, message));
    }
}

