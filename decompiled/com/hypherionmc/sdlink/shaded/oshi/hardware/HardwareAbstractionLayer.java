/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.CentralProcessor;
import com.hypherionmc.sdlink.shaded.oshi.hardware.ComputerSystem;
import com.hypherionmc.sdlink.shaded.oshi.hardware.Display;
import com.hypherionmc.sdlink.shaded.oshi.hardware.GlobalMemory;
import com.hypherionmc.sdlink.shaded.oshi.hardware.GraphicsCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.HWDiskStore;
import com.hypherionmc.sdlink.shaded.oshi.hardware.LogicalVolumeGroup;
import com.hypherionmc.sdlink.shaded.oshi.hardware.NetworkIF;
import com.hypherionmc.sdlink.shaded.oshi.hardware.PowerSource;
import com.hypherionmc.sdlink.shaded.oshi.hardware.Sensors;
import com.hypherionmc.sdlink.shaded.oshi.hardware.SoundCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.UsbDevice;
import java.util.Collections;
import java.util.List;

@ThreadSafe
public interface HardwareAbstractionLayer {
    public ComputerSystem getComputerSystem();

    public CentralProcessor getProcessor();

    public GlobalMemory getMemory();

    public List<PowerSource> getPowerSources();

    public List<HWDiskStore> getDiskStores();

    default public List<LogicalVolumeGroup> getLogicalVolumeGroups() {
        return Collections.emptyList();
    }

    public List<NetworkIF> getNetworkIFs();

    public List<NetworkIF> getNetworkIFs(boolean var1);

    public List<Display> getDisplays();

    public Sensors getSensors();

    public List<UsbDevice> getUsbDevices(boolean var1);

    public List<SoundCard> getSoundCards();

    public List<GraphicsCard> getGraphicsCards();
}

