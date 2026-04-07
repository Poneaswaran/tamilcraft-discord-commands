/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.RichSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.Sticker;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface StandardSticker
extends RichSticker {
    @Override
    @Nonnull
    default public Sticker.Type getType() {
        return Sticker.Type.STANDARD;
    }

    public long getPackIdLong();

    @Nonnull
    default public String getPackId() {
        return Long.toUnsignedString(this.getPackIdLong());
    }

    public int getSortValue();
}

