/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.update.GenericGuildMemberUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class GuildMemberUpdateNicknameEvent
extends GenericGuildMemberUpdateEvent<String> {
    public static final String IDENTIFIER = "nick";

    public GuildMemberUpdateNicknameEvent(@Nonnull JDA api, long responseNumber, @Nonnull Member member, @Nullable String oldNick) {
        super(api, responseNumber, member, oldNick, member.getNickname(), IDENTIFIER);
    }

    @Nullable
    public String getOldNickname() {
        return (String)this.getOldValue();
    }

    @Nullable
    public String getNewNickname() {
        return (String)this.getNewValue();
    }
}

