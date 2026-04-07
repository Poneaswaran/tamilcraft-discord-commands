/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Platform
 */
package com.hypherionmc.sdlink.shaded.oshi;

import com.hypherionmc.sdlink.shaded.oshi.PlatformEnum;
import com.hypherionmc.sdlink.shaded.oshi.hardware.HardwareAbstractionLayer;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.linux.LinuxHardwareAbstractionLayer;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac.MacHardwareAbstractionLayer;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.aix.AixHardwareAbstractionLayer;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.freebsd.FreeBsdHardwareAbstractionLayer;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.openbsd.OpenBsdHardwareAbstractionLayer;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.unix.solaris.SolarisHardwareAbstractionLayer;
import com.hypherionmc.sdlink.shaded.oshi.hardware.platform.windows.WindowsHardwareAbstractionLayer;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OperatingSystem;
import com.hypherionmc.sdlink.shaded.oshi.software.os.linux.LinuxOperatingSystem;
import com.hypherionmc.sdlink.shaded.oshi.software.os.mac.MacOperatingSystem;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.aix.AixOperatingSystem;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.freebsd.FreeBsdOperatingSystem;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.openbsd.OpenBsdOperatingSystem;
import com.hypherionmc.sdlink.shaded.oshi.software.os.unix.solaris.SolarisOperatingSystem;
import com.hypherionmc.sdlink.shaded.oshi.software.os.windows.WindowsOperatingSystem;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import com.sun.jna.Platform;
import java.util.function.Supplier;

public class SystemInfo {
    private static final PlatformEnum currentPlatform = PlatformEnum.getValue(Platform.getOSType());
    private static final String NOT_SUPPORTED = "Operating system not supported: ";
    private final Supplier<OperatingSystem> os = Memoizer.memoize(SystemInfo::createOperatingSystem);
    private final Supplier<HardwareAbstractionLayer> hardware = Memoizer.memoize(SystemInfo::createHardware);

    public static PlatformEnum getCurrentPlatform() {
        return currentPlatform;
    }

    @Deprecated
    public static PlatformEnum getCurrentPlatformEnum() {
        PlatformEnum platform = SystemInfo.getCurrentPlatform();
        return platform.equals((Object)PlatformEnum.MACOS) ? PlatformEnum.MACOSX : platform;
    }

    public OperatingSystem getOperatingSystem() {
        return this.os.get();
    }

    private static OperatingSystem createOperatingSystem() {
        switch (currentPlatform) {
            case WINDOWS: {
                return new WindowsOperatingSystem();
            }
            case LINUX: {
                return new LinuxOperatingSystem();
            }
            case MACOS: {
                return new MacOperatingSystem();
            }
            case SOLARIS: {
                return new SolarisOperatingSystem();
            }
            case FREEBSD: {
                return new FreeBsdOperatingSystem();
            }
            case AIX: {
                return new AixOperatingSystem();
            }
            case OPENBSD: {
                return new OpenBsdOperatingSystem();
            }
        }
        throw new UnsupportedOperationException(NOT_SUPPORTED + currentPlatform.getName());
    }

    public HardwareAbstractionLayer getHardware() {
        return this.hardware.get();
    }

    private static HardwareAbstractionLayer createHardware() {
        switch (currentPlatform) {
            case WINDOWS: {
                return new WindowsHardwareAbstractionLayer();
            }
            case LINUX: {
                return new LinuxHardwareAbstractionLayer();
            }
            case MACOS: {
                return new MacHardwareAbstractionLayer();
            }
            case SOLARIS: {
                return new SolarisHardwareAbstractionLayer();
            }
            case FREEBSD: {
                return new FreeBsdHardwareAbstractionLayer();
            }
            case AIX: {
                return new AixHardwareAbstractionLayer();
            }
            case OPENBSD: {
                return new OpenBsdHardwareAbstractionLayer();
            }
        }
        throw new UnsupportedOperationException(NOT_SUPPORTED + currentPlatform.getName());
    }
}

