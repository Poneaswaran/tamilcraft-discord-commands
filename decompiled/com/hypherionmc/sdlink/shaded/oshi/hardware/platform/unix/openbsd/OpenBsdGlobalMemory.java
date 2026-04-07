/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Memory
 *  com.sun.jna.Pointer
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.openbsd;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.VirtualMemory;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractGlobalMemory;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.openbsd.OpenBsdVirtualMemory;
import com.hypherionmc.sdlink.shaded.oshi.jna.platform.unix.openbsd.OpenBsdLibc;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.unix.openbsd.OpenBsdSysctlUtil;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import java.util.function.Supplier;

@ThreadSafe
final class OpenBsdGlobalMemory
extends AbstractGlobalMemory {
    private final Supplier<Long> available = Memoizer.memoize(OpenBsdGlobalMemory::queryAvailable, Memoizer.defaultExpiration());
    private final Supplier<Long> total = Memoizer.memoize(OpenBsdGlobalMemory::queryPhysMem);
    private final Supplier<Long> pageSize = Memoizer.memoize(OpenBsdGlobalMemory::queryPageSize);
    private final Supplier<VirtualMemory> vm = Memoizer.memoize(this::createVirtualMemory);

    OpenBsdGlobalMemory() {
    }

    @Override
    public long getAvailable() {
        return this.available.get() * this.getPageSize();
    }

    @Override
    public long getTotal() {
        return this.total.get();
    }

    @Override
    public long getPageSize() {
        return this.pageSize.get();
    }

    @Override
    public VirtualMemory getVirtualMemory() {
        return this.vm.get();
    }

    private static long queryAvailable() {
        long free = 0L;
        long inactive = 0L;
        for (String line : ExecutingCommand.runNative("vmstat -s")) {
            if (line.endsWith("pages free")) {
                free = ParseUtil.getFirstIntValue(line);
                continue;
            }
            if (!line.endsWith("pages inactive")) continue;
            inactive = ParseUtil.getFirstIntValue(line);
        }
        int[] mib = new int[]{10, 0, 3};
        Memory m = OpenBsdSysctlUtil.sysctl(mib);
        OpenBsdLibc.Bcachestats cache = new OpenBsdLibc.Bcachestats((Pointer)m);
        return cache.numbufpages + free + inactive;
    }

    private static long queryPhysMem() {
        return OpenBsdSysctlUtil.sysctl("hw.physmem", 0L);
    }

    private static long queryPageSize() {
        return OpenBsdSysctlUtil.sysctl("hw.pagesize", 4096L);
    }

    private VirtualMemory createVirtualMemory() {
        return new OpenBsdVirtualMemory(this);
    }
}

