/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.OffsetDateTime;

public interface GuildVoiceState
extends ISnowflake {
    @Nonnull
    public JDA getJDA();

    public boolean isSelfMuted();

    public boolean isSelfDeafened();

    public boolean isMuted();

    public boolean isDeafened();

    public boolean isGuildMuted();

    public boolean isGuildDeafened();

    public boolean isSuppressed();

    public boolean isStream();

    public boolean isSendingVideo();

    @Nullable
    public AudioChannelUnion getChannel();

    @Nonnull
    public Guild getGuild();

    @Nonnull
    public Member getMember();

    public boolean inAudioChannel();

    @Nullable
    public String getSessionId();

    @Nullable
    public OffsetDateTime getRequestToSpeakTimestamp();

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> approveSpeaker();

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> declineSpeaker();

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> inviteSpeaker();
}

