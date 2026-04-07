/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.core.config.AbstractConfig
 *  com.hypherionmc.craterlib.core.config.ConfigController
 *  com.hypherionmc.craterlib.core.config.formats.TomlConfigFormat
 *  com.hypherionmc.craterlib.libs.moonconfig.core.CommentedConfig
 *  com.hypherionmc.craterlib.libs.moonconfig.core.Config
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.ObjectConverter
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment
 *  com.hypherionmc.craterlib.libs.moonconfig.core.file.CommentedFileConfig
 *  org.apache.commons.io.FileUtils
 */
package com.hypherionmc.sdlink.core.config;

import com.hypherionmc.craterlib.core.config.AbstractConfig;
import com.hypherionmc.craterlib.core.config.ConfigController;
import com.hypherionmc.craterlib.core.config.formats.TomlConfigFormat;
import com.hypherionmc.craterlib.libs.moonconfig.core.CommentedConfig;
import com.hypherionmc.craterlib.libs.moonconfig.core.Config;
import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.ObjectConverter;
import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path;
import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment;
import com.hypherionmc.craterlib.libs.moonconfig.core.file.CommentedFileConfig;
import com.hypherionmc.sdlink.core.config.impl.RelayMessageConfig;
import com.hypherionmc.sdlink.core.config.impl.RelayServerConfig;
import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.core.relay.SDLinkRelayClient;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public final class SDLinkRelayConfig
extends AbstractConfig<SDLinkRelayConfig> {
    public static transient SDLinkRelayConfig INSTANCE;
    public static transient int configVer;
    public static transient boolean hasConfigLoaded;
    public static transient boolean wasReload;
    @Path(value="configVersion")
    @SpecComment(value="INTERNAL. DO NOT TOUCH")
    public int configVersion = configVer;
    @Path(value="relayServer")
    @SpecComment(value="General Relay Server Config")
    public RelayServerConfig relayServer = new RelayServerConfig();
    @Path(value="messageConfig")
    @SpecComment(value="Message config for relays")
    public RelayMessageConfig messageConfig = new RelayMessageConfig();

    public SDLinkRelayConfig() {
        this(false);
    }

    public SDLinkRelayConfig(boolean wasReload) {
        super("sdlink", "simple-discord-link", "simple-discord-relay");
        SDLinkRelayConfig.wasReload = wasReload;
        this.registerAndSetup(this);
    }

    public void registerAndSetup(SDLinkRelayConfig config) {
        if (this.getConfigPath().exists() && this.getConfigPath().length() >= 2L) {
            this.migrateConfig(config);
        } else {
            this.saveConfig((Object)config);
        }
        if (!wasReload) {
            ConfigController.register_config((AbstractConfig)this);
        }
        this.configReloaded();
    }

    public void migrateConfig(SDLinkRelayConfig conf) {
        CommentedFileConfig config = (CommentedFileConfig)CommentedFileConfig.builder((File)this.getConfigPath()).sync().build();
        CommentedFileConfig newConfig = (CommentedFileConfig)CommentedFileConfig.builder((File)this.getConfigPath()).sync().build();
        config.load();
        if (config.getInt("configVersion") == configVer) {
            newConfig.close();
            config.close();
            return;
        }
        new ObjectConverter().toConfig((Object)conf, (Config)newConfig);
        ((TomlConfigFormat)this.getConfigFormat()).updateConfigValues((CommentedConfig)config, (CommentedConfig)newConfig, (CommentedConfig)newConfig, "");
        newConfig.set("configVersion", (Object)configVer);
        try {
            FileUtils.copyFile((File)this.getConfigPath(), (File)new File(this.getConfigPath().getAbsolutePath().replace(".toml", ".old")));
        }
        catch (IOException e) {
            BotController.INSTANCE.getLogger().warn("Failed to create config backup.", (Throwable)e);
        }
        newConfig.save();
        newConfig.close();
        config.close();
    }

    public void configReloaded() {
        INSTANCE = (SDLinkRelayConfig)((Object)this.readConfig((Object)this));
        hasConfigLoaded = true;
        SDLinkRelayClient.INSTANCE.openConnection();
    }

    static {
        configVer = 2;
        hasConfigLoaded = false;
        wasReload = false;
    }
}

