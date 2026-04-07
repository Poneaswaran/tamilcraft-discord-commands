/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.platform.unix.solaris.LibKstat$Kstat
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.solaris;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.CentralProcessor;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractCentralProcessor;
import com.hypherionmc.sdlink.shaded.oshi.jna.platform.unix.solaris.SolarisLibc;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.unix.solaris.KstatUtil;
import com.sun.jna.platform.unix.solaris.LibKstat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ThreadSafe
final class SolarisCentralProcessor
extends AbstractCentralProcessor {
    private static final String CPU_INFO = "cpu_info";

    SolarisCentralProcessor() {
    }

    @Override
    protected CentralProcessor.ProcessorIdentifier queryProcessorId() {
        String cpuVendor = "";
        String cpuName = "";
        String cpuFamily = "";
        String cpuModel = "";
        String cpuStepping = "";
        long cpuFreq = 0L;
        try (KstatUtil.KstatChain kc = KstatUtil.openChain();){
            LibKstat.Kstat ksp = KstatUtil.KstatChain.lookup(CPU_INFO, -1, null);
            if (ksp != null && KstatUtil.KstatChain.read(ksp)) {
                cpuVendor = KstatUtil.dataLookupString(ksp, "vendor_id");
                cpuName = KstatUtil.dataLookupString(ksp, "brand");
                cpuFamily = KstatUtil.dataLookupString(ksp, "family");
                cpuModel = KstatUtil.dataLookupString(ksp, "model");
                cpuStepping = KstatUtil.dataLookupString(ksp, "stepping");
                cpuFreq = KstatUtil.dataLookupLong(ksp, "clock_MHz") * 1000000L;
            }
        }
        boolean cpu64bit = "64".equals(ExecutingCommand.getFirstAnswer("isainfo -b").trim());
        String processorID = SolarisCentralProcessor.getProcessorID(cpuStepping, cpuModel, cpuFamily);
        return new CentralProcessor.ProcessorIdentifier(cpuVendor, cpuName, cpuFamily, cpuModel, cpuStepping, processorID, cpu64bit, cpuFreq);
    }

    @Override
    protected List<CentralProcessor.LogicalProcessor> initProcessorCounts() {
        Map<Integer, Integer> numaNodeMap = SolarisCentralProcessor.mapNumaNodes();
        ArrayList<CentralProcessor.LogicalProcessor> logProcs = new ArrayList<CentralProcessor.LogicalProcessor>();
        try (KstatUtil.KstatChain kc = KstatUtil.openChain();){
            List<LibKstat.Kstat> kstats = KstatUtil.KstatChain.lookupAll(CPU_INFO, -1, null);
            for (LibKstat.Kstat ksp : kstats) {
                if (ksp == null || !KstatUtil.KstatChain.read(ksp)) continue;
                int procId = logProcs.size();
                String chipId = KstatUtil.dataLookupString(ksp, "chip_id");
                String coreId = KstatUtil.dataLookupString(ksp, "core_id");
                CentralProcessor.LogicalProcessor logProc = new CentralProcessor.LogicalProcessor(procId, ParseUtil.parseIntOrDefault(coreId, 0), ParseUtil.parseIntOrDefault(chipId, 0), numaNodeMap.getOrDefault(procId, 0));
                logProcs.add(logProc);
            }
        }
        if (logProcs.isEmpty()) {
            logProcs.add(new CentralProcessor.LogicalProcessor(0, 0, 0));
        }
        return logProcs;
    }

    private static Map<Integer, Integer> mapNumaNodes() {
        HashMap<Integer, Integer> numaNodeMap = new HashMap<Integer, Integer>();
        int lgroup = 0;
        for (String line : ExecutingCommand.runNative("lgrpinfo -c leaves")) {
            if (line.startsWith("lgroup")) {
                lgroup = ParseUtil.getFirstIntValue(line);
                continue;
            }
            if (!line.contains("CPUs:") && !line.contains("CPU:")) continue;
            for (Integer cpu : ParseUtil.parseHyphenatedIntList(line.split(":")[1])) {
                numaNodeMap.put(cpu, lgroup);
            }
        }
        return numaNodeMap;
    }

    @Override
    public long[] querySystemCpuLoadTicks() {
        long[] ticks = new long[CentralProcessor.TickType.values().length];
        long[][] procTicks = this.getProcessorCpuLoadTicks();
        int i = 0;
        while (i < ticks.length) {
            for (long[] procTick : procTicks) {
                int n = i;
                ticks[n] = ticks[n] + procTick[i];
            }
            int n = i++;
            ticks[n] = ticks[n] / (long)procTicks.length;
        }
        return ticks;
    }

    @Override
    public long[] queryCurrentFreq() {
        long[] freqs = new long[this.getLogicalProcessorCount()];
        Arrays.fill(freqs, -1L);
        try (KstatUtil.KstatChain kc = KstatUtil.openChain();){
            for (int i = 0; i < freqs.length; ++i) {
                for (LibKstat.Kstat ksp : KstatUtil.KstatChain.lookupAll(CPU_INFO, i, null)) {
                    if (!KstatUtil.KstatChain.read(ksp)) continue;
                    freqs[i] = KstatUtil.dataLookupLong(ksp, "current_clock_Hz");
                }
            }
        }
        return freqs;
    }

    @Override
    public long queryMaxFreq() {
        long max = -1L;
        try (KstatUtil.KstatChain kc = KstatUtil.openChain();){
            for (LibKstat.Kstat ksp : KstatUtil.KstatChain.lookupAll(CPU_INFO, 0, null)) {
                String suppFreq;
                if (!KstatUtil.KstatChain.read(ksp) || (suppFreq = KstatUtil.dataLookupString(ksp, "supported_frequencies_Hz")).isEmpty()) continue;
                for (String s : suppFreq.split(":")) {
                    long freq = ParseUtil.parseLongOrDefault(s, -1L);
                    if (max >= freq) continue;
                    max = freq;
                }
            }
        }
        return max;
    }

    @Override
    public double[] getSystemLoadAverage(int nelem) {
        if (nelem < 1 || nelem > 3) {
            throw new IllegalArgumentException("Must include from one to three elements.");
        }
        double[] average = new double[nelem];
        int retval = SolarisLibc.INSTANCE.getloadavg(average, nelem);
        if (retval < nelem) {
            for (int i = Math.max(retval, 0); i < average.length; ++i) {
                average[i] = -1.0;
            }
        }
        return average;
    }

    @Override
    public long[][] queryProcessorCpuLoadTicks() {
        long[][] ticks = new long[this.getLogicalProcessorCount()][CentralProcessor.TickType.values().length];
        int cpu = -1;
        try (KstatUtil.KstatChain kc = KstatUtil.openChain();){
            for (LibKstat.Kstat ksp : KstatUtil.KstatChain.lookupAll("cpu", -1, "sys")) {
                if (++cpu >= ticks.length) {
                    break;
                }
                if (!KstatUtil.KstatChain.read(ksp)) continue;
                ticks[cpu][CentralProcessor.TickType.IDLE.getIndex()] = KstatUtil.dataLookupLong(ksp, "cpu_ticks_idle");
                ticks[cpu][CentralProcessor.TickType.SYSTEM.getIndex()] = KstatUtil.dataLookupLong(ksp, "cpu_ticks_kernel");
                ticks[cpu][CentralProcessor.TickType.USER.getIndex()] = KstatUtil.dataLookupLong(ksp, "cpu_ticks_user");
            }
        }
        return ticks;
    }

    private static String getProcessorID(String stepping, String model, String family) {
        List<String> isainfo = ExecutingCommand.runNative("isainfo -v");
        StringBuilder flags = new StringBuilder();
        for (String line : isainfo) {
            if (line.startsWith("32-bit")) break;
            if (line.startsWith("64-bit")) continue;
            flags.append(' ').append(line.trim());
        }
        return SolarisCentralProcessor.createProcessorID(stepping, model, family, ParseUtil.whitespaces.split(flags.toString().toLowerCase()));
    }

    @Override
    public long queryContextSwitches() {
        long swtch = 0L;
        List<String> kstat = ExecutingCommand.runNative("kstat -p cpu_stat:::/pswitch\\\\|inv_swtch/");
        for (String s : kstat) {
            swtch += ParseUtil.parseLastLong(s, 0L);
        }
        return swtch;
    }

    @Override
    public long queryInterrupts() {
        long intr = 0L;
        List<String> kstat = ExecutingCommand.runNative("kstat -p cpu_stat:::/intr/");
        for (String s : kstat) {
            intr += ParseUtil.parseLastLong(s, 0L);
        }
        return intr;
    }
}

