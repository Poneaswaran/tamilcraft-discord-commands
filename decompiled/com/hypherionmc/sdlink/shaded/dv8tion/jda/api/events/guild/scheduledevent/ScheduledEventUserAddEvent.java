/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.GenericScheduledEventUserEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ScheduledEventUserAddEvent
extends GenericScheduledEventUserEvent {
    public ScheduledEventUserAddEvent(@Nonnull JDA api, long responseNumber, @Nonnull ScheduledEvent scheduledEvent, long userId) {
        super(api, responseNumber, scheduledEvent, userId);
    }
}

