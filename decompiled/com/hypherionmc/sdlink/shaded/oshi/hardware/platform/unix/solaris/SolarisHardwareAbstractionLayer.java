/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.solaris;

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
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.UnixDisplay;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.solaris.SolarisCentralProcessor;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.solaris.SolarisComputerSystem;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.solaris.SolarisGlobalMemory;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.solaris.SolarisGraphicsCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.solaris.SolarisHWDiskStore;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.solaris.SolarisNetworkIF;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.solaris.SolarisPowerSource;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.solaris.SolarisSensors;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.solaris.SolarisSoundCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.solaris.SolarisUsbDevice;
import java.util.List;

@ThreadSafe
public final class SolarisHardwareAbstractionLayer
extends AbstractHardwareAbstractionLayer {
    @Override
    public ComputerSystem createComputerSystem() {
        return new SolarisComputerSystem();
    }

    @Override
    public GlobalMemory createMemory() {
        return new SolarisGlobalMemory();
    }

    @Override
    public CentralProcessor createProcessor() {
        return new SolarisCentralProcessor();
    }

    @Override
    public Sensors createSensors() {
        return new SolarisSensors();
    }

    @Override
    public List<PowerSource> getPowerSources() {
        return SolarisPowerSource.getPowerSources();
    }

    @Override
    public List<HWDiskStore> getDiskStores() {
        return SolarisHWDiskStore.getDisks();
    }

    @Override
    public List<Display> getDisplays() {
        return UnixDisplay.getDisplays();
    }

    @Override
    public List<NetworkIF> getNetworkIFs(boolean includeLocalInterfaces) {
        return SolarisNetworkIF.getNetworks(includeLocalInterfaces);
    }

    @Override
    public List<UsbDevice> getUsbDevices(boolean tree) {
        return SolarisUsbDevice.getUsbDevices(tree);
    }

    @Override
    public List<SoundCard> getSoundCards() {
        return SolarisSoundCard.getSoundCards();
    }

    @Override
    public List<GraphicsCard> getGraphicsCards() {
        return SolarisGraphicsCard.getGraphicsCards();
    }
}

