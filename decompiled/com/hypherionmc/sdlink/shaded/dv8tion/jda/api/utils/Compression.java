/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils;

import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public enum Compression {
    NONE(""),
    ZLIB("zlib-stream");

    private final String key;

    private Compression(String key) {
        this.key = key;
    }

    @Nonnull
    public String getKey() {
        return this.key;
    }
}

