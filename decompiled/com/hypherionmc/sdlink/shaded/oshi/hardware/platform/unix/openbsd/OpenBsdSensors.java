/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.openbsd;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractSensors;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Triplet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@ThreadSafe
final class OpenBsdSensors
extends AbstractSensors {
    private final Supplier<Triplet<Double, int[], Double>> tempFanVolts = Memoizer.memoize(OpenBsdSensors::querySensors, Memoizer.defaultExpiration());

    OpenBsdSensors() {
    }

    @Override
    public double queryCpuTemperature() {
        return this.tempFanVolts.get().getA();
    }

    @Override
    public int[] queryFanSpeeds() {
        return this.tempFanVolts.get().getB();
    }

    @Override
    public double queryCpuVoltage() {
        return this.tempFanVolts.get().getC();
    }

    private static Triplet<Double, int[], Double> querySensors() {
        double volts = 0.0;
        ArrayList<Double> cpuTemps = new ArrayList<Double>();
        ArrayList<Double> allTemps = new ArrayList<Double>();
        ArrayList<Integer> fanRPMs = new ArrayList<Integer>();
        for (String line : ExecutingCommand.runNative("systat -ab sensors")) {
            String[] split = ParseUtil.whitespaces.split(line);
            if (split.length <= 1) continue;
            if (split[0].contains("cpu")) {
                if (split[0].contains("temp0")) {
                    cpuTemps.add(ParseUtil.parseDoubleOrDefault(split[1], Double.NaN));
                    continue;
                }
                if (!split[0].contains("volt0")) continue;
                volts = ParseUtil.parseDoubleOrDefault(split[1], 0.0);
                continue;
            }
            if (split[0].contains("temp0")) {
                allTemps.add(ParseUtil.parseDoubleOrDefault(split[1], Double.NaN));
                continue;
            }
            if (!split[0].contains("fan")) continue;
            fanRPMs.add(ParseUtil.parseIntOrDefault(split[1], 0));
        }
        double temp = cpuTemps.isEmpty() ? OpenBsdSensors.listAverage(allTemps) : OpenBsdSensors.listAverage(cpuTemps);
        int[] fans = new int[fanRPMs.size()];
        for (int i = 0; i < fans.length; ++i) {
            fans[i] = (Integer)fanRPMs.get(i);
        }
        return new Triplet<Double, int[], Double>(temp, fans, volts);
    }

    private static double listAverage(List<Double> doubles) {
        double sum = 0.0;
        int count = 0;
        for (Double d : doubles) {
            if (d.isNaN()) continue;
            sum += d.doubleValue();
            ++count;
        }
        return count > 0 ? sum / (double)count : 0.0;
    }
}

