/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.unix.freebsd.disk;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ThreadSafe
public final class Mount {
    private static final String MOUNT_CMD = "mount";
    private static final Pattern MOUNT_PATTERN = Pattern.compile("/dev/(\\S+p\\d+) on (\\S+) .*");

    private Mount() {
    }

    public static Map<String, String> queryPartitionToMountMap() {
        HashMap<String, String> mountMap = new HashMap<String, String>();
        for (String mnt : ExecutingCommand.runNative(MOUNT_CMD)) {
            Matcher m = MOUNT_PATTERN.matcher(mnt);
            if (!m.matches()) continue;
            mountMap.put(m.group(1), m.group(2));
        }
        return mountMap;
    }
}

