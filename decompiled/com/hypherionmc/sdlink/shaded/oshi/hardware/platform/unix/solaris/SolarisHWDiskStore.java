/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.platform.unix.solaris.LibKstat$Kstat
 *  com.sun.jna.platform.unix.solaris.LibKstat$KstatIO
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.solaris;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.solaris.disk.Iostat;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.solaris.disk.Lshal;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.solaris.disk.Prtvtoc;
import com.hypherionmc.sdlink.shaded.oshi.hardware.HWDiskStore;
import com.hypherionmc.sdlink.shaded.oshi.hardware.HWPartition;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractHWDiskStore;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.unix.solaris.KstatUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Quintet;
import com.sun.jna.platform.unix.solaris.LibKstat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ThreadSafe
public final class SolarisHWDiskStore
extends AbstractHWDiskStore {
    private long reads = 0L;
    private long readBytes = 0L;
    private long writes = 0L;
    private long writeBytes = 0L;
    private long currentQueueLength = 0L;
    private long transferTime = 0L;
    private long timeStamp = 0L;
    private List<HWPartition> partitionList;

    private SolarisHWDiskStore(String name, String model, String serial, long size) {
        super(name, model, serial, size);
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
        try (KstatUtil.KstatChain kc = KstatUtil.openChain();){
            LibKstat.Kstat ksp = KstatUtil.KstatChain.lookup(null, 0, this.getName());
            if (ksp != null && KstatUtil.KstatChain.read(ksp)) {
                LibKstat.KstatIO data = new LibKstat.KstatIO(ksp.ks_data);
                this.reads = data.reads;
                this.writes = data.writes;
                this.readBytes = data.nread;
                this.writeBytes = data.nwritten;
                this.currentQueueLength = (long)data.wcnt + (long)data.rcnt;
                this.transferTime = data.rtime / 1000000L;
                this.timeStamp = ksp.ks_snaptime / 1000000L;
                boolean bl = true;
                return bl;
            }
        }
        return false;
    }

    public static List<HWDiskStore> getDisks() {
        Map<String, String> deviceMap = Iostat.queryPartitionToMountMap();
        Map<String, Integer> majorMap = Lshal.queryDiskToMajorMap();
        Map<String, Quintet<String, String, String, String, Long>> deviceStringMap = Iostat.queryDeviceStrings(deviceMap.keySet());
        ArrayList<HWDiskStore> storeList = new ArrayList<HWDiskStore>();
        for (Map.Entry<String, Quintet<String, String, String, String, Long>> entry : deviceStringMap.entrySet()) {
            String storeName = entry.getKey();
            Quintet<String, String, String, String, Long> val = entry.getValue();
            storeList.add(SolarisHWDiskStore.createStore(storeName, val.getA(), val.getB(), val.getC(), val.getD(), val.getE(), deviceMap.getOrDefault(storeName, ""), majorMap.getOrDefault(storeName, 0)));
        }
        return storeList;
    }

    private static SolarisHWDiskStore createStore(String diskName, String model, String vendor, String product, String serial, long size, String mount, int major) {
        SolarisHWDiskStore store = new SolarisHWDiskStore(diskName, model.isEmpty() ? (vendor + " " + product).trim() : model, serial, size);
        store.partitionList = Collections.unmodifiableList(Prtvtoc.queryPartitions(mount, major).stream().sorted(Comparator.comparing(HWPartition::getName)).collect(Collectors.toList()));
        store.updateAttributes();
        return store;
    }
}

