/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GenericGuildEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildReadyEvent
extends GenericGuildEvent {
    public GuildReadyEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild) {
        super(api, responseNumber, guild);
    }
}

