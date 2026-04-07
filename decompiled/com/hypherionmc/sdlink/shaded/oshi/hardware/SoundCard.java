/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;

@Immutable
public interface SoundCard {
    public String getDriverVersion();

    public String getName();

    public String getCodec();
}

