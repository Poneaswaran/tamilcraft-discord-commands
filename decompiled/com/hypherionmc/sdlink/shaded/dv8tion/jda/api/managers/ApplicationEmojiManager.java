/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.ApplicationEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.Manager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface ApplicationEmojiManager
extends Manager<ApplicationEmojiManager> {
    public static final long NAME = 1L;

    @Override
    @Nonnull
    @CheckReturnValue
    public ApplicationEmojiManager reset(long var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public ApplicationEmojiManager reset(long ... var1);

    @Nonnull
    public ApplicationEmoji getEmoji();

    @Nonnull
    @CheckReturnValue
    public ApplicationEmojiManager setName(@Nonnull String var1);
}

