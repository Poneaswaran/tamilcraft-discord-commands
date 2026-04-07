/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.hypherionmc.craterlib.api.game.text.Text
 *  com.hypherionmc.craterlib.api.game.world.level.CraterCommonGameRules
 *  lombok.Generated
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.hypherionmc.sdlink.core.relay;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.level.CraterCommonGameRules;
import com.hypherionmc.sdlink.api.accounts.DiscordAuthor;
import com.hypherionmc.sdlink.api.messaging.MessageType;
import com.hypherionmc.sdlink.api.messaging.discord.DiscordMessage;
import com.hypherionmc.sdlink.api.messaging.discord.DiscordMessageBuilder;
import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.core.config.SDLinkRelayConfig;
import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.core.relay.DataMessage;
import com.hypherionmc.sdlink.core.relay.RelayMessage;
import com.hypherionmc.sdlink.server.ServerEvents;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocket;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocketAdapter;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocketException;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocketFactory;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocketFrame;
import com.hypherionmc.sdlink.util.EncryptionUtil;
import com.hypherionmc.sdlink.util.SDLinkChatUtils;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SDLinkRelayClient
extends WebSocketAdapter {
    public static final SDLinkRelayClient INSTANCE = new SDLinkRelayClient();
    private WebSocket webSocket;
    private EncryptionUtil encryption;
    private final Gson GSON = new GsonBuilder().serializeNulls().create();
    private final Logger logger = LoggerFactory.getLogger(SDLinkRelayClient.class);
    private int retryCount = 0;
    private boolean wasClose = false;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public void openConnection() {
        String identifier;
        if (!SDLinkRelayConfig.INSTANCE.relayServer.enabled) {
            this.closeServer(true);
            return;
        }
        if (this.scheduler.isShutdown()) {
            this.scheduler = Executors.newSingleThreadScheduledExecutor();
        }
        if ((identifier = EncryptionUtil.INSTANCE.decrypt(SDLinkRelayConfig.INSTANCE.relayServer.relayToken)) == null || identifier.isEmpty()) {
            BotController.INSTANCE.getLogger().error("Relay Server Token cannot be empty!");
            return;
        }
        try {
            this.closeServer(false);
            this.logger.info("Connecting to Relay Server at {}", (Object)SDLinkRelayConfig.INSTANCE.relayServer.relayServerUrl);
            this.encryption = new EncryptionUtil(identifier);
            this.webSocket = new WebSocketFactory().createSocket(String.format("wss://%s?identifier=%s&serverName=%s", SDLinkRelayConfig.INSTANCE.relayServer.relayServerUrl, identifier, URLEncoder.encode(SDLinkConfig.INSTANCE.channelsAndWebhooks.serverName, StandardCharsets.UTF_8)));
            this.webSocket.setPingInterval(10000L);
            this.webSocket.addListener(this);
            this.webSocket.connectAsynchronously();
        }
        catch (Exception e) {
            BotController.INSTANCE.getLogger().error("Failed to open connection to Relay Server", (Throwable)e);
            this.scheduleReconnect();
        }
    }

    public void relayMessage(RelayMessage message) {
        if (this.webSocket == null || !this.webSocket.isOpen()) {
            return;
        }
        try {
            String json = this.encryption.encrypt(this.GSON.toJson((Object)message));
            this.webSocket.sendText(json);
        }
        catch (Exception e) {
            BotController.INSTANCE.getLogger().error("Failed to send relay message", (Throwable)e);
        }
    }

    public void closeServer(boolean wasClose) {
        if (wasClose) {
            this.scheduler.shutdownNow();
            this.wasClose = true;
        }
        try {
            if (this.webSocket != null && this.webSocket.isOpen()) {
                this.webSocket.disconnect();
                this.logger.info("Disconnected from Relay Server");
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        this.logger.info("Successfully connected to Relay Server at {}", (Object)SDLinkRelayConfig.INSTANCE.relayServer.relayServerUrl);
        this.retryCount = 0;
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
        if (closedByServer) {
            this.logger.error("Relay Server Connection closed by Server");
        } else {
            if (this.wasClose) {
                return;
            }
            this.logger.error("Disconnected from Relay Server. Attempting to reconnect...");
            this.scheduleReconnect();
        }
    }

    @Override
    public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
        this.logger.error("Relay WebSocket Error: {}", (Object)cause.getMessage());
    }

    private void scheduleReconnect() {
        int maxRetries = 10;
        if (this.retryCount >= maxRetries) {
            this.logger.error("Relay Server Max Reconnect retries reached. Giving up....");
            return;
        }
        long INITIAL_BACKOFF_MS = 1000L;
        long MAX_BACKOFF_MS = 300000L;
        long delay = Math.min(INITIAL_BACKOFF_MS * (1L << this.retryCount), MAX_BACKOFF_MS);
        ++this.retryCount;
        this.logger.warn("Reconnecting in {} seconds...", (Object)(delay / 1000L));
        this.scheduler.schedule(this::openConnection, delay, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onTextFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
        String payload = frame.getPayloadText();
        try {
            RelayMessage relayMessage = (RelayMessage)this.GSON.fromJson(this.encryption.decrypt(payload), RelayMessage.class);
            this.handleMessage(relayMessage);
        }
        catch (Exception e) {
            this.logger.error("Failed to process incoming relay message", (Throwable)e);
        }
    }

    private void handleMessage(RelayMessage relayMessage) {
        Text base;
        if (relayMessage.getData() == null && relayMessage.getMessage() == null || relayMessage.getServerName() == null || relayMessage.getServerName().isEmpty()) {
            return;
        }
        String prefix = SDLinkRelayConfig.INSTANCE.messageConfig.relayMessagePrefix.replace("%server_name%", relayMessage.getServerName()) + " ";
        Text text = base = prefix.trim().isEmpty() ? Text.empty() : Text.formatted((String)prefix);
        if (relayMessage.getType() == RelayMessage.MessageType.DISCORD) {
            ServerEvents.getInstance().getMinecraftServer().broadcastSystemMessage(base.append(Text.fromJson((String)relayMessage.getMessage())), false);
            return;
        }
        DataMessage dataMessage = relayMessage.getData();
        switch (relayMessage.getType()) {
            case ADVANCEMENT: {
                if (!ServerEvents.getInstance().getMinecraftServer().getGameRules().getBoolean(CraterCommonGameRules.RULE_ANNOUNCE_ADVANCEMENTS)) {
                    return;
                }
                ServerEvents.getInstance().getMinecraftServer().broadcastSystemMessage(base.append(Text.translatable((String)"chat.type.advancement.task", (Text[])new Text[]{dataMessage.displayName(), dataMessage.additional()})), false);
                break;
            }
            case CHAT: {
                ServerEvents.getInstance().getMinecraftServer().broadcastSystemMessage(base.append(Text.translatable((String)"chat.type.text", (Text[])new Text[]{dataMessage.displayName(), dataMessage.message()})), false);
                break;
            }
            case DEATH: {
                if (!ServerEvents.getInstance().getMinecraftServer().getGameRules().getBoolean(CraterCommonGameRules.RULE_SHOWDEATHMESSAGES)) {
                    return;
                }
                ServerEvents.getInstance().getMinecraftServer().broadcastSystemMessage(base.append(dataMessage.message()), false);
                break;
            }
            case JOIN: {
                ServerEvents.getInstance().getMinecraftServer().broadcastSystemMessage(base.append(Text.translatable((String)"multiplayer.player.joined", (Text[])new Text[]{dataMessage.displayName()})), false);
                break;
            }
            case LEAVE: {
                ServerEvents.getInstance().getMinecraftServer().broadcastSystemMessage(base.append(Text.translatable((String)"multiplayer.player.left", (Text[])new Text[]{dataMessage.displayName()})), false);
            }
        }
        if (SDLinkRelayConfig.INSTANCE.messageConfig.relayMinecraftToDiscord) {
            switch (relayMessage.getType()) {
                case CHAT: {
                    if (!SDLinkConfig.INSTANCE.chatConfig.playerMessages) {
                        return;
                    }
                    String username = dataMessage.displayName().asString(SDLinkConfig.INSTANCE.chatConfig.formatting);
                    String msg = dataMessage.message().asString(SDLinkConfig.INSTANCE.chatConfig.formatting);
                    if (!SDLinkRelayConfig.INSTANCE.messageConfig.relayMessagePrefix.isEmpty()) {
                        username = Text.formatted((String)(prefix + " " + username)).asString();
                    }
                    if (SDLinkConfig.INSTANCE.chatConfig.allowMentionsFromChat) {
                        msg = SDLinkChatUtils.parse(msg);
                    }
                    DiscordAuthor author = DiscordAuthor.of(username, dataMessage.getUuid().toString(), dataMessage.getUsername()).setPlayerName(dataMessage.getUsername());
                    DiscordMessage discordMessage = new DiscordMessageBuilder(MessageType.CHAT).message(msg).author(!dataMessage.isFromServer() ? author : DiscordAuthor.getServer()).build();
                    discordMessage.sendMessage();
                    break;
                }
                case JOIN: {
                    if (!SDLinkConfig.INSTANCE.chatConfig.playerJoin) {
                        return;
                    }
                    String msg = SDLinkConfig.INSTANCE.messageFormatting.playerJoined.replace("%player%", dataMessage.displayName().asString(SDLinkConfig.INSTANCE.chatConfig.formatting));
                    if (!SDLinkRelayConfig.INSTANCE.messageConfig.relayMessagePrefix.isEmpty()) {
                        msg = Text.formatted((String)(prefix + " " + msg)).asString();
                    }
                    DiscordMessage discordMessage = new DiscordMessageBuilder(MessageType.JOIN).message(msg).author(DiscordAuthor.getServer().setPlayerName(dataMessage.displayName().asString()).setPlayerAvatar(dataMessage.getUsername(), dataMessage.getUuid().toString())).build();
                    discordMessage.sendMessage();
                    break;
                }
                case LEAVE: {
                    if (!SDLinkConfig.INSTANCE.chatConfig.playerLeave) {
                        return;
                    }
                    String msg = SDLinkConfig.INSTANCE.messageFormatting.playerLeft.replace("%player%", dataMessage.displayName().asString(SDLinkConfig.INSTANCE.chatConfig.formatting));
                    if (!SDLinkRelayConfig.INSTANCE.messageConfig.relayMessagePrefix.isEmpty()) {
                        msg = Text.formatted((String)(prefix + " " + msg)).asString();
                    }
                    DiscordMessage discordMessage = new DiscordMessageBuilder(MessageType.LEAVE).message(msg).author(DiscordAuthor.getServer().setPlayerName(dataMessage.displayName().asString()).setPlayerAvatar(dataMessage.getUsername(), dataMessage.getUuid().toString())).build();
                    discordMessage.sendMessage();
                    break;
                }
                case DEATH: {
                    String name = dataMessage.displayName().asString(SDLinkConfig.INSTANCE.chatConfig.formatting);
                    String msg = dataMessage.message().asString(SDLinkConfig.INSTANCE.chatConfig.formatting);
                    String finalMessage = SDLinkConfig.INSTANCE.messageFormatting.death;
                    if (msg.startsWith(name + " ")) {
                        msg = msg.substring((name + " ").length());
                    }
                    if (SDLinkConfig.INSTANCE.chatConfig.deathMessages.isFalse()) {
                        return;
                    }
                    finalMessage = finalMessage.replace("%player%", name).replace("%message%", msg);
                    if (!SDLinkRelayConfig.INSTANCE.messageConfig.relayMessagePrefix.isEmpty()) {
                        finalMessage = Text.formatted((String)(prefix + " " + finalMessage)).asString();
                    }
                    DiscordMessage message = new DiscordMessageBuilder(MessageType.DEATH).message(finalMessage).author(DiscordAuthor.getServer().setPlayerName(dataMessage.displayName().asString()).setPlayerAvatar(dataMessage.getUsername(), dataMessage.getUuid().toString())).build();
                    message.sendMessage();
                    break;
                }
                case ADVANCEMENT: {
                    String username = dataMessage.displayName().asString(SDLinkConfig.INSTANCE.chatConfig.formatting);
                    String finalAdvancement = dataMessage.message().asString(SDLinkConfig.INSTANCE.chatConfig.formatting);
                    String advancementBody = dataMessage.additional().asString(SDLinkConfig.INSTANCE.chatConfig.formatting);
                    String msg = SDLinkConfig.INSTANCE.messageFormatting.achievements.replace("%player%", username).replace("%title%", finalAdvancement).replace("%description%", advancementBody);
                    if (!SDLinkRelayConfig.INSTANCE.messageConfig.relayMessagePrefix.isEmpty()) {
                        msg = Text.formatted((String)(prefix + " " + msg)).asString();
                    }
                    DiscordMessage discordMessage = new DiscordMessageBuilder(MessageType.ADVANCEMENTS).message(msg).author(DiscordAuthor.getServer().setPlayerName(dataMessage.displayName().asString()).setPlayerAvatar(dataMessage.getUsername(), dataMessage.getUuid().toString())).build();
                    discordMessage.sendMessage();
                }
            }
        }
    }

    @Generated
    private SDLinkRelayClient() {
    }

    @Generated
    public ScheduledExecutorService getScheduler() {
        return this.scheduler;
    }
}

