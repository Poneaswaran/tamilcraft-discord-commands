/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Set;

public class GuildUpdateFeaturesEvent
extends GenericGuildUpdateEvent<Set<String>> {
    public static final String IDENTIFIER = "features";

    public GuildUpdateFeaturesEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nonnull Set<String> oldFeatures) {
        super(api, responseNumber, guild, oldFeatures, guild.getFeatures(), IDENTIFIER);
    }

    @Nonnull
    public Set<String> getOldFeatures() {
        return this.getOldValue();
    }

    @Nonnull
    public Set<String> getNewFeatures() {
        return this.getNewValue();
    }

    @Override
    @Nonnull
    public Set<String> getOldValue() {
        return (Set)super.getOldValue();
    }

    @Override
    @Nonnull
    public Set<String> getNewValue() {
        return (Set)super.getNewValue();
    }
}

