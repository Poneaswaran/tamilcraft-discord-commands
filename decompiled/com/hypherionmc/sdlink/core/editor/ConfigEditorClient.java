/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.api.game.commands.CraterCommandSourceStack
 *  lombok.Generated
 */
package com.hypherionmc.sdlink.core.editor;

import com.hypherionmc.craterlib.api.game.commands.CraterCommandSourceStack;
import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.core.editor.ConfigEditorWSEvents;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocket;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocketFactory;
import com.hypherionmc.sdlink.util.EncryptionUtil;
import lombok.Generated;

public final class ConfigEditorClient {
    public static final ConfigEditorClient INSTANCE = new ConfigEditorClient();
    private WebSocket webSocket;

    public void openConnection(CraterCommandSourceStack sourceStack) {
        String identifier = EncryptionUtil.getSaltString();
        try {
            this.closeServer();
            this.webSocket = new WebSocketFactory().createSocket("wss://editor.firstdark.dev/ws/config?identifier=" + identifier);
            this.webSocket.setPingInterval(10000L);
            this.webSocket.addListener(new ConfigEditorWSEvents(identifier, sourceStack));
            this.webSocket.connect();
        }
        catch (Exception e) {
            BotController.INSTANCE.getLogger().error("Failed to open connection to Config Editor", (Throwable)e);
        }
    }

    public void closeServer() {
        try {
            if (this.webSocket != null && this.webSocket.isOpen()) {
                this.webSocket.disconnect();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Generated
    private ConfigEditorClient() {
    }
}

