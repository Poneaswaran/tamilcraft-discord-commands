/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.PhysicalMemory;
import com.hypherionmc.sdlink.shaded.oshi.hardware.VirtualMemory;
import java.util.List;

@ThreadSafe
public interface GlobalMemory {
    public long getTotal();

    public long getAvailable();

    public long getPageSize();

    public VirtualMemory getVirtualMemory();

    public List<PhysicalMemory> getPhysicalMemory();
}

