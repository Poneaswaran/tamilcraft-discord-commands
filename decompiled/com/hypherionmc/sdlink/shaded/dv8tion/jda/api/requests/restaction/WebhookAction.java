/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Icon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Webhook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.IWebhookContainerUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public interface WebhookAction
extends AuditableRestAction<Webhook> {
    @Override
    @Nonnull
    @CheckReturnValue
    public WebhookAction setCheck(@Nullable BooleanSupplier var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public WebhookAction timeout(long var1, @Nonnull TimeUnit var3);

    @Override
    @Nonnull
    @CheckReturnValue
    public WebhookAction deadline(long var1);

    @Nonnull
    public IWebhookContainerUnion getChannel();

    @Nonnull
    default public Guild getGuild() {
        return this.getChannel().getGuild();
    }

    @Nonnull
    @CheckReturnValue
    public WebhookAction setName(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    public WebhookAction setAvatar(@Nullable Icon var1);
}

