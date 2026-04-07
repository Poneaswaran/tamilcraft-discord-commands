/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.override;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.override.GenericPermissionOverrideEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.EnumSet;

public class PermissionOverrideUpdateEvent
extends GenericPermissionOverrideEvent {
    private final long oldAllow;
    private final long oldDeny;

    public PermissionOverrideUpdateEvent(@Nonnull JDA api, long responseNumber, @Nonnull IPermissionContainer channel, @Nonnull PermissionOverride override, long oldAllow, long oldDeny) {
        super(api, responseNumber, channel, override);
        this.oldAllow = oldAllow;
        this.oldDeny = oldDeny;
    }

    public long getOldAllowRaw() {
        return this.oldAllow;
    }

    public long getOldDenyRaw() {
        return this.oldDeny;
    }

    public long getOldInheritedRaw() {
        return (this.oldAllow | this.oldDeny) ^ 0xFFFFFFFFFFFFFFFFL;
    }

    @Nonnull
    public EnumSet<Permission> getOldAllow() {
        return Permission.getPermissions(this.oldAllow);
    }

    @Nonnull
    public EnumSet<Permission> getOldDeny() {
        return Permission.getPermissions(this.oldDeny);
    }

    @Nonnull
    public EnumSet<Permission> getOldInherited() {
        return Permission.getPermissions(this.getOldInheritedRaw());
    }
}

