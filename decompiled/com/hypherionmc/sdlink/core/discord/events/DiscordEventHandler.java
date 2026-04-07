/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.core.event.CraterEvent
 *  com.hypherionmc.craterlib.core.event.CraterEventBus
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.core.discord.events;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.sdlink.api.accounts.MinecraftAccount;
import com.hypherionmc.sdlink.api.events.SDLinkReadyEvent;
import com.hypherionmc.sdlink.compat.rolesync.RoleSync;
import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.core.database.SDLinkAccount;
import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.core.discord.commands.slash.general.ServerStatusSlashCommand;
import com.hypherionmc.sdlink.core.discord.hooks.BotReadyHooks;
import com.hypherionmc.sdlink.core.discord.hooks.DiscordMessageHooks;
import com.hypherionmc.sdlink.core.discord.hooks.DiscordRoleHooks;
import com.hypherionmc.sdlink.core.discord.hooks.MinecraftCommandHook;
import com.hypherionmc.sdlink.core.managers.CacheManager;
import com.hypherionmc.sdlink.core.managers.ChannelManager;
import com.hypherionmc.sdlink.core.managers.DatabaseManager;
import com.hypherionmc.sdlink.core.managers.PermissionChecker;
import com.hypherionmc.sdlink.core.services.SDLinkPlatform;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.emoji.EmojiAddedEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.emoji.EmojiRemovedEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.emoji.update.EmojiUpdateNameEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.emoji.update.EmojiUpdateRolesEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GuildBanEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GuildJoinEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.RoleCreateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.RoleDeleteEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session.ReadyEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session.ShutdownEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.hooks.ListenerAdapter;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.CloseCode;
import com.hypherionmc.sdlink.util.PKUtil;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

