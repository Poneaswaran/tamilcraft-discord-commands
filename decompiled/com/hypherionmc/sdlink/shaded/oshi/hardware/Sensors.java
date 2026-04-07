/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface Sensors {
    public double getCpuTemperature();

    public int[] getFanSpeeds();

    public double getCpuVoltage();
}

