/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildUpdateVerificationLevelEvent
extends GenericGuildUpdateEvent<Guild.VerificationLevel> {
    public static final String IDENTIFIER = "verification_level";

    public GuildUpdateVerificationLevelEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nonnull Guild.VerificationLevel oldVerificationLevel) {
        super(api, responseNumber, guild, oldVerificationLevel, guild.getVerificationLevel(), IDENTIFIER);
    }

    @Nonnull
    public Guild.VerificationLevel getOldVerificationLevel() {
        return this.getOldValue();
    }

    @Nonnull
    public Guild.VerificationLevel getNewVerificationLevel() {
        return this.getNewValue();
    }

    @Override
    @Nonnull
    public Guild.VerificationLevel getOldValue() {
        return (Guild.VerificationLevel)((Object)super.getOldValue());
    }

    @Override
    @Nonnull
    public Guild.VerificationLevel getNewValue() {
        return (Guild.VerificationLevel)((Object)super.getNewValue());
    }
}

