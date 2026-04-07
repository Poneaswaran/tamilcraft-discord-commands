/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.unix.aix;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Pair;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ThreadSafe
public final class Lssrad {
    private Lssrad() {
    }

    public static Map<Integer, Pair<Integer, Integer>> queryNodesPackages() {
        int node = 0;
        int slot = 0;
        HashMap<Integer, Pair<Integer, Integer>> nodeMap = new HashMap<Integer, Pair<Integer, Integer>>();
        List<String> lssrad = ExecutingCommand.runNative("lssrad -av");
        if (!lssrad.isEmpty()) {
            lssrad.remove(0);
        }
        for (String s : lssrad) {
            String t = s.trim();
            if (t.isEmpty()) continue;
            if (Character.isDigit(s.charAt(0))) {
                node = ParseUtil.parseIntOrDefault(t, 0);
                continue;
            }
            if (t.contains(".")) {
                String[] split = ParseUtil.whitespaces.split(t, 3);
                slot = ParseUtil.parseIntOrDefault(split[0], 0);
                t = split.length > 2 ? split[2] : "";
            }
            for (Integer proc : ParseUtil.parseHyphenatedIntList(t)) {
                nodeMap.put(proc, new Pair<Integer, Integer>(node, slot));
            }
        }
        return nodeMap;
    }
}

