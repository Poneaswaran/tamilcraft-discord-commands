/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update.GenericScheduledEventUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class ScheduledEventUpdateDescriptionEvent
extends GenericScheduledEventUpdateEvent<String> {
    public static final String IDENTIFIER = "description";

    public ScheduledEventUpdateDescriptionEvent(@Nonnull JDA api, long responseNumber, @Nonnull ScheduledEvent scheduledEvent, @Nullable String previous) {
        super(api, responseNumber, scheduledEvent, previous, scheduledEvent.getDescription(), IDENTIFIER);
    }

    @Nullable
    public String getOldDescription() {
        return (String)this.getOldValue();
    }

    @Nullable
    public String getNewDescription() {
        return (String)this.getNewValue();
    }
}

