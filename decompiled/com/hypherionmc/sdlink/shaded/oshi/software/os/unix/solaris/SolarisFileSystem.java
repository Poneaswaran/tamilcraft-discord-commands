/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.platform.unix.solaris.LibKstat$Kstat
 */
package com.hypherionmc.sdlink.shaded.oshi.software.os.unix.solaris;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.software.common.AbstractFileSystem;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSFileStore;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.solaris.SolarisOSFileStore;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.FileSystemUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.unix.solaris.KstatUtil;
import com.sun.jna.platform.unix.solaris.LibKstat;
import java.io.File;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ThreadSafe
public class SolarisFileSystem
extends AbstractFileSystem {
    public static final String OSHI_SOLARIS_FS_PATH_EXCLUDES = "com.hypherionmc.sdlink.shaded.oshi.os.solaris.filesystem.path.excludes";
    public static final String OSHI_SOLARIS_FS_PATH_INCLUDES = "com.hypherionmc.sdlink.shaded.oshi.os.solaris.filesystem.path.includes";
    public static final String OSHI_SOLARIS_FS_VOLUME_EXCLUDES = "com.hypherionmc.sdlink.shaded.oshi.os.solaris.filesystem.volume.excludes";
    public static final String OSHI_SOLARIS_FS_VOLUME_INCLUDES = "com.hypherionmc.sdlink.shaded.oshi.os.solaris.filesystem.volume.includes";
    private static final List<PathMatcher> FS_PATH_EXCLUDES = FileSystemUtil.loadAndParseFileSystemConfig("com.hypherionmc.sdlink.shaded.oshi.os.solaris.filesystem.path.excludes");
    private static final List<PathMatcher> FS_PATH_INCLUDES = FileSystemUtil.loadAndParseFileSystemConfig("com.hypherionmc.sdlink.shaded.oshi.os.solaris.filesystem.path.includes");
    private static final List<PathMatcher> FS_VOLUME_EXCLUDES = FileSystemUtil.loadAndParseFileSystemConfig("com.hypherionmc.sdlink.shaded.oshi.os.solaris.filesystem.volume.excludes");
    private static final List<PathMatcher> FS_VOLUME_INCLUDES = FileSystemUtil.loadAndParseFileSystemConfig("com.hypherionmc.sdlink.shaded.oshi.os.solaris.filesystem.volume.includes");

    @Override
    public List<OSFileStore> getFileStores(boolean localOnly) {
        return SolarisFileSystem.getFileStoreMatching(null, localOnly);
    }

    static List<OSFileStore> getFileStoreMatching(String nameToMatch) {
        return SolarisFileSystem.getFileStoreMatching(nameToMatch, false);
    }

    private static List<OSFileStore> getFileStoreMatching(String nameToMatch, boolean localOnly) {
        ArrayList<OSFileStore> fsList = new ArrayList<OSFileStore>();
        HashMap<String, Long> inodeFreeMap = new HashMap<String, Long>();
        HashMap<String, Long> inodeTotalMap = new HashMap<String, Long>();
        String key = null;
        String total = null;
        String free = null;
        String command = "df -g" + (localOnly ? " -l" : "");
        for (String line : ExecutingCommand.runNative(command)) {
            if (line.startsWith("/")) {
                key = ParseUtil.whitespaces.split(line)[0];
                total = null;
                continue;
            }
            if (line.contains("available") && line.contains("total files")) {
                total = ParseUtil.getTextBetweenStrings(line, "available", "total files").trim();
                continue;
            }
            if (!line.contains("free files")) continue;
            free = ParseUtil.getTextBetweenStrings(line, "", "free files").trim();
            if (key == null || total == null) continue;
            inodeFreeMap.put(key, ParseUtil.parseLongOrDefault(free, 0L));
            inodeTotalMap.put(key, ParseUtil.parseLongOrDefault(total, 0L));
            key = null;
        }
        for (String fs : ExecutingCommand.runNative("cat /etc/mnttab")) {
            String[] split = ParseUtil.whitespaces.split(fs);
            if (split.length < 5) continue;
            String volume = split[0];
            String path = split[1];
            String type = split[2];
            String options = split[3];
            if (localOnly && NETWORK_FS_TYPES.contains(type) || !path.equals("/") && (PSEUDO_FS_TYPES.contains(type) || FileSystemUtil.isFileStoreExcluded(path, volume, FS_PATH_INCLUDES, FS_PATH_EXCLUDES, FS_VOLUME_INCLUDES, FS_VOLUME_EXCLUDES))) continue;
            String name = path.substring(path.lastIndexOf(47) + 1);
            if (name.isEmpty()) {
                name = volume.substring(volume.lastIndexOf(47) + 1);
            }
            if (nameToMatch != null && !nameToMatch.equals(name)) continue;
            File f = new File(path);
            long totalSpace = f.getTotalSpace();
            long usableSpace = f.getUsableSpace();
            long freeSpace = f.getFreeSpace();
            String description = volume.startsWith("/dev") || path.equals("/") ? "Local Disk" : (volume.equals("tmpfs") ? "Ram Disk" : (NETWORK_FS_TYPES.contains(type) ? "Network Disk" : "Mount Point"));
            fsList.add(new SolarisOSFileStore(name, volume, name, path, options, "", "", description, type, freeSpace, usableSpace, totalSpace, inodeFreeMap.containsKey(path) ? (Long)inodeFreeMap.get(path) : 0L, inodeTotalMap.containsKey(path) ? (Long)inodeTotalMap.get(path) : 0L));
        }
        return fsList;
    }

    @Override
    public long getOpenFileDescriptors() {
        try (KstatUtil.KstatChain kc = KstatUtil.openChain();){
            LibKstat.Kstat ksp = KstatUtil.KstatChain.lookup(null, -1, "file_cache");
            if (ksp != null && KstatUtil.KstatChain.read(ksp)) {
                long l = KstatUtil.dataLookupLong(ksp, "buf_inuse");
                return l;
            }
        }
        return 0L;
    }

    @Override
    public long getMaxFileDescriptors() {
        try (KstatUtil.KstatChain kc = KstatUtil.openChain();){
            LibKstat.Kstat ksp = KstatUtil.KstatChain.lookup(null, -1, "file_cache");
            if (ksp != null && KstatUtil.KstatChain.read(ksp)) {
                long l = KstatUtil.dataLookupLong(ksp, "buf_max");
                return l;
            }
        }
        return 0L;
    }
}

