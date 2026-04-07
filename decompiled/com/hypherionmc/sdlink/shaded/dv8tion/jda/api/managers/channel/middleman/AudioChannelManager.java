/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.middleman;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Region;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.middleman.StandardGuildChannelManager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface AudioChannelManager<T extends AudioChannel, M extends AudioChannelManager<T, M>>
extends StandardGuildChannelManager<T, M> {
    @Nonnull
    @CheckReturnValue
    public M setBitrate(int var1);

    @Nonnull
    @CheckReturnValue
    public M setUserLimit(int var1);

    @Nonnull
    @CheckReturnValue
    public M setRegion(@Nonnull Region var1);
}

