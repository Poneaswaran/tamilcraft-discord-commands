/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;

@Immutable
public interface GraphicsCard {
    public String getName();

    public String getDeviceId();

    public String getVendor();

    public String getVersionInfo();

    public long getVRam();
}

