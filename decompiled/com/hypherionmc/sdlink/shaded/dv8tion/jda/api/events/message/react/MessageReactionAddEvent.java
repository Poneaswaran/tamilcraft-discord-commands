/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.react;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageReaction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class MessageReactionAddEvent
extends GenericMessageReactionEvent {
    private final long messageAuthorId;

    public MessageReactionAddEvent(@Nonnull JDA api, long responseNumber, @Nullable User user, @Nullable Member member, @Nonnull MessageReaction reaction, long userId, long messageAuthorId) {
        super(api, responseNumber, user, member, reaction, userId);
        this.messageAuthorId = messageAuthorId;
    }

    @Nonnull
    public String getMessageAuthorId() {
        return Long.toUnsignedString(this.messageAuthorId);
    }

    public long getMessageAuthorIdLong() {
        return this.messageAuthorId;
    }
}

