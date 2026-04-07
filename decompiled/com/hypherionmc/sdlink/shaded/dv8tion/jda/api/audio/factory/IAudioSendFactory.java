/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.factory;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.factory.IAudioSendSystem;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.factory.IPacketProvider;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface IAudioSendFactory {
    @Nonnull
    public IAudioSendSystem createSendSystem(@Nonnull IPacketProvider var1);
}

