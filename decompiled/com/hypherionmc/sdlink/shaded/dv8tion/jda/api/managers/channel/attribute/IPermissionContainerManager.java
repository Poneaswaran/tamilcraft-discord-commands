/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IPermissionHolder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.ChannelManager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collection;

public interface IPermissionContainerManager<T extends IPermissionContainer, M extends IPermissionContainerManager<T, M>>
extends ChannelManager<T, M> {
    @Nonnull
    @CheckReturnValue
    public M clearOverridesAdded();

    @Nonnull
    @CheckReturnValue
    public M clearOverridesRemoved();

    @Nonnull
    @CheckReturnValue
    public M putPermissionOverride(@Nonnull IPermissionHolder var1, long var2, long var4);

    @Nonnull
    @CheckReturnValue
    default public M putPermissionOverride(@Nonnull IPermissionHolder permHolder, @Nullable Collection<Permission> allow, @Nullable Collection<Permission> deny) {
        long allowRaw = allow == null ? 0L : Permission.getRaw(allow);
        long denyRaw = deny == null ? 0L : Permission.getRaw(deny);
        return this.putPermissionOverride(permHolder, allowRaw, denyRaw);
    }

    @Nonnull
    @CheckReturnValue
    public M putRolePermissionOverride(long var1, long var3, long var5);

    @Nonnull
    @CheckReturnValue
    default public M putRolePermissionOverride(long roleId, @Nullable Collection<Permission> allow, @Nullable Collection<Permission> deny) {
        long allowRaw = allow == null ? 0L : Permission.getRaw(allow);
        long denyRaw = deny == null ? 0L : Permission.getRaw(deny);
        return this.putRolePermissionOverride(roleId, allowRaw, denyRaw);
    }

    @Nonnull
    @CheckReturnValue
    public M putMemberPermissionOverride(long var1, long var3, long var5);

    @Nonnull
    @CheckReturnValue
    default public M putMemberPermissionOverride(long memberId, @Nullable Collection<Permission> allow, @Nullable Collection<Permission> deny) {
        long allowRaw = allow == null ? 0L : Permission.getRaw(allow);
        long denyRaw = deny == null ? 0L : Permission.getRaw(deny);
        return this.putMemberPermissionOverride(memberId, allowRaw, denyRaw);
    }

    @Nonnull
    @CheckReturnValue
    public M removePermissionOverride(@Nonnull IPermissionHolder var1);

    @Nonnull
    @CheckReturnValue
    public M removePermissionOverride(long var1);
}

