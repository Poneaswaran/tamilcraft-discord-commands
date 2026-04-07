/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.linux.proc;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@ThreadSafe
public final class UserGroupInfo {
    private static final Supplier<Map<String, String>> usersIdMap = Memoizer.memoize(UserGroupInfo::getUserMap, TimeUnit.MINUTES.toNanos(1L));
    private static final Supplier<Map<String, String>> groupsIdMap = Memoizer.memoize(UserGroupInfo::getGroupMap, TimeUnit.MINUTES.toNanos(1L));

    private UserGroupInfo() {
    }

    public static String getUser(String userId) {
        return usersIdMap.get().getOrDefault(userId, "unknown");
    }

    public static String getGroupName(String groupId) {
        return groupsIdMap.get().getOrDefault(groupId, "unknown");
    }

    private static Map<String, String> getUserMap() {
        HashMap<String, String> userMap = new HashMap<String, String>();
        List<String> passwd = ExecutingCommand.runNative("getent passwd");
        for (String entry : passwd) {
            String[] split = entry.split(":");
            if (split.length <= 2) continue;
            String userName = split[0];
            String uid = split[2];
            userMap.putIfAbsent(uid, userName);
        }
        return userMap;
    }

    private static Map<String, String> getGroupMap() {
        HashMap<String, String> groupMap = new HashMap<String, String>();
        List<String> group = ExecutingCommand.runNative("getent group");
        for (String entry : group) {
            String[] split = entry.split(":");
            if (split.length <= 2) continue;
            String groupName = split[0];
            String gid = split[2];
            groupMap.putIfAbsent(gid, groupName);
        }
        return groupMap;
    }
}

