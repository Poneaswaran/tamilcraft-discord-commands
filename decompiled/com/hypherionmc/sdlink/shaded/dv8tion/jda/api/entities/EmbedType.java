/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public enum EmbedType {
    IMAGE("image"),
    VIDEO("video"),
    GIFV("gifv"),
    ARTICLE("article"),
    LINK("link"),
    RICH("rich"),
    AUTO_MODERATION("auto_moderation_message"),
    POLL_RESULT("poll_result"),
    UNKNOWN("");

    private final String key;

    private EmbedType(String key) {
        this.key = key;
    }

    @Nonnull
    public static EmbedType fromKey(String key) {
        for (EmbedType type : EmbedType.values()) {
            if (!type.key.equals(key)) continue;
            return type;
        }
        return UNKNOWN;
    }
}

