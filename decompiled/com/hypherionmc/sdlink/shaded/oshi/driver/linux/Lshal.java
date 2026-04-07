/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.linux;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;

@ThreadSafe
public final class Lshal {
    private Lshal() {
    }

    public static String querySerialNumber() {
        String marker = "system.hardware.serial =";
        for (String checkLine : ExecutingCommand.runNative("lshal")) {
            if (!checkLine.contains(marker)) continue;
            return ParseUtil.getSingleQuoteStringValue(checkLine);
        }
        return null;
    }

    public static String queryUUID() {
        String marker = "system.hardware.uuid =";
        for (String checkLine : ExecutingCommand.runNative("lshal")) {
            if (!checkLine.contains(marker)) continue;
            return ParseUtil.getSingleQuoteStringValue(checkLine);
        }
        return null;
    }
}

