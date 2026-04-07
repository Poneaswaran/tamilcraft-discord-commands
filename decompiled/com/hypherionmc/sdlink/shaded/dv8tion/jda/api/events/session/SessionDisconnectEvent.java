/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session.GenericSessionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session.SessionState;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.CloseCode;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocketFrame;
import java.time.OffsetDateTime;

public class SessionDisconnectEvent
extends GenericSessionEvent {
    protected final WebSocketFrame serverCloseFrame;
    protected final WebSocketFrame clientCloseFrame;
    protected final boolean closedByServer;
    protected final OffsetDateTime disconnectTime;

    public SessionDisconnectEvent(@Nonnull JDA api, @Nullable WebSocketFrame serverCloseFrame, @Nullable WebSocketFrame clientCloseFrame, boolean closedByServer, @Nonnull OffsetDateTime disconnectTime) {
        super(api, SessionState.DISCONNECTED);
        this.serverCloseFrame = serverCloseFrame;
        this.clientCloseFrame = clientCloseFrame;
        this.closedByServer = closedByServer;
        this.disconnectTime = disconnectTime;
    }

    @Nullable
    public CloseCode getCloseCode() {
        return this.serverCloseFrame != null ? CloseCode.from(this.serverCloseFrame.getCloseCode()) : null;
    }

    @Nullable
    public WebSocketFrame getServiceCloseFrame() {
        return this.serverCloseFrame;
    }

    @Nullable
    public WebSocketFrame getClientCloseFrame() {
        return this.clientCloseFrame;
    }

    public boolean isClosedByServer() {
        return this.closedByServer;
    }

    @Nonnull
    public OffsetDateTime getTimeDisconnected() {
        return this.disconnectTime;
    }
}

