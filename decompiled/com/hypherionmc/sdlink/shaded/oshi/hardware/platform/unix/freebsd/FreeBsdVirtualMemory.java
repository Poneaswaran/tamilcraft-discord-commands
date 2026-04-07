/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.freebsd;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractVirtualMemory;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.freebsd.FreeBsdGlobalMemory;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.unix.freebsd.BsdSysctlUtil;
import java.util.function.Supplier;

@ThreadSafe
final class FreeBsdVirtualMemory
extends AbstractVirtualMemory {
    FreeBsdGlobalMemory global;
    private final Supplier<Long> used = Memoizer.memoize(FreeBsdVirtualMemory::querySwapUsed, Memoizer.defaultExpiration());
    private final Supplier<Long> total = Memoizer.memoize(FreeBsdVirtualMemory::querySwapTotal, Memoizer.defaultExpiration());
    private final Supplier<Long> pagesIn = Memoizer.memoize(FreeBsdVirtualMemory::queryPagesIn, Memoizer.defaultExpiration());
    private final Supplier<Long> pagesOut = Memoizer.memoize(FreeBsdVirtualMemory::queryPagesOut, Memoizer.defaultExpiration());

    FreeBsdVirtualMemory(FreeBsdGlobalMemory freeBsdGlobalMemory) {
        this.global = freeBsdGlobalMemory;
    }

    @Override
    public long getSwapUsed() {
        return this.used.get();
    }

    @Override
    public long getSwapTotal() {
        return this.total.get();
    }

    @Override
    public long getVirtualMax() {
        return this.global.getTotal() + this.getSwapTotal();
    }

    @Override
    public long getVirtualInUse() {
        return this.global.getTotal() - this.global.getAvailable() + this.getSwapUsed();
    }

    @Override
    public long getSwapPagesIn() {
        return this.pagesIn.get();
    }

    @Override
    public long getSwapPagesOut() {
        return this.pagesOut.get();
    }

    private static long querySwapUsed() {
        String swapInfo = ExecutingCommand.getAnswerAt("swapinfo -k", 1);
        String[] split = ParseUtil.whitespaces.split(swapInfo);
        if (split.length < 5) {
            return 0L;
        }
        return ParseUtil.parseLongOrDefault(split[2], 0L) << 10;
    }

    private static long querySwapTotal() {
        return BsdSysctlUtil.sysctl("vm.swap_total", 0L);
    }

    private static long queryPagesIn() {
        return BsdSysctlUtil.sysctl("vm.stats.vm.v_swappgsin", 0L);
    }

    private static long queryPagesOut() {
        return BsdSysctlUtil.sysctl("vm.stats.vm.v_swappgsout", 0L);
    }
}

