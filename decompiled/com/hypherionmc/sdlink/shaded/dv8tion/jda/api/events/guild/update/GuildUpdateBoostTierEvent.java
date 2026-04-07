/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildUpdateBoostTierEvent
extends GenericGuildUpdateEvent<Guild.BoostTier> {
    public static final String IDENTIFIER = "boost_tier";

    public GuildUpdateBoostTierEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nonnull Guild.BoostTier previous) {
        super(api, responseNumber, guild, previous, guild.getBoostTier(), IDENTIFIER);
    }

    @Nonnull
    public Guild.BoostTier getOldBoostTier() {
        return this.getOldValue();
    }

    @Nonnull
    public Guild.BoostTier getNewBoostTier() {
        return this.getNewValue();
    }

    @Override
    @Nonnull
    public Guild.BoostTier getOldValue() {
        return (Guild.BoostTier)((Object)super.getOldValue());
    }

    @Override
    @Nonnull
    public Guild.BoostTier getNewValue() {
        return (Guild.BoostTier)((Object)super.getNewValue());
    }
}

