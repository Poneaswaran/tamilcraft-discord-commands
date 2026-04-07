/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.annotations.Incubating;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.update.GenericGuildMemberUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

@Incubating
public class GuildMemberUpdatePendingEvent
extends GenericGuildMemberUpdateEvent<Boolean> {
    public static final String IDENTIFIER = "pending";

    public GuildMemberUpdatePendingEvent(@Nonnull JDA api, long responseNumber, @Nonnull Member member, boolean previous) {
        super(api, responseNumber, member, previous, member.isPending(), IDENTIFIER);
    }

    public boolean getOldPending() {
        return (Boolean)this.getOldValue();
    }

    public boolean getNewPending() {
        return (Boolean)this.getNewValue();
    }
}

