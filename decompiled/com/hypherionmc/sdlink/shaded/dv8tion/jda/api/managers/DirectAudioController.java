/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface DirectAudioController {
    @Nonnull
    public JDA getJDA();

    public void connect(@Nonnull AudioChannel var1);

    public void disconnect(@Nonnull Guild var1);

    public void reconnect(@Nonnull AudioChannel var1);
}

