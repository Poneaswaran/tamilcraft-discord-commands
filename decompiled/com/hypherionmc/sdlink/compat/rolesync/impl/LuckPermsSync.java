/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.api.compat.LuckPermsCompat
 *  com.hypherionmc.craterlib.api.events.compat.LuckPermsCompatEvents$GroupAddedEvent
 *  com.hypherionmc.craterlib.api.events.compat.LuckPermsCompatEvents$GroupRemovedEvent
 *  com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile
 *  com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer
 *  com.hypherionmc.craterlib.core.event.annot.CraterEventListener
 */
package com.hypherionmc.sdlink.compat.rolesync.impl;

import com.hypherionmc.craterlib.api.compat.LuckPermsCompat;
import com.hypherionmc.craterlib.api.events.compat.LuckPermsCompatEvents;
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
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class LuckPermsSync
extends AbstractRoleSyncer {
    public static final LuckPermsSync INSTANCE = new LuckPermsSync();

    private LuckPermsSync() {
        super(() -> SDLinkCompatConfig.INSTANCE.common.luckperms && SDLinkCompatConfig.INSTANCE.luckpermsCompat.syncToMinecraft);
    }

    @Override
    public void sync(CraterPlayer p, List<Role> roles, Guild guild, Member member) {
        Optional<RoleSyncCompat.Sync> sync;
        Set ranks;
        if (SDLinkCompatConfig.INSTANCE.luckpermsCompat.syncToMinecraft) {
            for (Role role : roles) {
                Optional<RoleSyncCompat.Sync> sync2 = SDLinkCompatConfig.INSTANCE.luckpermsCompat.syncs.stream().filter(s -> s.role.equalsIgnoreCase(role.getId())).findFirst();
                sync2.ifPresent(s -> {
                    if (!LuckPermsCompat.getInstance().hasGroup(p.getUUID(), s.rank)) {
                        this.ignoreEvent = true;
                        LuckPermsCompat.getInstance().addGroupToUser(p.getUUID(), s.rank);
                        this.ignoreEvent = false;
                    }
                });
            }
            ranks = LuckPermsCompat.getInstance().getUserGroups(p.getUUID());
            for (String rank : ranks) {
                sync = SDLinkCompatConfig.INSTANCE.luckpermsCompat.syncs.stream().filter(s -> s.rank.equalsIgnoreCase(rank)).findFirst();
                sync.ifPresent(s -> {
                    if (roles.stream().noneMatch(r -> r.getId().equalsIgnoreCase(s.role)) && LuckPermsCompat.getInstance().hasGroup(p.getUUID(), s.rank)) {
                        this.ignoreEvent = true;
                        LuckPermsCompat.getInstance().removeGroupFromUser(p.getUUID(), s.rank);
                        this.ignoreEvent = false;
                    }
                });
            }
        }
        if (SDLinkCompatConfig.INSTANCE.luckpermsCompat.syncToDiscord) {
            ranks = LuckPermsCompat.getInstance().getUserGroups(p.getUUID());
            for (String rank : ranks) {
                sync = SDLinkCompatConfig.INSTANCE.luckpermsCompat.syncs.stream().filter(s -> s.rank.equalsIgnoreCase(rank)).findFirst();
                sync.ifPresent(s -> {
                    if (roles.stream().noneMatch(r -> r.getId().equalsIgnoreCase(s.role))) {
                        Optional<Role> r2 = RoleManager.getLuckPermsRoles().stream().filter(rr -> rr.getId().equalsIgnoreCase(s.role)).findFirst();
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
                sync = SDLinkCompatConfig.INSTANCE.luckpermsCompat.syncs.stream().filter(s -> s.role.equalsIgnoreCase(role.getId())).findFirst();
                if (!sync.isPresent() || LuckPermsCompat.getInstance().hasGroup(p.getUUID(), sync.get().rank)) continue;
                Optional<Role> r = RoleManager.getLuckPermsRoles().stream().filter(rr -> rr.getId().equalsIgnoreCase(((RoleSyncCompat.Sync)sync.get()).role)).findFirst();
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

    @CraterEventListener
    public void luckPermsGroupAddedToUser(LuckPermsCompatEvents.GroupAddedEvent event) {
        if (this.ignoreEvent) {
            return;
        }
        if (!SDLinkCompatConfig.INSTANCE.common.luckperms || !SDLinkCompatConfig.INSTANCE.luckpermsCompat.syncToDiscord) {
            return;
        }
        String identifier = null;
        try {
            Field identifierField = event.getClass().getDeclaredField("identifier");
            identifierField.setAccessible(true);
            identifier = identifierField.get(event).toString();
        }
        catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (identifier == null) {
            return;
        }
        this.updateLuckpermsGroup(identifier, event.toProfile(), true);
    }

    @CraterEventListener
    public void luckPermsGroupRemovedFromUser(LuckPermsCompatEvents.GroupRemovedEvent event) {
        if (this.ignoreEvent) {
            return;
        }
        if (!SDLinkCompatConfig.INSTANCE.common.luckperms || !SDLinkCompatConfig.INSTANCE.luckpermsCompat.syncToDiscord) {
            return;
        }
        String identifier = null;
        try {
            Field identifierField = event.getClass().getDeclaredField("identifier");
            identifierField.setAccessible(true);
            identifier = identifierField.get(event).toString();
        }
        catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (identifier == null) {
            return;
        }
        this.updateLuckpermsGroup(identifier, event.toProfile(), false);
    }

    private void updateLuckpermsGroup(String rank, CraterGameProfile profile, boolean add) {
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
        Optional<RoleSyncCompat.Sync> sync = SDLinkCompatConfig.INSTANCE.luckpermsCompat.syncs.stream().filter(s -> s.rank.equalsIgnoreCase(rank)).findFirst();
        sync.ifPresent(s -> {
            Role role = RoleManager.getLuckPermsRoles().stream().filter(r -> r.getId().equalsIgnoreCase(s.role)).findFirst().orElse(null);
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

    @Override
    void discordRoleChanged(Member member, Guild guild, Role role, boolean add, MinecraftAccount oldAccount) {
        MinecraftAccount account;
        MinecraftAccount minecraftAccount = account = oldAccount != null ? oldAccount : MinecraftAccount.fromDiscordId(member.getId());
        if (account == null) {
            return;
        }
        RoleSyncCompat.Sync sync = SDLinkCompatConfig.INSTANCE.luckpermsCompat.syncs.stream().filter(s -> s.role.equalsIgnoreCase(role.getId())).findFirst().orElse(null);
        if (sync == null) {
            return;
        }
        if (add) {
            if (!LuckPermsCompat.getInstance().hasGroup(account.getUuid(), sync.rank)) {
                this.ignoreEvent = true;
                LuckPermsCompat.getInstance().addGroupToUser(account.getUuid(), sync.rank);
                this.ignoreEvent = false;
            }
        } else if (LuckPermsCompat.getInstance().hasGroup(account.getUuid(), sync.rank)) {
            this.ignoreEvent = true;
            LuckPermsCompat.getInstance().removeGroupFromUser(account.getUuid(), sync.rank);
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
}

