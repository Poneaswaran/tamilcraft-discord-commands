/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildUpdateNSFWLevelEvent
extends GenericGuildUpdateEvent<Guild.NSFWLevel> {
    public static final String IDENTIFIER = "nsfw_level";

    public GuildUpdateNSFWLevelEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nonnull Guild.NSFWLevel oldNSFWLevel) {
        super(api, responseNumber, guild, oldNSFWLevel, guild.getNSFWLevel(), IDENTIFIER);
    }

    @Nonnull
    public Guild.NSFWLevel getOldNSFWLevel() {
        return this.getOldValue();
    }

    @Nonnull
    public Guild.NSFWLevel getNewNSFWLevel() {
        return this.getNewValue();
    }

    @Override
    @Nonnull
    public Guild.NSFWLevel getOldValue() {
        return (Guild.NSFWLevel)((Object)super.getOldValue());
    }

    @Override
    @Nonnull
    public Guild.NSFWLevel getNewValue() {
        return (Guild.NSFWLevel)((Object)super.getNewValue());
    }
}

