/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Icon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.UnicodeEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.Manager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.awt.Color;
import java.util.Arrays;
import java.util.Collection;

public interface RoleManager
extends Manager<RoleManager> {
    public static final long NAME = 1L;
    public static final long COLOR = 2L;
    public static final long PERMISSION = 4L;
    public static final long HOIST = 8L;
    public static final long MENTIONABLE = 16L;
    public static final long ICON = 32L;

    @Override
    @Nonnull
    @CheckReturnValue
    public RoleManager reset(long var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public RoleManager reset(long ... var1);

    @Nonnull
    public Role getRole();

    @Nonnull
    default public Guild getGuild() {
        return this.getRole().getGuild();
    }

    @Nonnull
    @CheckReturnValue
    public RoleManager setName(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    public RoleManager setPermissions(long var1);

    @Nonnull
    @CheckReturnValue
    default public RoleManager setPermissions(Permission ... permissions) {
        Checks.notNull(permissions, "Permissions");
        return this.setPermissions(Arrays.asList(permissions));
    }

    @Nonnull
    @CheckReturnValue
    default public RoleManager setPermissions(@Nonnull Collection<Permission> permissions) {
        Checks.noneNull(permissions, "Permissions");
        return this.setPermissions(Permission.getRaw(permissions));
    }

    @Nonnull
    @CheckReturnValue
    default public RoleManager setColor(@Nullable Color color) {
        return this.setColor(color == null ? 0x1FFFFFFF : color.getRGB());
    }

    @Nonnull
    @CheckReturnValue
    public RoleManager setColor(int var1);

    @Nonnull
    @CheckReturnValue
    public RoleManager setHoisted(boolean var1);

    @Nonnull
    @CheckReturnValue
    public RoleManager setMentionable(boolean var1);

    @Nonnull
    @CheckReturnValue
    public RoleManager setIcon(@Nullable Icon var1);

    @Nonnull
    @CheckReturnValue
    public RoleManager setIcon(@Nullable String var1);

    @Nonnull
    @CheckReturnValue
    default public RoleManager setIcon(@Nullable UnicodeEmoji emoji) {
        return this.setIcon(emoji == null ? null : emoji.getFormatted());
    }

    @Nonnull
    @CheckReturnValue
    default public RoleManager givePermissions(Permission ... perms) {
        Checks.notNull(perms, "Permissions");
        return this.givePermissions(Arrays.asList(perms));
    }

    @Nonnull
    @CheckReturnValue
    public RoleManager givePermissions(@Nonnull Collection<Permission> var1);

    @Nonnull
    @CheckReturnValue
    default public RoleManager revokePermissions(Permission ... perms) {
        Checks.notNull(perms, "Permissions");
        return this.revokePermissions(Arrays.asList(perms));
    }

    @Nonnull
    @CheckReturnValue
    public RoleManager revokePermissions(@Nonnull Collection<Permission> var1);
}

