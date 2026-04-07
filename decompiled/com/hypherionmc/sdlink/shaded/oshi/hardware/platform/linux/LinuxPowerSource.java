/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.linux;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.PowerSource;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractPowerSource;
import com.hypherionmc.sdlink.shaded.oshi.util.FileUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ThreadSafe
public final class LinuxPowerSource
extends AbstractPowerSource {
    private static final String PS_PATH = "/sys/class/power_supply/";

    public LinuxPowerSource(String psName, String psDeviceName, double psRemainingCapacityPercent, double psTimeRemainingEstimated, double psTimeRemainingInstant, double psPowerUsageRate, double psVoltage, double psAmperage, boolean psPowerOnLine, boolean psCharging, boolean psDischarging, PowerSource.CapacityUnits psCapacityUnits, int psCurrentCapacity, int psMaxCapacity, int psDesignCapacity, int psCycleCount, String psChemistry, LocalDate psManufactureDate, String psManufacturer, String psSerialNumber, double psTemperature) {
        super(psName, psDeviceName, psRemainingCapacityPercent, psTimeRemainingEstimated, psTimeRemainingInstant, psPowerUsageRate, psVoltage, psAmperage, psPowerOnLine, psCharging, psDischarging, psCapacityUnits, psCurrentCapacity, psMaxCapacity, psDesignCapacity, psCycleCount, psChemistry, psManufactureDate, psManufacturer, psSerialNumber, psTemperature);
    }

    public static List<PowerSource> getPowerSources() {
        double psRemainingCapacityPercent = -1.0;
        double psTimeRemainingEstimated = -1.0;
        double psTimeRemainingInstant = -1.0;
        double psPowerUsageRate = 0.0;
        double psVoltage = -1.0;
        double psAmperage = 0.0;
        boolean psPowerOnLine = false;
        boolean psCharging = false;
        boolean psDischarging = false;
        PowerSource.CapacityUnits psCapacityUnits = PowerSource.CapacityUnits.RELATIVE;
        int psCurrentCapacity = -1;
        int psMaxCapacity = -1;
        int psDesignCapacity = -1;
        int psCycleCount = -1;
        LocalDate psManufactureDate = null;
        double psTemperature = 0.0;
        File f = new File(PS_PATH);
        String[] psNames = f.list();
        ArrayList<PowerSource> psList = new ArrayList<PowerSource>();
        if (psNames != null) {
            for (String name : psNames) {
                List<String> psInfo;
                if (name.startsWith("ADP") || name.startsWith("AC") || (psInfo = FileUtil.readFile(PS_PATH + name + "/uevent", false)).isEmpty()) continue;
                HashMap<String, String> psMap = new HashMap<String, String>();
                for (String line : psInfo) {
                    String[] split = line.split("=");
                    if (split.length <= 1 || split[1].isEmpty()) continue;
                    psMap.put(split[0], split[1]);
                }
                String psName = psMap.getOrDefault("POWER_SUPPLY_NAME", name);
                String status = (String)psMap.get("POWER_SUPPLY_STATUS");
                psCharging = "Charging".equals(status);
                psDischarging = "Discharging".equals(status);
                if (psMap.containsKey("POWER_SUPPLY_CAPACITY")) {
                    psRemainingCapacityPercent = (double)ParseUtil.parseIntOrDefault((String)psMap.get("POWER_SUPPLY_CAPACITY"), -100) / 100.0;
                }
                if (psMap.containsKey("POWER_SUPPLY_ENERGY_NOW")) {
                    psCurrentCapacity = ParseUtil.parseIntOrDefault((String)psMap.get("POWER_SUPPLY_ENERGY_NOW"), -1);
                } else if (psMap.containsKey("POWER_SUPPLY_CHARGE_NOW")) {
                    psCurrentCapacity = ParseUtil.parseIntOrDefault((String)psMap.get("POWER_SUPPLY_CHARGE_NOW"), -1);
                }
                if (psMap.containsKey("POWER_SUPPLY_ENERGY_FULL")) {
                    psCurrentCapacity = ParseUtil.parseIntOrDefault((String)psMap.get("POWER_SUPPLY_ENERGY_FULL"), 1);
                } else if (psMap.containsKey("POWER_SUPPLY_CHARGE_FULL")) {
                    psCurrentCapacity = ParseUtil.parseIntOrDefault((String)psMap.get("POWER_SUPPLY_CHARGE_FULL"), 1);
                }
                if (psMap.containsKey("POWER_SUPPLY_ENERGY_FULL_DESIGN")) {
                    psMaxCapacity = ParseUtil.parseIntOrDefault((String)psMap.get("POWER_SUPPLY_ENERGY_FULL_DESIGN"), 1);
                } else if (psMap.containsKey("POWER_SUPPLY_CHARGE_FULL_DESIGN")) {
                    psMaxCapacity = ParseUtil.parseIntOrDefault((String)psMap.get("POWER_SUPPLY_CHARGE_FULL_DESIGN"), 1);
                }
                if (psMap.containsKey("POWER_SUPPLY_VOLTAGE_NOW")) {
                    psVoltage = ParseUtil.parseIntOrDefault((String)psMap.get("POWER_SUPPLY_VOLTAGE_NOW"), -1);
                }
                if (psMap.containsKey("POWER_SUPPLY_POWER_NOW")) {
                    psPowerUsageRate = ParseUtil.parseIntOrDefault((String)psMap.get("POWER_SUPPLY_POWER_NOW"), -1);
                }
                if (psVoltage > 0.0) {
                    psAmperage = psPowerUsageRate / psVoltage;
                }
                if (psMap.containsKey("POWER_SUPPLY_CYCLE_COUNT")) {
                    psCycleCount = ParseUtil.parseIntOrDefault((String)psMap.get("POWER_SUPPLY_CYCLE_COUNT"), -1);
                }
                String psChemistry = psMap.getOrDefault("POWER_SUPPLY_TECHNOLOGY", "unknown");
                String psDeviceName = psMap.getOrDefault("POWER_SUPPLY_MODEL_NAME", "unknown");
                String psManufacturer = psMap.getOrDefault("POWER_SUPPLY_MANUFACTURER", "unknown");
                String psSerialNumber = psMap.getOrDefault("POWER_SUPPLY_SERIAL_NUMBER", "unknown");
                if (ParseUtil.parseIntOrDefault((String)psMap.get("POWER_SUPPLY_PRESENT"), 1) <= 0) continue;
                psList.add(new LinuxPowerSource(psName, psDeviceName, psRemainingCapacityPercent, psTimeRemainingEstimated, psTimeRemainingInstant, psPowerUsageRate, psVoltage, psAmperage, psPowerOnLine, psCharging, psDischarging, psCapacityUnits, psCurrentCapacity, psMaxCapacity, psDesignCapacity, psCycleCount, psChemistry, psManufactureDate, psManufacturer, psSerialNumber, psTemperature));
            }
        }
        return psList;
    }
}

