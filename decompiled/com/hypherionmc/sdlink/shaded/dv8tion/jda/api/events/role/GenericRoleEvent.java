/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public abstract class GenericRoleEvent
extends Event {
    protected final Role role;

    public GenericRoleEvent(@Nonnull JDA api, long responseNumber, @Nonnull Role role) {
        super(api, responseNumber);
        this.role = role;
    }

    @Nonnull
    public Role getRole() {
        return this.role;
    }

    @Nonnull
    public Guild getGuild() {
        return this.role.getGuild();
    }
}

