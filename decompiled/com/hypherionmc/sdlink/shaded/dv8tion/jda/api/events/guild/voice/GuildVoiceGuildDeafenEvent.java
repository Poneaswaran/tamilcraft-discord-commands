/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildVoiceGuildDeafenEvent
extends GenericGuildVoiceEvent {
    protected final boolean guildDeafened;

    public GuildVoiceGuildDeafenEvent(@Nonnull JDA api, long responseNumber, @Nonnull Member member) {
        super(api, responseNumber, member);
        this.guildDeafened = member.getVoiceState().isGuildDeafened();
    }

    public boolean isGuildDeafened() {
        return this.guildDeafened;
    }
}

