/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.GenericRoleEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class RoleCreateEvent
extends GenericRoleEvent {
    public RoleCreateEvent(@Nonnull JDA api, long responseNumber, @Nonnull Role createdRole) {
        super(api, responseNumber, createdRole);
    }
}

