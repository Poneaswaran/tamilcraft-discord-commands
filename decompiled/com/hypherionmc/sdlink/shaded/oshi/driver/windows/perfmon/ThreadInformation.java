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
public final class ThreadInformation {
    private static final String THREAD = "Thread";
    private static final String WIN32_PERF_RAW_DATA_PERF_PROC_THREAD = "Win32_PerfRawData_PerfProc_Thread WHERE NOT Name LIKE \"%_Total\"";

    private ThreadInformation() {
    }

    public static Pair<List<String>, Map<ThreadPerformanceProperty, List<Long>>> queryThreadCounters() {
        return PerfCounterWildcardQuery.queryInstancesAndValues(ThreadPerformanceProperty.class, THREAD, WIN32_PERF_RAW_DATA_PERF_PROC_THREAD);
    }

    public static enum ThreadPerformanceProperty implements PerfCounterWildcardQuery.PdhCounterWildcardProperty
    {
        NAME("^*_Total"),
        PERCENTUSERTIME("% User Time"),
        PERCENTPRIVILEGEDTIME("% Privileged Time"),
        ELAPSEDTIME("Elapsed Time"),
        PRIORITYCURRENT("Priority Current"),
        STARTADDRESS("Start Address"),
        THREADSTATE("Thread State"),
        THREADWAITREASON("Thread Wait Reason"),
        IDPROCESS("ID Process"),
        IDTHREAD("ID Thread"),
        CONTEXTSWITCHESPERSEC("Context Switches/sec");

        private final String counter;

        private ThreadPerformanceProperty(String counter) {
            this.counter = counter;
        }

        @Override
        public String getCounter() {
            return this.counter;
        }
    }
}

