/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.update.GenericGuildMemberUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.EnumSet;

public class GuildMemberUpdateFlagsEvent
extends GenericGuildMemberUpdateEvent<EnumSet<Member.MemberFlag>> {
    public static final String IDENTIFIER = "flags";

    public GuildMemberUpdateFlagsEvent(@Nonnull JDA api, long responseNumber, @Nonnull Member member, @Nonnull EnumSet<Member.MemberFlag> previous) {
        super(api, responseNumber, member, previous, member.getFlags(), IDENTIFIER);
    }
}

