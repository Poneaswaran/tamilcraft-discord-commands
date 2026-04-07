/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface VirtualMemory {
    public long getSwapTotal();

    public long getSwapUsed();

    public long getVirtualMax();

    public long getVirtualInUse();

    public long getSwapPagesIn();

    public long getSwapPagesOut();
}

