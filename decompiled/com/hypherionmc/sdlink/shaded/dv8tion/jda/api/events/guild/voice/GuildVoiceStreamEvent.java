/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildVoiceStreamEvent
extends GenericGuildVoiceEvent {
    private final boolean stream;

    public GuildVoiceStreamEvent(@Nonnull JDA api, long responseNumber, @Nonnull Member member, boolean stream) {
        super(api, responseNumber, member);
        this.stream = stream;
    }

    public boolean isStream() {
        return this.stream;
    }
}

