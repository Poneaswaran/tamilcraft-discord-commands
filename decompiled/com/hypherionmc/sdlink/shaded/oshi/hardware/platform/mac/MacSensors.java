/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.platform.mac.IOKit$IOConnect
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractSensors;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.mac.SmcUtil;
import com.sun.jna.platform.mac.IOKit;

@ThreadSafe
final class MacSensors
extends AbstractSensors {
    private int numFans = 0;

    MacSensors() {
    }

    @Override
    public double queryCpuTemperature() {
        IOKit.IOConnect conn = SmcUtil.smcOpen();
        double temp = SmcUtil.smcGetFloat(conn, "TC0P");
        SmcUtil.smcClose(conn);
        if (temp > 0.0) {
            return temp;
        }
        return 0.0;
    }

    @Override
    public int[] queryFanSpeeds() {
        IOKit.IOConnect conn = SmcUtil.smcOpen();
        if (this.numFans == 0) {
            this.numFans = (int)SmcUtil.smcGetLong(conn, "FNum");
        }
        int[] fanSpeeds = new int[this.numFans];
        for (int i = 0; i < this.numFans; ++i) {
            fanSpeeds[i] = (int)SmcUtil.smcGetFloat(conn, String.format("F%dAc", i));
        }
        SmcUtil.smcClose(conn);
        return fanSpeeds;
    }

    @Override
    public double queryCpuVoltage() {
        IOKit.IOConnect conn = SmcUtil.smcOpen();
        double volts = SmcUtil.smcGetFloat(conn, "VC0C") / 1000.0;
        SmcUtil.smcClose(conn);
        return volts;
    }
}

