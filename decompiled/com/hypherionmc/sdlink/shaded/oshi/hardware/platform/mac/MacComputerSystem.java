/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Native
 *  com.sun.jna.platform.mac.IOKit$IOService
 *  com.sun.jna.platform.mac.IOKitUtil
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;
import com.hypherionmc.sdlink.shaded.oshi.hardware.Baseboard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.Firmware;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractComputerSystem;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac.MacBaseboard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac.MacFirmware;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import com.hypherionmc.sdlink.shaded.oshi.util.Util;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Quartet;
import com.sun.jna.Native;
import com.sun.jna.platform.mac.IOKit;
import com.sun.jna.platform.mac.IOKitUtil;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

@Immutable
final class MacComputerSystem
extends AbstractComputerSystem {
    private final Supplier<Quartet<String, String, String, String>> manufacturerModelSerialUUID = Memoizer.memoize(MacComputerSystem::platformExpert);

    MacComputerSystem() {
    }

    @Override
    public String getManufacturer() {
        return this.manufacturerModelSerialUUID.get().getA();
    }

    @Override
    public String getModel() {
        return this.manufacturerModelSerialUUID.get().getB();
    }

    @Override
    public String getSerialNumber() {
        return this.manufacturerModelSerialUUID.get().getC();
    }

    @Override
    public String getHardwareUUID() {
        return this.manufacturerModelSerialUUID.get().getD();
    }

    @Override
    public Firmware createFirmware() {
        return new MacFirmware();
    }

    @Override
    public Baseboard createBaseboard() {
        return new MacBaseboard();
    }

    private static Quartet<String, String, String, String> platformExpert() {
        String manufacturer = null;
        String model = null;
        String serialNumber = null;
        String uuid = null;
        IOKit.IOService platformExpert = IOKitUtil.getMatchingService((String)"IOPlatformExpertDevice");
        if (platformExpert != null) {
            byte[] data = platformExpert.getByteArrayProperty("manufacturer");
            if (data != null) {
                manufacturer = Native.toString((byte[])data, (Charset)StandardCharsets.UTF_8);
            }
            if ((data = platformExpert.getByteArrayProperty("model")) != null) {
                model = Native.toString((byte[])data, (Charset)StandardCharsets.UTF_8);
            }
            serialNumber = platformExpert.getStringProperty("IOPlatformSerialNumber");
            uuid = platformExpert.getStringProperty("IOPlatformUUID");
            platformExpert.release();
        }
        return new Quartet<String, String, String, String>(Util.isBlank(manufacturer) ? "Apple Inc." : manufacturer, Util.isBlank(model) ? "unknown" : model, Util.isBlank(serialNumber) ? "unknown" : serialNumber, Util.isBlank(uuid) ? "unknown" : uuid);
    }
}

