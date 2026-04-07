/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac;

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
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac.MacCentralProcessor;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac.MacComputerSystem;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac.MacDisplay;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac.MacGlobalMemory;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac.MacGraphicsCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac.MacHWDiskStore;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac.MacLogicalVolumeGroup;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac.MacNetworkIF;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac.MacPowerSource;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac.MacSensors;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac.MacSoundCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac.MacUsbDevice;
import java.util.List;

@ThreadSafe
public final class MacHardwareAbstractionLayer
extends AbstractHardwareAbstractionLayer {
    @Override
    public ComputerSystem createComputerSystem() {
        return new MacComputerSystem();
    }

    @Override
    public GlobalMemory createMemory() {
        return new MacGlobalMemory();
    }

    @Override
    public CentralProcessor createProcessor() {
        return new MacCentralProcessor();
    }

    @Override
    public Sensors createSensors() {
        return new MacSensors();
    }

    @Override
    public List<PowerSource> getPowerSources() {
        return MacPowerSource.getPowerSources();
    }

    @Override
    public List<HWDiskStore> getDiskStores() {
        return MacHWDiskStore.getDisks();
    }

    @Override
    public List<LogicalVolumeGroup> getLogicalVolumeGroups() {
        return MacLogicalVolumeGroup.getLogicalVolumeGroups();
    }

    @Override
    public List<Display> getDisplays() {
        return MacDisplay.getDisplays();
    }

    @Override
    public List<NetworkIF> getNetworkIFs(boolean includeLocalInterfaces) {
        return MacNetworkIF.getNetworks(includeLocalInterfaces);
    }

    @Override
    public List<UsbDevice> getUsbDevices(boolean tree) {
        return MacUsbDevice.getUsbDevices(tree);
    }

    @Override
    public List<SoundCard> getSoundCards() {
        return MacSoundCard.getSoundCards();
    }

    @Override
    public List<GraphicsCard> getGraphicsCards() {
        return MacGraphicsCard.getGraphicsCards();
    }
}

