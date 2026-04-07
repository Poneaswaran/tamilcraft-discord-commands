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
public final class Win32PhysicalMemory {
    private static final String WIN32_PHYSICAL_MEMORY = "Win32_PhysicalMemory";

    private Win32PhysicalMemory() {
    }

    public static WbemcliUtil.WmiResult<PhysicalMemoryProperty> queryphysicalMemory() {
        WbemcliUtil.WmiQuery physicalMemoryQuery = new WbemcliUtil.WmiQuery(WIN32_PHYSICAL_MEMORY, PhysicalMemoryProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(physicalMemoryQuery);
    }

    public static WbemcliUtil.WmiResult<PhysicalMemoryPropertyWin8> queryphysicalMemoryWin8() {
        WbemcliUtil.WmiQuery physicalMemoryQuery = new WbemcliUtil.WmiQuery(WIN32_PHYSICAL_MEMORY, PhysicalMemoryPropertyWin8.class);
        return WmiQueryHandler.createInstance().queryWMI(physicalMemoryQuery);
    }

    public static enum PhysicalMemoryProperty {
        BANKLABEL,
        CAPACITY,
        SPEED,
        MANUFACTURER,
        SMBIOSMEMORYTYPE;

    }

    public static enum PhysicalMemoryPropertyWin8 {
        BANKLABEL,
        CAPACITY,
        SPEED,
        MANUFACTURER,
        MEMORYTYPE;

    }
}

