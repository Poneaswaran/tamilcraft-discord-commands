/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.GenericScheduledEventGatewayEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ScheduledEventCreateEvent
extends GenericScheduledEventGatewayEvent {
    public ScheduledEventCreateEvent(@Nonnull JDA api, long responseNumber, @Nonnull ScheduledEvent scheduledEvent) {
        super(api, responseNumber, scheduledEvent);
    }
}

