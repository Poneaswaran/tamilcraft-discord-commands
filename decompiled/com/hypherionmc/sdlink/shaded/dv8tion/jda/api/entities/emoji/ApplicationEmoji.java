/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.CustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.ApplicationEmojiManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface ApplicationEmoji
extends CustomEmoji {
    public static final int APPLICATION_EMOJI_CAP = 2000;

    @Nonnull
    public JDA getJDA();

    @Nullable
    public User getOwner();

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> delete();

    @Nonnull
    @CheckReturnValue
    public ApplicationEmojiManager getManager();
}

