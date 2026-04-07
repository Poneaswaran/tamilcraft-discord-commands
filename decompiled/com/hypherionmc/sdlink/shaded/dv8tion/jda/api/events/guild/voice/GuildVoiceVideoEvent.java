/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildVoiceVideoEvent
extends GenericGuildVoiceEvent {
    private final boolean video;

    public GuildVoiceVideoEvent(@Nonnull JDA api, long responseNumber, @Nonnull Member member, boolean video) {
        super(api, responseNumber, member);
        this.video = video;
    }

    public boolean isSendingVideo() {
        return this.video;
    }
}

