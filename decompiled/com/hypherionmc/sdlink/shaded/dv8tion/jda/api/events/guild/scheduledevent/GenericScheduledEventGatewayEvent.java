/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GenericGuildEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public abstract class GenericScheduledEventGatewayEvent
extends GenericGuildEvent {
    protected final ScheduledEvent scheduledEvent;

    public GenericScheduledEventGatewayEvent(@Nonnull JDA api, long responseNumber, @Nonnull ScheduledEvent scheduledEvent) {
        super(api, responseNumber, scheduledEvent.getGuild());
        this.scheduledEvent = scheduledEvent;
    }

    @Nonnull
    public ScheduledEvent getScheduledEvent() {
        return this.scheduledEvent;
    }
}

