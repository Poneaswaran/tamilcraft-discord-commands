/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface InteractionCallbackAction<T>
extends RestAction<T> {
    @Nonnull
    @CheckReturnValue
    public InteractionCallbackAction<T> closeResources();

    public static enum ResponseType {
        CHANNEL_MESSAGE_WITH_SOURCE(4),
        DEFERRED_CHANNEL_MESSAGE_WITH_SOURCE(5),
        DEFERRED_MESSAGE_UPDATE(6),
        MESSAGE_UPDATE(7),
        COMMAND_AUTOCOMPLETE_CHOICES(8),
        MODAL(9),
        PREMIUM_REQUIRED(10);

        private final int raw;

        private ResponseType(int raw) {
            this.raw = raw;
        }

        public int getRaw() {
            return this.raw;
        }
    }
}

