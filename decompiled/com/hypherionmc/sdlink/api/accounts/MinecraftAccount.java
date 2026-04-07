/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile
 *  com.hypherionmc.craterlib.core.event.CraterEvent
 *  com.hypherionmc.craterlib.core.event.CraterEventBus
 *  lombok.Generated
 *  org.apache.commons.lang3.ArrayUtils
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.api.accounts;

import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.sdlink.api.accounts.DiscordUser;
import com.hypherionmc.sdlink.api.events.VerificationEvent;
import com.hypherionmc.sdlink.api.messaging.Result;
import com.hypherionmc.sdlink.compat.rolesync.RoleSync;
import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.core.database.SDLinkAccount;
import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.core.managers.CacheManager;
import com.hypherionmc.sdlink.core.managers.DatabaseManager;
import com.hypherionmc.sdlink.core.managers.RoleManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.UserSnowflake;
import com.hypherionmc.sdlink.util.SDLinkUtils;
import com.hypherionmc.sdlink.util.translations.SDText;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.Generated;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MinecraftAccount {
    private final String username;
    private final UUID uuid;

    private MinecraftAccount(String username, UUID uuid) {
        this.username = username;
        this.uuid = uuid;
    }

    public static MinecraftAccount of(SDLinkAccount account) {
        return new MinecraftAccount(account.getUsername(), UUID.fromString(account.getUuid()));
    }

    public static MinecraftAccount of(CraterGameProfile profile) {
        return new MinecraftAccount(profile.getName(), profile.getId());
    }

    public CraterGameProfile toGameProfile() {
        return CraterGameProfile.fromGame((String)this.username, (UUID)this.uuid);
    }

    @Nullable
    public static MinecraftAccount fromDiscordId(String discordId) {
        SDLinkAccount account = DatabaseManager.INSTANCE.getCollection(SDLinkAccount.class).stream().filter(a -> a.getDiscordID() != null && a.getDiscordID().equals(discordId)).findFirst().orElse(null);
        if (account == null) {
            return null;
        }
        return MinecraftAccount.of(account);
    }

    public boolean isAccountVerified() {
        SDLinkAccount account = this.getStoredAccount();
        if (account == null || account.getDiscordID() == null) {
            return false;
        }
        return !SDLinkUtils.isNullOrEmpty(account.getDiscordID());
    }

    public SDLinkAccount getStoredAccount() {
        SDLinkAccount account = DatabaseManager.INSTANCE.findById(this.uuid.toString(), SDLinkAccount.class);
        return account == null ? this.newDBEntry() : account;
    }

    @NotNull
    private SDLinkAccount newDBEntry() {
        SDLinkAccount account = new SDLinkAccount();
        account.setUsername(this.username);
        account.setUuid(this.uuid.toString());
        account.setInGameName(this.username);
        account.setDiscordID(null);
        account.setVerifyCode(null);
        account.setOffline(false);
        DatabaseManager.INSTANCE.updateEntry(account);
        return account;
    }

    @NotNull
    public String getDiscordName() {
        SDLinkAccount account = this.getStoredAccount();
        if (account == null || SDLinkUtils.isNullOrEmpty(account.getDiscordID())) {
            return SDText.translate("account.unlinked").toString();
        }
        DiscordUser user = this.getDiscordUser();
        return user == null ? SDText.translate("account.unlinked").toString() : user.getEffectiveName();
    }

    @Nullable
    public DiscordUser getDiscordUser() {
        SDLinkAccount storedAccount = this.getStoredAccount();
        if (storedAccount == null || SDLinkUtils.isNullOrEmpty(storedAccount.getDiscordID())) {
            return null;
        }
        if (CacheManager.getDiscordMembers().isEmpty()) {
            return null;
        }
        Optional<Member> member = CacheManager.getDiscordMembers().stream().filter(m -> m.getId().equalsIgnoreCase(storedAccount.getDiscordID())).findFirst();
        return member.map(value -> DiscordUser.of(value.getEffectiveName(), value.getEffectiveAvatarUrl(), value.getIdLong(), value.getAsMention(), value.getColorRaw())).orElse(null);
    }

    public Result verifyAccount(Member member, Guild guild) {
        SDLinkAccount account = this.getStoredAccount();
        if (account == null) {
            return Result.error(SDText.translate("account.notfound"));
        }
        account.setDiscordID(member.getId());
        account.setVerifyCode(null);
        try {
            DatabaseManager.INSTANCE.updateEntry(account);
        }
        catch (Exception e) {
            BotController.INSTANCE.getLogger().error("Failed to store verified account", (Throwable)e);
        }
        if (!RoleManager.getVerifiedRole().isEmpty()) {
            for (Role role : RoleManager.getVerifiedRole()) {
                try {
                    guild.addRoleToMember(UserSnowflake.fromId(member.getId()), role).queue();
                }
                catch (Exception e) {
                    BotController.INSTANCE.getLogger().error("Failed to add verified role {} to user", (Object)role.getName(), (Object)e);
                }
            }
        }
        if (SDLinkConfig.INSTANCE.accessControl.changeDiscordNickname) {
            try {
                member.modifyNickname(account.getInGameName()).queue();
            }
            catch (Exception e) {
                BotController.INSTANCE.getLogger().error("Failed to update Nickname for {}", (Object)member.getEffectiveName(), (Object)e);
            }
        }
        CraterEventBus.INSTANCE.postEvent((CraterEvent)new VerificationEvent.PlayerVerified(this));
        return Result.success(SDText.translate("account.verify_success"));
    }

    public Result unverifyAccount(Member member, Guild guild) {
        SDLinkAccount account = this.getStoredAccount();
        if (account == null) {
            return Result.error(SDText.translate("account.notfound"));
        }
        MinecraftAccount oldAccount = this;
        account.setDiscordID(null);
        account.setVerifyCode(null);
        try {
            DatabaseManager.INSTANCE.updateEntry(account);
        }
        catch (Exception e) {
            BotController.INSTANCE.getLogger().error("Failed to remove verified account", (Throwable)e);
        }
        if (!RoleManager.getVerifiedRole().isEmpty()) {
            for (Role role : RoleManager.getVerifiedRole()) {
                try {
                    guild.removeRoleFromMember(UserSnowflake.fromId(member.getId()), role).queue();
                }
                catch (Exception e) {
                    BotController.INSTANCE.getLogger().error("Failed to remove verified role {} from user", (Object)role.getName(), (Object)e);
                }
            }
        }
        if (SDLinkConfig.INSTANCE.accessControl.changeDiscordNickname) {
            try {
                if (member.getNickname() != null && member.getNickname().equalsIgnoreCase(account.getInGameName())) {
                    member.modifyNickname(null).queue();
                }
            }
            catch (Exception e) {
                BotController.INSTANCE.getLogger().error("Failed to update Nickname for {}", (Object)member.getEffectiveName(), (Object)e);
            }
        }
        try {
            List<Role> roles = member.getRoles();
            for (Role role : roles) {
                RoleSync.INSTANCE.roleRemovedFromMember(member, role, guild, oldAccount);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        CraterEventBus.INSTANCE.postEvent((CraterEvent)new VerificationEvent.PlayerUnverified(this));
        return Result.success(SDText.translate("account.unverify_success"));
    }

    public Result canLogin() {
        if (!SDLinkConfig.INSTANCE.accessControl.enabled && !SDLinkConfig.INSTANCE.accessControl.optionalVerification) {
            return Result.success("");
        }
        SDLinkAccount account = this.getStoredAccount();
        if (account == null) {
            return Result.error(SDText.translate("account.load_failed"));
        }
        if (!this.isAccountVerified() && SDLinkConfig.INSTANCE.accessControl.enabled) {
            if (SDLinkUtils.isNullOrEmpty(account.getVerifyCode())) {
                int code = SDLinkUtils.intInRange(1000, 9999);
                account.setVerifyCode(String.valueOf(code));
                DatabaseManager.INSTANCE.updateEntry(account);
                return Result.error(SDLinkConfig.INSTANCE.accessControl.verificationMessages.accountVerify.replace("{code}", String.valueOf(code)));
            }
            return Result.error(SDLinkConfig.INSTANCE.accessControl.verificationMessages.accountVerify.replace("{code}", account.getVerifyCode()));
        }
        Result result = this.checkAccessControl();
        if (result.isError()) {
            switch (result.getMessage()) {
                case "notFound": {
                    return Result.error(SDText.translate("account.not_in_database"));
                }
                case "noGuildFound": {
                    return Result.error(SDText.translate("error.no_discord_server"));
                }
                case "memberNotFound": {
                    return Result.error(SDLinkConfig.INSTANCE.accessControl.verificationMessages.nonMember);
                }
                case "userCacheEmpty": {
                    return Result.error(SDText.translate("error.empty_cache"));
                }
                case "rolesNotLoaded": {
                    return Result.error(SDText.translate("error.no_roles"));
                }
                case "accessDeniedByRole": {
                    return Result.error(SDLinkConfig.INSTANCE.accessControl.verificationMessages.roleDenied);
                }
                case "rolesNotFound": {
                    return Result.error(SDLinkConfig.INSTANCE.accessControl.verificationMessages.requireRoles.replace("{roles}", ArrayUtils.toString(RoleManager.getVerificationRoles().stream().map(Role::getName).toList())));
                }
            }
        }
        return Result.success("");
    }

    public Result checkAccessControl() {
        DiscordUser user;
        if (!SDLinkConfig.INSTANCE.accessControl.enabled && !SDLinkConfig.INSTANCE.accessControl.optionalVerification) {
            return Result.success("pass");
        }
        SDLinkAccount account = this.getStoredAccount();
        if (account == null) {
            return Result.error("notFound");
        }
        if (SDLinkUtils.isNullOrEmpty(account.getDiscordID()) && SDLinkConfig.INSTANCE.accessControl.enabled) {
            return Result.error("notVerified");
        }
        if (SDLinkConfig.INSTANCE.accessControl.requireDiscordMembership && (user = this.getDiscordUser()) == null) {
            return Result.error("memberNotFound");
        }
        if (!SDLinkConfig.INSTANCE.accessControl.requiredRoles.isEmpty() || !SDLinkConfig.INSTANCE.accessControl.deniedRoles.isEmpty()) {
            AtomicBoolean anyFound = new AtomicBoolean(false);
            AtomicBoolean deniedFound = new AtomicBoolean(false);
            Optional<Member> member = CacheManager.getDiscordMembers().stream().filter(m -> m.getId().equals(account.getDiscordID())).findFirst();
            member.ifPresent(m -> m.getRoles().forEach(r -> {
                if (RoleManager.getDeniedRoles().stream().anyMatch(role -> r.getIdLong() == role.getIdLong()) && !deniedFound.get()) {
                    deniedFound.set(true);
                }
                if (RoleManager.getVerificationRoles().stream().anyMatch(role -> role.getIdLong() == r.getIdLong()) && !anyFound.get()) {
                    anyFound.set(true);
                }
            }));
            if (deniedFound.get() && !RoleManager.getDeniedRoles().isEmpty()) {
                return Result.error("accessDeniedByRole");
            }
            if (!anyFound.get() && !RoleManager.getVerificationRoles().isEmpty()) {
                return Result.error("rolesNotFound");
            }
            if (member.isEmpty()) {
                return Result.error("memberCacheEmpty");
            }
        }
        return Result.success("pass");
    }

    public void banDiscordMember() {
        if (!SDLinkConfig.INSTANCE.accessControl.banMemberOnMinecraftBan) {
            return;
        }
        DiscordUser user = this.getDiscordUser();
        if (user == null) {
            return;
        }
        try {
            BotController.INSTANCE.getJDA().getGuilds().get(0).ban(UserSnowflake.fromId(user.getUserId()), 7, TimeUnit.DAYS).queue();
        }
        catch (Exception e) {
            BotController.INSTANCE.getLogger().error("Failed to ban discord member", (Throwable)e);
        }
    }

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public UUID getUuid() {
        return this.uuid;
    }
}

