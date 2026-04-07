/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.OffsetDateTime;

public class GuildVoiceRequestToSpeakEvent
extends GenericGuildVoiceEvent {
    private final OffsetDateTime oldTime;
    private final OffsetDateTime newTime;

    public GuildVoiceRequestToSpeakEvent(@Nonnull JDA api, long responseNumber, @Nonnull Member member, @Nullable OffsetDateTime oldTime, @Nullable OffsetDateTime newTime) {
        super(api, responseNumber, member);
        this.oldTime = oldTime;
        this.newTime = newTime;
    }

    @Nullable
    public OffsetDateTime getOldTime() {
        return this.oldTime;
    }

    @Nullable
    public OffsetDateTime getNewTime() {
        return this.newTime;
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> approveSpeaker() {
        return this.getVoiceState().approveSpeaker();
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> declineSpeaker() {
        return this.getVoiceState().declineSpeaker();
    }
}

