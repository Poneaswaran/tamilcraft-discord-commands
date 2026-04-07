/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.util;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ThreadSafe
public final class LsofUtil {
    private LsofUtil() {
    }

    public static Map<Integer, String> getCwdMap(int pid) {
        List<String> lsof = ExecutingCommand.runNative("lsof -F n -d cwd" + (pid < 0 ? "" : " -p " + pid));
        HashMap<Integer, String> cwdMap = new HashMap<Integer, String>();
        Integer key = -1;
        for (String line : lsof) {
            if (line.isEmpty()) continue;
            switch (line.charAt(0)) {
                case 'p': {
                    key = ParseUtil.parseIntOrDefault(line.substring(1), -1);
                    break;
                }
                case 'n': {
                    cwdMap.put(key, line.substring(1));
                    break;
                }
            }
        }
        return cwdMap;
    }

    public static String getCwd(int pid) {
        List<String> lsof = ExecutingCommand.runNative("lsof -F n -d cwd -p " + pid);
        for (String line : lsof) {
            if (line.isEmpty() || line.charAt(0) != 'n') continue;
            return line.substring(1).trim();
        }
        return "";
    }

    public static long getOpenFiles(int pid) {
        int openFiles = ExecutingCommand.runNative("lsof -p " + pid).size();
        return openFiles > 0 ? (long)openFiles - 1L : 0L;
    }
}

