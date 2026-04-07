/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildTimeoutEvent
extends Event {
    private final long guildId;

    public GuildTimeoutEvent(@Nonnull JDA api, long guildId) {
        super(api);
        this.guildId = guildId;
    }

    public long getGuildIdLong() {
        return this.guildId;
    }

    @Nonnull
    public String getGuildId() {
        return Long.toUnsignedString(this.guildId);
    }
}

