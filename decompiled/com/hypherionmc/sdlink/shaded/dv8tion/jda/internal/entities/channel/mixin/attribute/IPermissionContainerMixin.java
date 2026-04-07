/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IPermissionHolder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.IPermissionContainerUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.PermissionOverrideAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.GuildChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.PermissionOverrideActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public interface IPermissionContainerMixin<T extends IPermissionContainerMixin<T>>
extends IPermissionContainer,
IPermissionContainerUnion,
GuildChannelMixin<T> {
    @Override
    default public PermissionOverride getPermissionOverride(@Nonnull IPermissionHolder permissionHolder) {
        Checks.notNull(permissionHolder, "Permission Holder");
        Checks.check(permissionHolder.getGuild().equals(this.getGuild()), "Provided permission holder is not from the same guild as this channel!");
        TLongObjectMap<PermissionOverride> overrides = this.getPermissionOverrideMap();
        return overrides.get(permissionHolder.getIdLong());
    }

    @Override
    @Nonnull
    default public List<PermissionOverride> getPermissionOverrides() {
        TLongObjectMap<PermissionOverride> overrides = this.getPermissionOverrideMap();
        return Arrays.asList(overrides.values((PermissionOverride[])new PermissionOverride[overrides.size()]));
    }

    @Override
    @Nonnull
    default public PermissionOverrideAction upsertPermissionOverride(@Nonnull IPermissionHolder permissionHolder) {
        this.checkPermission(Permission.MANAGE_PERMISSIONS);
        Checks.notNull(permissionHolder, "PermissionHolder");
        Checks.check(permissionHolder.getGuild().equals(this.getGuild()), "Provided permission holder is not from the same guild as this channel!");
        PermissionOverride override = this.getPermissionOverride(permissionHolder);
        if (override != null) {
            return override.getManager();
        }
        return new PermissionOverrideActionImpl(this.getJDA(), this, permissionHolder);
    }

    @Override
    @Nonnull
    default public IPermissionContainer getPermissionContainer() {
        return this;
    }

    public TLongObjectMap<PermissionOverride> getPermissionOverrideMap();
}

