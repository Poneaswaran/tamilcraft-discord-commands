/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.core.managers;

import com.hypherionmc.sdlink.api.messaging.MessageDestination;
import com.hypherionmc.sdlink.api.messaging.MessageType;
import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.core.config.impl.MessageChannelConfig;
import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.core.managers.CacheManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import com.hypherionmc.sdlink.util.EncryptionUtil;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.jetbrains.annotations.Nullable;

public final class ChannelManager {
    private static final HashMap<MessageDestination, GuildMessageChannel> channelMap = new HashMap();
    private static final HashMap<MessageType, GuildMessageChannel> overrideChannels = new HashMap();
    private static GuildMessageChannel consoleChannel;

    public static void loadChannels() {
        channelMap.clear();
        overrideChannels.clear();
        JDA jda = BotController.INSTANCE.getJDA();
        GuildMessageChannel chatChannel = jda.getChannelById(GuildMessageChannel.class, SDLinkConfig.INSTANCE.channelsAndWebhooks.channels.chatChannelID.isBlank() ? "0" : SDLinkConfig.INSTANCE.channelsAndWebhooks.channels.chatChannelID);
        GuildMessageChannel eventChannel = jda.getChannelById(GuildMessageChannel.class, SDLinkConfig.INSTANCE.channelsAndWebhooks.channels.eventsChannelID.isBlank() ? "0" : SDLinkConfig.INSTANCE.channelsAndWebhooks.channels.eventsChannelID);
        consoleChannel = jda.getChannelById(GuildMessageChannel.class, SDLinkConfig.INSTANCE.channelsAndWebhooks.channels.consoleChannelID.isBlank() ? "0" : SDLinkConfig.INSTANCE.channelsAndWebhooks.channels.consoleChannelID);
        if (chatChannel != null) {
            channelMap.put(MessageDestination.CHAT, chatChannel);
        }
        channelMap.put(MessageDestination.EVENT, eventChannel != null ? eventChannel : chatChannel);
        channelMap.put(MessageDestination.CONSOLE, consoleChannel != null ? consoleChannel : chatChannel);
        for (Map.Entry<MessageType, MessageChannelConfig.DestinationObject> d : CacheManager.messageDestinations.entrySet()) {
            String override = EncryptionUtil.INSTANCE.decrypt(d.getValue().override);
            if (!d.getValue().channel.isOverride() || d.getValue().override == null || override.startsWith("http")) continue;
            String id = d.getValue().override;
            if (overrideChannels.containsKey((Object)d.getKey())) continue;
            GuildMessageChannel channel = jda.getChannelById(GuildMessageChannel.class, id);
            if (channel == null) {
                BotController.INSTANCE.getLogger().error("Failed to load override channel {} for {}", (Object)id, (Object)d.getKey().name());
                continue;
            }
            BotController.INSTANCE.getLogger().info("Using channel override {} for {}", (Object)channel.getName(), (Object)d.getKey().name());
            overrideChannels.put(d.getKey(), channel);
        }
    }

    @Nullable
    public static GuildMessageChannel getOverride(MessageType type) {
        if (overrideChannels.get((Object)type) == null) {
            return null;
        }
        return overrideChannels.get((Object)type);
    }

    @Nullable
    public static GuildMessageChannel getDestinationChannel(MessageDestination destination) {
        if (channelMap.get((Object)destination) == null) {
            return null;
        }
        return channelMap.get((Object)destination);
    }

    @Generated
    public static GuildMessageChannel getConsoleChannel() {
        return consoleChannel;
    }
}

