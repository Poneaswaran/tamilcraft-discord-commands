/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.UpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.GenericRoleEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public abstract class GenericRoleUpdateEvent<T>
extends GenericRoleEvent
implements UpdateEvent<Role, T> {
    protected final T previous;
    protected final T next;
    protected final String identifier;

    public GenericRoleUpdateEvent(@Nonnull JDA api, long responseNumber, @Nonnull Role role, @Nullable T previous, @Nullable T next, @Nonnull String identifier) {
        super(api, responseNumber, role);
        this.previous = previous;
        this.next = next;
        this.identifier = identifier;
    }

    @Override
    @Nonnull
    public Role getEntity() {
        return this.role;
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

