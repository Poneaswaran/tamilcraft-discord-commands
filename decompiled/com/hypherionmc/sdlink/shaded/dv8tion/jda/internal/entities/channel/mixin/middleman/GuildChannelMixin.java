/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.MissingAccessException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.ChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface GuildChannelMixin<T extends GuildChannelMixin<T>>
extends GuildChannel,
GuildChannelUnion,
ChannelMixin<T> {
    @Override
    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Void> delete() {
        this.checkCanAccess();
        this.checkCanManage();
        Route.CompiledRoute route = Route.Channels.DELETE_CHANNEL.compile(this.getId());
        return new AuditableRestActionImpl<Void>(this.getJDA(), route);
    }

    default public boolean hasPermission(Permission permission) {
        IPermissionContainer permChannel = this.getPermissionContainer();
        return this.getGuild().getSelfMember().hasPermission((GuildChannel)permChannel, permission);
    }

    default public void checkPermission(Permission permission) {
        this.checkPermission(permission, null);
    }

    default public void checkPermission(Permission permission, String message) {
        if (!this.hasPermission(permission)) {
            if (message != null) {
                throw new InsufficientPermissionException(this, permission, message);
            }
            throw new InsufficientPermissionException(this, permission);
        }
    }

    default public void checkCanManage() {
        this.checkPermission(Permission.MANAGE_CHANNEL);
    }

    @Override
    default public void checkCanAccess() {
        if (!this.hasPermission(Permission.VIEW_CHANNEL)) {
            throw new MissingAccessException(this, Permission.VIEW_CHANNEL);
        }
    }
}

