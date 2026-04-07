/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GenericGuildEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildUnbanEvent
extends GenericGuildEvent {
    private final User user;

    public GuildUnbanEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nonnull User user) {
        super(api, responseNumber, guild);
        this.user = user;
    }

    @Nonnull
    public User getUser() {
        return this.user;
    }
}

