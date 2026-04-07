/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.software.os;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;
import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.Who;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.Xwininfo;
import com.hypherionmc.sdlink.shaded.oshi.software.os.FileSystem;
import com.hypherionmc.sdlink.shaded.oshi.software.os.InternetProtocolStats;
import com.hypherionmc.sdlink.shaded.oshi.software.os.NetworkParams;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSDesktopWindow;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSProcess;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSService;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSSession;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.Util;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ThreadSafe
public interface OperatingSystem {
    public String getFamily();

    public String getManufacturer();

    public OSVersionInfo getVersionInfo();

    public FileSystem getFileSystem();

    public InternetProtocolStats getInternetProtocolStats();

    default public List<OSProcess> getProcesses() {
        return this.getProcesses(null, null, 0);
    }

    public List<OSProcess> getProcesses(Predicate<OSProcess> var1, Comparator<OSProcess> var2, int var3);

    @Deprecated
    default public List<OSProcess> getProcesses(int limit, ProcessSort sort) {
        return this.getProcesses(null, ProcessSorting.convertSortToComparator(sort), limit);
    }

    default public List<OSProcess> getProcesses(Collection<Integer> pids) {
        return pids.stream().map(this::getProcess).filter(Objects::nonNull).filter(ProcessFiltering.VALID_PROCESS).collect(Collectors.toList());
    }

    public OSProcess getProcess(int var1);

    @Deprecated
    default public List<OSProcess> getChildProcesses(int parentPid, int limit, ProcessSort sort) {
        return this.getChildProcesses(parentPid, null, ProcessSorting.convertSortToComparator(sort), limit);
    }

    public List<OSProcess> getChildProcesses(int var1, Predicate<OSProcess> var2, Comparator<OSProcess> var3, int var4);

    public List<OSProcess> getDescendantProcesses(int var1, Predicate<OSProcess> var2, Comparator<OSProcess> var3, int var4);

    public int getProcessId();

    public int getProcessCount();

    public int getThreadCount();

    public int getBitness();

    public long getSystemUptime();

    public long getSystemBootTime();

    default public boolean isElevated() {
        return 0 == ParseUtil.parseIntOrDefault(ExecutingCommand.getFirstAnswer("id -u"), -1);
    }

    public NetworkParams getNetworkParams();

    default public OSService[] getServices() {
        return new OSService[0];
    }

    default public List<OSSession> getSessions() {
        return Who.queryWho();
    }

    default public List<OSDesktopWindow> getDesktopWindows(boolean visibleOnly) {
        return Xwininfo.queryXWindows(visibleOnly);
    }

    public static final class ProcessSorting {
        public static final Comparator<OSProcess> NO_SORTING = (p1, p2) -> 0;
        public static final Comparator<OSProcess> CPU_DESC = Comparator.comparingDouble(OSProcess::getProcessCpuLoadCumulative).reversed();
        public static final Comparator<OSProcess> RSS_DESC = Comparator.comparingLong(OSProcess::getResidentSetSize).reversed();
        public static final Comparator<OSProcess> UPTIME_ASC = Comparator.comparingLong(OSProcess::getUpTime);
        public static final Comparator<OSProcess> UPTIME_DESC = UPTIME_ASC.reversed();
        public static final Comparator<OSProcess> PID_ASC = Comparator.comparingInt(OSProcess::getProcessID);
        public static final Comparator<OSProcess> PARENTPID_ASC = Comparator.comparingInt(OSProcess::getParentProcessID);
        public static final Comparator<OSProcess> NAME_ASC = Comparator.comparing(OSProcess::getName, String.CASE_INSENSITIVE_ORDER);

        private ProcessSorting() {
        }

        private static Comparator<OSProcess> convertSortToComparator(ProcessSort sort) {
            if (sort != null) {
                switch (sort) {
                    case CPU: {
                        return CPU_DESC;
                    }
                    case MEMORY: {
                        return RSS_DESC;
                    }
                    case OLDEST: {
                        return UPTIME_DESC;
                    }
                    case NEWEST: {
                        return UPTIME_ASC;
                    }
                    case PID: {
                        return PID_ASC;
                    }
                    case PARENTPID: {
                        return PARENTPID_ASC;
                    }
                    case NAME: {
                        return NAME_ASC;
                    }
                }
                throw new IllegalArgumentException("Unimplemented enum type: " + sort.toString());
            }
            return NO_SORTING;
        }
    }

    @Deprecated
    public static enum ProcessSort {
        CPU,
        MEMORY,
        OLDEST,
        NEWEST,
        PID,
        PARENTPID,
        NAME;

    }

    public static final class ProcessFiltering {
        public static final Predicate<OSProcess> ALL_PROCESSES = p -> true;
        public static final Predicate<OSProcess> VALID_PROCESS = p -> !p.getState().equals((Object)OSProcess.State.INVALID);
        public static final Predicate<OSProcess> NO_PARENT = p -> p.getParentProcessID() == p.getProcessID();
        public static final Predicate<OSProcess> BITNESS_64 = p -> p.getBitness() == 64;
        public static final Predicate<OSProcess> BITNESS_32 = p -> p.getBitness() == 32;

        private ProcessFiltering() {
        }
    }

    @Immutable
    public static class OSVersionInfo {
        private final String version;
        private final String codeName;
        private final String buildNumber;
        private final String versionStr;

        public OSVersionInfo(String version, String codeName, String buildNumber) {
            this.version = "10".equals(version) && buildNumber.compareTo("22000") >= 0 ? "11" : version;
            this.codeName = codeName;
            this.buildNumber = buildNumber;
            StringBuilder sb = new StringBuilder(this.getVersion() != null ? this.getVersion() : "unknown");
            if (!Util.isBlank(this.getCodeName())) {
                sb.append(" (").append(this.getCodeName()).append(')');
            }
            if (!Util.isBlank(this.getBuildNumber())) {
                sb.append(" build ").append(this.getBuildNumber());
            }
            this.versionStr = sb.toString();
        }

        public String getVersion() {
            return this.version;
        }

        public String getCodeName() {
            return this.codeName;
        }

        public String getBuildNumber() {
            return this.buildNumber;
        }

        public String toString() {
            return this.versionStr;
        }
    }
}

