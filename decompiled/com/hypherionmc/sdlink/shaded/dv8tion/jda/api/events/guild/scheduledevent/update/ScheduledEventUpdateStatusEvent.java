/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update.GenericScheduledEventUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ScheduledEventUpdateStatusEvent
extends GenericScheduledEventUpdateEvent<ScheduledEvent.Status> {
    public static final String IDENTIFIER = "status";

    public ScheduledEventUpdateStatusEvent(@Nonnull JDA api, long responseNumber, @Nonnull ScheduledEvent scheduledEvent, @Nonnull ScheduledEvent.Status previous) {
        super(api, responseNumber, scheduledEvent, previous, scheduledEvent.getStatus(), IDENTIFIER);
    }

    @Nonnull
    public ScheduledEvent.Status getOldStatus() {
        return this.getOldValue();
    }

    @Nonnull
    public ScheduledEvent.Status getNewStatus() {
        return this.getNewValue();
    }

    @Override
    @Nonnull
    public ScheduledEvent.Status getOldValue() {
        return (ScheduledEvent.Status)((Object)super.getOldValue());
    }

    @Override
    @Nonnull
    public ScheduledEvent.Status getNewValue() {
        return (ScheduledEvent.Status)((Object)super.getNewValue());
    }
}

