/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.api.compat.playerroles.BridgedPlayerRoles
 *  com.hypherionmc.craterlib.api.compat.playerroles.PlayerRolesCompat
 *  com.hypherionmc.craterlib.api.events.compat.PlayerRolesEvents$RoleAddedEvent
 *  com.hypherionmc.craterlib.api.events.compat.PlayerRolesEvents$RoleRemovedEvent
 *  com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile
 *  com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer
 *  com.hypherionmc.craterlib.core.event.annot.CraterEventListener
 */
package com.hypherionmc.sdlink.compat.rolesync.impl;

import com.hypherionmc.craterlib.api.compat.playerroles.BridgedPlayerRoles;
import com.hypherionmc.craterlib.api.compat.playerroles.PlayerRolesCompat;
import com.hypherionmc.craterlib.api.events.compat.PlayerRolesEvents;
import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.core.event.annot.CraterEventListener;
import com.hypherionmc.sdlink.api.accounts.DiscordUser;
import com.hypherionmc.sdlink.api.accounts.MinecraftAccount;
import com.hypherionmc.sdlink.compat.rolesync.impl.AbstractRoleSyncer;
import com.hypherionmc.sdlink.core.config.SDLinkCompatConfig;
import com.hypherionmc.sdlink.core.config.impl.compat.RoleSyncCompat;
import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.core.managers.RoleManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.UserSnowflake;
import java.util.List;
import java.util.Optional;

