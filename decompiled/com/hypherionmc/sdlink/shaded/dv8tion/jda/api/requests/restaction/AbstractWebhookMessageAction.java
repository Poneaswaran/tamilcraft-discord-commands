/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.FluentRestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface AbstractWebhookMessageAction<T, R extends AbstractWebhookMessageAction<T, R>>
extends FluentRestAction<T, R> {
    @Nonnull
    @CheckReturnValue
    public R setThreadId(@Nullable String var1);

    @Nonnull
    @CheckReturnValue
    default public R setThreadId(long threadId) {
        return this.setThreadId(threadId == 0L ? null : Long.toUnsignedString(threadId));
    }

    @Nonnull
    @CheckReturnValue
    default public R setThread(@Nullable ThreadChannel channel) {
        return this.setThreadId(channel == null ? null : channel.getId());
    }
}

