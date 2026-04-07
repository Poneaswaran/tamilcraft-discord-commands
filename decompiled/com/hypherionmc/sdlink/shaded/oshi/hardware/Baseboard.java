/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;

@Immutable
public interface Baseboard {
    public String getManufacturer();

    public String getModel();

    public String getVersion();

    public String getSerialNumber();
}

