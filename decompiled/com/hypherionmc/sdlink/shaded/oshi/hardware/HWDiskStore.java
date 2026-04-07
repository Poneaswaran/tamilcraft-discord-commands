/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.HWPartition;
import java.util.List;

@ThreadSafe
public interface HWDiskStore {
    public String getName();

    public String getModel();

    public String getSerial();

    public long getSize();

    public long getReads();

    public long getReadBytes();

    public long getWrites();

    public long getWriteBytes();

    public long getCurrentQueueLength();

    public long getTransferTime();

    public List<HWPartition> getPartitions();

    public long getTimeStamp();

    public boolean updateAttributes();
}

