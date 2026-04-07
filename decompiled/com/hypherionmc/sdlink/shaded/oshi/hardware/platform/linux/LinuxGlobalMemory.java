/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.linux;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.VirtualMemory;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractGlobalMemory;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.linux.LinuxVirtualMemory;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.FileUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.linux.ProcPath;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Pair;
import java.util.List;
import java.util.function.Supplier;

@ThreadSafe
public final class LinuxGlobalMemory
extends AbstractGlobalMemory {
    public static final long PAGE_SIZE = ParseUtil.parseLongOrDefault(ExecutingCommand.getFirstAnswer("getconf PAGE_SIZE"), 4096L);
    private final Supplier<Pair<Long, Long>> availTotal = Memoizer.memoize(LinuxGlobalMemory::readMemInfo, Memoizer.defaultExpiration());
    private final Supplier<VirtualMemory> vm = Memoizer.memoize(this::createVirtualMemory);

    @Override
    public long getAvailable() {
        return this.availTotal.get().getA();
    }

    @Override
    public long getTotal() {
        return this.availTotal.get().getB();
    }

    @Override
    public long getPageSize() {
        return PAGE_SIZE;
    }

    @Override
    public VirtualMemory getVirtualMemory() {
        return this.vm.get();
    }

    private static Pair<Long, Long> readMemInfo() {
        long memFree = 0L;
        long activeFile = 0L;
        long inactiveFile = 0L;
        long sReclaimable = 0L;
        long memTotal = 0L;
        List<String> procMemInfo = FileUtil.readFile(ProcPath.MEMINFO);
        for (String checkLine : procMemInfo) {
            String[] memorySplit = ParseUtil.whitespaces.split(checkLine, 2);
            if (memorySplit.length <= 1) continue;
            switch (memorySplit[0]) {
                case "MemTotal:": {
                    memTotal = ParseUtil.parseDecimalMemorySizeToBinary(memorySplit[1]);
                    break;
                }
                case "MemAvailable:": {
                    long memAvailable = ParseUtil.parseDecimalMemorySizeToBinary(memorySplit[1]);
                    return new Pair<Long, Long>(memAvailable, memTotal);
                }
                case "MemFree:": {
                    memFree = ParseUtil.parseDecimalMemorySizeToBinary(memorySplit[1]);
                    break;
                }
                case "Active(file):": {
                    activeFile = ParseUtil.parseDecimalMemorySizeToBinary(memorySplit[1]);
                    break;
                }
                case "Inactive(file):": {
                    inactiveFile = ParseUtil.parseDecimalMemorySizeToBinary(memorySplit[1]);
                    break;
                }
                case "SReclaimable:": {
                    sReclaimable = ParseUtil.parseDecimalMemorySizeToBinary(memorySplit[1]);
                    break;
                }
            }
        }
        return new Pair<Long, Long>(memFree + activeFile + inactiveFile + sReclaimable, memTotal);
    }

    private VirtualMemory createVirtualMemory() {
        return new LinuxVirtualMemory(this);
    }
}

