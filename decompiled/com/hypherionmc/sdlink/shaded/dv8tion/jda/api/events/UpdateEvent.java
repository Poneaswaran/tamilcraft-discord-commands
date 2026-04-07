/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.GenericEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface UpdateEvent<E, T>
extends GenericEvent {
    @Nonnull
    default public Class<E> getEntityType() {
        return this.getEntity().getClass();
    }

    @Nonnull
    public String getPropertyIdentifier();

    @Nonnull
    public E getEntity();

    @Nullable
    public T getOldValue();

    @Nullable
    public T getNewValue();
}

