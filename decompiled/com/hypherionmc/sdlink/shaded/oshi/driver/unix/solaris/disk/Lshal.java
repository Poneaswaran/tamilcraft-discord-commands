/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.unix.solaris.disk;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ThreadSafe
public final class Lshal {
    private static final String LSHAL_CMD = "lshal";

    private Lshal() {
    }

    public static Map<String, Integer> queryDiskToMajorMap() {
        HashMap<String, Integer> majorMap = new HashMap<String, Integer>();
        List<String> lshal = ExecutingCommand.runNative(LSHAL_CMD);
        String diskName = null;
        for (String line : lshal) {
            if (line.startsWith("udi ")) {
                String udi = ParseUtil.getSingleQuoteStringValue(line);
                diskName = udi.substring(udi.lastIndexOf(47) + 1);
                continue;
            }
            if (!(line = line.trim()).startsWith("block.major") || diskName == null) continue;
            majorMap.put(diskName, ParseUtil.getFirstIntValue(line));
        }
        return majorMap;
    }
}

