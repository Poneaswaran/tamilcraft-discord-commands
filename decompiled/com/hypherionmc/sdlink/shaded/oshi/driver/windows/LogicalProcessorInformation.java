/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.platform.win32.Kernel32Util
 *  com.sun.jna.platform.win32.WinNT$GROUP_AFFINITY
 *  com.sun.jna.platform.win32.WinNT$NUMA_NODE_RELATIONSHIP
 *  com.sun.jna.platform.win32.WinNT$PROCESSOR_RELATIONSHIP
 *  com.sun.jna.platform.win32.WinNT$SYSTEM_LOGICAL_PROCESSOR_INFORMATION
 *  com.sun.jna.platform.win32.WinNT$SYSTEM_LOGICAL_PROCESSOR_INFORMATION_EX
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.windows;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.CentralProcessor;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.WinNT;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ThreadSafe
public final class LogicalProcessorInformation {
    private LogicalProcessorInformation() {
    }

    public static List<CentralProcessor.LogicalProcessor> getLogicalProcessorInformationEx() {
        WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION_EX[] procInfo = Kernel32Util.getLogicalProcessorInformationEx((int)65535);
        ArrayList<WinNT.GROUP_AFFINITY[]> packages = new ArrayList<WinNT.GROUP_AFFINITY[]>();
        ArrayList<WinNT.GROUP_AFFINITY> cores = new ArrayList<WinNT.GROUP_AFFINITY>();
        ArrayList<WinNT.NUMA_NODE_RELATIONSHIP> numaNodes = new ArrayList<WinNT.NUMA_NODE_RELATIONSHIP>();
        block5: for (int i = 0; i < procInfo.length; ++i) {
            switch (procInfo[i].relationship) {
                case 3: {
                    packages.add(((WinNT.PROCESSOR_RELATIONSHIP)procInfo[i]).groupMask);
                    continue block5;
                }
                case 0: {
                    cores.add(((WinNT.PROCESSOR_RELATIONSHIP)procInfo[i]).groupMask[0]);
                    continue block5;
                }
                case 1: {
                    numaNodes.add((WinNT.NUMA_NODE_RELATIONSHIP)procInfo[i]);
                    continue block5;
                }
            }
        }
        cores.sort(Comparator.comparing(c -> (long)c.group * 64L + c.mask.longValue()));
        packages.sort(Comparator.comparing(p -> (long)p[0].group * 64L + p[0].mask.longValue()));
        numaNodes.sort(Comparator.comparing(n -> n.nodeNumber));
        ArrayList<CentralProcessor.LogicalProcessor> logProcs = new ArrayList<CentralProcessor.LogicalProcessor>();
        for (WinNT.NUMA_NODE_RELATIONSHIP node : numaNodes) {
            int nodeNum = node.nodeNumber;
            short group = node.groupMask.group;
            long mask = node.groupMask.mask.longValue();
            int lowBit = Long.numberOfTrailingZeros(mask);
            int hiBit = 63 - Long.numberOfLeadingZeros(mask);
            for (int lp = lowBit; lp <= hiBit; ++lp) {
                if ((mask & 1L << lp) == 0L) continue;
                CentralProcessor.LogicalProcessor logProc = new CentralProcessor.LogicalProcessor(lp, LogicalProcessorInformation.getMatchingCore(cores, group, lp), LogicalProcessorInformation.getMatchingPackage(packages, group, lp), nodeNum, group);
                logProcs.add(logProc);
            }
        }
        return logProcs;
    }

    private static int getMatchingPackage(List<WinNT.GROUP_AFFINITY[]> packages, int g, int lp) {
        for (int i = 0; i < packages.size(); ++i) {
            for (int j = 0; j < packages.get(i).length; ++j) {
                if ((packages.get((int)i)[j].mask.longValue() & 1L << lp) == 0L || packages.get((int)i)[j].group != g) continue;
                return i;
            }
        }
        return 0;
    }

    private static int getMatchingCore(List<WinNT.GROUP_AFFINITY> cores, int g, int lp) {
        for (int j = 0; j < cores.size(); ++j) {
            if ((cores.get((int)j).mask.longValue() & 1L << lp) == 0L || cores.get((int)j).group != g) continue;
            return j;
        }
        return 0;
    }

    public static List<CentralProcessor.LogicalProcessor> getLogicalProcessorInformation() {
        WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION[] processors;
        ArrayList<Long> packageMaskList = new ArrayList<Long>();
        ArrayList<Long> coreMaskList = new ArrayList<Long>();
        for (WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION proc : processors = Kernel32Util.getLogicalProcessorInformation()) {
            if (proc.relationship == 3) {
                packageMaskList.add(proc.processorMask.longValue());
                continue;
            }
            if (proc.relationship != 0) continue;
            coreMaskList.add(proc.processorMask.longValue());
        }
        coreMaskList.sort(null);
        packageMaskList.sort(null);
        ArrayList<CentralProcessor.LogicalProcessor> logProcs = new ArrayList<CentralProcessor.LogicalProcessor>();
        for (int core = 0; core < coreMaskList.size(); ++core) {
            long coreMask = (Long)coreMaskList.get(core);
            int lowBit = Long.numberOfTrailingZeros(coreMask);
            int hiBit = 63 - Long.numberOfLeadingZeros(coreMask);
            for (int i = lowBit; i <= hiBit; ++i) {
                if ((coreMask & 1L << i) == 0L) continue;
                CentralProcessor.LogicalProcessor logProc = new CentralProcessor.LogicalProcessor(i, core, LogicalProcessorInformation.getBitMatchingPackageNumber(packageMaskList, i));
                logProcs.add(logProc);
            }
        }
        return logProcs;
    }

    private static int getBitMatchingPackageNumber(List<Long> packageMaskList, int logProc) {
        for (int i = 0; i < packageMaskList.size(); ++i) {
            if ((packageMaskList.get(i) & 1L << logProc) == 0L) continue;
            return i;
        }
        return 0;
    }
}

