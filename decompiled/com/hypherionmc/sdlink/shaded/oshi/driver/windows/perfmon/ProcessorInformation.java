/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.platform.win32.VersionHelpers
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.windows.perfmon;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.windows.PerfCounterQuery;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.windows.PerfCounterWildcardQuery;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Pair;
import com.sun.jna.platform.win32.VersionHelpers;
import java.util.List;
import java.util.Map;

@ThreadSafe
public final class ProcessorInformation {
    private static final String PROCESSOR = "Processor";
    private static final String PROCESSOR_INFORMATION = "Processor Information";
    private static final String WIN32_PERF_RAW_DATA_COUNTERS_PROCESSOR_INFORMATION_WHERE_NOT_NAME_LIKE_TOTAL = "Win32_PerfRawData_Counters_ProcessorInformation WHERE NOT Name LIKE \"%_Total\"";
    private static final String WIN32_PERF_RAW_DATA_PERF_OS_PROCESSOR_WHERE_NOT_NAME_TOTAL = "Win32_PerfRawData_PerfOS_Processor WHERE NOT Name=\"_Total\"";
    private static final String WIN32_PERF_RAW_DATA_PERF_OS_PROCESSOR_WHERE_NAME_TOTAL = "Win32_PerfRawData_PerfOS_Processor WHERE Name=\"_Total\"";
    private static final boolean IS_WIN7_OR_GREATER = VersionHelpers.IsWindows7OrGreater();

    private ProcessorInformation() {
    }

    public static Pair<List<String>, Map<ProcessorTickCountProperty, List<Long>>> queryProcessorCounters() {
        return IS_WIN7_OR_GREATER ? PerfCounterWildcardQuery.queryInstancesAndValues(ProcessorTickCountProperty.class, PROCESSOR_INFORMATION, WIN32_PERF_RAW_DATA_COUNTERS_PROCESSOR_INFORMATION_WHERE_NOT_NAME_LIKE_TOTAL) : PerfCounterWildcardQuery.queryInstancesAndValues(ProcessorTickCountProperty.class, PROCESSOR, WIN32_PERF_RAW_DATA_PERF_OS_PROCESSOR_WHERE_NOT_NAME_TOTAL);
    }

    public static Map<InterruptsProperty, Long> queryInterruptCounters() {
        return PerfCounterQuery.queryValues(InterruptsProperty.class, PROCESSOR, WIN32_PERF_RAW_DATA_PERF_OS_PROCESSOR_WHERE_NAME_TOTAL);
    }

    public static Pair<List<String>, Map<ProcessorFrequencyProperty, List<Long>>> queryFrequencyCounters() {
        return PerfCounterWildcardQuery.queryInstancesAndValues(ProcessorFrequencyProperty.class, PROCESSOR_INFORMATION, WIN32_PERF_RAW_DATA_COUNTERS_PROCESSOR_INFORMATION_WHERE_NOT_NAME_LIKE_TOTAL);
    }

    public static enum ProcessorTickCountProperty implements PerfCounterWildcardQuery.PdhCounterWildcardProperty
    {
        NAME("^*_Total"),
        PERCENTDPCTIME("% DPC Time"),
        PERCENTINTERRUPTTIME("% Interrupt Time"),
        PERCENTPRIVILEGEDTIME("% Privileged Time"),
        PERCENTPROCESSORTIME("% Processor Time"),
        PERCENTUSERTIME("% User Time");

        private final String counter;

        private ProcessorTickCountProperty(String counter) {
            this.counter = counter;
        }

        @Override
        public String getCounter() {
            return this.counter;
        }
    }

    public static enum InterruptsProperty implements PerfCounterQuery.PdhCounterProperty
    {
        INTERRUPTSPERSEC("_Total", "Interrupts/sec");

        private final String instance;
        private final String counter;

        private InterruptsProperty(String instance, String counter) {
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

    public static enum ProcessorFrequencyProperty implements PerfCounterWildcardQuery.PdhCounterWildcardProperty
    {
        NAME("^*_Total"),
        PERCENTOFMAXIMUMFREQUENCY("% of Maximum Frequency");

        private final String counter;

        private ProcessorFrequencyProperty(String counter) {
            this.counter = counter;
        }

        @Override
        public String getCounter() {
            return this.counter;
        }
    }
}

