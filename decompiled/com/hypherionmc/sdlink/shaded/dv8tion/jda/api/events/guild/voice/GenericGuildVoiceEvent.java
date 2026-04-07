/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.GuildVoiceState;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.GenericGuildEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public abstract class GenericGuildVoiceEvent
extends GenericGuildEvent {
    protected final Member member;

    public GenericGuildVoiceEvent(@Nonnull JDA api, long responseNumber, @Nonnull Member member) {
        super(api, responseNumber, member.getGuild());
        this.member = member;
    }

    @Nonnull
    public Member getMember() {
        return this.member;
    }

    @Nonnull
    public GuildVoiceState getVoiceState() {
        return this.member.getVoiceState();
    }
}

