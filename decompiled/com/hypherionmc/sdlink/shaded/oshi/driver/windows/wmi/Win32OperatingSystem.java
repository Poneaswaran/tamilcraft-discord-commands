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
public final class Win32OperatingSystem {
    private static final String WIN32_OPERATING_SYSTEM = "Win32_OperatingSystem";

    private Win32OperatingSystem() {
    }

    public static WbemcliUtil.WmiResult<OSVersionProperty> queryOsVersion() {
        WbemcliUtil.WmiQuery osVersionQuery = new WbemcliUtil.WmiQuery(WIN32_OPERATING_SYSTEM, OSVersionProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(osVersionQuery);
    }

    public static enum OSVersionProperty {
        VERSION,
        PRODUCTTYPE,
        BUILDNUMBER,
        CSDVERSION,
        SUITEMASK;

    }
}

