/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.freebsd;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.CentralProcessor;
import com.hypherionmc.sdlink.shaded.oshi.hardware.ComputerSystem;
import com.hypherionmc.sdlink.shaded.oshi.hardware.Display;
import com.hypherionmc.sdlink.shaded.oshi.hardware.GlobalMemory;
import com.hypherionmc.sdlink.shaded.oshi.hardware.GraphicsCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.HWDiskStore;
import com.hypherionmc.sdlink.shaded.oshi.hardware.NetworkIF;
import com.hypherionmc.sdlink.shaded.oshi.hardware.PowerSource;
import com.hypherionmc.sdlink.shaded.oshi.hardware.Sensors;
import com.hypherionmc.sdlink.shaded.oshi.hardware.SoundCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.UsbDevice;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractHardwareAbstractionLayer;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.BsdNetworkIF;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.UnixDisplay;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.freebsd.FreeBsdCentralProcessor;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.freebsd.FreeBsdComputerSystem;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.freebsd.FreeBsdGlobalMemory;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.freebsd.FreeBsdGraphicsCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.freebsd.FreeBsdHWDiskStore;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.freebsd.FreeBsdPowerSource;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.freebsd.FreeBsdSensors;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.freebsd.FreeBsdSoundCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.freebsd.FreeBsdUsbDevice;
import java.util.List;

@ThreadSafe
public final class FreeBsdHardwareAbstractionLayer
extends AbstractHardwareAbstractionLayer {
    @Override
    public ComputerSystem createComputerSystem() {
        return new FreeBsdComputerSystem();
    }

    @Override
    public GlobalMemory createMemory() {
        return new FreeBsdGlobalMemory();
    }

    @Override
    public CentralProcessor createProcessor() {
        return new FreeBsdCentralProcessor();
    }

    @Override
    public Sensors createSensors() {
        return new FreeBsdSensors();
    }

    @Override
    public List<PowerSource> getPowerSources() {
        return FreeBsdPowerSource.getPowerSources();
    }

    @Override
    public List<HWDiskStore> getDiskStores() {
        return FreeBsdHWDiskStore.getDisks();
    }

    @Override
    public List<Display> getDisplays() {
        return UnixDisplay.getDisplays();
    }

    @Override
    public List<NetworkIF> getNetworkIFs(boolean includeLocalInterfaces) {
        return BsdNetworkIF.getNetworks(includeLocalInterfaces);
    }

    @Override
    public List<UsbDevice> getUsbDevices(boolean tree) {
        return FreeBsdUsbDevice.getUsbDevices(tree);
    }

    @Override
    public List<SoundCard> getSoundCards() {
        return FreeBsdSoundCard.getSoundCards();
    }

    @Override
    public List<GraphicsCard> getGraphicsCards() {
        return FreeBsdGraphicsCard.getGraphicsCards();
    }
}

