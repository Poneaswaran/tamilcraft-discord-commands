/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.linux;

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
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.linux.LinuxCentralProcessor;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.linux.LinuxComputerSystem;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.linux.LinuxGlobalMemory;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.linux.LinuxGraphicsCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.linux.LinuxHWDiskStore;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.linux.LinuxLogicalVolumeGroup;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.linux.LinuxNetworkIF;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.linux.LinuxPowerSource;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.linux.LinuxSensors;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.linux.LinuxSoundCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.linux.LinuxUsbDevice;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.UnixDisplay;
import java.util.List;

@ThreadSafe
public final class LinuxHardwareAbstractionLayer
extends AbstractHardwareAbstractionLayer {
    @Override
    public ComputerSystem createComputerSystem() {
        return new LinuxComputerSystem();
    }

    @Override
    public GlobalMemory createMemory() {
        return new LinuxGlobalMemory();
    }

    @Override
    public CentralProcessor createProcessor() {
        return new LinuxCentralProcessor();
    }

    @Override
    public Sensors createSensors() {
        return new LinuxSensors();
    }

    @Override
    public List<PowerSource> getPowerSources() {
        return LinuxPowerSource.getPowerSources();
    }

    @Override
    public List<HWDiskStore> getDiskStores() {
        return LinuxHWDiskStore.getDisks();
    }

    @Override
    public List<LogicalVolumeGroup> getLogicalVolumeGroups() {
        return LinuxLogicalVolumeGroup.getLogicalVolumeGroups();
    }

    @Override
    public List<Display> getDisplays() {
        return UnixDisplay.getDisplays();
    }

    @Override
    public List<NetworkIF> getNetworkIFs(boolean includeLocalInterfaces) {
        return LinuxNetworkIF.getNetworks(includeLocalInterfaces);
    }

    @Override
    public List<UsbDevice> getUsbDevices(boolean tree) {
        return LinuxUsbDevice.getUsbDevices(tree);
    }

    @Override
    public List<SoundCard> getSoundCards() {
        return LinuxSoundCard.getSoundCards();
    }

    @Override
    public List<GraphicsCard> getGraphicsCards() {
        return LinuxGraphicsCard.getGraphicsCards();
    }
}

