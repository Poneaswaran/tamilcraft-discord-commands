/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.update.GenericRoleUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class RoleUpdateNameEvent
extends GenericRoleUpdateEvent<String> {
    public static final String IDENTIFIER = "name";

    public RoleUpdateNameEvent(@Nonnull JDA api, long responseNumber, @Nonnull Role role, @Nonnull String oldName) {
        super(api, responseNumber, role, oldName, role.getName(), IDENTIFIER);
    }

    @Nonnull
    public String getOldName() {
        return this.getOldValue();
    }

    @Nonnull
    public String getNewName() {
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

