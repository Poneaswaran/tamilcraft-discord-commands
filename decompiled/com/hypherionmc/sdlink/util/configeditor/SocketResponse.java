/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package com.hypherionmc.sdlink.util.configeditor;

import lombok.Generated;

public final class SocketResponse {
    private String socketCode;
    private String message;

    @Generated
    private SocketResponse(String socketCode, String message) {
        this.socketCode = socketCode;
        this.message = message;
    }

    @Generated
    public static SocketResponse of(String socketCode, String message) {
        return new SocketResponse(socketCode, message);
    }

    @Generated
    public String getSocketCode() {
        return this.socketCode;
    }

    @Generated
    public String getMessage() {
        return this.message;
    }
}

