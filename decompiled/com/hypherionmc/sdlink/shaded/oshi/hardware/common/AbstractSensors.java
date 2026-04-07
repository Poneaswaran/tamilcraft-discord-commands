/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.common;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.Sensors;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import java.util.Arrays;
import java.util.function.Supplier;

@ThreadSafe
public abstract class AbstractSensors
implements Sensors {
    private final Supplier<Double> cpuTemperature = Memoizer.memoize(this::queryCpuTemperature, Memoizer.defaultExpiration());
    private final Supplier<int[]> fanSpeeds = Memoizer.memoize(this::queryFanSpeeds, Memoizer.defaultExpiration());
    private final Supplier<Double> cpuVoltage = Memoizer.memoize(this::queryCpuVoltage, Memoizer.defaultExpiration());

    @Override
    public double getCpuTemperature() {
        return this.cpuTemperature.get();
    }

    protected abstract double queryCpuTemperature();

    @Override
    public int[] getFanSpeeds() {
        return this.fanSpeeds.get();
    }

    protected abstract int[] queryFanSpeeds();

    @Override
    public double getCpuVoltage() {
        return this.cpuVoltage.get();
    }

    protected abstract double queryCpuVoltage();

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CPU Temperature=").append(this.getCpuTemperature()).append("\u00b0C, ");
        sb.append("Fan Speeds=").append(Arrays.toString(this.getFanSpeeds())).append(", ");
        sb.append("CPU Voltage=").append(this.getCpuVoltage());
        return sb.toString();
    }
}

