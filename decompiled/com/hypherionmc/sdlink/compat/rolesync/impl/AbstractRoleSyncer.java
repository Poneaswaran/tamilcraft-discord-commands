/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.compat.rolesync.impl;

import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.sdlink.api.accounts.MinecraftAccount;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import java.util.List;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractRoleSyncer {
    private final Supplier<Boolean> isSyncActive;
    boolean ignoreEvent = false;

    public AbstractRoleSyncer(Supplier<Boolean> isSyncActive) {
        this.isSyncActive = isSyncActive;
    }

    public abstract void sync(CraterPlayer var1, List<Role> var2, Guild var3, Member var4);

    public void discordRoleAddedToMember(Member member, Role role, Guild guild) {
        if (this.ignoreEvent || !this.isSyncActive.get().booleanValue()) {
            return;
        }
        this.discordRoleChanged(member, guild, role, true);
    }

    public void discordRoleRemovedFromMember(Member member, Role role, Guild guild, MinecraftAccount oldAccount) {
        if (this.ignoreEvent || !this.isSyncActive.get().booleanValue()) {
            return;
        }
        this.discordRoleChanged(member, guild, role, false, oldAccount);
    }

    void discordRoleChanged(Member member, Guild guild, Role role, boolean added) {
        this.discordRoleChanged(member, guild, role, added, null);
    }

    abstract void discordRoleChanged(Member var1, Guild var2, Role var3, boolean var4, @Nullable MinecraftAccount var5);
}

