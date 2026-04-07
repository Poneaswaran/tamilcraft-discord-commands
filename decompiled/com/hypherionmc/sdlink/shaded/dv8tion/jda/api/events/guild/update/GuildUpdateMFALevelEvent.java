/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildUpdateMFALevelEvent
extends GenericGuildUpdateEvent<Guild.MFALevel> {
    public static final String IDENTIFIER = "mfa_level";

    public GuildUpdateMFALevelEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nonnull Guild.MFALevel oldMFALevel) {
        super(api, responseNumber, guild, oldMFALevel, guild.getRequiredMFALevel(), IDENTIFIER);
    }

    @Nonnull
    public Guild.MFALevel getOldMFALevel() {
        return this.getOldValue();
    }

    @Nonnull
    public Guild.MFALevel getNewMFALevel() {
        return this.getNewValue();
    }

    @Override
    @Nonnull
    public Guild.MFALevel getOldValue() {
        return (Guild.MFALevel)((Object)super.getOldValue());
    }

    @Override
    @Nonnull
    public Guild.MFALevel getNewValue() {
        return (Guild.MFALevel)((Object)super.getNewValue());
    }
}

