/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.DiscordLocale;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildUpdateLocaleEvent
extends GenericGuildUpdateEvent<DiscordLocale> {
    public static final String IDENTIFIER = "locale";

    public GuildUpdateLocaleEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nonnull DiscordLocale previous) {
        super(api, responseNumber, guild, previous, guild.getLocale(), IDENTIFIER);
    }

    @Override
    @Nonnull
    public DiscordLocale getOldValue() {
        return (DiscordLocale)((Object)super.getOldValue());
    }

    @Override
    @Nonnull
    public DiscordLocale getNewValue() {
        return (DiscordLocale)((Object)super.getNewValue());
    }
}

