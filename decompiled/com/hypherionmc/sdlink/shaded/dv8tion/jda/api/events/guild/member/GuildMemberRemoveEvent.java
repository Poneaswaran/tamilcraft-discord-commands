/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GenericGuildEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class GuildMemberRemoveEvent
extends GenericGuildEvent {
    private final User user;
    private final Member member;

    public GuildMemberRemoveEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nonnull User user, @Nullable Member member) {
        super(api, responseNumber, guild);
        this.user = user;
        this.member = member;
    }

    @Nonnull
    public User getUser() {
        return this.user;
    }

    @Nullable
    public Member getMember() {
        return this.member;
    }
}

