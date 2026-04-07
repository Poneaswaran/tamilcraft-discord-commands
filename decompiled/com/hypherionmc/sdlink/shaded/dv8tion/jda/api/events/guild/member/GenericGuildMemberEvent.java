/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GenericGuildEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public abstract class GenericGuildMemberEvent
extends GenericGuildEvent {
    private final Member member;

    public GenericGuildMemberEvent(@Nonnull JDA api, long responseNumber, @Nonnull Member member) {
        super(api, responseNumber, member.getGuild());
        this.member = member;
    }

    @Nonnull
    public User getUser() {
        return this.getMember().getUser();
    }

    @Nonnull
    public Member getMember() {
        return this.member;
    }
}

