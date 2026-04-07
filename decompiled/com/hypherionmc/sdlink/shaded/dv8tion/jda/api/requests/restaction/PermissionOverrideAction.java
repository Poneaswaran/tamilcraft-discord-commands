/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public interface PermissionOverrideAction
extends AuditableRestAction<PermissionOverride> {
    @Override
    @Nonnull
    @CheckReturnValue
    public PermissionOverrideAction setCheck(@Nullable BooleanSupplier var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public PermissionOverrideAction timeout(long var1, @Nonnull TimeUnit var3);

    @Override
    @Nonnull
    @CheckReturnValue
    public PermissionOverrideAction deadline(long var1);

    @Nonnull
    @CheckReturnValue
    default public PermissionOverrideAction reset() {
        return this.resetAllow().resetDeny();
    }

    @Nonnull
    @CheckReturnValue
    public PermissionOverrideAction resetAllow();

    @Nonnull
    @CheckReturnValue
    public PermissionOverrideAction resetDeny();

    @Nonnull
    public IPermissionContainer getChannel();

    @Nullable
    public Role getRole();

    @Nullable
    public Member getMember();

    @Nonnull
    default public Guild getGuild() {
        return this.getChannel().getGuild();
    }

    public long getAllowed();

    @Nonnull
    default public EnumSet<Permission> getAllowedPermissions() {
        return Permission.getPermissions(this.getAllowed());
    }

    public long getDenied();

    @Nonnull
    default public EnumSet<Permission> getDeniedPermissions() {
        return Permission.getPermissions(this.getDenied());
    }

    public long getInherited();

    @Nonnull
    default public EnumSet<Permission> getInheritedPermissions() {
        return Permission.getPermissions(this.getInherited());
    }

    public boolean isMember();

    public boolean isRole();

    @Nonnull
    @CheckReturnValue
    public PermissionOverrideAction setAllowed(long var1);

    @Nonnull
    @CheckReturnValue
    default public PermissionOverrideAction setAllowed(@Nullable Collection<Permission> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return this.setAllowed(0L);
        }
        Checks.noneNull(permissions, "Permissions");
        return this.setAllowed(Permission.getRaw(permissions));
    }

    @Nonnull
    @CheckReturnValue
    default public PermissionOverrideAction setAllowed(Permission ... permissions) {
        if (permissions == null || permissions.length == 0) {
            return this.setAllowed(0L);
        }
        Checks.noneNull((Object[])permissions, "Permissions");
        return this.setAllowed(Permission.getRaw(permissions));
    }

    @Nonnull
    @CheckReturnValue
    public PermissionOverrideAction grant(long var1);

    @Nonnull
    @CheckReturnValue
    default public PermissionOverrideAction grant(@Nonnull Collection<Permission> permissions) {
        return this.grant(Permission.getRaw(permissions));
    }

    @Nonnull
    @CheckReturnValue
    default public PermissionOverrideAction grant(Permission ... permissions) {
        return this.grant(Permission.getRaw(permissions));
    }

    @Nonnull
    @CheckReturnValue
    public PermissionOverrideAction setDenied(long var1);

    @Nonnull
    @CheckReturnValue
    default public PermissionOverrideAction setDenied(@Nullable Collection<Permission> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return this.setDenied(0L);
        }
        Checks.noneNull(permissions, "Permissions");
        return this.setDenied(Permission.getRaw(permissions));
    }

    @Nonnull
    @CheckReturnValue
    default public PermissionOverrideAction setDenied(Permission ... permissions) {
        if (permissions == null || permissions.length == 0) {
            return this.setDenied(0L);
        }
        Checks.noneNull((Object[])permissions, "Permissions");
        return this.setDenied(Permission.getRaw(permissions));
    }

    @Nonnull
    @CheckReturnValue
    public PermissionOverrideAction deny(long var1);

    @Nonnull
    @CheckReturnValue
    default public PermissionOverrideAction deny(@Nonnull Collection<Permission> permissions) {
        return this.deny(Permission.getRaw(permissions));
    }

    @Nonnull
    @CheckReturnValue
    default public PermissionOverrideAction deny(Permission ... permissions) {
        return this.deny(Permission.getRaw(permissions));
    }

    @Nonnull
    @CheckReturnValue
    public PermissionOverrideAction clear(long var1);

    @Nonnull
    @CheckReturnValue
    default public PermissionOverrideAction clear(@Nonnull Collection<Permission> permissions) {
        return this.clear(Permission.getRaw(permissions));
    }

    @Nonnull
    @CheckReturnValue
    default public PermissionOverrideAction clear(Permission ... permissions) {
        return this.clear(Permission.getRaw(permissions));
    }

    @Nonnull
    @CheckReturnValue
    public PermissionOverrideAction setPermissions(long var1, long var3);

    @Nonnull
    @CheckReturnValue
    default public PermissionOverrideAction setPermissions(@Nullable Collection<Permission> grantPermissions, @Nullable Collection<Permission> denyPermissions) {
        return this.setAllowed(grantPermissions).setDenied(denyPermissions);
    }
}

