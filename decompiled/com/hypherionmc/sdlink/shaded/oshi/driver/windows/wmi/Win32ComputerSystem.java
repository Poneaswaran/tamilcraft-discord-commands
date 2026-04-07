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
public final class Win32ComputerSystem {
    private static final String WIN32_COMPUTER_SYSTEM = "Win32_ComputerSystem";

    private Win32ComputerSystem() {
    }

    public static WbemcliUtil.WmiResult<ComputerSystemProperty> queryComputerSystem() {
        WbemcliUtil.WmiQuery computerSystemQuery = new WbemcliUtil.WmiQuery(WIN32_COMPUTER_SYSTEM, ComputerSystemProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(computerSystemQuery);
    }

    public static enum ComputerSystemProperty {
        MANUFACTURER,
        MODEL;

    }
}

