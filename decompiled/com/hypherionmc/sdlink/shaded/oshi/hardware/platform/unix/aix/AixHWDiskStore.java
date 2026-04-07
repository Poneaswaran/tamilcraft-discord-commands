/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Native
 *  com.sun.jna.platform.unix.aix.Perfstat$perfstat_disk_t
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.aix.Ls;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.aix.Lscfg;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.aix.Lspv;
import com.hypherionmc.sdlink.shaded.oshi.hardware.HWDiskStore;
import com.hypherionmc.sdlink.shaded.oshi.hardware.HWPartition;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractHWDiskStore;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Pair;
import com.sun.jna.Native;
import com.sun.jna.platform.unix.aix.Perfstat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@ThreadSafe
public final class AixHWDiskStore
extends AbstractHWDiskStore {
    private final Supplier<Perfstat.perfstat_disk_t[]> diskStats;
    private long reads = 0L;
    private long readBytes = 0L;
    private long writes = 0L;
    private long writeBytes = 0L;
    private long currentQueueLength = 0L;
    private long transferTime = 0L;
    private long timeStamp = 0L;
    private List<HWPartition> partitionList;

    private AixHWDiskStore(String name, String model, String serial, long size, Supplier<Perfstat.perfstat_disk_t[]> diskStats) {
        super(name, model, serial, size);
        this.diskStats = diskStats;
    }

    @Override
    public long getReads() {
        return this.reads;
    }

    @Override
    public long getReadBytes() {
        return this.readBytes;
    }

    @Override
    public long getWrites() {
        return this.writes;
    }

    @Override
    public long getWriteBytes() {
        return this.writeBytes;
    }

    @Override
    public long getCurrentQueueLength() {
        return this.currentQueueLength;
    }

    @Override
    public long getTransferTime() {
        return this.transferTime;
    }

    @Override
    public long getTimeStamp() {
        return this.timeStamp;
    }

    @Override
    public List<HWPartition> getPartitions() {
        return this.partitionList;
    }

    @Override
    public boolean updateAttributes() {
        for (Perfstat.perfstat_disk_t stat : this.diskStats.get()) {
            String name = Native.toString((byte[])stat.name);
            if (!name.equals(this.getName())) continue;
            long blks = stat.rblks + stat.wblks;
            this.reads = stat.xfers;
            if (blks > 0L) {
                this.writes = stat.xfers * stat.wblks / blks;
                this.reads -= this.writes;
            }
            this.readBytes = stat.rblks * stat.bsize;
            this.writeBytes = stat.wblks * stat.bsize;
            this.currentQueueLength = stat.qdepth;
            this.transferTime = stat.time;
            return true;
        }
        return false;
    }

    public static List<HWDiskStore> getDisks(Supplier<Perfstat.perfstat_disk_t[]> diskStats) {
        Map<String, Pair<Integer, Integer>> majMinMap = Ls.queryDeviceMajorMinor();
        ArrayList<AixHWDiskStore> storeList = new ArrayList<AixHWDiskStore>();
        for (Perfstat.perfstat_disk_t disk : diskStats.get()) {
            String storeName = Native.toString((byte[])disk.name);
            Pair<String, String> ms = Lscfg.queryModelSerial(storeName);
            String model = ms.getA() == null ? Native.toString((byte[])disk.description) : ms.getA();
            String serial = ms.getB() == null ? "unknown" : ms.getB();
            storeList.add(AixHWDiskStore.createStore(storeName, model, serial, disk.size << 20, diskStats, majMinMap));
        }
        return storeList.stream().sorted(Comparator.comparingInt(s -> s.getPartitions().isEmpty() ? Integer.MAX_VALUE : s.getPartitions().get(0).getMajor())).collect(Collectors.toList());
    }

    private static AixHWDiskStore createStore(String diskName, String model, String serial, long size, Supplier<Perfstat.perfstat_disk_t[]> diskStats, Map<String, Pair<Integer, Integer>> majMinMap) {
        AixHWDiskStore store = new AixHWDiskStore(diskName, model.isEmpty() ? "unknown" : model, serial, size, diskStats);
        store.partitionList = Collections.unmodifiableList(Lspv.queryLogicalVolumes(diskName, majMinMap).stream().sorted(Comparator.comparing(HWPartition::getMinor).thenComparing(HWPartition::getName)).collect(Collectors.toList()));
        store.updateAttributes();
        return store;
    }
}

