/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;

@Immutable
public interface Firmware {
    public String getManufacturer();

    public String getName();

    public String getDescription();

    public String getVersion();

    public String getReleaseDate();
}

