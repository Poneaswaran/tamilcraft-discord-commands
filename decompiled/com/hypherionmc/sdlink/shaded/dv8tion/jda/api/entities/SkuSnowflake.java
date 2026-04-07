/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.SkuSnowflakeImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface SkuSnowflake
extends ISnowflake {
    @Nonnull
    public static SkuSnowflake fromId(long id) {
        return new SkuSnowflakeImpl(id);
    }

    @Nonnull
    public static SkuSnowflake fromId(@Nonnull String id) {
        return SkuSnowflake.fromId(MiscUtil.parseSnowflake(id));
    }
}

