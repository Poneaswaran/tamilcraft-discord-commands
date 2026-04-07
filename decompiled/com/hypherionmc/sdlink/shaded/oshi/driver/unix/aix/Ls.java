/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.unix.aix;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Pair;
import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public final class Ls {
    private Ls() {
    }

    public static Map<String, Pair<Integer, Integer>> queryDeviceMajorMinor() {
        HashMap<String, Pair<Integer, Integer>> majMinMap = new HashMap<String, Pair<Integer, Integer>>();
        for (String s : ExecutingCommand.runNative("ls -l /dev")) {
            int idx;
            if (s.isEmpty() || s.charAt(0) != 'b' || (idx = s.lastIndexOf(32)) <= 0 || idx >= s.length()) continue;
            String device = s.substring(idx + 1);
            int major = ParseUtil.getNthIntValue(s, 2);
            int minor = ParseUtil.getNthIntValue(s, 3);
            majMinMap.put(device, new Pair<Integer, Integer>(major, minor));
        }
        return majMinMap;
    }
}

