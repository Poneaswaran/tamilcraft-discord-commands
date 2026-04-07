/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.Manager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Set;

public interface CustomEmojiManager
extends Manager<CustomEmojiManager> {
    public static final long NAME = 1L;
    public static final long ROLES = 2L;

    @Override
    @Nonnull
    @CheckReturnValue
    public CustomEmojiManager reset(long var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public CustomEmojiManager reset(long ... var1);

    @Nonnull
    default public Guild getGuild() {
        return this.getEmoji().getGuild();
    }

    @Nonnull
    public RichCustomEmoji getEmoji();

    @Nonnull
    @CheckReturnValue
    public CustomEmojiManager setName(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    public CustomEmojiManager setRoles(@Nullable Set<Role> var1);
}

