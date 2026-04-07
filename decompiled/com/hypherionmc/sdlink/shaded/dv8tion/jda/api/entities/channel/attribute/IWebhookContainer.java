/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Webhook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.List;
import org.jetbrains.annotations.Unmodifiable;

public interface IWebhookContainer
extends GuildChannel {
    @Nonnull
    @CheckReturnValue
    public RestAction<@Unmodifiable List<Webhook>> retrieveWebhooks();

    @Nonnull
    @CheckReturnValue
    public WebhookAction createWebhook(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> deleteWebhookById(@Nonnull String var1);
}

