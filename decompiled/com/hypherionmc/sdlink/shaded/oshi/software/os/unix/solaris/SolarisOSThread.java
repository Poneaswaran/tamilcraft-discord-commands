/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.software.os.unix.solaris;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.software.common.AbstractOSThread;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSProcess;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.solaris.SolarisOSProcess;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.solaris.SolarisOperatingSystem;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import java.util.List;
import java.util.Map;

@ThreadSafe
public class SolarisOSThread
extends AbstractOSThread {
    private int threadId;
    private OSProcess.State state = OSProcess.State.INVALID;
    private long startMemoryAddress;
    private long contextSwitches;
    private long kernelTime;
    private long userTime;
    private long startTime;
    private long upTime;
    private int priority;

    public SolarisOSThread(int pid, Map<SolarisOSProcess.PsThreadColumns, String> psMap, Map<SolarisOperatingSystem.PrstatKeywords, String> prstatMap) {
        super(pid);
        this.updateAttributes(psMap, prstatMap);
    }

    @Override
    public int getThreadId() {
        return this.threadId;
    }

    @Override
    public OSProcess.State getState() {
        return this.state;
    }

    @Override
    public long getStartMemoryAddress() {
        return this.startMemoryAddress;
    }

    @Override
    public long getContextSwitches() {
        return this.contextSwitches;
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
    public int getPriority() {
        return this.priority;
    }

    @Override
    public boolean updateAttributes() {
        int pid = this.getOwningProcessId();
        List<String> threadList = ExecutingCommand.runNative("ps -o " + SolarisOSProcess.PS_THREAD_COLUMNS + " -p " + pid);
        if (threadList.size() > 1) {
            String lwpStr = Integer.toString(this.threadId);
            for (String psOutput : threadList) {
                Map<SolarisOSProcess.PsThreadColumns, String> threadMap = ParseUtil.stringToEnumMap(SolarisOSProcess.PsThreadColumns.class, psOutput.trim(), ' ');
                if (!threadMap.containsKey((Object)SolarisOSProcess.PsThreadColumns.PRI) || !lwpStr.equals(threadMap.get((Object)SolarisOSProcess.PsThreadColumns.LWP))) continue;
                List<String> prstatList = ExecutingCommand.runNative("prstat -L -v -p " + pid + " 1 1");
                String prstatRow = "";
                for (String s : prstatList) {
                    String row = s.trim();
                    if (!row.endsWith("/" + lwpStr)) continue;
                    prstatRow = row;
                    break;
                }
                Map<SolarisOperatingSystem.PrstatKeywords, String> prstatMap = ParseUtil.stringToEnumMap(SolarisOperatingSystem.PrstatKeywords.class, prstatRow, ' ');
                return this.updateAttributes(threadMap, prstatMap);
            }
        }
        this.state = OSProcess.State.INVALID;
        return false;
    }

    private boolean updateAttributes(Map<SolarisOSProcess.PsThreadColumns, String> psMap, Map<SolarisOperatingSystem.PrstatKeywords, String> prstatMap) {
        this.threadId = ParseUtil.parseIntOrDefault(psMap.get((Object)SolarisOSProcess.PsThreadColumns.LWP), 0);
        this.state = SolarisOSProcess.getStateFromOutput(psMap.get((Object)SolarisOSProcess.PsThreadColumns.S).charAt(0));
        long elapsedTime = ParseUtil.parseDHMSOrDefault(psMap.get((Object)SolarisOSProcess.PsThreadColumns.ETIME), 0L);
        this.upTime = elapsedTime < 1L ? 1L : elapsedTime;
        long now = System.currentTimeMillis();
        this.startTime = now - this.upTime;
        this.kernelTime = 0L;
        this.userTime = ParseUtil.parseDHMSOrDefault(psMap.get((Object)SolarisOSProcess.PsThreadColumns.TIME), 0L);
        this.startMemoryAddress = ParseUtil.hexStringToLong(psMap.get((Object)SolarisOSProcess.PsThreadColumns.ADDR), 0L);
        this.priority = ParseUtil.parseIntOrDefault(psMap.get((Object)SolarisOSProcess.PsThreadColumns.PRI), 0);
        long nonVoluntaryContextSwitches = ParseUtil.parseLongOrDefault(prstatMap.get((Object)SolarisOperatingSystem.PrstatKeywords.ICX), 0L);
        long voluntaryContextSwitches = ParseUtil.parseLongOrDefault(prstatMap.get((Object)SolarisOperatingSystem.PrstatKeywords.VCX), 0L);
        this.contextSwitches = voluntaryContextSwitches + nonVoluntaryContextSwitches;
        return true;
    }
}

