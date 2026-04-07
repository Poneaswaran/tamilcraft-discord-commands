/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.UpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.GenericScheduledEventGatewayEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public abstract class GenericScheduledEventUpdateEvent<T>
extends GenericScheduledEventGatewayEvent
implements UpdateEvent<ScheduledEvent, T> {
    protected final T previous;
    protected final T next;
    protected final String identifier;

    public GenericScheduledEventUpdateEvent(@Nonnull JDA api, long responseNumber, @Nonnull ScheduledEvent scheduledEvent, @Nullable T previous, @Nullable T next, @Nonnull String identifier) {
        super(api, responseNumber, scheduledEvent);
        this.previous = previous;
        this.next = next;
        this.identifier = identifier;
    }

    @Override
    @Nonnull
    public ScheduledEvent getEntity() {
        return this.getScheduledEvent();
    }

    @Override
    @Nonnull
    public String getPropertyIdentifier() {
        return this.identifier;
    }

    @Override
    @Nullable
    public T getOldValue() {
        return this.previous;
    }

    @Override
    @Nullable
    public T getNewValue() {
        return this.next;
    }

    @Override
    public String toString() {
        return "ScheduledEventUpdate[" + this.getPropertyIdentifier() + "](" + this.getOldValue() + "->" + this.getNewValue() + ')';
    }
}

