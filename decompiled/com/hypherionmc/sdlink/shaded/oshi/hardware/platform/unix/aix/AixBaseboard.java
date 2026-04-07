/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.aix.Lscfg;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractBaseboard;
import com.hypherionmc.sdlink.shaded.oshi.util.Util;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Triplet;
import java.util.List;
import java.util.function.Supplier;

@Immutable
final class AixBaseboard
extends AbstractBaseboard {
    private static final String IBM = "IBM";
    private final String model;
    private final String serialNumber;
    private final String version;

    AixBaseboard(Supplier<List<String>> lscfg) {
        Triplet<String, String, String> msv = Lscfg.queryBackplaneModelSerialVersion(lscfg.get());
        this.model = Util.isBlank(msv.getA()) ? "unknown" : msv.getA();
        this.serialNumber = Util.isBlank(msv.getB()) ? "unknown" : msv.getB();
        this.version = Util.isBlank(msv.getC()) ? "unknown" : msv.getC();
    }

    @Override
    public String getManufacturer() {
        return IBM;
    }

    @Override
    public String getModel() {
        return this.model;
    }

    @Override
    public String getSerialNumber() {
        return this.serialNumber;
    }

    @Override
    public String getVersion() {
        return this.version;
    }
}

