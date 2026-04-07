/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collection;
import java.util.EnumSet;

public interface IPermissionHolder
extends ISnowflake {
    @Nonnull
    public Guild getGuild();

    @Nonnull
    public EnumSet<Permission> getPermissions();

    @Nonnull
    public EnumSet<Permission> getPermissions(@Nonnull GuildChannel var1);

    @Nonnull
    public EnumSet<Permission> getPermissionsExplicit();

    @Nonnull
    public EnumSet<Permission> getPermissionsExplicit(@Nonnull GuildChannel var1);

    public boolean hasPermission(Permission ... var1);

    public boolean hasPermission(@Nonnull Collection<Permission> var1);

    public boolean hasPermission(@Nonnull GuildChannel var1, Permission ... var2);

    public boolean hasPermission(@Nonnull GuildChannel var1, @Nonnull Collection<Permission> var2);

    default public boolean hasAccess(@Nonnull GuildChannel channel) {
        Checks.notNull(channel, "Channel");
        return channel.getType().isAudio() ? this.hasPermission(channel, Permission.VOICE_CONNECT, Permission.VIEW_CHANNEL) : this.hasPermission(channel, Permission.VIEW_CHANNEL);
    }

    public boolean canSync(@Nonnull IPermissionContainer var1, @Nonnull IPermissionContainer var2);

    public boolean canSync(@Nonnull IPermissionContainer var1);
}

