/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Native
 *  com.sun.jna.platform.unix.aix.Perfstat$perfstat_partition_config_t
 *  com.sun.jna.platform.unix.aix.Perfstat$perfstat_process_t
 */
package com.hypherionmc.sdlink.shaded.oshi.software.os.unix.aix;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.aix.Uptime;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.aix.Who;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.aix.perfstat.PerfstatConfig;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.aix.perfstat.PerfstatProcess;
import com.hypherionmc.sdlink.shaded.oshi.jna.platform.unix.aix.AixLibc;
import com.hypherionmc.sdlink.shaded.oshi.software.common.AbstractOperatingSystem;
import com.hypherionmc.sdlink.shaded.oshi.software.os.FileSystem;
import com.hypherionmc.sdlink.shaded.oshi.software.os.InternetProtocolStats;
import com.hypherionmc.sdlink.shaded.oshi.software.os.NetworkParams;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSProcess;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSService;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OperatingSystem;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.aix.AixFileSystem;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.aix.AixInternetProtocolStats;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.aix.AixNetworkParams;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.aix.AixOSProcess;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.Util;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Pair;
import com.sun.jna.Native;
import com.sun.jna.platform.unix.aix.Perfstat;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@ThreadSafe
public class AixOperatingSystem
extends AbstractOperatingSystem {
    private final Supplier<Perfstat.perfstat_partition_config_t> config = Memoizer.memoize(PerfstatConfig::queryConfig);
    Supplier<Perfstat.perfstat_process_t[]> procCpu = Memoizer.memoize(PerfstatProcess::queryProcesses, Memoizer.defaultExpiration());
    private static final long BOOTTIME = AixOperatingSystem.querySystemBootTimeMillis() / 1000L;
    static final String PS_COMMAND_ARGS = Arrays.stream(PsKeywords.values()).map(Enum::name).map(String::toLowerCase).collect(Collectors.joining(","));

    @Override
    public String queryManufacturer() {
        return "IBM";
    }

    @Override
    public Pair<String, OperatingSystem.OSVersionInfo> queryFamilyVersionInfo() {
        String releaseNumber;
        Perfstat.perfstat_partition_config_t cfg = this.config.get();
        String systemName = System.getProperty("os.name");
        String archName = System.getProperty("os.arch");
        String versionNumber = System.getProperty("os.version");
        if (Util.isBlank(versionNumber)) {
            versionNumber = ExecutingCommand.getFirstAnswer("oslevel");
        }
        if (Util.isBlank(releaseNumber = Native.toString((byte[])cfg.OSBuild))) {
            releaseNumber = ExecutingCommand.getFirstAnswer("oslevel -s");
        } else {
            int idx = releaseNumber.lastIndexOf(32);
            if (idx > 0 && idx < releaseNumber.length()) {
                releaseNumber = releaseNumber.substring(idx + 1);
            }
        }
        return new Pair<String, OperatingSystem.OSVersionInfo>(systemName, new OperatingSystem.OSVersionInfo(versionNumber, archName, releaseNumber));
    }

    @Override
    protected int queryBitness(int jvmBitness) {
        if (jvmBitness == 64) {
            return 64;
        }
        return (this.config.get().conf & 0x800000) > 0 ? 64 : 32;
    }

    @Override
    public FileSystem getFileSystem() {
        return new AixFileSystem();
    }

    @Override
    public InternetProtocolStats getInternetProtocolStats() {
        return new AixInternetProtocolStats();
    }

    @Override
    public List<OSProcess> queryAllProcesses() {
        return this.getProcessListFromPS("ps -A -o " + PS_COMMAND_ARGS, -1);
    }

    @Override
    public List<OSProcess> queryChildProcesses(int parentPid) {
        List<OSProcess> allProcs = this.queryAllProcesses();
        Set<Integer> descendantPids = AixOperatingSystem.getChildrenOrDescendants(allProcs, parentPid, false);
        return allProcs.stream().filter(p -> descendantPids.contains(p.getProcessID())).collect(Collectors.toList());
    }

    @Override
    public List<OSProcess> queryDescendantProcesses(int parentPid) {
        List<OSProcess> allProcs = this.queryAllProcesses();
        Set<Integer> descendantPids = AixOperatingSystem.getChildrenOrDescendants(allProcs, parentPid, true);
        return allProcs.stream().filter(p -> descendantPids.contains(p.getProcessID())).collect(Collectors.toList());
    }

    @Override
    public OSProcess getProcess(int pid) {
        List<OSProcess> procs = this.getProcessListFromPS("ps -o " + PS_COMMAND_ARGS + " -p ", pid);
        if (procs.isEmpty()) {
            return null;
        }
        return procs.get(0);
    }

    private List<OSProcess> getProcessListFromPS(String psCommand, int pid) {
        Perfstat.perfstat_process_t[] perfstat = this.procCpu.get();
        List<String> procList = ExecutingCommand.runNative(psCommand + (pid < 0 ? "" : Integer.valueOf(pid)));
        if (procList.isEmpty() || procList.size() < 2) {
            return Collections.emptyList();
        }
        HashMap<Integer, Pair<Long, Long>> cpuMap = new HashMap<Integer, Pair<Long, Long>>();
        for (Perfstat.perfstat_process_t stat : perfstat) {
            cpuMap.put((int)stat.pid, new Pair<Long, Long>((long)stat.ucpu_time, (long)stat.scpu_time));
        }
        procList.remove(0);
        ArrayList<OSProcess> procs = new ArrayList<OSProcess>();
        for (String proc : procList) {
            Map<PsKeywords, String> psMap = ParseUtil.stringToEnumMap(PsKeywords.class, proc.trim(), ' ');
            if (!psMap.containsKey((Object)PsKeywords.ARGS)) continue;
            procs.add(new AixOSProcess(pid < 0 ? ParseUtil.parseIntOrDefault(psMap.get((Object)PsKeywords.PID), 0) : pid, psMap, cpuMap, this.procCpu));
        }
        return procs;
    }

    @Override
    public int getProcessId() {
        return AixLibc.INSTANCE.getpid();
    }

    @Override
    public int getProcessCount() {
        return this.procCpu.get().length;
    }

    @Override
    public int getThreadCount() {
        long tc = 0L;
        for (Perfstat.perfstat_process_t proc : this.procCpu.get()) {
            tc += proc.num_threads;
        }
        return (int)tc;
    }

    @Override
    public long getSystemUptime() {
        return System.currentTimeMillis() / 1000L - BOOTTIME;
    }

    @Override
    public long getSystemBootTime() {
        return BOOTTIME;
    }

    private static long querySystemBootTimeMillis() {
        long bootTime = Who.queryBootTime();
        if (bootTime >= 1000L) {
            return bootTime;
        }
        return System.currentTimeMillis() - Uptime.queryUpTime();
    }

    @Override
    public NetworkParams getNetworkParams() {
        return new AixNetworkParams();
    }

    @Override
    public OSService[] getServices() {
        File[] listFiles;
        File dir;
        ArrayList<OSService> services = new ArrayList<OSService>();
        List<String> systemServicesInfoList = ExecutingCommand.runNative("lssrc -a");
        if (systemServicesInfoList.size() > 1) {
            systemServicesInfoList.remove(0);
            for (String systemService : systemServicesInfoList) {
                String[] serviceSplit = ParseUtil.whitespaces.split(systemService.trim());
                if (systemService.contains("active")) {
                    if (serviceSplit.length == 4) {
                        services.add(new OSService(serviceSplit[0], ParseUtil.parseIntOrDefault(serviceSplit[2], 0), OSService.State.RUNNING));
                        continue;
                    }
                    if (serviceSplit.length != 3) continue;
                    services.add(new OSService(serviceSplit[0], ParseUtil.parseIntOrDefault(serviceSplit[1], 0), OSService.State.RUNNING));
                    continue;
                }
                if (!systemService.contains("inoperative")) continue;
                services.add(new OSService(serviceSplit[0], 0, OSService.State.STOPPED));
            }
        }
        if ((dir = new File("/etc/rc.d/init.d")).exists() && dir.isDirectory() && (listFiles = dir.listFiles()) != null) {
            for (File file : listFiles) {
                String installedService = ExecutingCommand.getFirstAnswer(file.getAbsolutePath() + " status");
                if (installedService.contains("running")) {
                    services.add(new OSService(file.getName(), ParseUtil.parseLastInt(installedService, 0), OSService.State.RUNNING));
                    continue;
                }
                services.add(new OSService(file.getName(), 0, OSService.State.STOPPED));
            }
        }
        return services.toArray(new OSService[0]);
    }

    static enum PsKeywords {
        ST,
        PID,
        PPID,
        USER,
        UID,
        GROUP,
        GID,
        THCOUNT,
        PRI,
        VSIZE,
        RSSIZE,
        ETIME,
        TIME,
        COMM,
        PAGEIN,
        ARGS;

    }
}

