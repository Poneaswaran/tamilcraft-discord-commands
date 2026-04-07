/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.GenericMessageEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class MessageDeleteEvent
extends GenericMessageEvent {
    public MessageDeleteEvent(@Nonnull JDA api, long responseNumber, long messageId, @Nonnull MessageChannel channel) {
        super(api, responseNumber, messageId, channel);
    }
}

