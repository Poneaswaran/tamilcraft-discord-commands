/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collection;
import java.util.EnumSet;

public enum ChannelFlag {
    PINNED(2),
    REQUIRE_TAG(16),
    HIDE_MEDIA_DOWNLOAD_OPTIONS(32768);

    private final int value;

    private ChannelFlag(int value) {
        this.value = value;
    }

    public int getRaw() {
        return this.value;
    }

    @Nonnull
    public static EnumSet<ChannelFlag> fromRaw(int bitset) {
        EnumSet<ChannelFlag> set = EnumSet.noneOf(ChannelFlag.class);
        if (bitset == 0) {
            return set;
        }
        for (ChannelFlag flag : ChannelFlag.values()) {
            if (flag.value != bitset) continue;
            set.add(flag);
        }
        return set;
    }

    public static int getRaw(@Nonnull Collection<ChannelFlag> flags) {
        Checks.notNull(flags, "Flags");
        int raw = 0;
        for (ChannelFlag flag : flags) {
            raw |= flag.getRaw();
        }
        return raw;
    }
}

