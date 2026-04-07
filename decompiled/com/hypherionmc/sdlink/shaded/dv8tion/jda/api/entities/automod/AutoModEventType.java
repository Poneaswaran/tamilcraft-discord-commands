/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.automod;

import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public enum AutoModEventType {
    MESSAGE_SEND(1),
    MEMBER_UPDATE(2),
    UNKNOWN(-1);

    private final int key;

    private AutoModEventType(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    @Nonnull
    public static AutoModEventType fromKey(int key) {
        for (AutoModEventType type : AutoModEventType.values()) {
            if (type.key != key) continue;
            return type;
        }
        return UNKNOWN;
    }
}

