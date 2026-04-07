/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.WebhookClient;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.WebhookType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.IWebhookContainerUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.WebhookManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.regex.Pattern;

public interface Webhook
extends ISnowflake,
WebhookClient<Message> {
    public static final Pattern WEBHOOK_URL = Pattern.compile("https?://(?:[^\\s.]+\\.)?discord(?:app)?\\.com/api(?:/v\\d+)?/webhooks/(?<id>\\d+)/(?<token>[^\\s/]+)", 2);

    @Override
    @Nonnull
    public JDA getJDA();

    @Nonnull
    public WebhookType getType();

    public boolean isPartial();

    @Nonnull
    public Guild getGuild();

    @Nonnull
    public IWebhookContainerUnion getChannel();

    @Nullable
    public Member getOwner();

    @Nullable
    public User getOwnerAsUser();

    @Nonnull
    public User getDefaultUser();

    @Nonnull
    public String getName();

    @Override
    @Nullable
    public String getToken();

    @Nonnull
    public String getUrl();

    @Nullable
    public ChannelReference getSourceChannel();

    @Nullable
    public GuildReference getSourceGuild();

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> delete();

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> delete(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    public WebhookManager getManager();

    public static class GuildReference
    implements ISnowflake {
        private final long id;
        private final String name;

        public GuildReference(long id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public long getIdLong() {
            return this.id;
        }

        @Nonnull
        public String getName() {
            return this.name;
        }
    }

    public static class ChannelReference
    implements ISnowflake {
        private final long id;
        private final String name;

        public ChannelReference(long id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public long getIdLong() {
            return this.id;
        }

        @Nonnull
        public String getName() {
            return this.name;
        }
    }

    public static class WebhookReference
    implements ISnowflake {
        private final JDA api;
        private final long webhookId;
        private final long channelId;

        public WebhookReference(JDA api, long webhookId, long channelId) {
            this.api = api;
            this.webhookId = webhookId;
            this.channelId = channelId;
        }

        @Override
        public long getIdLong() {
            return this.webhookId;
        }

        @Nonnull
        public String getChannelId() {
            return Long.toUnsignedString(this.channelId);
        }

        public long getChannelIdLong() {
            return this.channelId;
        }

        @Nonnull
        @CheckReturnValue
        public RestAction<Webhook> resolve() {
            Route.CompiledRoute route = Route.Webhooks.GET_WEBHOOK.compile(this.getId());
            return new RestActionImpl<Webhook>(this.api, route, (response, request) -> request.getJDA().getEntityBuilder().createWebhook(response.getObject(), true));
        }
    }
}

