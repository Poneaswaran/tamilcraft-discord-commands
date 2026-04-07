/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.common;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.CentralProcessor;
import com.hypherionmc.sdlink.shaded.oshi.hardware.ComputerSystem;
import com.hypherionmc.sdlink.shaded.oshi.hardware.GlobalMemory;
import com.hypherionmc.sdlink.shaded.oshi.hardware.HardwareAbstractionLayer;
import com.hypherionmc.sdlink.shaded.oshi.hardware.NetworkIF;
import com.hypherionmc.sdlink.shaded.oshi.hardware.Sensors;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import java.util.List;
import java.util.function.Supplier;

@ThreadSafe
public abstract class AbstractHardwareAbstractionLayer
implements HardwareAbstractionLayer {
    private final Supplier<ComputerSystem> computerSystem = Memoizer.memoize(this::createComputerSystem);
    private final Supplier<CentralProcessor> processor = Memoizer.memoize(this::createProcessor);
    private final Supplier<GlobalMemory> memory = Memoizer.memoize(this::createMemory);
    private final Supplier<Sensors> sensors = Memoizer.memoize(this::createSensors);

    @Override
    public ComputerSystem getComputerSystem() {
        return this.computerSystem.get();
    }

    protected abstract ComputerSystem createComputerSystem();

    @Override
    public CentralProcessor getProcessor() {
        return this.processor.get();
    }

    protected abstract CentralProcessor createProcessor();

    @Override
    public GlobalMemory getMemory() {
        return this.memory.get();
    }

    protected abstract GlobalMemory createMemory();

    @Override
    public Sensors getSensors() {
        return this.sensors.get();
    }

    protected abstract Sensors createSensors();

    @Override
    public List<NetworkIF> getNetworkIFs() {
        return this.getNetworkIFs(false);
    }
}

