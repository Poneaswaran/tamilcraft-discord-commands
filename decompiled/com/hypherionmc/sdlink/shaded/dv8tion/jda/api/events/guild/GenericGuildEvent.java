/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public abstract class GenericGuildEvent
extends Event {
    protected final Guild guild;

    public GenericGuildEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild) {
        super(api, responseNumber);
        this.guild = guild;
    }

    @Nonnull
    public Guild getGuild() {
        return this.guild;
    }
}

