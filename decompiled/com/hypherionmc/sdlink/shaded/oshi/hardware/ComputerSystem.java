/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;
import com.hypherionmc.sdlink.shaded.oshi.hardware.Baseboard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.Firmware;

@Immutable
public interface ComputerSystem {
    public String getManufacturer();

    public String getModel();

    public String getSerialNumber();

    public String getHardwareUUID();

    public Firmware getFirmware();

    public Baseboard getBaseboard();
}

