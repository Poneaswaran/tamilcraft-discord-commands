/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.openbsd;

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
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.openbsd.OpenBsdCentralProcessor;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.openbsd.OpenBsdComputerSystem;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.openbsd.OpenBsdGlobalMemory;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.openbsd.OpenBsdGraphicsCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.openbsd.OpenBsdHWDiskStore;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.openbsd.OpenBsdPowerSource;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.openbsd.OpenBsdSensors;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.openbsd.OpenBsdSoundCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.openbsd.OpenBsdUsbDevice;
import java.util.List;

@ThreadSafe
public final class OpenBsdHardwareAbstractionLayer
extends AbstractHardwareAbstractionLayer {
    @Override
    public ComputerSystem createComputerSystem() {
        return new OpenBsdComputerSystem();
    }

    @Override
    public GlobalMemory createMemory() {
        return new OpenBsdGlobalMemory();
    }

    @Override
    public CentralProcessor createProcessor() {
        return new OpenBsdCentralProcessor();
    }

    @Override
    public Sensors createSensors() {
        return new OpenBsdSensors();
    }

    @Override
    public List<PowerSource> getPowerSources() {
        return OpenBsdPowerSource.getPowerSources();
    }

    @Override
    public List<HWDiskStore> getDiskStores() {
        return OpenBsdHWDiskStore.getDisks();
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
        return OpenBsdUsbDevice.getUsbDevices(tree);
    }

    @Override
    public List<SoundCard> getSoundCards() {
        return OpenBsdSoundCard.getSoundCards();
    }

    @Override
    public List<GraphicsCard> getGraphicsCards() {
        return OpenBsdGraphicsCard.getGraphicsCards();
    }
}

