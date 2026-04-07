/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.ApplicationEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.CustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.UnicodeEmoji;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface EmojiUnion
extends Emoji {
    @Nonnull
    public UnicodeEmoji asUnicode();

    @Nonnull
    public CustomEmoji asCustom();

    @Nonnull
    public RichCustomEmoji asRich();

    @Nonnull
    public ApplicationEmoji asApplication();
}

