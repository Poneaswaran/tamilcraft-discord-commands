/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.platform.unix.aix.Perfstat
 *  com.sun.jna.platform.unix.aix.Perfstat$perfstat_memory_total_t
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.unix.aix.perfstat;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.sun.jna.platform.unix.aix.Perfstat;

@ThreadSafe
public final class PerfstatMemory {
    private static final Perfstat PERF = Perfstat.INSTANCE;

    private PerfstatMemory() {
    }

    public static Perfstat.perfstat_memory_total_t queryMemoryTotal() {
        Perfstat.perfstat_memory_total_t memory = new Perfstat.perfstat_memory_total_t();
        int ret = PERF.perfstat_memory_total(null, memory, memory.size(), 1);
        if (ret > 0) {
            return memory;
        }
        return new Perfstat.perfstat_memory_total_t();
    }
}

