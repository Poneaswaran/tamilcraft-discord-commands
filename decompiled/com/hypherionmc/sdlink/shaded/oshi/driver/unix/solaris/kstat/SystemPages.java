/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.platform.unix.solaris.LibKstat$Kstat
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.unix.solaris.kstat;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.unix.solaris.KstatUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Pair;
import com.sun.jna.platform.unix.solaris.LibKstat;

@ThreadSafe
public final class SystemPages {
    private SystemPages() {
    }

    public static Pair<Long, Long> queryAvailableTotal() {
        long memAvailable = 0L;
        long memTotal = 0L;
        try (KstatUtil.KstatChain kc = KstatUtil.openChain();){
            LibKstat.Kstat ksp = KstatUtil.KstatChain.lookup(null, -1, "system_pages");
            if (ksp != null && KstatUtil.KstatChain.read(ksp)) {
                memAvailable = KstatUtil.dataLookupLong(ksp, "availrmem");
                memTotal = KstatUtil.dataLookupLong(ksp, "physmem");
            }
        }
        return new Pair<Long, Long>(memAvailable, memTotal);
    }
}

