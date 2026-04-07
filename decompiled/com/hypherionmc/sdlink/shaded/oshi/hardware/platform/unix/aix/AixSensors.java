/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractSensors;
import java.util.List;
import java.util.function.Supplier;

@ThreadSafe
final class AixSensors
extends AbstractSensors {
    private final Supplier<List<String>> lscfg;

    AixSensors(Supplier<List<String>> lscfg) {
        this.lscfg = lscfg;
    }

    @Override
    public double queryCpuTemperature() {
        return 0.0;
    }

    @Override
    public int[] queryFanSpeeds() {
        int fans = 0;
        for (String s : this.lscfg.get()) {
            if (!s.contains("Air Mover")) continue;
            ++fans;
        }
        return new int[fans];
    }

    @Override
    public double queryCpuVoltage() {
        return 0.0;
    }
}

