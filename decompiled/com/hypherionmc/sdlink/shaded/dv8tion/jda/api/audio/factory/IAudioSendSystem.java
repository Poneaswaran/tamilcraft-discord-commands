/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.factory;

import com.hypherionmc.sdlink.shaded.javax.annotation.CheckForNull;
import java.util.concurrent.ConcurrentMap;

public interface IAudioSendSystem {
    public void start();

    public void shutdown();

    default public void setContextMap(@CheckForNull ConcurrentMap<String, String> contextMap) {
    }
}

