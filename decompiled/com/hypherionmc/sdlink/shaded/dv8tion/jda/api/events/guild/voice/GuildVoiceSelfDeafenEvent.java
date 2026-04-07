/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildVoiceSelfDeafenEvent
extends GenericGuildVoiceEvent {
    protected final boolean selfDeafened;

    public GuildVoiceSelfDeafenEvent(@Nonnull JDA api, long responseNumber, @Nonnull Member member) {
        super(api, responseNumber, member);
        this.selfDeafened = member.getVoiceState().isSelfDeafened();
    }

    public boolean isSelfDeafened() {
        return this.selfDeafened;
    }
}

