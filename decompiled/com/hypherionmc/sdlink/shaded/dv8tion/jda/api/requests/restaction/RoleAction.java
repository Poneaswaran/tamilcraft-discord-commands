/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Icon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.UnicodeEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.awt.Color;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public interface RoleAction
extends AuditableRestAction<Role> {
    @Override
    @Nonnull
    @CheckReturnValue
    public RoleAction setCheck(@Nullable BooleanSupplier var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public RoleAction timeout(long var1, @Nonnull TimeUnit var3);

    @Override
    @Nonnull
    @CheckReturnValue
    public RoleAction deadline(long var1);

    @Nonnull
    public Guild getGuild();

    @Nonnull
    @CheckReturnValue
    public RoleAction setName(@Nullable String var1);

    @Nonnull
    @CheckReturnValue
    public RoleAction setHoisted(@Nullable Boolean var1);

    @Nonnull
    @CheckReturnValue
    public RoleAction setMentionable(@Nullable Boolean var1);

    @Nonnull
    @CheckReturnValue
    default public RoleAction setColor(@Nullable Color color) {
        return this.setColor(color != null ? Integer.valueOf(color.getRGB()) : null);
    }

    @Nonnull
    @CheckReturnValue
    public RoleAction setColor(@Nullable Integer var1);

    @Nonnull
    @CheckReturnValue
    default public RoleAction setPermissions(Permission ... permissions) {
        if (permissions != null) {
            Checks.noneNull((Object[])permissions, "Permissions");
        }
        return this.setPermissions(permissions == null ? null : Long.valueOf(Permission.getRaw(permissions)));
    }

    @Nonnull
    @CheckReturnValue
    default public RoleAction setPermissions(@Nullable Collection<Permission> permissions) {
        if (permissions != null) {
            Checks.noneNull(permissions, "Permissions");
        }
        return this.setPermissions(permissions == null ? null : Long.valueOf(Permission.getRaw(permissions)));
    }

    @Nonnull
    @CheckReturnValue
    public RoleAction setPermissions(@Nullable Long var1);

    @Nonnull
    @CheckReturnValue
    public RoleAction setIcon(@Nullable Icon var1);

    @Nonnull
    @CheckReturnValue
    public RoleAction setIcon(@Nullable String var1);

    @Nonnull
    @CheckReturnValue
    default public RoleAction setIcon(@Nullable UnicodeEmoji emoji) {
        return this.setIcon(emoji == null ? null : emoji.getFormatted());
    }
}

