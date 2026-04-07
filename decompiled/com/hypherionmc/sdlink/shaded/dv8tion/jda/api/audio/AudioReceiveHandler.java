/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.CombinedAudio;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.OpusPacket;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.UserAudio;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import javax.sound.sampled.AudioFormat;

public interface AudioReceiveHandler {
    public static final AudioFormat OUTPUT_FORMAT = new AudioFormat(48000.0f, 16, 2, true, true);

    default public boolean canReceiveCombined() {
        return false;
    }

    default public boolean canReceiveUser() {
        return false;
    }

    default public boolean canReceiveEncoded() {
        return false;
    }

    default public void handleEncodedAudio(@Nonnull OpusPacket packet) {
    }

    default public void handleCombinedAudio(@Nonnull CombinedAudio combinedAudio) {
    }

    default public void handleUserAudio(@Nonnull UserAudio userAudio) {
    }

    default public boolean includeUserInCombinedAudio(@Nonnull User user) {
        return true;
    }
}

