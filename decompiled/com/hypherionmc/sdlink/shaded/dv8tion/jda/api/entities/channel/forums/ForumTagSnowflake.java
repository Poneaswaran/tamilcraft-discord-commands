/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ForumTagSnowflakeImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface ForumTagSnowflake
extends ISnowflake {
    @Nonnull
    public static ForumTagSnowflake fromId(long id) {
        return new ForumTagSnowflakeImpl(id);
    }

    @Nonnull
    public static ForumTagSnowflake fromId(@Nonnull String id) {
        return new ForumTagSnowflakeImpl(MiscUtil.parseSnowflake(id));
    }
}

