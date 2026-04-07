/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.self;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.SelfUser;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.UpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public abstract class GenericSelfUpdateEvent<T>
extends Event
implements UpdateEvent<SelfUser, T> {
    protected final T previous;
    protected final T next;
    protected final String identifier;

    public GenericSelfUpdateEvent(@Nonnull JDA api, long responseNumber, @Nullable T previous, @Nullable T next, @Nonnull String identifier) {
        super(api, responseNumber);
        this.previous = previous;
        this.next = next;
        this.identifier = identifier;
    }

    @Nonnull
    public SelfUser getSelfUser() {
        return this.api.getSelfUser();
    }

    @Override
    @Nonnull
    public SelfUser getEntity() {
        return this.getSelfUser();
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
}

