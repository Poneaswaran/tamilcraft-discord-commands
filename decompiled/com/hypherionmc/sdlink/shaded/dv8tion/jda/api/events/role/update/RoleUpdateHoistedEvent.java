/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.update.GenericRoleUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class RoleUpdateHoistedEvent
extends GenericRoleUpdateEvent<Boolean> {
    public static final String IDENTIFIER = "hoist";

    public RoleUpdateHoistedEvent(@Nonnull JDA api, long responseNumber, @Nonnull Role role, boolean wasHoisted) {
        super(api, responseNumber, role, wasHoisted, !wasHoisted, IDENTIFIER);
    }

    public boolean wasHoisted() {
        return this.getOldValue();
    }

    @Override
    @Nonnull
    public Boolean getOldValue() {
        return (Boolean)super.getOldValue();
    }

    @Override
    @Nonnull
    public Boolean getNewValue() {
        return (Boolean)super.getNewValue();
    }
}

