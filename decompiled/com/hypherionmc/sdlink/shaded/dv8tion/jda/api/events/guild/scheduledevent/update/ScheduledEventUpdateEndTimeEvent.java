/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update.GenericScheduledEventUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.OffsetDateTime;

public class ScheduledEventUpdateEndTimeEvent
extends GenericScheduledEventUpdateEvent<OffsetDateTime> {
    public static final String IDENTIFIER = "end_time";

    public ScheduledEventUpdateEndTimeEvent(@Nonnull JDA api, long responseNumber, @Nonnull ScheduledEvent scheduledEvent, @Nullable OffsetDateTime previous) {
        super(api, responseNumber, scheduledEvent, previous, scheduledEvent.getEndTime(), IDENTIFIER);
    }

    @Nullable
    public OffsetDateTime getOldEndTime() {
        return (OffsetDateTime)this.getOldValue();
    }

    @Nullable
    public OffsetDateTime getNewEndTime() {
        return (OffsetDateTime)this.getNewValue();
    }
}

