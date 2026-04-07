/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.RoleIcon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.update.GenericRoleUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class RoleUpdateIconEvent
extends GenericRoleUpdateEvent<RoleIcon> {
    public static final String IDENTIFIER = "icon";

    public RoleUpdateIconEvent(@Nonnull JDA api, long responseNumber, @Nonnull Role role, @Nullable RoleIcon oldIcon) {
        super(api, responseNumber, role, oldIcon, role.getIcon(), IDENTIFIER);
    }

    @Nullable
    public RoleIcon getOldIcon() {
        return (RoleIcon)this.getOldValue();
    }

    @Nullable
    public RoleIcon getNewIcon() {
        return (RoleIcon)this.getNewValue();
    }
}

