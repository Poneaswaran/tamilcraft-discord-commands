/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import java.time.LocalDate;

@ThreadSafe
public interface PowerSource {
    public String getName();

    public String getDeviceName();

    public double getRemainingCapacityPercent();

    public double getTimeRemainingEstimated();

    public double getTimeRemainingInstant();

    public double getPowerUsageRate();

    public double getVoltage();

    public double getAmperage();

    public boolean isPowerOnLine();

    public boolean isCharging();

    public boolean isDischarging();

    public CapacityUnits getCapacityUnits();

    public int getCurrentCapacity();

    public int getMaxCapacity();

    public int getDesignCapacity();

    public int getCycleCount();

    public String getChemistry();

    public LocalDate getManufactureDate();

    public String getManufacturer();

    public String getSerialNumber();

    public double getTemperature();

    public boolean updateAttributes();

    public static enum CapacityUnits {
        MWH,
        MAH,
        RELATIVE;

    }
}

