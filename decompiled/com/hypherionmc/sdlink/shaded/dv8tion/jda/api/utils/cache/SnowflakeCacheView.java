/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheView;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface SnowflakeCacheView<T extends ISnowflake>
extends CacheView<T> {
    @Nullable
    public T getElementById(long var1);

    @Nullable
    default public T getElementById(@Nonnull String id) {
        return this.getElementById(MiscUtil.parseSnowflake(id));
    }
}

