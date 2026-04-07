/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.GenericMessageEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class MessageReceivedEvent
extends GenericMessageEvent {
    private final Message message;

    public MessageReceivedEvent(@Nonnull JDA api, long responseNumber, @Nonnull Message message) {
        super(api, responseNumber, message.getIdLong(), message.getChannel());
        this.message = message;
    }

    @Nonnull
    public Message getMessage() {
        return this.message;
    }

    @Nonnull
    public User getAuthor() {
        return this.message.getAuthor();
    }

    @Nullable
    public Member getMember() {
        return this.message.getMember();
    }

    public boolean isWebhookMessage() {
        return this.getMessage().isWebhookMessage();
    }
}

