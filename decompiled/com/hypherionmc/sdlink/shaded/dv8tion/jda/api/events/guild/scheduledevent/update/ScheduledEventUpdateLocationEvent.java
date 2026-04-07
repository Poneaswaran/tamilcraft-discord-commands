/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update.GenericScheduledEventUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ScheduledEventUpdateLocationEvent
extends GenericScheduledEventUpdateEvent<String> {
    public static final String IDENTIFIER = "location";

    public ScheduledEventUpdateLocationEvent(@Nonnull JDA api, long responseNumber, @Nonnull ScheduledEvent scheduledEvent, @Nonnull String previous) {
        super(api, responseNumber, scheduledEvent, previous, scheduledEvent.getLocation(), IDENTIFIER);
    }

    @Nonnull
    public String getOldLocation() {
        return this.getOldValue();
    }

    @Nonnull
    public String getNewLocation() {
        return this.getNewValue();
    }

    @Override
    @Nonnull
    public String getOldValue() {
        return (String)super.getOldValue();
    }

    @Override
    @Nonnull
    public String getNewValue() {
        return (String)super.getNewValue();
    }
}

