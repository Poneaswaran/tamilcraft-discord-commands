/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.PowerSource;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractPowerSource;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@ThreadSafe
public final class AixPowerSource
extends AbstractPowerSource {
    public AixPowerSource(String name, String deviceName, double remainingCapacityPercent, double timeRemainingEstimated, double timeRemainingInstant, double powerUsageRate, double voltage, double amperage, boolean powerOnLine, boolean charging, boolean discharging, PowerSource.CapacityUnits capacityUnits, int currentCapacity, int maxCapacity, int designCapacity, int cycleCount, String chemistry, LocalDate manufactureDate, String manufacturer, String serialNumber, double temperature) {
        super(name, deviceName, remainingCapacityPercent, timeRemainingEstimated, timeRemainingInstant, powerUsageRate, voltage, amperage, powerOnLine, charging, discharging, capacityUnits, currentCapacity, maxCapacity, designCapacity, cycleCount, chemistry, manufactureDate, manufacturer, serialNumber, temperature);
    }

    public static List<PowerSource> getPowerSources() {
        return Collections.emptyList();
    }
}

