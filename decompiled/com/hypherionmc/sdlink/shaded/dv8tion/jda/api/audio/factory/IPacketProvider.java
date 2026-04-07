/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.factory;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.hooks.ConnectionStatus;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.javax.annotation.concurrent.NotThreadSafe;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

@NotThreadSafe
public interface IPacketProvider {
    @Nonnull
    public String getIdentifier();

    @Nonnull
    public AudioChannel getConnectedChannel();

    @Nonnull
    public DatagramSocket getUdpSocket();

    @Nonnull
    public InetSocketAddress getSocketAddress();

    @Nullable
    public ByteBuffer getNextPacketRaw(boolean var1);

    @Nullable
    public DatagramPacket getNextPacket(boolean var1);

    public void onConnectionError(@Nonnull ConnectionStatus var1);

    public void onConnectionLost();
}

