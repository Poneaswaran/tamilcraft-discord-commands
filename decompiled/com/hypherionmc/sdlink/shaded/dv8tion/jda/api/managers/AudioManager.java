/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.annotations.Incubating;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.AudioReceiveHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.AudioSendHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.SpeakingMode;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.hooks.ConnectionListener;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.hooks.ConnectionStatus;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import org.slf4j.Logger;

public interface AudioManager {
    public static final long DEFAULT_CONNECTION_TIMEOUT = 10000L;
    public static final Logger LOG = JDALogger.getLog(AudioManager.class);

    public void openAudioConnection(AudioChannel var1);

    public void closeAudioConnection();

    @Incubating
    public void setSpeakingMode(@Nonnull Collection<SpeakingMode> var1);

    @Incubating
    default public void setSpeakingMode(SpeakingMode ... mode) {
        Checks.notNull(mode, "Speaking Mode");
        this.setSpeakingMode(Arrays.asList(mode));
    }

    @Nonnull
    @Incubating
    public EnumSet<SpeakingMode> getSpeakingMode();

    @Nonnull
    public JDA getJDA();

    @Nonnull
    public Guild getGuild();

    @Nullable
    public AudioChannelUnion getConnectedChannel();

    public boolean isConnected();

    public void setConnectTimeout(long var1);

    public long getConnectTimeout();

    public void setSendingHandler(@Nullable AudioSendHandler var1);

    @Nullable
    public AudioSendHandler getSendingHandler();

    public void setReceivingHandler(@Nullable AudioReceiveHandler var1);

    @Nullable
    public AudioReceiveHandler getReceivingHandler();

    public void setConnectionListener(@Nullable ConnectionListener var1);

    @Nullable
    public ConnectionListener getConnectionListener();

    @Nonnull
    public ConnectionStatus getConnectionStatus();

    public void setAutoReconnect(boolean var1);

    public boolean isAutoReconnect();

    public void setSelfMuted(boolean var1);

    public boolean isSelfMuted();

    public void setSelfDeafened(boolean var1);

    public boolean isSelfDeafened();
}

