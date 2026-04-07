/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.linux;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.driver.linux.Lshw;
import com.hypherionmc.sdlink.shaded.oshi.driver.linux.proc.CpuStat;
import com.hypherionmc.sdlink.shaded.oshi.hardware.CentralProcessor;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractCentralProcessor;
import com.hypherionmc.sdlink.shaded.oshi.jna.platform.linux.LinuxLibc;
import com.hypherionmc.sdlink.shaded.oshi.software.os.linux.LinuxOperatingSystem;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.FileUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.GlobalConfig;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.linux.ProcPath;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

@ThreadSafe
final class LinuxCentralProcessor
extends AbstractCentralProcessor {
    private static final String CPUFREQ_PATH = "com.hypherionmc.sdlink.shaded.oshi.cpu.freq.path";

    LinuxCentralProcessor() {
    }

    @Override
    protected CentralProcessor.ProcessorIdentifier queryProcessorId() {
        String cpuVendor = "";
        String cpuName = "";
        String cpuFamily = "";
        String cpuModel = "";
        String cpuStepping = "";
        long cpuFreq = 0L;
        boolean cpu64bit = false;
        StringBuilder armStepping = new StringBuilder();
        String[] flags = new String[]{};
        List<String> cpuInfo = FileUtil.readFile(ProcPath.CPUINFO);
        block25: for (String line : cpuInfo) {
            String[] splitLine = ParseUtil.whitespacesColonWhitespace.split(line);
            if (splitLine.length < 2) {
                if (!line.startsWith("CPU architecture: ")) continue;
                cpuFamily = line.replace("CPU architecture: ", "").trim();
                continue;
            }
            block14 : switch (splitLine[0]) {
                case "vendor_id": 
                case "CPU implementer": {
                    cpuVendor = splitLine[1];
                    break;
                }
                case "model name": 
                case "Processor": {
                    cpuName = splitLine[1];
                    break;
                }
                case "flags": {
                    for (String flag : flags = splitLine[1].toLowerCase().split(" ")) {
                        if (!"lm".equals(flag)) continue;
                        cpu64bit = true;
                        break block14;
                    }
                    continue block25;
                }
                case "stepping": {
                    cpuStepping = splitLine[1];
                    break;
                }
                case "CPU variant": {
                    if (armStepping.toString().startsWith("r")) break;
                    armStepping.insert(0, "r" + splitLine[1]);
                    break;
                }
                case "CPU revision": {
                    if (armStepping.toString().contains("p")) break;
                    armStepping.append('p').append(splitLine[1]);
                    break;
                }
                case "model": 
                case "CPU part": {
                    cpuModel = splitLine[1];
                    break;
                }
                case "cpu family": {
                    cpuFamily = splitLine[1];
                    break;
                }
                case "cpu MHz": {
                    cpuFreq = ParseUtil.parseHertz(splitLine[1]);
                    break;
                }
            }
        }
        if (cpuName.contains("Hz")) {
            cpuFreq = -1L;
        } else {
            long cpuCapacity = Lshw.queryCpuCapacity();
            if (cpuCapacity > cpuFreq) {
                cpuFreq = cpuCapacity;
            }
        }
        if (cpuStepping.isEmpty()) {
            cpuStepping = armStepping.toString();
        }
        String processorID = LinuxCentralProcessor.getProcessorID(cpuVendor, cpuStepping, cpuModel, cpuFamily, flags);
        if (cpuVendor.startsWith("0x")) {
            List<String> lscpu = ExecutingCommand.runNative("lscpu");
            for (String line : lscpu) {
                if (!line.startsWith("Architecture:")) continue;
                cpuVendor = line.replace("Architecture:", "").trim();
            }
        }
        return new CentralProcessor.ProcessorIdentifier(cpuVendor, cpuName, cpuFamily, cpuModel, cpuStepping, processorID, cpu64bit, cpuFreq);
    }

    @Override
    protected List<CentralProcessor.LogicalProcessor> initProcessorCounts() {
        Map<Integer, Integer> numaNodeMap = LinuxCentralProcessor.mapNumaNodes();
        List<String> procCpu = FileUtil.readFile(ProcPath.CPUINFO);
        ArrayList<CentralProcessor.LogicalProcessor> logProcs = new ArrayList<CentralProcessor.LogicalProcessor>();
        int currentProcessor = 0;
        int currentCore = 0;
        int currentPackage = 0;
        boolean first = true;
        for (String cpu : procCpu) {
            if (cpu.startsWith("processor")) {
                if (!first) {
                    logProcs.add(new CentralProcessor.LogicalProcessor(currentProcessor, currentCore, currentPackage, numaNodeMap.getOrDefault(currentProcessor, 0)));
                } else {
                    first = false;
                }
                currentProcessor = ParseUtil.parseLastInt(cpu, 0);
                continue;
            }
            if (cpu.startsWith("core id") || cpu.startsWith("cpu number")) {
                currentCore = ParseUtil.parseLastInt(cpu, 0);
                continue;
            }
            if (!cpu.startsWith("physical id")) continue;
            currentPackage = ParseUtil.parseLastInt(cpu, 0);
        }
        logProcs.add(new CentralProcessor.LogicalProcessor(currentProcessor, currentCore, currentPackage, numaNodeMap.getOrDefault(currentProcessor, 0)));
        return logProcs;
    }

    private static Map<Integer, Integer> mapNumaNodes() {
        HashMap<Integer, Integer> numaNodeMap = new HashMap<Integer, Integer>();
        List<String> lscpu = ExecutingCommand.runNative("lscpu -p=cpu,node");
        for (String line : lscpu) {
            String[] split;
            if (line.startsWith("#") || (split = line.split(",")).length != 2) continue;
            numaNodeMap.put(ParseUtil.parseIntOrDefault(split[0], 0), ParseUtil.parseIntOrDefault(split[1], 0));
        }
        return numaNodeMap;
    }

    @Override
    public long[] querySystemCpuLoadTicks() {
        long[] ticks = CpuStat.getSystemCpuLoadTicks();
        if (LongStream.of(ticks).sum() == 0L) {
            ticks = CpuStat.getSystemCpuLoadTicks();
        }
        long hz = LinuxOperatingSystem.getHz();
        for (int i = 0; i < ticks.length; ++i) {
            ticks[i] = ticks[i] * 1000L / hz;
        }
        return ticks;
    }

    @Override
    public long[] queryCurrentFreq() {
        int i;
        String cpuFreqPath = GlobalConfig.get(CPUFREQ_PATH, "");
        long[] freqs = new long[this.getLogicalProcessorCount()];
        long max = 0L;
        for (i = 0; i < freqs.length; ++i) {
            freqs[i] = FileUtil.getLongFromFile(cpuFreqPath + "/cpu" + i + "/cpufreq/scaling_cur_freq");
            if (freqs[i] == 0L) {
                freqs[i] = FileUtil.getLongFromFile(cpuFreqPath + "/cpu" + i + "/cpufreq/cpuinfo_cur_freq");
            }
            if (max >= freqs[i]) continue;
            max = freqs[i];
        }
        if (max > 0L) {
            i = 0;
            while (i < freqs.length) {
                int n = i++;
                freqs[n] = freqs[n] * 1000L;
            }
            return freqs;
        }
        Arrays.fill(freqs, -1L);
        List<String> cpuInfo = FileUtil.readFile(ProcPath.CPUINFO);
        int proc = 0;
        for (String s : cpuInfo) {
            if (!s.toLowerCase().contains("cpu mhz")) continue;
            freqs[proc] = Math.round(ParseUtil.parseLastDouble(s, 0.0) * 1000000.0);
            if (++proc < freqs.length) continue;
            break;
        }
        return freqs;
    }

    @Override
    public long queryMaxFreq() {
        File cpufreqdir;
        File[] policies;
        String cpuFreqPath = GlobalConfig.get(CPUFREQ_PATH, "");
        long max = Arrays.stream(this.getCurrentFreq()).max().orElse(-1L);
        if (max > 0L) {
            max /= 1000L;
        }
        if ((policies = (cpufreqdir = new File(cpuFreqPath + "/cpufreq")).listFiles()) != null) {
            for (int i = 0; i < policies.length; ++i) {
                File f = policies[i];
                if (!f.getName().startsWith("policy")) continue;
                long freq = FileUtil.getLongFromFile(cpuFreqPath + "/cpufreq/" + f.getName() + "/scaling_max_freq");
                if (freq == 0L) {
                    freq = FileUtil.getLongFromFile(cpuFreqPath + "/cpufreq/" + f.getName() + "/cpuinfo_max_freq");
                }
                if (max >= freq) continue;
                max = freq;
            }
        }
        if (max > 0L) {
            long lshwMax = Lshw.queryCpuCapacity();
            return lshwMax > (max *= 1000L) ? lshwMax : max;
        }
        return -1L;
    }

    @Override
    public double[] getSystemLoadAverage(int nelem) {
        if (nelem < 1 || nelem > 3) {
            throw new IllegalArgumentException("Must include from one to three elements.");
        }
        double[] average = new double[nelem];
        int retval = LinuxLibc.INSTANCE.getloadavg(average, nelem);
        if (retval < nelem) {
            for (int i = Math.max(retval, 0); i < average.length; ++i) {
                average[i] = -1.0;
            }
        }
        return average;
    }

    @Override
    public long[][] queryProcessorCpuLoadTicks() {
        long[][] ticks = CpuStat.getProcessorCpuLoadTicks(this.getLogicalProcessorCount());
        if (LongStream.of(ticks[0]).sum() == 0L) {
            ticks = CpuStat.getProcessorCpuLoadTicks(this.getLogicalProcessorCount());
        }
        long hz = LinuxOperatingSystem.getHz();
        for (int i = 0; i < ticks.length; ++i) {
            for (int j = 0; j < ticks[i].length; ++j) {
                ticks[i][j] = ticks[i][j] * 1000L / hz;
            }
        }
        return ticks;
    }

    private static String getProcessorID(String vendor, String stepping, String model, String family, String[] flags) {
        boolean procInfo = false;
        String marker = "Processor Information";
        for (String checkLine : ExecutingCommand.runNative("dmidecode -t 4")) {
            if (!procInfo && checkLine.contains(marker)) {
                marker = "ID:";
                procInfo = true;
                continue;
            }
            if (!procInfo || !checkLine.contains(marker)) continue;
            return checkLine.split(marker)[1].trim();
        }
        marker = "eax=";
        for (String checkLine : ExecutingCommand.runNative("cpuid -1r")) {
            if (!checkLine.contains(marker) || !checkLine.trim().startsWith("0x00000001")) continue;
            String eax = "";
            String edx = "";
            for (String register : ParseUtil.whitespaces.split(checkLine)) {
                if (register.startsWith("eax=")) {
                    eax = ParseUtil.removeMatchingString(register, "eax=0x");
                    continue;
                }
                if (!register.startsWith("edx=")) continue;
                edx = ParseUtil.removeMatchingString(register, "edx=0x");
            }
            return edx + eax;
        }
        if (vendor.startsWith("0x")) {
            return LinuxCentralProcessor.createMIDR(vendor, stepping, model, family) + "00000000";
        }
        return LinuxCentralProcessor.createProcessorID(stepping, model, family, flags);
    }

    private static String createMIDR(String vendor, String stepping, String model, String family) {
        int midrBytes = 0;
        if (stepping.startsWith("r") && stepping.contains("p")) {
            String[] rev = stepping.substring(1).split("p");
            midrBytes |= ParseUtil.parseLastInt(rev[1], 0);
            midrBytes |= ParseUtil.parseLastInt(rev[0], 0) << 20;
        }
        midrBytes |= ParseUtil.parseLastInt(model, 0) << 4;
        midrBytes |= ParseUtil.parseLastInt(family, 0) << 16;
        return String.format("%08X", midrBytes |= ParseUtil.parseLastInt(vendor, 0) << 24);
    }

    @Override
    public long queryContextSwitches() {
        return CpuStat.getContextSwitches();
    }

    @Override
    public long queryInterrupts() {
        return CpuStat.getInterrupts();
    }
}

