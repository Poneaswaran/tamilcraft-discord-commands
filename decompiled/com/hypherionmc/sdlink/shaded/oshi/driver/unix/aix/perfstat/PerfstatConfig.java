/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.platform.unix.aix.Perfstat
 *  com.sun.jna.platform.unix.aix.Perfstat$perfstat_partition_config_t
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.unix.aix.perfstat;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.sun.jna.platform.unix.aix.Perfstat;

@ThreadSafe
public final class PerfstatConfig {
    private static final Perfstat PERF = Perfstat.INSTANCE;

    private PerfstatConfig() {
    }

    public static Perfstat.perfstat_partition_config_t queryConfig() {
        Perfstat.perfstat_partition_config_t config = new Perfstat.perfstat_partition_config_t();
        int ret = PERF.perfstat_partition_config(null, config, config.size(), 1);
        if (ret > 0) {
            return config;
        }
        return new Perfstat.perfstat_partition_config_t();
    }
}

