/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.SnowflakeCacheView;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface ChannelCacheView<T extends Channel>
extends SnowflakeCacheView<T> {
    @Nonnull
    public <C extends T> ChannelCacheView<C> ofType(@Nonnull Class<C> var1);

    @Nullable
    public T getElementById(@Nonnull ChannelType var1, long var2);

    @Nullable
    default public T getElementById(@Nonnull ChannelType type, @Nonnull String id) {
        return this.getElementById(type, MiscUtil.parseSnowflake(id));
    }
}

