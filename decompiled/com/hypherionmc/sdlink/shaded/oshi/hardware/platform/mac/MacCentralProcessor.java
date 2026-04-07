/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Native
 *  com.sun.jna.Structure
 *  com.sun.jna.platform.mac.IOKit$IOIterator
 *  com.sun.jna.platform.mac.IOKit$IORegistryEntry
 *  com.sun.jna.platform.mac.IOKit$IOService
 *  com.sun.jna.platform.mac.IOKitUtil
 *  com.sun.jna.platform.mac.SystemB
 *  com.sun.jna.platform.mac.SystemB$HostCpuLoadInfo
 *  com.sun.jna.ptr.IntByReference
 *  com.sun.jna.ptr.PointerByReference
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.platform.mac;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.CentralProcessor;
import com.hypherionmc.sdlink.shaded.oshi.hardware.common.AbstractCentralProcessor;
import com.hypherionmc.sdlink.shaded.oshi.util.FormatUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.Memoizer;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.Util;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.mac.SysctlUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Triplet;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.platform.mac.IOKit;
import com.sun.jna.platform.mac.IOKitUtil;
import com.sun.jna.platform.mac.SystemB;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ThreadSafe
final class MacCentralProcessor
extends AbstractCentralProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(MacCentralProcessor.class);
    private final Supplier<String> vendor = Memoizer.memoize(MacCentralProcessor::platformExpert);
    private final Supplier<Triplet<Integer, Integer, Long>> typeFamilyFreq = Memoizer.memoize(MacCentralProcessor::queryArmCpu);
    private static final int ROSETTA_CPUTYPE = 7;
    private static final int ROSETTA_CPUFAMILY = 1463508716;
    private static final int M1_CPUTYPE = 0x100000C;
    private static final int M1_CPUFAMILY = 458787763;

    MacCentralProcessor() {
    }

    @Override
    protected CentralProcessor.ProcessorIdentifier queryProcessorId() {
        String processorID;
        String cpuFamily;
        String cpuModel;
        String cpuStepping;
        String cpuVendor;
        String cpuName = SysctlUtil.sysctl("machdep.cpu.brand_string", "");
        long cpuFreq = 0L;
        if (cpuName.startsWith("Apple")) {
            cpuVendor = this.vendor.get();
            cpuStepping = "0";
            cpuModel = "0";
            int type = SysctlUtil.sysctl("hw.cputype", 0);
            int family = SysctlUtil.sysctl("hw.cpufamily", 0);
            if (family == 1463508716) {
                type = this.typeFamilyFreq.get().getA();
                family = this.typeFamilyFreq.get().getB();
            }
            cpuFreq = this.typeFamilyFreq.get().getC();
            cpuFamily = String.format("0x%08x", family);
            processorID = String.format("%08x%08x", type, family);
        } else {
            cpuVendor = SysctlUtil.sysctl("machdep.cpu.vendor", "");
            int i = SysctlUtil.sysctl("machdep.cpu.stepping", -1);
            cpuStepping = i < 0 ? "" : Integer.toString(i);
            i = SysctlUtil.sysctl("machdep.cpu.model", -1);
            cpuModel = i < 0 ? "" : Integer.toString(i);
            i = SysctlUtil.sysctl("machdep.cpu.family", -1);
            cpuFamily = i < 0 ? "" : Integer.toString(i);
            long processorIdBits = 0L;
            processorIdBits |= (long)SysctlUtil.sysctl("machdep.cpu.signature", 0);
            processorID = String.format("%016x", processorIdBits |= (SysctlUtil.sysctl("machdep.cpu.feature_bits", 0L) & 0xFFFFFFFFFFFFFFFFL) << 32);
        }
        if (cpuFreq == 0L) {
            cpuFreq = SysctlUtil.sysctl("hw.cpufrequency", 0L);
        }
        boolean cpu64bit = SysctlUtil.sysctl("hw.cpu64bit_capable", 0) != 0;
        return new CentralProcessor.ProcessorIdentifier(cpuVendor, cpuName, cpuFamily, cpuModel, cpuStepping, processorID, cpu64bit, cpuFreq);
    }

    @Override
    protected List<CentralProcessor.LogicalProcessor> initProcessorCounts() {
        int logicalProcessorCount = SysctlUtil.sysctl("hw.logicalcpu", 1);
        int physicalProcessorCount = SysctlUtil.sysctl("hw.physicalcpu", 1);
        int physicalPackageCount = SysctlUtil.sysctl("hw.packages", 1);
        ArrayList<CentralProcessor.LogicalProcessor> logProcs = new ArrayList<CentralProcessor.LogicalProcessor>(logicalProcessorCount);
        for (int i = 0; i < logicalProcessorCount; ++i) {
            logProcs.add(new CentralProcessor.LogicalProcessor(i, i * physicalProcessorCount / logicalProcessorCount, i * physicalPackageCount / logicalProcessorCount));
        }
        return logProcs;
    }

    @Override
    public long[] querySystemCpuLoadTicks() {
        SystemB.HostCpuLoadInfo cpuLoadInfo;
        long[] ticks = new long[CentralProcessor.TickType.values().length];
        int machPort = SystemB.INSTANCE.mach_host_self();
        if (0 != SystemB.INSTANCE.host_statistics(machPort, 3, (Structure)(cpuLoadInfo = new SystemB.HostCpuLoadInfo()), new IntByReference(cpuLoadInfo.size()))) {
            LOG.error("Failed to get System CPU ticks. Error code: {} ", (Object)Native.getLastError());
            return ticks;
        }
        ticks[CentralProcessor.TickType.USER.getIndex()] = cpuLoadInfo.cpu_ticks[0];
        ticks[CentralProcessor.TickType.NICE.getIndex()] = cpuLoadInfo.cpu_ticks[3];
        ticks[CentralProcessor.TickType.SYSTEM.getIndex()] = cpuLoadInfo.cpu_ticks[1];
        ticks[CentralProcessor.TickType.IDLE.getIndex()] = cpuLoadInfo.cpu_ticks[2];
        return ticks;
    }

    @Override
    public long[] queryCurrentFreq() {
        long[] freq = new long[]{SysctlUtil.sysctl("hw.cpufrequency", this.getProcessorIdentifier().getVendorFreq())};
        return freq;
    }

    @Override
    public long queryMaxFreq() {
        return SysctlUtil.sysctl("hw.cpufrequency_max", this.getProcessorIdentifier().getVendorFreq());
    }

    @Override
    public double[] getSystemLoadAverage(int nelem) {
        if (nelem < 1 || nelem > 3) {
            throw new IllegalArgumentException("Must include from one to three elements.");
        }
        double[] average = new double[nelem];
        int retval = SystemB.INSTANCE.getloadavg(average, nelem);
        if (retval < nelem) {
            Arrays.fill(average, -1.0);
        }
        return average;
    }

    @Override
    public long[][] queryProcessorCpuLoadTicks() {
        IntByReference procInfoCount;
        PointerByReference procCpuLoadInfo;
        IntByReference procCount;
        long[][] ticks = new long[this.getLogicalProcessorCount()][CentralProcessor.TickType.values().length];
        int machPort = SystemB.INSTANCE.mach_host_self();
        if (0 != SystemB.INSTANCE.host_processor_info(machPort, 2, procCount = new IntByReference(), procCpuLoadInfo = new PointerByReference(), procInfoCount = new IntByReference())) {
            LOG.error("Failed to update CPU Load. Error code: {}", (Object)Native.getLastError());
            return ticks;
        }
        int[] cpuTicks = procCpuLoadInfo.getValue().getIntArray(0L, procInfoCount.getValue());
        for (int cpu = 0; cpu < procCount.getValue(); ++cpu) {
            int offset = cpu * 4;
            ticks[cpu][CentralProcessor.TickType.USER.getIndex()] = FormatUtil.getUnsignedInt(cpuTicks[offset + 0]);
            ticks[cpu][CentralProcessor.TickType.NICE.getIndex()] = FormatUtil.getUnsignedInt(cpuTicks[offset + 3]);
            ticks[cpu][CentralProcessor.TickType.SYSTEM.getIndex()] = FormatUtil.getUnsignedInt(cpuTicks[offset + 1]);
            ticks[cpu][CentralProcessor.TickType.IDLE.getIndex()] = FormatUtil.getUnsignedInt(cpuTicks[offset + 2]);
        }
        return ticks;
    }

    @Override
    public long queryContextSwitches() {
        return 0L;
    }

    @Override
    public long queryInterrupts() {
        return 0L;
    }

    private static String platformExpert() {
        String manufacturer = null;
        IOKit.IOService platformExpert = IOKitUtil.getMatchingService((String)"IOPlatformExpertDevice");
        if (platformExpert != null) {
            byte[] data = platformExpert.getByteArrayProperty("manufacturer");
            if (data != null) {
                manufacturer = Native.toString((byte[])data, (Charset)StandardCharsets.UTF_8);
            }
            platformExpert.release();
        }
        return Util.isBlank(manufacturer) ? "Apple Inc." : manufacturer;
    }

    private static Triplet<Integer, Integer, Long> queryArmCpu() {
        int type = 7;
        int family = 1463508716;
        long freq = 0L;
        IOKit.IOIterator iter = IOKitUtil.getMatchingServices((String)"IOPlatformDevice");
        if (iter != null) {
            HashSet<String> compatibleStrSet = new HashSet<String>();
            IOKit.IORegistryEntry cpu = iter.next();
            while (cpu != null) {
                if (cpu.getName().startsWith("cpu")) {
                    long cpuFreq;
                    byte[] data = cpu.getByteArrayProperty("clock-frequency");
                    if (data != null && (cpuFreq = ParseUtil.byteArrayToLong(data, data.length, false) * 1000L) > freq) {
                        freq = cpuFreq;
                    }
                    if ((data = cpu.getByteArrayProperty("compatible")) != null) {
                        for (String s : new String(data, StandardCharsets.UTF_8).split("\u0000")) {
                            if (s.isEmpty()) continue;
                            compatibleStrSet.add(s);
                        }
                    }
                }
                cpu.release();
                cpu = iter.next();
            }
            iter.release();
            List<String> m1compatible = Arrays.asList("ARM,v8", "apple,firestorm", "apple,icestorm");
            compatibleStrSet.retainAll(m1compatible);
            if (compatibleStrSet.size() == m1compatible.size()) {
                type = 0x100000C;
                family = 458787763;
            }
        }
        return new Triplet<Integer, Integer, Long>(type, family, freq);
    }
}

