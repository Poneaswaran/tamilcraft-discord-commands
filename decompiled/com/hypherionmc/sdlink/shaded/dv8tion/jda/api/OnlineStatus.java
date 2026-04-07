/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api;

import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public enum OnlineStatus {
    ONLINE("online"),
    IDLE("idle"),
    DO_NOT_DISTURB("dnd"),
    INVISIBLE("invisible"),
    OFFLINE("offline"),
    UNKNOWN("");

    private final String key;

    private OnlineStatus(String key) {
        this.key = key;
    }

    @Nonnull
    public String getKey() {
        return this.key;
    }

    @Nonnull
    public static OnlineStatus fromKey(@Nonnull String key) {
        for (OnlineStatus onlineStatus : OnlineStatus.values()) {
            if (!onlineStatus.key.equalsIgnoreCase(key)) continue;
            return onlineStatus;
        }
        return UNKNOWN;
    }
}

