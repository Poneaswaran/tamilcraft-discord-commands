/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.platform.win32.COM.WbemcliUtil$WmiQuery
 *  com.sun.jna.platform.win32.COM.WbemcliUtil$WmiResult
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.windows.wmi;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.windows.WmiQueryHandler;
import com.sun.jna.platform.win32.COM.WbemcliUtil;

@ThreadSafe
public final class Win32DiskDriveToDiskPartition {
    private static final String WIN32_DISK_DRIVE_TO_DISK_PARTITION = "Win32_DiskDriveToDiskPartition";

    private Win32DiskDriveToDiskPartition() {
    }

    public static WbemcliUtil.WmiResult<DriveToPartitionProperty> queryDriveToPartition(WmiQueryHandler h) {
        WbemcliUtil.WmiQuery driveToPartitionQuery = new WbemcliUtil.WmiQuery(WIN32_DISK_DRIVE_TO_DISK_PARTITION, DriveToPartitionProperty.class);
        return h.queryWMI(driveToPartitionQuery, false);
    }

    public static enum DriveToPartitionProperty {
        ANTECEDENT,
        DEPENDENT;

    }
}

