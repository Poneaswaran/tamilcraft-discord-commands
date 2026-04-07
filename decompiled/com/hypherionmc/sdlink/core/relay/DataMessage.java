/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.api.game.text.Text
 *  lombok.Generated
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.core.relay;

import com.hypherionmc.craterlib.api.game.text.Text;
import java.util.UUID;
import lombok.Generated;
import org.jetbrains.annotations.Nullable;

public class DataMessage {
    private String displayName;
    private String username;
    private String message;
    private UUID uuid;
    private String additional;
    private boolean isFromServer;

    public static DataMessage of(Text displayName, String username, @Nullable Text message, UUID uuid, boolean isFromServer) {
        return DataMessage.of(displayName, username, message, uuid, null, isFromServer);
    }

    public static DataMessage of(Text displayName, String username, @Nullable Text message, UUID uuid, @Nullable Text additional, boolean isFromServer) {
        return DataMessage.of(displayName.toJsonString(), username, message == null ? null : message.toJsonString(), uuid, additional == null ? null : additional.toJsonString(), isFromServer);
    }

    public Text displayName() {
        return Text.fromJson((String)this.displayName);
    }

    public Text message() {
        return Text.fromJson((String)this.message);
    }

    public Text additional() {
        return Text.fromJson((String)this.additional);
    }

    @Generated
    public DataMessage() {
    }

    @Generated
    private DataMessage(String displayName, String username, String message, UUID uuid, String additional, boolean isFromServer) {
        this.displayName = displayName;
        this.username = username;
        this.message = message;
        this.uuid = uuid;
        this.additional = additional;
        this.isFromServer = isFromServer;
    }

    @Generated
    public static DataMessage of(String displayName, String username, String message, UUID uuid, String additional, boolean isFromServer) {
        return new DataMessage(displayName, username, message, uuid, additional, isFromServer);
    }

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public UUID getUuid() {
        return this.uuid;
    }

    @Generated
    public boolean isFromServer() {
        return this.isFromServer;
    }
}

