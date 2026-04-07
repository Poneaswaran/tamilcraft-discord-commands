/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildUpdateMaxMembersEvent
extends GenericGuildUpdateEvent<Integer> {
    public static final String IDENTIFIER = "max_members";

    public GuildUpdateMaxMembersEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, int previous) {
        super(api, responseNumber, guild, previous, guild.getMaxMembers(), IDENTIFIER);
    }

    public int getOldMaxMembers() {
        return this.getOldValue();
    }

    public int getNewMaxMembers() {
        return this.getNewValue();
    }

    @Override
    @Nonnull
    public Integer getOldValue() {
        return (Integer)super.getOldValue();
    }

    @Override
    @Nonnull
    public Integer getNewValue() {
        return (Integer)super.getNewValue();
    }
}

