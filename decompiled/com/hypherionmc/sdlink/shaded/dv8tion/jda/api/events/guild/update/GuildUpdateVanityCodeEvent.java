/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class GuildUpdateVanityCodeEvent
extends GenericGuildUpdateEvent<String> {
    public static final String IDENTIFIER = "vanity_code";

    public GuildUpdateVanityCodeEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nullable String previous) {
        super(api, responseNumber, guild, previous, guild.getVanityCode(), IDENTIFIER);
    }

    @Nullable
    public String getOldVanityCode() {
        return (String)this.getOldValue();
    }

    @Nullable
    public String getOldVanityUrl() {
        return this.getOldVanityCode() == null ? null : "https://discord.gg/" + this.getOldVanityCode();
    }

    @Nullable
    public String getNewVanityCode() {
        return (String)this.getNewValue();
    }

    @Nullable
    public String getNewVanityUrl() {
        return this.getNewVanityCode() == null ? null : "https://discord.gg/" + this.getNewVanityCode();
    }
}

