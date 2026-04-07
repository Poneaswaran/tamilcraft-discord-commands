/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update.GenericScheduledEventUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.time.OffsetDateTime;

public class ScheduledEventUpdateStartTimeEvent
extends GenericScheduledEventUpdateEvent<OffsetDateTime> {
    public static final String IDENTIFIER = "start_time";

    public ScheduledEventUpdateStartTimeEvent(@Nonnull JDA api, long responseNumber, @Nonnull ScheduledEvent scheduledEvent, @Nonnull OffsetDateTime previous) {
        super(api, responseNumber, scheduledEvent, previous, scheduledEvent.getStartTime(), IDENTIFIER);
    }

    @Nonnull
    public OffsetDateTime getOldStartTime() {
        return this.getOldValue();
    }

    @Nonnull
    public OffsetDateTime getNewStartTime() {
        return this.getNewValue();
    }

    @Override
    @Nonnull
    public OffsetDateTime getOldValue() {
        return (OffsetDateTime)super.getOldValue();
    }

    @Override
    @Nonnull
    public OffsetDateTime getNewValue() {
        return (OffsetDateTime)super.getNewValue();
    }
}

