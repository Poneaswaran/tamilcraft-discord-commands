/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildUpdateExplicitContentLevelEvent
extends GenericGuildUpdateEvent<Guild.ExplicitContentLevel> {
    public static final String IDENTIFIER = "explicit_content_filter";

    public GuildUpdateExplicitContentLevelEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nonnull Guild.ExplicitContentLevel oldLevel) {
        super(api, responseNumber, guild, oldLevel, guild.getExplicitContentLevel(), IDENTIFIER);
    }

    @Nonnull
    public Guild.ExplicitContentLevel getOldLevel() {
        return this.getOldValue();
    }

    @Nonnull
    public Guild.ExplicitContentLevel getNewLevel() {
        return this.getNewValue();
    }

    @Override
    @Nonnull
    public Guild.ExplicitContentLevel getOldValue() {
        return (Guild.ExplicitContentLevel)((Object)super.getOldValue());
    }

    @Override
    @Nonnull
    public Guild.ExplicitContentLevel getNewValue() {
        return (Guild.ExplicitContentLevel)((Object)super.getNewValue());
    }
}

