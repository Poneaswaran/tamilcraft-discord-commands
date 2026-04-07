/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.api.messaging;

public enum MessageDestination {
    CHAT,
    EVENT,
    CONSOLE,
    OVERRIDE;


    public boolean isChat() {
        return this == CHAT;
    }

    public boolean isEvent() {
        return this == EVENT;
    }

    public boolean isConsole() {
        return this == CONSOLE;
    }

    public boolean isOverride() {
        return this == OVERRIDE;
    }
}

