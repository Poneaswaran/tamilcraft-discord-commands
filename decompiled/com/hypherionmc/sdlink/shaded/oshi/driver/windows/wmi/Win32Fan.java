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
public final class Win32Fan {
    private static final String WIN32_FAN = "Win32_Fan";

    private Win32Fan() {
    }

    public static WbemcliUtil.WmiResult<SpeedProperty> querySpeed() {
        WbemcliUtil.WmiQuery fanQuery = new WbemcliUtil.WmiQuery(WIN32_FAN, SpeedProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(fanQuery);
    }

    public static enum SpeedProperty {
        DESIREDSPEED;

    }
}

