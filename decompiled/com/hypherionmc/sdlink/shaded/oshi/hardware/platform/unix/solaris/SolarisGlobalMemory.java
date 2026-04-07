/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.solaris;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.solaris.kstat.SystemPages;
import com.hypherionmc.sdlink.shaded.oshi.hardware.VirtualMemory;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractGlobalMemory;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.solaris.SolarisVirtualMemory;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Pair;
import java.util.function.Supplier;

@ThreadSafe
final class SolarisGlobalMemory
extends AbstractGlobalMemory {
    private final Supplier<Pair<Long, Long>> availTotal = Memoizer.memoize(SystemPages::queryAvailableTotal, Memoizer.defaultExpiration());
    private final Supplier<Long> pageSize = Memoizer.memoize(SolarisGlobalMemory::queryPageSize);
    private final Supplier<VirtualMemory> vm = Memoizer.memoize(this::createVirtualMemory);

    SolarisGlobalMemory() {
    }

    @Override
    public long getAvailable() {
        return this.availTotal.get().getA() * this.getPageSize();
    }

    @Override
    public long getTotal() {
        return this.availTotal.get().getB() * this.getPageSize();
    }

    @Override
    public long getPageSize() {
        return this.pageSize.get();
    }

    @Override
    public VirtualMemory getVirtualMemory() {
        return this.vm.get();
    }

    private static long queryPageSize() {
        return ParseUtil.parseLongOrDefault(ExecutingCommand.getFirstAnswer("pagesize"), 4096L);
    }

    private VirtualMemory createVirtualMemory() {
        return new SolarisVirtualMemory(this);
    }
}

