/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class GuildUpdateDescriptionEvent
extends GenericGuildUpdateEvent<String> {
    public static final String IDENTIFIER = "description";

    public GuildUpdateDescriptionEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nullable String previous) {
        super(api, responseNumber, guild, previous, guild.getDescription(), IDENTIFIER);
    }

    @Nullable
    public String getOldDescription() {
        return (String)this.getOldValue();
    }

    @Nullable
    public String getNewDescription() {
        return (String)this.getNewValue();
    }
}