public final class DiscordEventHandler
extends ListenerAdapter {
    private boolean isStuckInNotReady = false;

    @Override
    public void onShutdown(ShutdownEvent event) {
        CloseCode code = event.getCloseCode();
        if (code == null) {
            BotController.INSTANCE.getLogger().error("Got disconnected from discord for an unknown reason. Code: {}", (Object)event.getCode());
            return;
        }
        if (code == CloseCode.DISALLOWED_INTENTS) {
            BotController.INSTANCE.getLogger().error("Your bot is missing a required setup step, and cannot continue. Please review https://sdlink.fdd-docs.com/installation/bot-creation/#privileged-gateway-intents to fix this");
            return;
        }
        BotController.INSTANCE.getLogger().error("Disconnected from discord with error {}", (Object)event.getCloseCode().name());
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor() == event.getJDA().getSelfUser()) {
            return;
        }
        if (event.isFromType(ChannelType.PRIVATE)) {
            DiscordMessageHooks.checkVerification(event);
            return;
        }
        if (!event.isFromGuild()) {
            return;
        }
        if (!event.isWebhookMessage()) {
            MinecraftCommandHook.discordMessageEvent(event);
        }
        if (SDLinkConfig.INSTANCE.chatConfig.pluralKitCompat && PKUtil.PK_USERS.contains(event.getAuthor().getId())) {
            new Thread(() -> {
                try {
                    Thread.sleep(SDLinkConfig.INSTANCE.chatConfig.pluralKitCompatMessageDelay);
                }
                catch (InterruptedException e) {
                    BotController.INSTANCE.getLogger().error("Unexpected InterruptedException", (Throwable)e);
                }
                DiscordMessageHooks.discordMessageEvent(event);
            }).start();
        } else {
            DiscordMessageHooks.discordMessageEvent(event);
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        if (event.getJDA().getStatus() != JDA.Status.CONNECTED && event.getJDA().getStatus() != JDA.Status.DISCONNECTED) {
            this.isStuckInNotReady = true;
            this.startReadyDetection(event.getJDA());
        }
        if (event.getJDA().getStatus() == JDA.Status.CONNECTED) {
            this.isStuckInNotReady = false;
            BotController.INSTANCE.getLogger().info("Successfully connected to discord");
            PermissionChecker.checkBotSetup();
            ChannelManager.loadChannels();
            BotReadyHooks.startActivityUpdates(event);
            BotReadyHooks.startTopicUpdates();
            CacheManager.loadCache();
            CraterEventBus.INSTANCE.postEvent((CraterEvent)new SDLinkReadyEvent());
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (event.getComponentId().equals("sdrefreshbtn")) {
            event.deferEdit().queue(s -> s.editOriginalEmbeds(ServerStatusSlashCommand.runStatusCommand()).queue());
        }
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        if (event.getJDA().getStatus() == JDA.Status.CONNECTED) {
            CacheManager.loadUserCache();
        }
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        if (event.getJDA().getStatus() == JDA.Status.CONNECTED) {
            CacheManager.loadUserCache();
        }
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        if (event.getJDA().getStatus() == JDA.Status.CONNECTED) {
            CacheManager.loadUserCache();
        }
        if (event.getUser().isBot() || !SDLinkConfig.INSTANCE.accessControl.enabled && !SDLinkConfig.INSTANCE.accessControl.optionalVerification) {
            return;
        }
        try {
            List<SDLinkAccount> accounts = DatabaseManager.INSTANCE.getCollection(SDLinkAccount.class);
            Optional<SDLinkAccount> account = accounts.stream().filter(a -> a.getDiscordID() != null && a.getDiscordID().equalsIgnoreCase(event.getUser().getId())).findFirst();
            account.ifPresent(a -> DatabaseManager.INSTANCE.deleteEntry(a, SDLinkAccount.class));
        }
        catch (Exception e) {
            BotController.INSTANCE.getLogger().error("Failed to remove linked account", (Throwable)e);
        }
    }

    @Override
    public void onRoleCreate(@NotNull RoleCreateEvent event) {
        if (event.getJDA().getStatus() == JDA.Status.CONNECTED) {
            CacheManager.loadRoleCache();
        }
    }

    @Override
    public void onRoleDelete(@NotNull RoleDeleteEvent event) {
        if (event.getJDA().getStatus() == JDA.Status.CONNECTED) {
            CacheManager.loadRoleCache();
        }
    }

    @Override
    public void onChannelCreate(@NotNull ChannelCreateEvent event) {
        if (event.getJDA().getStatus() == JDA.Status.CONNECTED) {
            CacheManager.loadChannelCache();
        }
    }

    @Override
    public void onChannelDelete(@NotNull ChannelDeleteEvent event) {
        if (event.getJDA().getStatus() == JDA.Status.CONNECTED) {
            CacheManager.loadChannelCache();
        }
    }

    @Override
    public void onGuildBan(@NotNull GuildBanEvent event) {
        if (event.getUser().isBot()) {
            return;
        }
        CacheManager.loadUserCache();
        if (!SDLinkConfig.INSTANCE.accessControl.enabled && !SDLinkConfig.INSTANCE.accessControl.optionalVerification) {
            return;
        }
        try {
            List<SDLinkAccount> accounts = DatabaseManager.INSTANCE.getCollection(SDLinkAccount.class);
            Optional<SDLinkAccount> account = accounts.stream().filter(a -> a.getDiscordID() != null && a.getDiscordID().equalsIgnoreCase(event.getUser().getId())).findFirst();
            account.ifPresent(a -> {
                MinecraftAccount acc = MinecraftAccount.of(a);
                if (acc != null && SDLinkConfig.INSTANCE.accessControl.banPlayerOnDiscordBan) {
                    SDLinkPlatform.minecraftHelper.banPlayer(acc);
                }
                DatabaseManager.INSTANCE.deleteEntry(a, SDLinkAccount.class);
            });
        }
        catch (Exception e) {
            BotController.INSTANCE.getLogger().error("Failed to remove linked account", (Throwable)e);
        }
    }

    @Override
    public void onGuildMemberRoleAdd(@NotNull GuildMemberRoleAddEvent event) {
        DiscordRoleHooks.INSTANCE.onRoleAdded(event);
        event.getRoles().forEach(role -> RoleSync.INSTANCE.roleAddedToMember(event.getMember(), (Role)role, event.getGuild()));
    }

    @Override
    public void onGuildMemberRoleRemove(@NotNull GuildMemberRoleRemoveEvent event) {
        DiscordRoleHooks.INSTANCE.onRoleRemoved(event);
        event.getRoles().forEach(role -> RoleSync.INSTANCE.roleRemovedFromMember(event.getMember(), (Role)role, event.getGuild(), null));
    }

    @Override
    public void onEmojiAdded(EmojiAddedEvent event) {
        CacheManager.loadEmoteCache();
    }

    @Override
    public void onEmojiRemoved(EmojiRemovedEvent event) {
        CacheManager.loadEmoteCache();
    }

    @Override
    public void onEmojiUpdateName(EmojiUpdateNameEvent event) {
        CacheManager.loadEmoteCache();
    }

    @Override
    public void onEmojiUpdateRoles(EmojiUpdateRolesEvent event) {
        CacheManager.loadEmoteCache();
    }

    private void startReadyDetection(JDA jda) {
        BotController.INSTANCE.updatesManager.scheduleAtFixedRate(() -> {
            if (this.isStuckInNotReady && jda.getStatus() == JDA.Status.CONNECTED) {
                this.onReady(new ReadyEvent(jda));
                this.isStuckInNotReady = false;
            }
        }, 5L, 5L, TimeUnit.SECONDS);
    }
}

