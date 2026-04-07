/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.update.GenericGuildMemberUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.OffsetDateTime;

public class GuildMemberUpdateBoostTimeEvent
extends GenericGuildMemberUpdateEvent<OffsetDateTime> {
    public static final String IDENTIFIER = "boost_time";

    public GuildMemberUpdateBoostTimeEvent(@Nonnull JDA api, long responseNumber, @Nonnull Member member, @Nullable OffsetDateTime previous) {
        super(api, responseNumber, member, previous, member.getTimeBoosted(), IDENTIFIER);
    }

    @Nullable
    public OffsetDateTime getOldTimeBoosted() {
        return (OffsetDateTime)this.getOldValue();
    }

    @Nullable
    public OffsetDateTime getNewTimeBoosted() {
        return (OffsetDateTime)this.getNewValue();
    }
}

