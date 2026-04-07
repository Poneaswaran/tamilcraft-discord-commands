/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.TimeUtil;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.time.OffsetDateTime;

public interface ISnowflake {
    @Nonnull
    default public String getId() {
        return Long.toUnsignedString(this.getIdLong());
    }

    public long getIdLong();

    @Nonnull
    default public OffsetDateTime getTimeCreated() {
        return TimeUtil.getTimeCreated(this.getIdLong());
    }
}

