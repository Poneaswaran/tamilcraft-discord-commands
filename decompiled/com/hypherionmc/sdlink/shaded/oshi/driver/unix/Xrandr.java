/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.unix;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ThreadSafe
public final class Xrandr {
    private static final String[] XRANDR_VERBOSE = new String[]{"xrandr", "--verbose"};

    private Xrandr() {
    }

    public static List<byte[]> getEdidArrays() {
        List<String> xrandr = ExecutingCommand.runNative(XRANDR_VERBOSE, null);
        if (xrandr.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<byte[]> displays = new ArrayList<byte[]>();
        StringBuilder sb = null;
        for (String s : xrandr) {
            if (s.contains("EDID")) {
                sb = new StringBuilder();
                continue;
            }
            if (sb == null) continue;
            sb.append(s.trim());
            if (sb.length() < 256) continue;
            String edidStr = sb.toString();
            byte[] edid = ParseUtil.hexStringToByteArray(edidStr);
            if (edid.length >= 128) {
                displays.add(edid);
            }
            sb = null;
        }
        return displays;
    }
}

