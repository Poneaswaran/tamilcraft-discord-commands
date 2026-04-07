/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.OpusPacket;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class UserAudio {
    protected User user;
    protected short[] audioData;

    public UserAudio(@Nonnull User user, @Nonnull short[] audioData) {
        this.user = user;
        this.audioData = audioData;
    }

    @Nonnull
    public User getUser() {
        return this.user;
    }

    @Nonnull
    public byte[] getAudioData(double volume) {
        return OpusPacket.getAudioData(this.audioData, volume);
    }
}

