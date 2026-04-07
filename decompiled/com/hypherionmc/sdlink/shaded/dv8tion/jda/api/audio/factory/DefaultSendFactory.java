/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.factory;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.factory.DefaultSendSystem;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.factory.IAudioSendFactory;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.factory.IAudioSendSystem;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.factory.IPacketProvider;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class DefaultSendFactory
implements IAudioSendFactory {
    @Override
    @Nonnull
    public IAudioSendSystem createSendSystem(@Nonnull IPacketProvider packetProvider) {
        return new DefaultSendSystem(packetProvider);
    }
}

