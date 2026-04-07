/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.slf4j.Logger
 */
package com.hypherionmc.sdlink.core.discord;

import com.hypherionmc.sdlink.core.config.SDLinkCompatConfig;
import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.core.config.SDLinkRelayConfig;
import com.hypherionmc.sdlink.core.discord.commands.CommandManager;
import com.hypherionmc.sdlink.core.discord.events.DiscordEventHandler;
import com.hypherionmc.sdlink.core.editor.ConfigEditorClient;
import com.hypherionmc.sdlink.core.experimental.ExperimentalFeatures;
import com.hypherionmc.sdlink.core.managers.DatabaseManager;
import com.hypherionmc.sdlink.core.managers.EmbedManager;
import com.hypherionmc.sdlink.core.managers.HiddenPlayersManager;
import com.hypherionmc.sdlink.core.managers.SpamManager;
import com.hypherionmc.sdlink.core.managers.WebhookManager;
import com.hypherionmc.sdlink.core.relay.SDLinkRelayClient;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDABuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.GatewayIntent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ChunkingFilter;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MemberCachePolicy;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheFlag;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CommandClient;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CommandClientBuilder;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.hypherionmc.sdlink.util.EncryptionUtil;
import com.hypherionmc.sdlink.util.ThreadedEventManager;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import lombok.Generated;
import org.slf4j.Logger;

public final class BotController {
    public final ExecutorService taskManager = Executors.newCachedThreadPool();
    public final ScheduledExecutorService updatesManager = Executors.newScheduledThreadPool(4);
    public static BotController INSTANCE;
    private final EventWaiter eventWaiter = new EventWaiter();
    private final Logger logger;
    private final SpamManager spamManager;
    private JDA _jda;
    private boolean shutdownCalled = false;

    private BotController(Logger logger, boolean wasReload) {
        INSTANCE = this;
        this.logger = logger;
        File newConfigDir = new File("./config/simple-discord-link");
        newConfigDir.mkdirs();
        ExperimentalFeatures.INSTANCE.loadFeatures();
        new SDLinkConfig(wasReload);
        new SDLinkCompatConfig(wasReload);
        if (ExperimentalFeatures.INSTANCE.RELAY_SERVER) {
            new SDLinkRelayConfig(wasReload);
        }
        DatabaseManager.INSTANCE.initialize();
        WebhookManager.init();
        EmbedManager.init();
        HiddenPlayersManager.INSTANCE.loadHiddenPlayers();
        this.spamManager = new SpamManager(5, 2000, 120000, this.updatesManager);
    }

    public static void newInstance(Logger logger) {
        new BotController(logger, false);
    }

    public static void reloadInstance(boolean isReload) {
        INSTANCE.shutdownBot(isReload);
        new BotController(BotController.INSTANCE.logger, true);
        INSTANCE.initializeBot();
    }

    public void initializeBot() {
        this.shutdownCalled = false;
        if (SDLinkConfig.INSTANCE == null || !SDLinkConfig.hasConfigLoaded) {
            this.logger.error("Failed to load config. Check your log for errors");
            return;
        }
        if (SDLinkConfig.INSTANCE.botConfig.botToken.isEmpty()) {
            this.logger.error("Missing bot token. Mod will be disabled. Please double check this in {}", (Object)SDLinkConfig.INSTANCE.getConfigPath());
            return;
        }
        if (!SDLinkConfig.INSTANCE.generalConfig.enabled) {
            this.logger.warn("Simple Discord Link is disabled. Not continuing");
            return;
        }
        try {
            String token = EncryptionUtil.INSTANCE.decrypt(SDLinkConfig.INSTANCE.botConfig.botToken);
            CommandClientBuilder clientBuilder = new CommandClientBuilder();
            clientBuilder.setOwnerId("354707828298088459");
            clientBuilder.setHelpWord("help");
            clientBuilder.useHelpBuilder(false);
            clientBuilder.setActivity(null);
            CommandClient commandClient = clientBuilder.build();
            CommandManager.INSTANCE.register(commandClient);
            this._jda = JDABuilder.createLight(token, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_EXPRESSIONS).addEventListeners(commandClient, this.eventWaiter, new DiscordEventHandler()).setAutoReconnect(true).enableCache(CacheFlag.EMOJI, new CacheFlag[0]).setMemberCachePolicy(MemberCachePolicy.ALL).setChunkingFilter(ChunkingFilter.ALL).setBulkDeleteSplittingEnabled(true).setEventManager(new ThreadedEventManager()).build();
        }
        catch (Exception e) {
            this.logger.error("Failed to connect to discord", (Throwable)e);
        }
    }

    public boolean isBotReady() {
        if (SDLinkConfig.INSTANCE == null) {
            return false;
        }
        if (this.shutdownCalled) {
            return false;
        }
        if (!SDLinkConfig.INSTANCE.generalConfig.enabled) {
            return false;
        }
        if (this._jda == null) {
            return false;
        }
        if (this._jda.getStatus() == JDA.Status.SHUTTING_DOWN || this._jda.getStatus() == JDA.Status.SHUTDOWN) {
            return false;
        }
        return this._jda.getStatus() == JDA.Status.CONNECTED;
    }

    public void shutdownBot(boolean isReload) {
        try {
            this.shutdownCalled = true;
            if (this._jda != null) {
                List<Object> listeners = this._jda.getRegisteredListeners();
                listeners.forEach(l -> this._jda.removeEventListener(l));
                this._jda.shutdownNow();
            }
            WebhookManager.shutdown();
            this.taskManager.shutdownNow();
            this.updatesManager.shutdownNow();
        }
        catch (IllegalStateException listeners) {
        }
        catch (Exception e) {
            this.logger.error("Failed to shutdown bot.", (Throwable)e);
        }
        if (!isReload) {
            try {
                ConfigEditorClient.INSTANCE.closeServer();
                if (ExperimentalFeatures.INSTANCE.RELAY_SERVER) {
                    SDLinkRelayClient.INSTANCE.closeServer(true);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public JDA getJDA() {
        return this._jda;
    }

    @Generated
    public EventWaiter getEventWaiter() {
        return this.eventWaiter;
    }

    @Generated
    public Logger getLogger() {
        return this.logger;
    }

    @Generated
    public SpamManager getSpamManager() {
        return this.spamManager;
    }
}

