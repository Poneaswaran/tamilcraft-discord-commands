/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.software.os.unix.solaris;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.solaris.PsInfo;
import com.hypherionmc.sdlink.shaded.oshi.software.common.AbstractOSProcess;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSProcess;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSThread;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.solaris.SolarisOSThread;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.solaris.SolarisOperatingSystem;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.LsofUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@ThreadSafe
public class SolarisOSProcess
extends AbstractOSProcess {
    static final String PS_THREAD_COLUMNS = Arrays.stream(PsThreadColumns.values()).map(Enum::name).map(String::toLowerCase).collect(Collectors.joining(","));
    private Supplier<Integer> bitness = Memoizer.memoize(this::queryBitness);
    private Supplier<String> commandLine = Memoizer.memoize(this::queryCommandLine);
    private Supplier<Pair<List<String>, Map<String, String>>> cmdEnv = Memoizer.memoize(this::queryCommandlineEnvironment);
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
    private long contextSwitches = 0L;

    public SolarisOSProcess(int pid, Map<SolarisOperatingSystem.PsKeywords, String> psMap, Map<SolarisOperatingSystem.PrstatKeywords, String> prstatMap) {
        super(pid);
        this.updateAttributes(psMap, prstatMap);
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
    public long getContextSwitches() {
        return this.contextSwitches;
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
        long bitMask = 0L;
        String cpuset = ExecutingCommand.getFirstAnswer("pbind -q " + this.getProcessID());
        if (cpuset.isEmpty()) {
            List<String> allProcs = ExecutingCommand.runNative("psrinfo");
            for (String proc : allProcs) {
                String[] split = ParseUtil.whitespaces.split(proc);
                int bitToSet = ParseUtil.parseIntOrDefault(split[0], -1);
                if (bitToSet < 0) continue;
                bitMask |= 1L << bitToSet;
            }
            return bitMask;
        }
        if (cpuset.endsWith(".") && cpuset.contains("strongly bound to processor(s)")) {
            int bitToSet;
            String parse = cpuset.substring(0, cpuset.length() - 1);
            String[] split = ParseUtil.whitespaces.split(parse);
            for (int i = split.length - 1; i >= 0 && (bitToSet = ParseUtil.parseIntOrDefault(split[i], -1)) >= 0; --i) {
                bitMask |= 1L << bitToSet;
            }
        }
        return bitMask;
    }

    @Override
    public List<OSThread> getThreadDetails() {
        ArrayList<OSThread> threads = new ArrayList<OSThread>();
        List<String> threadList = ExecutingCommand.runNative("ps -o " + PS_THREAD_COLUMNS + " -p " + this.getProcessID());
        if (threadList.size() > 1) {
            List<String> prstatList = ExecutingCommand.runNative("prstat -L -v -p " + this.getProcessID() + " 1 1");
            HashMap<String, String> prstatRowMap = new HashMap<String, String>();
            for (String s : prstatList) {
                String row = s.trim();
                int idx = row.lastIndexOf(47);
                if (idx <= 0) continue;
                prstatRowMap.put(row.substring(idx + 1), row);
            }
            threadList.remove(0);
            for (String thread : threadList) {
                Map<PsThreadColumns, String> psMap = ParseUtil.stringToEnumMap(PsThreadColumns.class, thread.trim(), ' ');
                if (!psMap.containsKey((Object)PsThreadColumns.PRI)) continue;
                String lwpStr = psMap.get((Object)PsThreadColumns.LWP);
                Map<SolarisOperatingSystem.PrstatKeywords, String> prstatMap = ParseUtil.stringToEnumMap(SolarisOperatingSystem.PrstatKeywords.class, prstatRowMap.getOrDefault(lwpStr, ""), ' ');
                threads.add(new SolarisOSThread(this.getProcessID(), psMap, prstatMap));
            }
        }
        return threads;
    }

    @Override
    public boolean updateAttributes() {
        Map<SolarisOperatingSystem.PsKeywords, String> psMap;
        int pid = this.getProcessID();
        List<String> procList = ExecutingCommand.runNative("ps -o " + SolarisOperatingSystem.PS_COMMAND_ARGS + " -p " + pid);
        if (procList.size() > 1 && (psMap = ParseUtil.stringToEnumMap(SolarisOperatingSystem.PsKeywords.class, procList.get(1).trim(), ' ')).containsKey((Object)SolarisOperatingSystem.PsKeywords.ARGS)) {
            String pidStr = psMap.get((Object)SolarisOperatingSystem.PsKeywords.PID);
            List<String> prstatList = ExecutingCommand.runNative("prstat -v -p " + pidStr + " 1 1");
            String prstatRow = "";
            for (String s : prstatList) {
                String row = s.trim();
                if (!row.startsWith(pidStr + " ")) continue;
                prstatRow = row;
                break;
            }
            Map<SolarisOperatingSystem.PrstatKeywords, String> prstatMap = ParseUtil.stringToEnumMap(SolarisOperatingSystem.PrstatKeywords.class, prstatRow, ' ');
            return this.updateAttributes(psMap, prstatMap);
        }
        this.state = OSProcess.State.INVALID;
        return false;
    }

    private boolean updateAttributes(Map<SolarisOperatingSystem.PsKeywords, String> psMap, Map<SolarisOperatingSystem.PrstatKeywords, String> prstatMap) {
        long now = System.currentTimeMillis();
        this.state = SolarisOSProcess.getStateFromOutput(psMap.get((Object)SolarisOperatingSystem.PsKeywords.S).charAt(0));
        this.parentProcessID = ParseUtil.parseIntOrDefault(psMap.get((Object)SolarisOperatingSystem.PsKeywords.PPID), 0);
        this.user = psMap.get((Object)SolarisOperatingSystem.PsKeywords.USER);
        this.userID = psMap.get((Object)SolarisOperatingSystem.PsKeywords.UID);
        this.group = psMap.get((Object)SolarisOperatingSystem.PsKeywords.GROUP);
        this.groupID = psMap.get((Object)SolarisOperatingSystem.PsKeywords.GID);
        this.threadCount = ParseUtil.parseIntOrDefault(psMap.get((Object)SolarisOperatingSystem.PsKeywords.NLWP), 0);
        this.priority = ParseUtil.parseIntOrDefault(psMap.get((Object)SolarisOperatingSystem.PsKeywords.PRI), 0);
        this.virtualSize = ParseUtil.parseLongOrDefault(psMap.get((Object)SolarisOperatingSystem.PsKeywords.VSZ), 0L) * 1024L;
        this.residentSetSize = ParseUtil.parseLongOrDefault(psMap.get((Object)SolarisOperatingSystem.PsKeywords.RSS), 0L) * 1024L;
        long elapsedTime = ParseUtil.parseDHMSOrDefault(psMap.get((Object)SolarisOperatingSystem.PsKeywords.ETIME), 0L);
        this.upTime = elapsedTime < 1L ? 1L : elapsedTime;
        this.startTime = now - this.upTime;
        this.kernelTime = 0L;
        this.userTime = ParseUtil.parseDHMSOrDefault(psMap.get((Object)SolarisOperatingSystem.PsKeywords.TIME), 0L);
        this.path = psMap.get((Object)SolarisOperatingSystem.PsKeywords.COMM);
        this.name = this.path.substring(this.path.lastIndexOf(47) + 1);
        this.commandLineBackup = psMap.get((Object)SolarisOperatingSystem.PsKeywords.ARGS);
        if (prstatMap.containsKey((Object)SolarisOperatingSystem.PrstatKeywords.ICX)) {
            long nonVoluntaryContextSwitches = ParseUtil.parseLongOrDefault(prstatMap.get((Object)SolarisOperatingSystem.PrstatKeywords.ICX), 0L);
            long voluntaryContextSwitches = ParseUtil.parseLongOrDefault(prstatMap.get((Object)SolarisOperatingSystem.PrstatKeywords.VCX), 0L);
            this.contextSwitches = voluntaryContextSwitches + nonVoluntaryContextSwitches;
        }
        return true;
    }

    static OSProcess.State getStateFromOutput(char stateValue) {
        OSProcess.State state;
        switch (stateValue) {
            case 'O': {
                state = OSProcess.State.RUNNING;
                break;
            }
            case 'S': {
                state = OSProcess.State.SLEEPING;
                break;
            }
            case 'R': 
            case 'W': {
                state = OSProcess.State.WAITING;
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
        LWP,
        S,
        ETIME,
        TIME,
        ADDR,
        PRI;

    }
}

