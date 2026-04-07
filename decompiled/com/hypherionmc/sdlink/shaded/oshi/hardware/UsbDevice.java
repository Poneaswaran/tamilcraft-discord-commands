/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;
import java.util.List;

@Immutable
public interface UsbDevice
extends Comparable<UsbDevice> {
    public String getName();

    public String getVendor();

    public String getVendorId();

    public String getProductId();

    public String getSerialNumber();

    public String getUniqueDeviceId();

    public List<UsbDevice> getConnectedDevices();
}

