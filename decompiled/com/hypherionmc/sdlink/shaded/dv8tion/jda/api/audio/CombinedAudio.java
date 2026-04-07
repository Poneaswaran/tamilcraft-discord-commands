/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.OpusPacket;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class CombinedAudio {
    protected List<User> users;
    protected short[] audioData;

    public CombinedAudio(@Nonnull List<User> users, @Nonnull short[] audioData) {
        this.users = Collections.unmodifiableList(users);
        this.audioData = audioData;
    }

    @Nonnull
    public List<User> getUsers() {
        return this.users;
    }

    @Nonnull
    public byte[] getAudioData(double volume) {
        return OpusPacket.getAudioData(this.audioData, volume);
    }
}