public final class PlayerRolesSync
extends AbstractRoleSyncer {
    public static final PlayerRolesSync INSTANCE = new PlayerRolesSync();

    private PlayerRolesSync() {
        super(() -> SDLinkCompatConfig.INSTANCE.common.playerroles && SDLinkCompatConfig.INSTANCE.playerroles.syncToMinecraft);
    }

    @Override
    public void sync(CraterPlayer p, List<Role> roles, Guild guild, Member member) {
        Optional<RoleSyncCompat.Sync> sync;
        List ranks;
        if (SDLinkCompatConfig.INSTANCE.playerroles.syncToMinecraft) {
            for (Role role : roles) {
                Optional<RoleSyncCompat.Sync> sync2 = SDLinkCompatConfig.INSTANCE.playerroles.syncs.stream().filter(s -> s.role.equalsIgnoreCase(role.getId())).findFirst();
                sync2.ifPresent(s -> {
                    if (!PlayerRolesCompat.INSTANCE.hasRole(p.getGameProfile(), s.rank)) {
                        this.ignoreEvent = true;
                        PlayerRolesCompat.INSTANCE.addRole(p.getGameProfile(), s.rank);
                        this.ignoreEvent = false;
                    }
                });
            }
            ranks = PlayerRolesCompat.INSTANCE.getRoles(p.getGameProfile());
            for (String rank : ranks) {
                sync = SDLinkCompatConfig.INSTANCE.playerroles.syncs.stream().filter(s -> s.rank.equalsIgnoreCase(rank)).findFirst();
                sync.ifPresent(s -> {
                    if (roles.stream().noneMatch(r -> r.getId().equalsIgnoreCase(s.role)) && PlayerRolesCompat.INSTANCE.hasRole(p.getGameProfile(), s.rank)) {
                        this.ignoreEvent = true;
                        PlayerRolesCompat.INSTANCE.removeRole(p.getGameProfile(), s.rank);
                        this.ignoreEvent = false;
                    }
                });
            }
        }
        if (SDLinkCompatConfig.INSTANCE.playerroles.syncToDiscord) {
            ranks = PlayerRolesCompat.INSTANCE.getRoles(p.getGameProfile());
            for (String rank : ranks) {
                sync = SDLinkCompatConfig.INSTANCE.playerroles.syncs.stream().filter(s -> s.rank.equalsIgnoreCase(rank)).findFirst();
                sync.ifPresent(s -> {
                    if (roles.stream().noneMatch(r -> r.getId().equalsIgnoreCase(s.role))) {
                        Optional<Role> r2 = RoleManager.getPlayerRoleRoles().stream().filter(rr -> rr.getId().equalsIgnoreCase(s.role)).findFirst();
                        if (r2.isEmpty()) {
                            return;
                        }
                        this.ignoreEvent = true;
                        guild.addRoleToMember(UserSnowflake.fromId(member.getId()), r2.get()).queue(suc -> {
                            this.ignoreEvent = false;
                        });
                    }
                });
            }
            for (Role role : roles) {
                sync = SDLinkCompatConfig.INSTANCE.playerroles.syncs.stream().filter(s -> s.role.equalsIgnoreCase(role.getId())).findFirst();
                if (!sync.isPresent() || PlayerRolesCompat.INSTANCE.hasRole(p.getGameProfile(), sync.get().rank)) continue;
                Optional<Role> r = RoleManager.getPlayerRoleRoles().stream().filter(rr -> rr.getId().equalsIgnoreCase(((RoleSyncCompat.Sync)sync.get()).role)).findFirst();
                if (r.isEmpty()) {
                    return;
                }
                this.ignoreEvent = true;
                guild.removeRoleFromMember(UserSnowflake.fromId(member.getId()), r.get()).queue(suc -> {
                    this.ignoreEvent = false;
                });
            }
        }
    }

    @Override
    void discordRoleChanged(Member member, Guild guild, Role role, boolean add, MinecraftAccount oldAccount) {
        MinecraftAccount account;
        MinecraftAccount minecraftAccount = account = oldAccount != null ? oldAccount : MinecraftAccount.fromDiscordId(member.getId());
        if (account == null) {
            return;
        }
        RoleSyncCompat.Sync sync = SDLinkCompatConfig.INSTANCE.playerroles.syncs.stream().filter(s -> s.role.equalsIgnoreCase(role.getId())).findFirst().orElse(null);
        if (sync == null) {
            return;
        }
        if (add) {
            if (!PlayerRolesCompat.INSTANCE.hasRole(account.toGameProfile(), sync.rank)) {
                this.ignoreEvent = true;
                PlayerRolesCompat.INSTANCE.addRole(account.toGameProfile(), sync.rank);
                this.ignoreEvent = false;
            }
        } else if (PlayerRolesCompat.INSTANCE.hasRole(account.toGameProfile(), sync.rank)) {
            this.ignoreEvent = true;
            PlayerRolesCompat.INSTANCE.removeRole(account.toGameProfile(), sync.rank);
            if (oldAccount != null) {
                try {
                    guild.removeRoleFromMember(UserSnowflake.fromId(member.getId()), role).queue();
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            this.ignoreEvent = false;
        }
    }

    @CraterEventListener
    public void playerRoleAddedToUser(PlayerRolesEvents.RoleAddedEvent event) {
        if (this.ignoreEvent) {
            return;
        }
        if (!SDLinkCompatConfig.INSTANCE.common.playerroles || !SDLinkCompatConfig.INSTANCE.playerroles.syncToDiscord) {
            return;
        }
        this.updatePlayerRole(event.getProfile(), event.getRole(), true);
    }

    @CraterEventListener
    public void playerRoleRemovedFromUser(PlayerRolesEvents.RoleRemovedEvent event) {
        if (this.ignoreEvent) {
            return;
        }
        if (!SDLinkCompatConfig.INSTANCE.common.playerroles || !SDLinkCompatConfig.INSTANCE.playerroles.syncToDiscord) {
            return;
        }
        this.updatePlayerRole(event.getProfile(), event.getRole(), false);
    }

    private void updatePlayerRole(CraterGameProfile profile, BridgedPlayerRoles rank, boolean add) {
        MinecraftAccount account = MinecraftAccount.of(profile);
        DiscordUser user = account.getDiscordUser();
        if (user == null) {
            return;
        }
        Guild g = BotController.INSTANCE.getJDA().getGuilds().get(0);
        if (g == null) {
            return;
        }
        Member member = g.getMemberById(user.getUserId());
        if (member == null) {
            return;
        }
        Optional<RoleSyncCompat.Sync> sync = SDLinkCompatConfig.INSTANCE.playerroles.syncs.stream().filter(s -> s.rank.equalsIgnoreCase(rank.getId())).findFirst();
        sync.ifPresent(s -> {
            Role role = RoleManager.getPlayerRoleRoles().stream().filter(r -> r.getId().equalsIgnoreCase(s.role)).findFirst().orElse(null);
            if (role == null) {
                return;
            }
            if (add) {
                this.ignoreEvent = true;
                g.addRoleToMember(UserSnowflake.fromId(member.getId()), role).queue(suc -> {
                    this.ignoreEvent = false;
                });
            } else {
                this.ignoreEvent = true;
                g.removeRoleFromMember(UserSnowflake.fromId(member.getId()), role).queue(suc -> {
                    this.ignoreEvent = false;
                });
            }
        });
    }
}

