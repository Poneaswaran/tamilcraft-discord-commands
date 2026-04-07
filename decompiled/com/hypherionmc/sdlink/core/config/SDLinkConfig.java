/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.core.config.AbstractConfig
 *  com.hypherionmc.craterlib.core.config.ConfigController
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
import com.hypherionmc.craterlib.libs.moonconfig.core.CommentedConfig;
import com.hypherionmc.craterlib.libs.moonconfig.core.Config;
import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.ObjectConverter;
import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path;
import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment;
import com.hypherionmc.craterlib.libs.moonconfig.core.file.CommentedFileConfig;
import com.hypherionmc.sdlink.api.messaging.MessageType;
import com.hypherionmc.sdlink.core.config.TriBoolean;
import com.hypherionmc.sdlink.core.config.impl.AccessControl;
import com.hypherionmc.sdlink.core.config.impl.BotConfigSettings;
import com.hypherionmc.sdlink.core.config.impl.ChannelWebhookConfig;
import com.hypherionmc.sdlink.core.config.impl.ChatSettingsConfig;
import com.hypherionmc.sdlink.core.config.impl.GeneralConfigSettings;
import com.hypherionmc.sdlink.core.config.impl.MessageChannelConfig;
import com.hypherionmc.sdlink.core.config.impl.MessageFormatting;
import com.hypherionmc.sdlink.core.config.impl.MessageIgnoreConfig;
import com.hypherionmc.sdlink.core.config.impl.MinecraftCommands;
import com.hypherionmc.sdlink.core.config.impl.TriggerCommandsConfig;
import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.core.managers.CacheManager;
import com.hypherionmc.sdlink.util.EncryptionUtil;
import com.hypherionmc.sdlink.util.translations.TranslationManager;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public final class SDLinkConfig
extends AbstractConfig<SDLinkConfig> {
    public static transient SDLinkConfig INSTANCE;
    public static transient int configVer;
    public static transient boolean hasConfigLoaded;
    public static transient boolean wasReload;
    @Path(value="general")
    @SpecComment(value="General Mod Config")
    public GeneralConfigSettings generalConfig = new GeneralConfigSettings();
    @Path(value="botConfig")
    @SpecComment(value="Config specific to the discord bot")
    public BotConfigSettings botConfig = new BotConfigSettings();
    @Path(value="channelsAndWebhooks")
    @SpecComment(value="Config relating to the discord channels and webhooks to use with the mod")
    public ChannelWebhookConfig channelsAndWebhooks = new ChannelWebhookConfig();
    @Path(value="chat")
    @SpecComment(value="Configure which types of messages are delivered to Minecraft/Discord")
    public ChatSettingsConfig chatConfig = new ChatSettingsConfig();
    @Path(value="messageFormatting")
    @SpecComment(value="Change the format in which messages are displayed")
    public MessageFormatting messageFormatting = new MessageFormatting();
    @Path(value="messageDestinations")
    @SpecComment(value="Change in which channel messages appear")
    public MessageChannelConfig messageDestinations = new MessageChannelConfig();
    @Path(value="accessControl")
    @SpecComment(value="Manage access to your server, similar to whitelisting")
    public AccessControl accessControl = new AccessControl();
    @Path(value="minecraftCommands")
    @SpecComment(value="Execute Minecraft commands in Discord")
    public MinecraftCommands linkedCommands = new MinecraftCommands();
    @Path(value="filtering")
    @SpecComment(value="Configure message/username filtering for discord messages")
    public MessageIgnoreConfig ignoreConfig = new MessageIgnoreConfig();
    @Path(value="triggerCommands")
    @SpecComment(value="Run Minecraft commands when discord roles changes. Requires Access Control to be enabled")
    public TriggerCommandsConfig triggerCommands = new TriggerCommandsConfig();

    public SDLinkConfig(boolean wasReload) {
        super("sdlink", "simple-discord-link", "simple-discord-link");
        SDLinkConfig.wasReload = wasReload;
        this.registerAndSetup(this);
    }

    public SDLinkConfig() {
        this(false);
    }

    public void registerAndSetup(SDLinkConfig config) {
        if (this.getConfigPath().exists() && this.getConfigPath().length() >= 2L) {
            this.migrateConfig(config);
        } else {
            this.saveConfig((Object)config);
        }
        this.configReloaded();
        this.performEncryption(wasReload);
    }

    public void migrateConfig(SDLinkConfig conf) {
        CommentedFileConfig config = (CommentedFileConfig)CommentedFileConfig.builder((File)this.getConfigPath()).sync().build();
        CommentedFileConfig newConfig = (CommentedFileConfig)CommentedFileConfig.builder((File)this.getConfigPath()).sync().build();
        config.load();
        if (config.getInt("general.configVersion") == configVer) {
            newConfig.close();
            config.close();
            return;
        }
        new ObjectConverter().toConfig((Object)conf, (Config)newConfig);
        this.updateConfigValues((CommentedConfig)config, (CommentedConfig)newConfig, (CommentedConfig)newConfig, "");
        newConfig.set("general.configVersion", (Object)configVer);
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
        INSTANCE = (SDLinkConfig)((Object)this.readConfig((Object)this));
        hasConfigLoaded = true;
        CacheManager.reloadChannelConfigCache();
        try {
            TranslationManager.INSTANCE.loadTranslations(SDLinkConfig.INSTANCE.generalConfig.language);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void performEncryption(boolean wasReload) {
        CommentedFileConfig oldConfig = (CommentedFileConfig)CommentedFileConfig.builder((File)this.getConfigPath()).sync().build();
        oldConfig.load();
        String botToken = (String)oldConfig.getOrElse("botConfig.botToken", (Object)"");
        String chatWebhook = (String)oldConfig.getOrElse("channelsAndWebhooks.webhooks.chatWebhook", (Object)"");
        String eventsWebhook = (String)oldConfig.getOrElse("channelsAndWebhooks.webhooks.eventsWebhook", (Object)"");
        String consoleWebhook = (String)oldConfig.getOrElse("channelsAndWebhooks.webhooks.consoleWebhook", (Object)"");
        if (!botToken.isEmpty()) {
            botToken = EncryptionUtil.INSTANCE.encrypt(botToken);
            oldConfig.set("botConfig.botToken", (Object)botToken);
        }
        if (!chatWebhook.isEmpty()) {
            chatWebhook = EncryptionUtil.INSTANCE.encrypt(chatWebhook);
            oldConfig.set("channelsAndWebhooks.webhooks.chatWebhook", (Object)chatWebhook);
        }
        if (!eventsWebhook.isEmpty()) {
            eventsWebhook = EncryptionUtil.INSTANCE.encrypt(eventsWebhook);
            oldConfig.set("channelsAndWebhooks.webhooks.eventsWebhook", (Object)eventsWebhook);
        }
        if (!consoleWebhook.isEmpty()) {
            consoleWebhook = EncryptionUtil.INSTANCE.encrypt(consoleWebhook);
            oldConfig.set("channelsAndWebhooks.webhooks.consoleWebhook", (Object)consoleWebhook);
        }
        for (Map.Entry<MessageType, MessageChannelConfig.DestinationObject> d : CacheManager.messageDestinations.entrySet()) {
            if (!d.getValue().channel.isOverride() || d.getValue().override == null || !d.getValue().override.startsWith("http")) continue;
            String url = d.getValue().override;
            this.encryptOverrideUrls(d.getKey().name().toLowerCase(), oldConfig, url);
        }
        oldConfig.save();
        oldConfig.close();
        if (!wasReload) {
            ConfigController.register_config((AbstractConfig)this);
        }
        this.configReloaded();
    }

    private void updateConfigValues(CommentedConfig oldConfig, CommentedConfig newConfig, CommentedConfig outputConfig, String subKey) {
        int ver = oldConfig.getInt("general.configVersion");
        newConfig.valueMap().forEach((key, value) -> {
            String finalKey = subKey + (subKey.isEmpty() ? "" : ".") + key;
            if (ver < 21 && finalKey.equalsIgnoreCase("botConfig.botStatus")) {
                outputConfig.set(finalKey, Collections.singletonList(oldConfig.get(finalKey)));
                return;
            }
            if (ver < 27) {
                if (finalKey.equalsIgnoreCase("accessControl.verifiedRole")) {
                    if (!(oldConfig.get(finalKey) instanceof String)) {
                        return;
                    }
                    outputConfig.set(finalKey, oldConfig.get(finalKey).toString().trim().isEmpty() ? Collections.emptyList() : Collections.singletonList(oldConfig.get(finalKey)));
                    return;
                }
                if (finalKey.equalsIgnoreCase("chat.advancementMessages") || finalKey.equalsIgnoreCase("chat.deathMessages")) {
                    if (!(oldConfig.get(finalKey) instanceof Boolean)) {
                        return;
                    }
                    outputConfig.set(finalKey, (Object)((Boolean)oldConfig.get(finalKey) != false ? TriBoolean.ALWAYS : TriBoolean.NEVER));
                    return;
                }
            }
            if (ver == 27 || ver <= 26) {
                if (finalKey.equalsIgnoreCase("messageFormatting.advancements")) {
                    outputConfig.set(finalKey, oldConfig.get("messageFormatting.achievements"));
                    return;
                }
                if (finalKey.equalsIgnoreCase("ignoredMessages")) {
                    outputConfig.set("filtering", oldConfig.get("ignoredMessages"));
                    outputConfig.set("filtering.enabled", oldConfig.get("ignoredMessages.enabled"));
                    return;
                }
            }
            if (value instanceof CommentedConfig) {
                CommentedConfig commentedConfig = (CommentedConfig)value;
                this.updateConfigValues(oldConfig, commentedConfig, outputConfig, finalKey);
            } else {
                outputConfig.set(finalKey, oldConfig.contains(finalKey) ? oldConfig.get(finalKey) : value);
            }
        });
    }

    private void encryptOverrideUrls(String key, CommentedFileConfig oldConfig, String url) {
        oldConfig.set("messageDestinations." + key + ".override", (Object)EncryptionUtil.INSTANCE.encrypt(url));
    }

    static {
        configVer = 36;
        hasConfigLoaded = false;
        wasReload = false;
    }
}

