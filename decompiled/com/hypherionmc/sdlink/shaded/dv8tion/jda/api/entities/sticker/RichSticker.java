/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.Sticker;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Set;

public interface RichSticker
extends Sticker {
    @Nonnull
    public Sticker.Type getType();

    @Nonnull
    public Set<String> getTags();

    @Nonnull
    public String getDescription();
}

