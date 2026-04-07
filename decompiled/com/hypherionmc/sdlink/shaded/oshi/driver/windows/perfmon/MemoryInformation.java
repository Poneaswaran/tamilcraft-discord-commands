/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.windows.perfmon;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.windows.PerfCounterQuery;
import java.util.Map;

@ThreadSafe
public final class MemoryInformation {
    private static final String MEMORY = "Memory";
    private static final String WIN32_PERF_RAW_DATA_PERF_OS_MEMORY = "Win32_PerfRawData_PerfOS_Memory";

    private MemoryInformation() {
    }

    public static Map<PageSwapProperty, Long> queryPageSwaps() {
        return PerfCounterQuery.queryValues(PageSwapProperty.class, MEMORY, WIN32_PERF_RAW_DATA_PERF_OS_MEMORY);
    }

    public static enum PageSwapProperty implements PerfCounterQuery.PdhCounterProperty
    {
        PAGESINPUTPERSEC(null, "Pages Input/sec"),
        PAGESOUTPUTPERSEC(null, "Pages Output/sec");

        private final String instance;
        private final String counter;

        private PageSwapProperty(String instance, String counter) {
            this.instance = instance;
            this.counter = counter;
        }

        @Override
        public String getInstance() {
            return this.instance;
        }

        @Override
        public String getCounter() {
            return this.counter;
        }
    }
}

