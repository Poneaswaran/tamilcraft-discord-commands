/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.update.GenericRoleUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.EnumSet;

public class RoleUpdatePermissionsEvent
extends GenericRoleUpdateEvent<EnumSet<Permission>> {
    public static final String IDENTIFIER = "permission";
    private final long oldPermissionsRaw;
    private final long newPermissionsRaw;

    public RoleUpdatePermissionsEvent(@Nonnull JDA api, long responseNumber, @Nonnull Role role, long oldPermissionsRaw) {
        super(api, responseNumber, role, Permission.getPermissions(oldPermissionsRaw), role.getPermissions(), IDENTIFIER);
        this.oldPermissionsRaw = oldPermissionsRaw;
        this.newPermissionsRaw = role.getPermissionsRaw();
    }

    @Nonnull
    public EnumSet<Permission> getOldPermissions() {
        return this.getOldValue();
    }

    public long getOldPermissionsRaw() {
        return this.oldPermissionsRaw;
    }

    @Nonnull
    public EnumSet<Permission> getNewPermissions() {
        return this.getNewValue();
    }

    public long getNewPermissionsRaw() {
        return this.newPermissionsRaw;
    }

    @Override
    @Nonnull
    public EnumSet<Permission> getOldValue() {
        return (EnumSet)super.getOldValue();
    }

    @Override
    @Nonnull
    public EnumSet<Permission> getNewValue() {
        return (EnumSet)super.getNewValue();
    }
}

