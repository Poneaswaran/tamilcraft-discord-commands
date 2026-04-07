/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheView;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface ShardCacheView
extends CacheView<JDA> {
    @Nullable
    public JDA getElementById(int var1);

    @Nullable
    default public JDA getElementById(@Nonnull String id) {
        return this.getElementById(Integer.parseUnsignedInt(id));
    }
}

