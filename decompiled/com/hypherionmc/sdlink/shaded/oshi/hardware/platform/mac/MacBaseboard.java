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
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractBaseboard;
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
final class MacBaseboard
extends AbstractBaseboard {
    private final Supplier<Quartet<String, String, String, String>> manufModelVersSerial = Memoizer.memoize(MacBaseboard::queryPlatform);

    MacBaseboard() {
    }

    @Override
    public String getManufacturer() {
        return this.manufModelVersSerial.get().getA();
    }

    @Override
    public String getModel() {
        return this.manufModelVersSerial.get().getB();
    }

    @Override
    public String getVersion() {
        return this.manufModelVersSerial.get().getC();
    }

    @Override
    public String getSerialNumber() {
        return this.manufModelVersSerial.get().getD();
    }

    private static Quartet<String, String, String, String> queryPlatform() {
        String manufacturer = null;
        String model = null;
        String version = null;
        String serialNumber = null;
        IOKit.IOService platformExpert = IOKitUtil.getMatchingService((String)"IOPlatformExpertDevice");
        if (platformExpert != null) {
            byte[] data = platformExpert.getByteArrayProperty("manufacturer");
            if (data != null) {
                manufacturer = Native.toString((byte[])data, (Charset)StandardCharsets.UTF_8);
            }
            if ((data = platformExpert.getByteArrayProperty("board-id")) != null) {
                model = Native.toString((byte[])data, (Charset)StandardCharsets.UTF_8);
            }
            if (Util.isBlank(model) && (data = platformExpert.getByteArrayProperty("model-number")) != null) {
                model = Native.toString((byte[])data, (Charset)StandardCharsets.UTF_8);
            }
            if ((data = platformExpert.getByteArrayProperty("version")) != null) {
                version = Native.toString((byte[])data, (Charset)StandardCharsets.UTF_8);
            }
            if ((data = platformExpert.getByteArrayProperty("mlb-serial-number")) != null) {
                serialNumber = Native.toString((byte[])data, (Charset)StandardCharsets.UTF_8);
            }
            if (Util.isBlank(serialNumber)) {
                serialNumber = platformExpert.getStringProperty("IOPlatformSerialNumber");
            }
            platformExpert.release();
        }
        return new Quartet<String, String, String, String>(Util.isBlank(manufacturer) ? "Apple Inc." : manufacturer, Util.isBlank(model) ? "unknown" : model, Util.isBlank(version) ? "unknown" : version, Util.isBlank(serialNumber) ? "unknown" : serialNumber);
    }
}

