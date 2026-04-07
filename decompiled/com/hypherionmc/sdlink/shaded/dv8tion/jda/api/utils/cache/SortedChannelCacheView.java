/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.ChannelCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.SortedSnowflakeCacheView;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface SortedChannelCacheView<T extends Channel & Comparable<? super T>>
extends ChannelCacheView<T>,
SortedSnowflakeCacheView<T> {
    @Override
    @Nonnull
    public <C extends T> SortedChannelCacheView<C> ofType(@Nonnull Class<C> var1);
}

