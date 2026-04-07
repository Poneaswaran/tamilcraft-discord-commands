/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.slf4j.LoggerFactory
 */
package com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.util;

import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.WebhookClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;

@FunctionalInterface
public interface WebhookErrorHandler {
    public static final WebhookErrorHandler DEFAULT = (client, message, throwable) -> LoggerFactory.getLogger(WebhookClient.class).error(message, throwable);

    public void handle(@NotNull WebhookClient var1, @NotNull String var2, @Nullable Throwable var3);
}

