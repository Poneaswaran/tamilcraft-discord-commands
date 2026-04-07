/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.platform.unix.aix.Perfstat$perfstat_disk_t
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.aix.Lscfg;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.aix.perfstat.PerfstatDisk;
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
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix.AixCentralProcessor;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix.AixComputerSystem;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix.AixGlobalMemory;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix.AixGraphicsCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix.AixHWDiskStore;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix.AixNetworkIF;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix.AixPowerSource;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix.AixSensors;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix.AixSoundCard;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix.AixUsbDevice;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import com.sun.jna.platform.unix.aix.Perfstat;
import java.util.List;
import java.util.function.Supplier;

@ThreadSafe
public final class AixHardwareAbstractionLayer
extends AbstractHardwareAbstractionLayer {
    private final Supplier<List<String>> lscfg = Memoizer.memoize(Lscfg::queryAllDevices, Memoizer.defaultExpiration());
    private final Supplier<Perfstat.perfstat_disk_t[]> diskStats = Memoizer.memoize(PerfstatDisk::queryDiskStats, Memoizer.defaultExpiration());

    @Override
    public ComputerSystem createComputerSystem() {
        return new AixComputerSystem(this.lscfg);
    }

    @Override
    public GlobalMemory createMemory() {
        return new AixGlobalMemory(this.lscfg);
    }

    @Override
    public CentralProcessor createProcessor() {
        return new AixCentralProcessor();
    }

    @Override
    public Sensors createSensors() {
        return new AixSensors(this.lscfg);
    }

    @Override
    public List<PowerSource> getPowerSources() {
        return AixPowerSource.getPowerSources();
    }

    @Override
    public List<HWDiskStore> getDiskStores() {
        return AixHWDiskStore.getDisks(this.diskStats);
    }

    @Override
    public List<Display> getDisplays() {
        return UnixDisplay.getDisplays();
    }

    @Override
    public List<NetworkIF> getNetworkIFs(boolean includeLocalInterfaces) {
        return AixNetworkIF.getNetworks(includeLocalInterfaces);
    }

    @Override
    public List<UsbDevice> getUsbDevices(boolean tree) {
        return AixUsbDevice.getUsbDevices(tree, this.lscfg);
    }

    @Override
    public List<SoundCard> getSoundCards() {
        return AixSoundCard.getSoundCards(this.lscfg);
    }

    @Override
    public List<GraphicsCard> getGraphicsCards() {
        return AixGraphicsCard.getGraphicsCards(this.lscfg);
    }
}

