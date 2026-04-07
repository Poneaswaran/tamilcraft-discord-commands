/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package com.hypherionmc.sdlink.core.relay;

import com.hypherionmc.sdlink.core.relay.DataMessage;
import lombok.Generated;

public final class RelayMessage {
    private MessageType type;
    private String serverName;
    private DataMessage data;
    private String message;

    public static RelayMessage of(MessageType type, String serverName, DataMessage data) {
        return new RelayMessage(type, serverName, data, null);
    }

    @Generated
    private RelayMessage(MessageType type, String serverName, DataMessage data, String message) {
        this.type = type;
        this.serverName = serverName;
        this.data = data;
        this.message = message;
    }

    @Generated
    public static RelayMessage of(MessageType type, String serverName, DataMessage data, String message) {
        return new RelayMessage(type, serverName, data, message);
    }

    @Generated
    public RelayMessage() {
    }

    @Generated
    public MessageType getType() {
        return this.type;
    }

    @Generated
    public String getServerName() {
        return this.serverName;
    }

    @Generated
    public DataMessage getData() {
        return this.data;
    }

    @Generated
    public String getMessage() {
        return this.message;
    }

    public static enum MessageType {
        CHAT,
        JOIN,
        LEAVE,
        ADVANCEMENT,
        DEATH,
        DISCORD;

    }
}

