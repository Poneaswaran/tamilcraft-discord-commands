/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.linux.proc;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.FileUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.linux.ProcPath;

@ThreadSafe
public final class UpTime {
    private UpTime() {
    }

    public static double getSystemUptimeSeconds() {
        String uptime = FileUtil.getStringFromFile(ProcPath.UPTIME);
        int spaceIndex = uptime.indexOf(32);
        try {
            if (spaceIndex < 0) {
                return 0.0;
            }
            return Double.parseDouble(uptime.substring(0, spaceIndex));
        }
        catch (NumberFormatException nfe) {
            return 0.0;
        }
    }
}

