/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumTagSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AbstractWebhookMessageAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.ThreadCreateMetadata;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateRequest;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface WebhookMessageCreateAction<T>
extends MessageCreateRequest<WebhookMessageCreateAction<T>>,
AbstractWebhookMessageAction<T, WebhookMessageCreateAction<T>> {
    @Nonnull
    @CheckReturnValue
    public WebhookMessageCreateAction<T> setEphemeral(boolean var1);

    @Nonnull
    @CheckReturnValue
    public WebhookMessageCreateAction<T> setUsername(@Nullable String var1);

    @Nonnull
    @CheckReturnValue
    public WebhookMessageCreateAction<T> setAvatarUrl(@Nullable String var1);

    @Nonnull
    @CheckReturnValue
    public WebhookMessageCreateAction<T> createThread(@Nonnull ThreadCreateMetadata var1);

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageCreateAction<T> createThread(@Nonnull String threadName, ForumTagSnowflake ... tags) {
        return this.createThread(new ThreadCreateMetadata(threadName).addTags(tags));
    }
}

