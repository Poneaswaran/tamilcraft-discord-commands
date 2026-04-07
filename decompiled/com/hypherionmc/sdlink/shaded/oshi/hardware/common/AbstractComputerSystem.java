/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.common;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;
import com.hypherionmc.sdlink.shaded.oshi.hardware.Baseboard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.ComputerSystem;
import com.hypherionmc.sdlink.shaded.oshi.hardware.Firmware;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import java.util.function.Supplier;

@Immutable
public abstract class AbstractComputerSystem
implements ComputerSystem {
    private final Supplier<Firmware> firmware = Memoizer.memoize(this::createFirmware);
    private final Supplier<Baseboard> baseboard = Memoizer.memoize(this::createBaseboard);

    @Override
    public Firmware getFirmware() {
        return this.firmware.get();
    }

    protected abstract Firmware createFirmware();

    @Override
    public Baseboard getBaseboard() {
        return this.baseboard.get();
    }

    protected abstract Baseboard createBaseboard();

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("manufacturer=").append(this.getManufacturer()).append(", ");
        sb.append("model=").append(this.getModel()).append(", ");
        sb.append("serial number=").append(this.getSerialNumber()).append(", ");
        sb.append("uuid=").append(this.getHardwareUUID());
        return sb.toString();
    }
}

