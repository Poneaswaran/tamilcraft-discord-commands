/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Webhook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IWebhookContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.IWebhookContainerUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.WebhookImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.GuildChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.WebhookActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface IWebhookContainerMixin<T extends IWebhookContainerMixin<T>>
extends IWebhookContainer,
IWebhookContainerUnion,
GuildChannelMixin<T> {
    @Override
    @Nonnull
    default public RestAction<List<Webhook>> retrieveWebhooks() {
        this.checkPermission(Permission.MANAGE_WEBHOOKS);
        Route.CompiledRoute route = Route.Channels.GET_WEBHOOKS.compile(this.getId());
        JDAImpl jda = (JDAImpl)this.getJDA();
        return new RestActionImpl<List<Webhook>>((JDA)jda, route, (response, request) -> {
            DataArray array = response.getArray();
            ArrayList<WebhookImpl> webhooks = new ArrayList<WebhookImpl>(array.length());
            EntityBuilder builder = jda.getEntityBuilder();
            for (int i = 0; i < array.length(); ++i) {
                try {
                    webhooks.add(builder.createWebhook(array.getObject(i)));
                    continue;
                }
                catch (UncheckedIOException | NullPointerException e) {
                    JDAImpl.LOG.error("Error while creating websocket from json", (Throwable)e);
                }
            }
            return Collections.unmodifiableList(webhooks);
        });
    }

    @Override
    @Nonnull
    default public WebhookAction createWebhook(@Nonnull String name) {
        Checks.notBlank(name, "Webhook name");
        name = name.trim();
        Checks.notEmpty(name, "Name");
        Checks.notLonger(name, 100, "Name");
        this.checkPermission(Permission.MANAGE_WEBHOOKS);
        return new WebhookActionImpl(this.getJDA(), this, name);
    }

    @Override
    @Nonnull
    default public AuditableRestAction<Void> deleteWebhookById(@Nonnull String id) {
        Checks.isSnowflake(id, "Webhook ID");
        this.checkPermission(Permission.MANAGE_WEBHOOKS);
        Route.CompiledRoute route = Route.Webhooks.DELETE_WEBHOOK.compile(id);
        return new AuditableRestActionImpl<Void>(this.getJDA(), route);
    }
}

