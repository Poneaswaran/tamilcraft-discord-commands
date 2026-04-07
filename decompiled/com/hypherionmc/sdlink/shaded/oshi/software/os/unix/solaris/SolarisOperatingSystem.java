/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.platform.unix.solaris.LibKstat$Kstat
 */
package com.hypherionmc.sdlink.shaded.oshi.software.os.unix.solaris;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.driver.linux.proc.ProcessStat;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.solaris.Who;
import com.hypherionmc.sdlink.shaded.oshi.jna.platform.unix.solaris.SolarisLibc;
import com.hypherionmc.sdlink.shaded.oshi.software.common.AbstractOperatingSystem;
import com.hypherionmc.sdlink.shaded.oshi.software.os.FileSystem;
import com.hypherionmc.sdlink.shaded.oshi.software.os.InternetProtocolStats;
import com.hypherionmc.sdlink.shaded.oshi.software.os.NetworkParams;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSProcess;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSService;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSSession;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OperatingSystem;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.solaris.SolarisFileSystem;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.solaris.SolarisInternetProtocolStats;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.solaris.SolarisNetworkParams;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.solaris.SolarisOSProcess;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.unix.solaris.KstatUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Pair;
import com.sun.jna.platform.unix.solaris.LibKstat;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ThreadSafe
public class SolarisOperatingSystem
extends AbstractOperatingSystem {
    static final String PS_COMMAND_ARGS = Arrays.stream(PsKeywords.values()).map(Enum::name).map(String::toLowerCase).collect(Collectors.joining(","));
    private static final long BOOTTIME = SolarisOperatingSystem.querySystemBootTime();

    @Override
    public String queryManufacturer() {
        return "Oracle";
    }

    @Override
    public Pair<String, OperatingSystem.OSVersionInfo> queryFamilyVersionInfo() {
        String[] split = ParseUtil.whitespaces.split(ExecutingCommand.getFirstAnswer("uname -rv"));
        String version = split[0];
        String buildNumber = null;
        if (split.length > 1) {
            buildNumber = split[1];
        }
        return new Pair<String, OperatingSystem.OSVersionInfo>("SunOS", new OperatingSystem.OSVersionInfo(version, "Solaris", buildNumber));
    }

    @Override
    protected int queryBitness(int jvmBitness) {
        if (jvmBitness == 64) {
            return 64;
        }
        return ParseUtil.parseIntOrDefault(ExecutingCommand.getFirstAnswer("isainfo -b"), 32);
    }

    @Override
    public FileSystem getFileSystem() {
        return new SolarisFileSystem();
    }

    @Override
    public InternetProtocolStats getInternetProtocolStats() {
        return new SolarisInternetProtocolStats();
    }

    @Override
    public List<OSSession> getSessions() {
        return USE_WHO_COMMAND ? super.getSessions() : Who.queryUtxent();
    }

    @Override
    public OSProcess getProcess(int pid) {
        List<OSProcess> procs = SolarisOperatingSystem.getProcessListFromPS("ps -o " + PS_COMMAND_ARGS + " -p " + pid, pid);
        if (procs.isEmpty()) {
            return null;
        }
        return procs.get(0);
    }

    @Override
    public List<OSProcess> queryAllProcesses() {
        return SolarisOperatingSystem.queryAllProcessesFromPS();
    }

    @Override
    public List<OSProcess> queryChildProcesses(int parentPid) {
        List<OSProcess> allProcs = SolarisOperatingSystem.queryAllProcessesFromPS();
        Set<Integer> descendantPids = SolarisOperatingSystem.getChildrenOrDescendants(allProcs, parentPid, false);
        return allProcs.stream().filter(p -> descendantPids.contains(p.getProcessID())).collect(Collectors.toList());
    }

    @Override
    public List<OSProcess> queryDescendantProcesses(int parentPid) {
        List<OSProcess> allProcs = SolarisOperatingSystem.queryAllProcessesFromPS();
        Set<Integer> descendantPids = SolarisOperatingSystem.getChildrenOrDescendants(allProcs, parentPid, true);
        return allProcs.stream().filter(p -> descendantPids.contains(p.getProcessID())).collect(Collectors.toList());
    }

    private static List<OSProcess> queryAllProcessesFromPS() {
        return SolarisOperatingSystem.getProcessListFromPS("ps -eo " + PS_COMMAND_ARGS, -1);
    }

    private static List<OSProcess> getProcessListFromPS(String psCommand, int pid) {
        ArrayList<OSProcess> procs = new ArrayList<OSProcess>();
        List<String> procList = ExecutingCommand.runNative(psCommand);
        if (procList.size() > 1) {
            List<String> prstatList = pid < 0 ? ExecutingCommand.runNative("prstat -v 1 1") : ExecutingCommand.runNative("prstat -v -p " + pid + " 1 1");
            HashMap<String, String> prstatRowMap = new HashMap<String, String>();
            for (String s : prstatList) {
                String row = s.trim();
                int idx = row.indexOf(32);
                if (idx <= 0) continue;
                prstatRowMap.put(row.substring(0, idx), row);
            }
            procList.remove(0);
            for (String proc : procList) {
                Map<PsKeywords, String> psMap = ParseUtil.stringToEnumMap(PsKeywords.class, proc.trim(), ' ');
                if (!psMap.containsKey((Object)PsKeywords.ARGS)) continue;
                String pidStr = psMap.get((Object)PsKeywords.PID);
                Map<PrstatKeywords, String> prstatMap = ParseUtil.stringToEnumMap(PrstatKeywords.class, prstatRowMap.getOrDefault(pidStr, ""), ' ');
                procs.add(new SolarisOSProcess(pid < 0 ? ParseUtil.parseIntOrDefault(pidStr, 0) : pid, psMap, prstatMap));
            }
        }
        return procs;
    }

    @Override
    public int getProcessId() {
        return SolarisLibc.INSTANCE.getpid();
    }

    @Override
    public int getProcessCount() {
        return ProcessStat.getPidFiles().length;
    }

    @Override
    public int getThreadCount() {
        List<String> threadList = ExecutingCommand.runNative("ps -eLo pid");
        if (!threadList.isEmpty()) {
            return threadList.size() - 1;
        }
        return this.getProcessCount();
    }

    @Override
    public long getSystemUptime() {
        return SolarisOperatingSystem.querySystemUptime();
    }

    private static long querySystemUptime() {
        try (KstatUtil.KstatChain kc = KstatUtil.openChain();){
            LibKstat.Kstat ksp = KstatUtil.KstatChain.lookup("unix", 0, "system_misc");
            if (ksp != null) {
                long l = ksp.ks_snaptime / 1000000000L;
                return l;
            }
        }
        return 0L;
    }

    @Override
    public long getSystemBootTime() {
        return BOOTTIME;
    }

    private static long querySystemBootTime() {
        try (KstatUtil.KstatChain kc = KstatUtil.openChain();){
            LibKstat.Kstat ksp = KstatUtil.KstatChain.lookup("unix", 0, "system_misc");
            if (ksp != null && KstatUtil.KstatChain.read(ksp)) {
                long l = KstatUtil.dataLookupLong(ksp, "boot_time");
                return l;
            }
        }
        return System.currentTimeMillis() / 1000L - SolarisOperatingSystem.querySystemUptime();
    }

    @Override
    public NetworkParams getNetworkParams() {
        return new SolarisNetworkParams();
    }

    @Override
    public OSService[] getServices() {
        File[] listFiles;
        ArrayList<OSService> services = new ArrayList<OSService>();
        ArrayList<String> legacySvcs = new ArrayList<String>();
        File dir = new File("/etc/init.d");
        if (dir.exists() && dir.isDirectory() && (listFiles = dir.listFiles()) != null) {
            for (File f : listFiles) {
                legacySvcs.add(f.getName());
            }
        }
        List<String> svcs = ExecutingCommand.runNative("svcs -p");
        block1: for (String line : svcs) {
            if (line.startsWith("online")) {
                int delim = line.lastIndexOf(":/");
                if (delim <= 0) continue;
                String name = line.substring(delim + 1);
                if (name.endsWith(":default")) {
                    name = name.substring(0, name.length() - 8);
                }
                services.add(new OSService(name, 0, OSService.State.STOPPED));
                continue;
            }
            if (line.startsWith(" ")) {
                String[] split = ParseUtil.whitespaces.split(line.trim());
                if (split.length != 3) continue;
                services.add(new OSService(split[2], ParseUtil.parseIntOrDefault(split[1], 0), OSService.State.RUNNING));
                continue;
            }
            if (!line.startsWith("legacy_run")) continue;
            for (String svc : legacySvcs) {
                if (!line.endsWith(svc)) continue;
                services.add(new OSService(svc, 0, OSService.State.STOPPED));
                continue block1;
            }
        }
        return services.toArray(new OSService[0]);
    }

    static enum PsKeywords {
        S,
        PID,
        PPID,
        USER,
        UID,
        GROUP,
        GID,
        NLWP,
        PRI,
        VSZ,
        RSS,
        ETIME,
        TIME,
        COMM,
        ARGS;

    }

    static enum PrstatKeywords {
        PID,
        USERNAME,
        USR,
        SYS,
        TRP,
        TFL,
        DFL,
        LCK,
        SLP,
        LAT,
        VCX,
        ICX,
        SCL,
        SIG,
        PROCESS_NLWP;

    }
}

