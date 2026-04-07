/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.platform.unix.aix.Perfstat$perfstat_memory_total_t
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractVirtualMemory;
import com.sun.jna.platform.unix.aix.Perfstat;
import java.util.function.Supplier;

@ThreadSafe
final class AixVirtualMemory
extends AbstractVirtualMemory {
    private final Supplier<Perfstat.perfstat_memory_total_t> perfstatMem;
    private static final long PAGESIZE = 4096L;

    AixVirtualMemory(Supplier<Perfstat.perfstat_memory_total_t> perfstatMem) {
        this.perfstatMem = perfstatMem;
    }

    @Override
    public long getSwapUsed() {
        Perfstat.perfstat_memory_total_t perfstat = this.perfstatMem.get();
        return (perfstat.pgsp_total - perfstat.pgsp_free) * 4096L;
    }

    @Override
    public long getSwapTotal() {
        return this.perfstatMem.get().pgsp_total * 4096L;
    }

    @Override
    public long getVirtualMax() {
        return this.perfstatMem.get().virt_total * 4096L;
    }

    @Override
    public long getVirtualInUse() {
        return this.perfstatMem.get().virt_active * 4096L;
    }

    @Override
    public long getSwapPagesIn() {
        return this.perfstatMem.get().pgspins;
    }

    @Override
    public long getSwapPagesOut() {
        return this.perfstatMem.get().pgspouts;
    }
}

