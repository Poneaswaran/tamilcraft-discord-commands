/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.util.platform.unix.openbsd;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import java.util.List;

@ThreadSafe
public final class FstatUtil {
    private FstatUtil() {
    }

    public static String getCwd(int pid) {
        List<String> ps = ExecutingCommand.runNative("ps -axwwo cwd -p " + pid);
        if (!ps.isEmpty()) {
            return ps.get(1);
        }
        return "";
    }

    public static long getOpenFiles(int pid) {
        long fd = 0L;
        List<String> fstat = ExecutingCommand.runNative("fstat -sp " + pid);
        for (String line : fstat) {
            String[] split = ParseUtil.whitespaces.split(line.trim(), 11);
            if (split.length != 11 || "pipe".contains(split[4]) || "unix".contains(split[4])) continue;
            ++fd;
        }
        return fd - 1L;
    }
}

