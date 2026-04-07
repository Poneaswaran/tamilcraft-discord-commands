/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  discord4j.core.object.entity.Webhook
 *  org.javacord.api.entity.webhook.IncomingWebhook
 *  org.javacord.api.entity.webhook.Webhook
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook;

import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.WebhookClient;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.external.D4JWebhookClient;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.external.JDAWebhookClient;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.external.JavacordWebhookClient;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.AllowedMentions;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.util.ThreadPools;
import com.hypherionmc.sdlink.shaded.okhttp3.OkHttpClient;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.javacord.api.entity.webhook.IncomingWebhook;
import org.javacord.api.entity.webhook.Webhook;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WebhookClientBuilder {
    public static final Pattern WEBHOOK_PATTERN = Pattern.compile("(?:https?://)?(?:\\w+\\.)?discord(?:app)?\\.com/api(?:/v\\d+)?/webhooks/(\\d+)/([\\w-]+)(?:/(?:\\w+)?)?");
    protected final long id;
    protected final String token;
    protected long threadId;
    protected ScheduledExecutorService pool;
    protected OkHttpClient client;
    protected ThreadFactory threadFactory;
    protected AllowedMentions allowedMentions = AllowedMentions.all();
    protected boolean isDaemon;
    protected boolean parseMessage = true;

    public WebhookClientBuilder(long id, @NotNull String token) {
        Objects.requireNonNull(token, "Token");
        this.id = id;
        this.token = token;
    }

    public WebhookClientBuilder(@NotNull String url) {
        Objects.requireNonNull(url, "Url");
        Matcher matcher = WEBHOOK_PATTERN.matcher(url);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Failed to parse webhook URL");
        }
        this.id = Long.parseUnsignedLong(matcher.group(1));
        this.token = matcher.group(2);
    }

    @NotNull
    public static WebhookClientBuilder fromJDA(@NotNull com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Webhook webhook) {
        Objects.requireNonNull(webhook, "Webhook");
        return new WebhookClientBuilder(webhook.getIdLong(), Objects.requireNonNull(webhook.getToken(), "Webhook Token"));
    }

    @NotNull
    public static WebhookClientBuilder fromD4J(@NotNull discord4j.core.object.entity.Webhook webhook) {
        Objects.requireNonNull(webhook, "Webhook");
        String token = (String)webhook.getToken().orElseThrow(() -> new NullPointerException("Webhook Token is missing"));
        if (token.isEmpty()) {
            throw new NullPointerException("Webhook Token is empty");
        }
        return new WebhookClientBuilder(webhook.getId().asLong(), token);
    }

    @NotNull
    public static WebhookClientBuilder fromJavacord(@NotNull Webhook webhook) {
        Objects.requireNonNull(webhook, "Webhook");
        return new WebhookClientBuilder(webhook.getId(), webhook.asIncomingWebhook().map(IncomingWebhook::getToken).orElseThrow(() -> new NullPointerException("Webhook Token is missing")));
    }

    @NotNull
    public WebhookClientBuilder setExecutorService(@Nullable ScheduledExecutorService executorService) {
        this.pool = executorService;
        return this;
    }

    @NotNull
    public WebhookClientBuilder setHttpClient(@Nullable OkHttpClient client) {
        this.client = client;
        return this;
    }

    @NotNull
    public WebhookClientBuilder setThreadFactory(@Nullable ThreadFactory factory2) {
        this.threadFactory = factory2;
        return this;
    }

    @NotNull
    public WebhookClientBuilder setAllowedMentions(@Nullable AllowedMentions mentions) {
        this.allowedMentions = mentions == null ? AllowedMentions.all() : mentions;
        return this;
    }

    @NotNull
    public WebhookClientBuilder setDaemon(boolean isDaemon) {
        this.isDaemon = isDaemon;
        return this;
    }

    @NotNull
    public WebhookClientBuilder setWait(boolean waitForMessage) {
        this.parseMessage = waitForMessage;
        return this;
    }

    @NotNull
    public WebhookClientBuilder setThreadId(long threadId) {
        this.threadId = threadId;
        return this;
    }

    @NotNull
    public WebhookClient build() {
        OkHttpClient client = this.client == null ? new OkHttpClient() : this.client;
        ScheduledExecutorService pool = this.pool != null ? this.pool : ThreadPools.getDefaultPool(this.id, this.threadFactory, this.isDaemon);
        return new WebhookClient(this.id, this.token, this.parseMessage, client, pool, this.allowedMentions, this.threadId);
    }

    @NotNull
    public JDAWebhookClient buildJDA() {
        OkHttpClient client = this.client == null ? new OkHttpClient() : this.client;
        ScheduledExecutorService pool = this.pool != null ? this.pool : ThreadPools.getDefaultPool(this.id, this.threadFactory, this.isDaemon);
        return new JDAWebhookClient(this.id, this.token, this.parseMessage, client, pool, this.allowedMentions, this.threadId);
    }

    @NotNull
    public D4JWebhookClient buildD4J() {
        OkHttpClient client = this.client == null ? new OkHttpClient() : this.client;
        ScheduledExecutorService pool = this.pool != null ? this.pool : ThreadPools.getDefaultPool(this.id, this.threadFactory, this.isDaemon);
        return new D4JWebhookClient(this.id, this.token, this.parseMessage, client, pool, this.allowedMentions, this.threadId);
    }

    @NotNull
    public JavacordWebhookClient buildJavacord() {
        OkHttpClient client = this.client == null ? new OkHttpClient() : this.client;
        ScheduledExecutorService pool = this.pool != null ? this.pool : ThreadPools.getDefaultPool(this.id, this.threadFactory, this.isDaemon);
        return new JavacordWebhookClient(this.id, this.token, this.parseMessage, client, pool, this.allowedMentions, this.threadId);
    }
}

