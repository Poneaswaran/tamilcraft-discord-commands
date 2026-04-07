/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.software.os;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface OSFileStore {
    public String getName();

    public String getVolume();

    public String getLabel();

    public String getLogicalVolume();

    public String getMount();

    public String getDescription();

    public String getType();

    public String getOptions();

    public String getUUID();

    public long getFreeSpace();

    public long getUsableSpace();

    public long getTotalSpace();

    public long getFreeInodes();

    public long getTotalInodes();

    public boolean updateAttributes();
}

