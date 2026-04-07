/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.invite;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Invite;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.invite.GenericGuildInviteEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildInviteCreateEvent
extends GenericGuildInviteEvent {
    private final Invite invite;

    public GuildInviteCreateEvent(@Nonnull JDA api, long responseNumber, @Nonnull Invite invite, @Nonnull GuildChannel channel) {
        super(api, responseNumber, invite.getCode(), channel);
        this.invite = invite;
    }

    @Nonnull
    public Invite getInvite() {
        return this.invite;
    }
}

