/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Icon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Webhook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.IWebhookContainerUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.Manager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface WebhookManager
extends Manager<WebhookManager> {
    public static final long NAME = 1L;
    public static final long CHANNEL = 2L;
    public static final long AVATAR = 4L;

    @Override
    @Nonnull
    @CheckReturnValue
    public WebhookManager reset(long var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public WebhookManager reset(long ... var1);

    @Nonnull
    public Webhook getWebhook();

    @Nonnull
    default public IWebhookContainerUnion getChannel() {
        return this.getWebhook().getChannel();
    }

    @Nonnull
    default public Guild getGuild() {
        return this.getWebhook().getGuild();
    }

    @Nonnull
    @CheckReturnValue
    public WebhookManager setName(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    public WebhookManager setAvatar(@Nullable Icon var1);

    @Nonnull
    @CheckReturnValue
    public WebhookManager setChannel(@Nonnull TextChannel var1);
}

