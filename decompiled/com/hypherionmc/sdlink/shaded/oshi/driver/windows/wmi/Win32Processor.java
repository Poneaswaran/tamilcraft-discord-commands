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
public final class Win32Processor {
    private static final String WIN32_PROCESSOR = "Win32_Processor";

    private Win32Processor() {
    }

    public static WbemcliUtil.WmiResult<VoltProperty> queryVoltage() {
        WbemcliUtil.WmiQuery voltQuery = new WbemcliUtil.WmiQuery(WIN32_PROCESSOR, VoltProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(voltQuery);
    }

    public static WbemcliUtil.WmiResult<ProcessorIdProperty> queryProcessorId() {
        WbemcliUtil.WmiQuery idQuery = new WbemcliUtil.WmiQuery(WIN32_PROCESSOR, ProcessorIdProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(idQuery);
    }

    public static WbemcliUtil.WmiResult<BitnessProperty> queryBitness() {
        WbemcliUtil.WmiQuery bitnessQuery = new WbemcliUtil.WmiQuery(WIN32_PROCESSOR, BitnessProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(bitnessQuery);
    }

    public static enum VoltProperty {
        CURRENTVOLTAGE,
        VOLTAGECAPS;

    }

    public static enum ProcessorIdProperty {
        PROCESSORID;

    }

    public static enum BitnessProperty {
        ADDRESSWIDTH;

    }
}

