/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface UnicodeEmoji
extends Emoji {
    @Nonnull
    public String getAsCodepoints();

    @Override
    @Nonnull
    default public Emoji.Type getType() {
        return Emoji.Type.UNICODE;
    }

    @Override
    @Nonnull
    default public String getFormatted() {
        return this.getName();
    }
}

