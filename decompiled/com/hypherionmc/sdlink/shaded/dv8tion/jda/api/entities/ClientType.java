/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public enum ClientType {
    DESKTOP("desktop"),
    MOBILE("mobile"),
    WEB("web"),
    UNKNOWN("unknown");

    private final String key;

    private ClientType(String key) {
        this.key = key;
    }

    @Nonnull
    public String getKey() {
        return this.key;
    }

    @Nonnull
    public static ClientType fromKey(@Nonnull String key) {
        for (ClientType type : ClientType.values()) {
            if (!type.key.equals(key)) continue;
            return type;
        }
        return UNKNOWN;
    }
}

