/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.GenericRoleEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class RoleDeleteEvent
extends GenericRoleEvent {
    public RoleDeleteEvent(@Nonnull JDA api, long responseNumber, @Nonnull Role deletedRole) {
        super(api, responseNumber, deletedRole);
    }
}

