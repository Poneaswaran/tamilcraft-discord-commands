/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.hooks;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.SpeakingMode;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.hooks.ConnectionStatus;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.UserSnowflake;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.EnumSet;

public interface ConnectionListener {
    default public void onPing(long ping) {
    }

    default public void onStatusChange(@Nonnull ConnectionStatus status) {
    }

    default public void onUserSpeakingModeUpdate(@Nonnull User user, @Nonnull EnumSet<SpeakingMode> modes) {
    }

    default public void onUserSpeakingModeUpdate(@Nonnull UserSnowflake user, @Nonnull EnumSet<SpeakingMode> modes) {
    }
}

