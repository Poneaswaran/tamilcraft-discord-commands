/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.freebsd;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;
import com.hypherionmc.sdlink.shaded.oshi.hardware.Baseboard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.Firmware;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractComputerSystem;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.UnixBaseboard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.freebsd.FreeBsdFirmware;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.Util;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.unix.freebsd.BsdSysctlUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Quintet;
import java.util.function.Supplier;

@Immutable
final class FreeBsdComputerSystem
extends AbstractComputerSystem {
    private final Supplier<Quintet<String, String, String, String, String>> manufModelSerialUuidVers = Memoizer.memoize(FreeBsdComputerSystem::readDmiDecode);

    FreeBsdComputerSystem() {
    }

    @Override
    public String getManufacturer() {
        return this.manufModelSerialUuidVers.get().getA();
    }

    @Override
    public String getModel() {
        return this.manufModelSerialUuidVers.get().getB();
    }

    @Override
    public String getSerialNumber() {
        return this.manufModelSerialUuidVers.get().getC();
    }

    @Override
    public String getHardwareUUID() {
        return this.manufModelSerialUuidVers.get().getD();
    }

    @Override
    public Firmware createFirmware() {
        return new FreeBsdFirmware();
    }

    @Override
    public Baseboard createBaseboard() {
        return new UnixBaseboard(this.manufModelSerialUuidVers.get().getA(), this.manufModelSerialUuidVers.get().getB(), this.manufModelSerialUuidVers.get().getC(), this.manufModelSerialUuidVers.get().getE());
    }

    private static Quintet<String, String, String, String, String> readDmiDecode() {
        String manufacturer = null;
        String model = null;
        String serialNumber = null;
        String uuid = null;
        String version = null;
        String manufacturerMarker = "Manufacturer:";
        String productNameMarker = "Product Name:";
        String serialNumMarker = "Serial Number:";
        String uuidMarker = "UUID:";
        String versionMarker = "Version:";
        for (String checkLine : ExecutingCommand.runNative("dmidecode -t system")) {
            if (checkLine.contains("Manufacturer:")) {
                manufacturer = checkLine.split("Manufacturer:")[1].trim();
                continue;
            }
            if (checkLine.contains("Product Name:")) {
                model = checkLine.split("Product Name:")[1].trim();
                continue;
            }
            if (checkLine.contains("Serial Number:")) {
                serialNumber = checkLine.split("Serial Number:")[1].trim();
                continue;
            }
            if (checkLine.contains("UUID:")) {
                uuid = checkLine.split("UUID:")[1].trim();
                continue;
            }
            if (!checkLine.contains("Version:")) continue;
            version = checkLine.split("Version:")[1].trim();
        }
        if (Util.isBlank(serialNumber)) {
            serialNumber = FreeBsdComputerSystem.querySystemSerialNumber();
        }
        if (Util.isBlank(uuid)) {
            uuid = BsdSysctlUtil.sysctl("kern.hostuuid", "unknown");
        }
        return new Quintet<String, String, String, String, String>(Util.isBlank(manufacturer) ? "unknown" : manufacturer, Util.isBlank(model) ? "unknown" : model, Util.isBlank(serialNumber) ? "unknown" : serialNumber, Util.isBlank(uuid) ? "unknown" : uuid, Util.isBlank(version) ? "unknown" : version);
    }

    private static String querySystemSerialNumber() {
        String marker = "system.hardware.serial =";
        for (String checkLine : ExecutingCommand.runNative("lshal")) {
            if (!checkLine.contains(marker)) continue;
            return ParseUtil.getSingleQuoteStringValue(checkLine);
        }
        return "unknown";
    }
}

