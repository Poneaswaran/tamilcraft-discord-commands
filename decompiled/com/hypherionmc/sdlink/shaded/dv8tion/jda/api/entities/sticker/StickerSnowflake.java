/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.sticker.StickerSnowflakeImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface StickerSnowflake
extends ISnowflake {
    @Nonnull
    public static StickerSnowflake fromId(long id) {
        return new StickerSnowflakeImpl(id);
    }

    @Nonnull
    public static StickerSnowflake fromId(@Nonnull String id) {
        return StickerSnowflake.fromId(MiscUtil.parseSnowflake(id));
    }
}

