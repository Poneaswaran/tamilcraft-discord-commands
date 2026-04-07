/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.platform.unix.aix.Perfstat$perfstat_process_t
 */
package com.hypherionmc.sdlink.shaded.oshi.software.os.unix.aix;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.aix.PsInfo;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.aix.perfstat.PerfstatCpu;
import com.hypherionmc.sdlink.shaded.oshi.software.common.AbstractOSProcess;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSProcess;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSThread;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.aix.AixOSThread;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.aix.AixOperatingSystem;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.LsofUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Pair;
import com.sun.jna.platform.unix.aix.Perfstat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@ThreadSafe
public class AixOSProcess
extends AbstractOSProcess {
    private Supplier<Integer> bitness = Memoizer.memoize(this::queryBitness);
    private Supplier<String> commandLine = Memoizer.memoize(this::queryCommandLine);
    private Supplier<Pair<List<String>, Map<String, String>>> cmdEnv = Memoizer.memoize(this::queryCommandlineEnvironment);
    private final Supplier<Long> affinityMask = Memoizer.memoize(PerfstatCpu::queryCpuAffinityMask, Memoizer.defaultExpiration());
    private String name;
    private String path = "";
    private String commandLineBackup;
    private String user;
    private String userID;
    private String group;
    private String groupID;
    private OSProcess.State state = OSProcess.State.INVALID;
    private int parentProcessID;
    private int threadCount;
    private int priority;
    private long virtualSize;
    private long residentSetSize;
    private long kernelTime;
    private long userTime;
    private long startTime;
    private long upTime;
    private long bytesRead;
    private long bytesWritten;
    private long majorFaults;
    private Supplier<Perfstat.perfstat_process_t[]> procCpu;

    public AixOSProcess(int pid, Map<AixOperatingSystem.PsKeywords, String> psMap, Map<Integer, Pair<Long, Long>> cpuMap, Supplier<Perfstat.perfstat_process_t[]> procCpu) {
        super(pid);
        this.procCpu = procCpu;
        this.updateAttributes(psMap, cpuMap);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public String getCommandLine() {
        return this.commandLine.get();
    }

    private String queryCommandLine() {
        String cl = String.join((CharSequence)" ", this.getArguments());
        return cl.isEmpty() ? this.commandLineBackup : cl;
    }

    @Override
    public List<String> getArguments() {
        return this.cmdEnv.get().getA();
    }

    @Override
    public Map<String, String> getEnvironmentVariables() {
        return this.cmdEnv.get().getB();
    }

    private Pair<List<String>, Map<String, String>> queryCommandlineEnvironment() {
        return PsInfo.queryArgsEnv(this.getProcessID());
    }

    @Override
    public String getCurrentWorkingDirectory() {
        return LsofUtil.getCwd(this.getProcessID());
    }

    @Override
    public String getUser() {
        return this.user;
    }

    @Override
    public String getUserID() {
        return this.userID;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public String getGroupID() {
        return this.groupID;
    }

    @Override
    public OSProcess.State getState() {
        return this.state;
    }

    @Override
    public int getParentProcessID() {
        return this.parentProcessID;
    }

    @Override
    public int getThreadCount() {
        return this.threadCount;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public long getVirtualSize() {
        return this.virtualSize;
    }

    @Override
    public long getResidentSetSize() {
        return this.residentSetSize;
    }

    @Override
    public long getKernelTime() {
        return this.kernelTime;
    }

    @Override
    public long getUserTime() {
        return this.userTime;
    }

    @Override
    public long getUpTime() {
        return this.upTime;
    }

    @Override
    public long getStartTime() {
        return this.startTime;
    }

    @Override
    public long getBytesRead() {
        return this.bytesRead;
    }

    @Override
    public long getBytesWritten() {
        return this.bytesWritten;
    }

    @Override
    public long getOpenFiles() {
        return LsofUtil.getOpenFiles(this.getProcessID());
    }

    @Override
    public int getBitness() {
        return this.bitness.get();
    }

    private int queryBitness() {
        List<String> pflags = ExecutingCommand.runNative("pflags " + this.getProcessID());
        for (String line : pflags) {
            if (!line.contains("data model")) continue;
            if (line.contains("LP32")) {
                return 32;
            }
            if (!line.contains("LP64")) continue;
            return 64;
        }
        return 0;
    }

    @Override
    public long getAffinityMask() {
        long mask = 0L;
        List<String> processAffinityInfoList = ExecutingCommand.runNative("ps -m -o THREAD -p " + this.getProcessID());
        if (processAffinityInfoList.size() > 2) {
            processAffinityInfoList.remove(0);
            processAffinityInfoList.remove(0);
            for (String processAffinityInfo : processAffinityInfoList) {
                Map<PsThreadColumns, String> threadMap = ParseUtil.stringToEnumMap(PsThreadColumns.class, processAffinityInfo.trim(), ' ');
                if (!threadMap.containsKey((Object)PsThreadColumns.COMMAND) || threadMap.get((Object)PsThreadColumns.ST).charAt(0) == 'Z') continue;
                String bnd = threadMap.get((Object)PsThreadColumns.BND);
                if (bnd.charAt(0) == '-') {
                    return this.affinityMask.get();
                }
                int affinity = ParseUtil.parseIntOrDefault(bnd, 0);
                mask |= 1L << affinity;
            }
        }
        return mask;
    }

    @Override
    public List<OSThread> getThreadDetails() {
        List<String> threadListInfoPs = ExecutingCommand.runNative("ps -m -o THREAD -p " + this.getProcessID());
        if (threadListInfoPs.size() > 2) {
            ArrayList<OSThread> threads = new ArrayList<OSThread>();
            threadListInfoPs.remove(0);
            threadListInfoPs.remove(0);
            for (String threadInfo : threadListInfoPs) {
                Map<PsThreadColumns, String> threadMap = ParseUtil.stringToEnumMap(PsThreadColumns.class, threadInfo.trim(), ' ');
                if (!threadMap.containsKey((Object)PsThreadColumns.COMMAND)) continue;
                threads.add(new AixOSThread(this.getProcessID(), threadMap));
            }
            return threads;
        }
        return Collections.emptyList();
    }

    @Override
    public long getMajorFaults() {
        return this.majorFaults;
    }

    @Override
    public boolean updateAttributes() {
        Map<AixOperatingSystem.PsKeywords, String> psMap;
        Perfstat.perfstat_process_t[] perfstat = this.procCpu.get();
        List<String> procList = ExecutingCommand.runNative("ps -o " + AixOperatingSystem.PS_COMMAND_ARGS + " -p " + this.getProcessID());
        HashMap<Integer, Pair<Long, Long>> cpuMap = new HashMap<Integer, Pair<Long, Long>>();
        for (Perfstat.perfstat_process_t stat : perfstat) {
            cpuMap.put((int)stat.pid, new Pair<Long, Long>((long)stat.ucpu_time, (long)stat.scpu_time));
        }
        if (procList.size() > 1 && (psMap = ParseUtil.stringToEnumMap(AixOperatingSystem.PsKeywords.class, procList.get(1).trim(), ' ')).containsKey((Object)AixOperatingSystem.PsKeywords.ARGS)) {
            return this.updateAttributes(psMap, cpuMap);
        }
        this.state = OSProcess.State.INVALID;
        return false;
    }

    private boolean updateAttributes(Map<AixOperatingSystem.PsKeywords, String> psMap, Map<Integer, Pair<Long, Long>> cpuMap) {
        long now = System.currentTimeMillis();
        this.state = AixOSProcess.getStateFromOutput(psMap.get((Object)AixOperatingSystem.PsKeywords.ST).charAt(0));
        this.parentProcessID = ParseUtil.parseIntOrDefault(psMap.get((Object)AixOperatingSystem.PsKeywords.PPID), 0);
        this.user = psMap.get((Object)AixOperatingSystem.PsKeywords.USER);
        this.userID = psMap.get((Object)AixOperatingSystem.PsKeywords.UID);
        this.group = psMap.get((Object)AixOperatingSystem.PsKeywords.GROUP);
        this.groupID = psMap.get((Object)AixOperatingSystem.PsKeywords.GID);
        this.threadCount = ParseUtil.parseIntOrDefault(psMap.get((Object)AixOperatingSystem.PsKeywords.THCOUNT), 0);
        this.priority = ParseUtil.parseIntOrDefault(psMap.get((Object)AixOperatingSystem.PsKeywords.PRI), 0);
        this.virtualSize = ParseUtil.parseLongOrDefault(psMap.get((Object)AixOperatingSystem.PsKeywords.VSIZE), 0L) << 10;
        this.residentSetSize = ParseUtil.parseLongOrDefault(psMap.get((Object)AixOperatingSystem.PsKeywords.RSSIZE), 0L) << 10;
        long elapsedTime = ParseUtil.parseDHMSOrDefault(psMap.get((Object)AixOperatingSystem.PsKeywords.ETIME), 0L);
        if (cpuMap.containsKey(this.getProcessID())) {
            Pair<Long, Long> userSystem = cpuMap.get(this.getProcessID());
            this.userTime = userSystem.getA();
            this.kernelTime = userSystem.getB();
        } else {
            this.userTime = ParseUtil.parseDHMSOrDefault(psMap.get((Object)AixOperatingSystem.PsKeywords.TIME), 0L);
            this.kernelTime = 0L;
        }
        long l = this.upTime = elapsedTime < 1L ? 1L : elapsedTime;
        while (this.upTime < this.userTime + this.kernelTime) {
            this.upTime += 500L;
        }
        this.startTime = now - this.upTime;
        this.name = psMap.get((Object)AixOperatingSystem.PsKeywords.COMM);
        this.majorFaults = ParseUtil.parseLongOrDefault(psMap.get((Object)AixOperatingSystem.PsKeywords.PAGEIN), 0L);
        this.commandLineBackup = psMap.get((Object)AixOperatingSystem.PsKeywords.ARGS);
        this.path = ParseUtil.whitespaces.split(this.commandLineBackup)[0];
        return true;
    }

    static OSProcess.State getStateFromOutput(char stateValue) {
        OSProcess.State state;
        switch (stateValue) {
            case 'O': {
                state = OSProcess.State.INVALID;
                break;
            }
            case 'A': 
            case 'R': {
                state = OSProcess.State.RUNNING;
                break;
            }
            case 'I': {
                state = OSProcess.State.WAITING;
                break;
            }
            case 'S': 
            case 'W': {
                state = OSProcess.State.SLEEPING;
                break;
            }
            case 'Z': {
                state = OSProcess.State.ZOMBIE;
                break;
            }
            case 'T': {
                state = OSProcess.State.STOPPED;
                break;
            }
            default: {
                state = OSProcess.State.OTHER;
            }
        }
        return state;
    }

    static enum PsThreadColumns {
        USER,
        PID,
        PPID,
        TID,
        ST,
        CP,
        PRI,
        SC,
        WCHAN,
        F,
        TT,
        BND,
        COMMAND;

    }
}

