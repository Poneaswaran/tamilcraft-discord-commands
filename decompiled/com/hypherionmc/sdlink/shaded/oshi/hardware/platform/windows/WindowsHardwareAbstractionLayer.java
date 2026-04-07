/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.windows;

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
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractHardwareAbstractionLayer;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.windows.WindowsCentralProcessor;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.windows.WindowsComputerSystem;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.windows.WindowsDisplay;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.windows.WindowsGlobalMemory;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.windows.WindowsGraphicsCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.windows.WindowsHWDiskStore;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.windows.WindowsLogicalVolumeGroup;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.windows.WindowsNetworkIF;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.windows.WindowsPowerSource;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.windows.WindowsSensors;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.windows.WindowsSoundCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.windows.WindowsUsbDevice;
import java.util.List;

@ThreadSafe
public final class WindowsHardwareAbstractionLayer
extends AbstractHardwareAbstractionLayer {
    @Override
    public ComputerSystem createComputerSystem() {
        return new WindowsComputerSystem();
    }

    @Override
    public GlobalMemory createMemory() {
        return new WindowsGlobalMemory();
    }

    @Override
    public CentralProcessor createProcessor() {
        return new WindowsCentralProcessor();
    }

    @Override
    public Sensors createSensors() {
        return new WindowsSensors();
    }

    @Override
    public List<PowerSource> getPowerSources() {
        return WindowsPowerSource.getPowerSources();
    }

    @Override
    public List<HWDiskStore> getDiskStores() {
        return WindowsHWDiskStore.getDisks();
    }

    @Override
    public List<LogicalVolumeGroup> getLogicalVolumeGroups() {
        return WindowsLogicalVolumeGroup.getLogicalVolumeGroups();
    }

    @Override
    public List<Display> getDisplays() {
        return WindowsDisplay.getDisplays();
    }

    @Override
    public List<NetworkIF> getNetworkIFs(boolean includeLocalInterfaces) {
        return WindowsNetworkIF.getNetworks(includeLocalInterfaces);
    }

    @Override
    public List<UsbDevice> getUsbDevices(boolean tree) {
        return WindowsUsbDevice.getUsbDevices(tree);
    }

    @Override
    public List<SoundCard> getSoundCards() {
        return WindowsSoundCard.getSoundCards();
    }

    @Override
    public List<GraphicsCard> getGraphicsCards() {
        return WindowsGraphicsCard.getGraphicsCards();
    }
}

