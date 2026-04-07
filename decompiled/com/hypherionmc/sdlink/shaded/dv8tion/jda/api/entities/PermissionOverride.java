/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IPermissionHolder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.IPermissionContainerUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.PermissionOverrideAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.EnumSet;

public interface PermissionOverride
extends ISnowflake {
    public long getAllowedRaw();

    public long getInheritRaw();

    public long getDeniedRaw();

    @Nonnull
    public EnumSet<Permission> getAllowed();

    @Nonnull
    public EnumSet<Permission> getInherit();

    @Nonnull
    public EnumSet<Permission> getDenied();

    @Nonnull
    public JDA getJDA();

    @Nullable
    public IPermissionHolder getPermissionHolder();

    @Nullable
    public Member getMember();

    @Nullable
    public Role getRole();

    @Nonnull
    public IPermissionContainerUnion getChannel();

    @Nonnull
    public Guild getGuild();

    public boolean isMemberOverride();

    public boolean isRoleOverride();

    @Nonnull
    @CheckReturnValue
    public PermissionOverrideAction getManager();

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> delete();
}

