/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.GenericChannelEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ChannelCreateEvent
extends GenericChannelEvent {
    public ChannelCreateEvent(@Nonnull JDA api, long responseNumber, Channel channel) {
        super(api, responseNumber, channel);
    }
}

