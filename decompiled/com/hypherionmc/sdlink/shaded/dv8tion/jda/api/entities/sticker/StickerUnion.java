/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.GuildSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.RichSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StandardSticker;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface StickerUnion
extends RichSticker {
    @Nonnull
    public StandardSticker asStandardSticker();

    @Nonnull
    public GuildSticker asGuildSticker();
}

