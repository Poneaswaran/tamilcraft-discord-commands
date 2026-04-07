/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.openbsd;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;
import com.hypherionmc.sdlink.shaded.oshi.hardware.Baseboard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.Firmware;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractComputerSystem;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.UnixBaseboard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.openbsd.OpenBsdFirmware;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.unix.openbsd.OpenBsdSysctlUtil;
import java.util.function.Supplier;

@Immutable
public class OpenBsdComputerSystem
extends AbstractComputerSystem {
    private final Supplier<String> manufacturer = Memoizer.memoize(OpenBsdComputerSystem::queryManufacturer);
    private final Supplier<String> model = Memoizer.memoize(OpenBsdComputerSystem::queryModel);
    private final Supplier<String> serialNumber = Memoizer.memoize(OpenBsdComputerSystem::querySerialNumber);
    private final Supplier<String> uuid = Memoizer.memoize(OpenBsdComputerSystem::queryUUID);

    @Override
    public String getManufacturer() {
        return this.manufacturer.get();
    }

    @Override
    public String getModel() {
        return this.model.get();
    }

    @Override
    public String getSerialNumber() {
        return this.serialNumber.get();
    }

    @Override
    public String getHardwareUUID() {
        return this.uuid.get();
    }

    @Override
    protected Firmware createFirmware() {
        return new OpenBsdFirmware();
    }

    @Override
    protected Baseboard createBaseboard() {
        return new UnixBaseboard(this.manufacturer.get(), this.model.get(), this.serialNumber.get(), OpenBsdSysctlUtil.sysctl("hw.product", "unknown"));
    }

    private static String queryManufacturer() {
        return OpenBsdSysctlUtil.sysctl("hw.vendor", "unknown");
    }

    private static String queryModel() {
        return OpenBsdSysctlUtil.sysctl("hw.version", "unknown");
    }

    private static String querySerialNumber() {
        return OpenBsdSysctlUtil.sysctl("hw.serialno", "unknown");
    }

    private static String queryUUID() {
        return OpenBsdSysctlUtil.sysctl("hw.uuid", "unknown");
    }
}

