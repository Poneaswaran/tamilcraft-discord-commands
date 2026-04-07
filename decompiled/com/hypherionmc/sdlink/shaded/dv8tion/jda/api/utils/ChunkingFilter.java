/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

@FunctionalInterface
public interface ChunkingFilter {
    public static final ChunkingFilter ALL = x -> true;
    public static final ChunkingFilter NONE = x -> false;

    public boolean filter(long var1);

    @Nonnull
    public static ChunkingFilter include(long ... ids) {
        Checks.notNull(ids, "ID array");
        if (ids.length == 0) {
            return NONE;
        }
        return guild -> {
            for (long id : ids) {
                if (id != guild) continue;
                return true;
            }
            return false;
        };
    }

    @Nonnull
    public static ChunkingFilter exclude(long ... ids) {
        Checks.notNull(ids, "ID array");
        if (ids.length == 0) {
            return ALL;
        }
        return guild -> {
            for (long id : ids) {
                if (id != guild) continue;
                return false;
            }
            return true;
        };
    }
}

