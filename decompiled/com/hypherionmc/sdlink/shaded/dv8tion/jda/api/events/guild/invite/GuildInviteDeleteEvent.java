/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.invite;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.invite.GenericGuildInviteEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildInviteDeleteEvent
extends GenericGuildInviteEvent {
    public GuildInviteDeleteEvent(@Nonnull JDA api, long responseNumber, @Nonnull String code, @Nonnull GuildChannel channel) {
        super(api, responseNumber, code, channel);
    }
}

