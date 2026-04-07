/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.hypherionmc.craterlib.api.game.commands.CraterCommandSourceStack
 *  com.hypherionmc.craterlib.api.game.text.Text
 *  lombok.Generated
 *  net.kyori.adventure.text.event.ClickEvent
 *  org.apache.commons.io.FileUtils
 */
package com.hypherionmc.sdlink.core.editor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hypherionmc.craterlib.api.game.commands.CraterCommandSourceStack;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.ThreadType;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocket;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocketException;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocketFrame;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocketListener;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocketState;
import com.hypherionmc.sdlink.util.EncryptionUtil;
import com.hypherionmc.sdlink.util.configeditor.SocketResponse;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import net.kyori.adventure.text.event.ClickEvent;
import org.apache.commons.io.FileUtils;

public final class ConfigEditorWSEvents
implements WebSocketListener {
    private final Gson GSON = new GsonBuilder().serializeNulls().create();
    private final String identifier;
    private final CraterCommandSourceStack sourceStack;

    @Override
    public void onConnected(WebSocket webSocket, Map<String, List<String>> map) throws Exception {
        BotController.INSTANCE.getLogger().info("Editor Websocket connected");
    }

    @Override
    public void onTextFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {
        String config;
        EncryptionUtil ec;
        SocketResponse response = (SocketResponse)this.GSON.fromJson(webSocketFrame.getPayloadText(), SocketResponse.class);
        if (response.getSocketCode().equalsIgnoreCase("WS_WAITING")) {
            this.sourceStack.sendMessage(Text.literal((String)String.format("Editor Connection Ready. Visit https://editor.firstdark.dev/%s to get started", this.identifier)).clickEvent(ClickEvent.openUrl((String)String.format("https://editor.firstdark.dev/%s", this.identifier))));
            BotController.INSTANCE.getLogger().info("Editor Connection Ready. Visit https://editor.firstdark.dev/{} to get started", (Object)this.identifier);
        }
        if (response.getSocketCode().equalsIgnoreCase("WS_GET_CONFIG")) {
            ec = new EncryptionUtil(this.identifier);
            config = FileUtils.readFileToString((File)SDLinkConfig.INSTANCE.getConfigPath(), (Charset)StandardCharsets.UTF_8);
            webSocket.sendText(this.GSON.toJson((Object)SocketResponse.of("WS_SEND_CONFIG", ec.encrypt(config))));
        }
        if (response.getSocketCode().equalsIgnoreCase("WS_SAVE_CONFIG")) {
            BotController.INSTANCE.getLogger().info("Got Config update from editor");
            ec = new EncryptionUtil(this.identifier);
            config = ec.decrypt(response.getMessage());
            FileUtils.writeStringToFile((File)SDLinkConfig.INSTANCE.getConfigPath(), (String)config, (Charset)StandardCharsets.UTF_8);
            SDLinkConfig.INSTANCE.configReloaded();
        }
    }

    @Override
    public void onConnectError(WebSocket webSocket, WebSocketException e) throws Exception {
        BotController.INSTANCE.getLogger().error("Failed to connect to editor web socket", (Throwable)e);
    }

    @Override
    public void onDisconnected(WebSocket webSocket, WebSocketFrame webSocketFrame, WebSocketFrame webSocketFrame1, boolean b) throws Exception {
        BotController.INSTANCE.getLogger().warn("Disconnected from Editor Websocket with code {}: {}", (Object)webSocketFrame.getCloseCode(), (Object)webSocketFrame.getCloseReason());
    }

    @Override
    public void onCloseFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {
        BotController.INSTANCE.getLogger().warn("Connection from Editor Terminated with code {}: {}", (Object)webSocketFrame.getCloseCode(), (Object)webSocketFrame.getCloseReason());
    }

    @Override
    public void onStateChanged(WebSocket webSocket, WebSocketState webSocketState) throws Exception {
    }

    @Override
    public void onFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {
    }

    @Override
    public void onContinuationFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {
    }

    @Override
    public void onBinaryFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {
    }

    @Override
    public void onPingFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {
    }

    @Override
    public void onPongFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {
    }

    @Override
    public void onTextMessage(WebSocket webSocket, String s) throws Exception {
    }

    @Override
    public void onTextMessage(WebSocket webSocket, byte[] bytes) throws Exception {
    }

    @Override
    public void onBinaryMessage(WebSocket webSocket, byte[] bytes) throws Exception {
    }

    @Override
    public void onSendingFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {
    }

    @Override
    public void onFrameSent(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {
    }

    @Override
    public void onFrameUnsent(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {
    }

    @Override
    public void onThreadCreated(WebSocket webSocket, ThreadType threadType, Thread thread) throws Exception {
    }

    @Override
    public void onThreadStarted(WebSocket webSocket, ThreadType threadType, Thread thread) throws Exception {
    }

    @Override
    public void onThreadStopping(WebSocket webSocket, ThreadType threadType, Thread thread) throws Exception {
    }

    @Override
    public void onError(WebSocket webSocket, WebSocketException e) throws Exception {
    }

    @Override
    public void onFrameError(WebSocket webSocket, WebSocketException e, WebSocketFrame webSocketFrame) throws Exception {
    }

    @Override
    public void onMessageError(WebSocket webSocket, WebSocketException e, List<WebSocketFrame> list) throws Exception {
    }

    @Override
    public void onMessageDecompressionError(WebSocket webSocket, WebSocketException e, byte[] bytes) throws Exception {
    }

    @Override
    public void onTextMessageError(WebSocket webSocket, WebSocketException e, byte[] bytes) throws Exception {
    }

    @Override
    public void onSendError(WebSocket webSocket, WebSocketException e, WebSocketFrame webSocketFrame) throws Exception {
    }

    @Override
    public void onUnexpectedError(WebSocket webSocket, WebSocketException e) throws Exception {
    }

    @Override
    public void handleCallbackError(WebSocket webSocket, Throwable throwable) throws Exception {
    }

    @Override
    public void onSendingHandshake(WebSocket webSocket, String s, List<String[]> list) throws Exception {
    }

    @Generated
    ConfigEditorWSEvents(String identifier, CraterCommandSourceStack sourceStack) {
        this.identifier = identifier;
        this.sourceStack = sourceStack;
    }
}

