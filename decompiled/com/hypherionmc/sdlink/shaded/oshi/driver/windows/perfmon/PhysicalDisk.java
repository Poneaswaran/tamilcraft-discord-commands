/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.windows.perfmon;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.windows.PerfCounterWildcardQuery;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Pair;
import java.util.List;
import java.util.Map;

@ThreadSafe
public final class PhysicalDisk {
    private static final String PHYSICAL_DISK = "PhysicalDisk";
    private static final String WIN32_PERF_RAW_DATA_PERF_DISK_PHYSICAL_DISK_WHERE_NOT_NAME_TOTAL = "Win32_PerfRawData_PerfDisk_PhysicalDisk WHERE NOT Name=\"_Total\"";

    private PhysicalDisk() {
    }

    public static Pair<List<String>, Map<PhysicalDiskProperty, List<Long>>> queryDiskCounters() {
        return PerfCounterWildcardQuery.queryInstancesAndValues(PhysicalDiskProperty.class, PHYSICAL_DISK, WIN32_PERF_RAW_DATA_PERF_DISK_PHYSICAL_DISK_WHERE_NOT_NAME_TOTAL);
    }

    public static enum PhysicalDiskProperty implements PerfCounterWildcardQuery.PdhCounterWildcardProperty
    {
        NAME("^_Total"),
        DISKREADSPERSEC("Disk Reads/sec"),
        DISKREADBYTESPERSEC("Disk Read Bytes/sec"),
        DISKWRITESPERSEC("Disk Writes/sec"),
        DISKWRITEBYTESPERSEC("Disk Write Bytes/sec"),
        CURRENTDISKQUEUELENGTH("Current Disk Queue Length"),
        PERCENTDISKTIME("% Disk Time");

        private final String counter;

        private PhysicalDiskProperty(String counter) {
            this.counter = counter;
        }

        @Override
        public String getCounter() {
            return this.counter;
        }
    }
}

