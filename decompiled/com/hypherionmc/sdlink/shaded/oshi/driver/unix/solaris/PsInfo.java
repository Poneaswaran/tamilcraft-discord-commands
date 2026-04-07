/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Memory
 *  com.sun.jna.Native
 *  com.sun.jna.NativeLong
 *  com.sun.jna.Pointer
 *  com.sun.jna.platform.unix.LibCAPI$size_t
 *  com.sun.jna.platform.unix.LibCAPI$ssize_t
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.unix.solaris;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.jna.platform.unix.solaris.SolarisLibc;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Pair;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Quartet;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.platform.unix.LibCAPI;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ThreadSafe
public final class PsInfo {
    private static final Logger LOG = LoggerFactory.getLogger(PsInfo.class);
    private static final boolean IS_LITTLE_ENDIAN = "little".equals(System.getProperty("sun.cpu.endian"));
    private static final SolarisLibc LIBC = SolarisLibc.INSTANCE;
    private static final long PAGE_SIZE = ParseUtil.parseLongOrDefault(ExecutingCommand.getFirstAnswer("pagesize"), 4096L);
    private static Map<LwpsInfoT, Integer> lwpsInfoOffsets = PsInfo.initLwpsOffsets();
    private static Map<PsInfoT, Integer> psInfoOffsets = PsInfo.initPsOffsets();

    private PsInfo() {
    }

    private static Map<LwpsInfoT, Integer> initLwpsOffsets() {
        EnumMap<LwpsInfoT, Integer> infoMap = new EnumMap<LwpsInfoT, Integer>(LwpsInfoT.class);
        int offset = 0;
        for (LwpsInfoT field : LwpsInfoT.values()) {
            infoMap.put(field, offset);
            offset += field.size;
        }
        return infoMap;
    }

    private static Map<PsInfoT, Integer> initPsOffsets() {
        EnumMap<PsInfoT, Integer> infoMap = new EnumMap<PsInfoT, Integer>(PsInfoT.class);
        int offset = 0;
        for (PsInfoT field : PsInfoT.values()) {
            infoMap.put(field, offset);
            offset += field.size;
        }
        return infoMap;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Quartet<Integer, Long, Long, Byte> queryArgsEnvAddrs(int pid) {
        File procpsinfo = new File("/proc/" + pid + "/psinfo");
        try (RandomAccessFile psinfo = new RandomAccessFile(procpsinfo, "r");
             FileChannel chan = psinfo.getChannel();
             ByteArrayOutputStream out = new ByteArrayOutputStream();){
            int argc;
            ByteBuffer buf;
            int bufferSize = psInfoOffsets.get((Object)PsInfoT.SIZE);
            if ((long)bufferSize > chan.size()) {
                bufferSize = (int)chan.size();
            }
            if (chan.read(buf = ByteBuffer.allocate(bufferSize)) <= psInfoOffsets.get((Object)PsInfoT.PR_DMODEL)) return null;
            if (IS_LITTLE_ENDIAN) {
                buf.order(ByteOrder.LITTLE_ENDIAN);
            }
            if ((argc = buf.getInt(psInfoOffsets.get((Object)PsInfoT.PR_ARGC))) > 0) {
                long argv = Native.POINTER_SIZE == 8 ? buf.getLong(psInfoOffsets.get((Object)PsInfoT.PR_ARGV)) : (long)buf.getInt(psInfoOffsets.get((Object)PsInfoT.PR_ARGV));
                long envp = Native.POINTER_SIZE == 8 ? buf.getLong(psInfoOffsets.get((Object)PsInfoT.PR_ENVP)) : (long)buf.getInt(psInfoOffsets.get((Object)PsInfoT.PR_ENVP));
                byte dmodel = buf.get(psInfoOffsets.get((Object)PsInfoT.PR_DMODEL));
                if ((long)(dmodel * 4) != (envp - argv) / (long)(argc + 1)) {
                    LOG.trace("Failed data model and offset increment sanity check: dm={} diff={}", (Object)dmodel, (Object)(envp - argv));
                    Quartet<Integer, Long, Long, Byte> quartet = null;
                    return quartet;
                }
                Quartet<Integer, Long, Long, Byte> quartet = new Quartet<Integer, Long, Long, Byte>(argc, argv, envp, dmodel);
                return quartet;
            }
            LOG.trace("No permission to read file: {} ", (Object)procpsinfo);
            return null;
        }
        catch (IOException e) {
            LOG.debug("Failed to read file: {} ", (Object)procpsinfo);
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Pair<List<String>, Map<String, String>> queryArgsEnv(int pid) {
        ArrayList<String> args2 = new ArrayList<String>();
        LinkedHashMap<String, String> env = new LinkedHashMap<String, String>();
        Quartet<Integer, Long, Long, Byte> addrs = PsInfo.queryArgsEnvAddrs(pid);
        if (addrs != null) {
            String procas = "/proc/" + pid + "/as";
            int fd = LIBC.open(procas, 0);
            if (fd < 0) {
                LOG.trace("No permission to read file: {} ", (Object)procas);
                return new Pair<List<String>, Map<String, String>>(args2, env);
            }
            try {
                int argc = addrs.getA();
                long argv = addrs.getB();
                long envp = addrs.getC();
                long increment = (long)addrs.getD().byteValue() * 4L;
                long bufStart = 0L;
                Memory buffer = new Memory(PAGE_SIZE * 2L);
                LibCAPI.size_t bufSize = new LibCAPI.size_t(buffer.size());
                long[] argp = new long[argc];
                long offset = argv;
                for (int i = 0; i < argc; ++i) {
                    argp[i] = (bufStart = PsInfo.conditionallyReadBufferFromStartOfPage(fd, buffer, bufSize, bufStart, offset)) == 0L ? 0L : PsInfo.getOffsetFromBuffer(buffer, offset - bufStart, increment);
                    offset += increment;
                }
                ArrayList<Long> envPtrList = new ArrayList<Long>();
                offset = envp;
                long addr = 0L;
                int limit = 500;
                do {
                    long l = addr = (bufStart = PsInfo.conditionallyReadBufferFromStartOfPage(fd, buffer, bufSize, bufStart, offset)) == 0L ? 0L : PsInfo.getOffsetFromBuffer(buffer, offset - bufStart, increment);
                    if (addr != 0L) {
                        envPtrList.add(addr);
                    }
                    offset += increment;
                } while (addr != 0L && --limit > 0);
                for (int i = 0; i < argp.length && argp[i] != 0L; ++i) {
                    String argStr;
                    if ((bufStart = PsInfo.conditionallyReadBufferFromStartOfPage(fd, buffer, bufSize, bufStart, argp[i])) == 0L || (argStr = buffer.getString(argp[i] - bufStart)).isEmpty()) continue;
                    args2.add(argStr);
                }
                for (Long envPtr : envPtrList) {
                    String envStr;
                    int idx;
                    if ((bufStart = PsInfo.conditionallyReadBufferFromStartOfPage(fd, buffer, bufSize, bufStart, envPtr)) == 0L || (idx = (envStr = buffer.getString(envPtr - bufStart)).indexOf(61)) <= 0) continue;
                    env.put(envStr.substring(0, idx), envStr.substring(idx + 1));
                }
            }
            finally {
                LIBC.close(fd);
            }
        }
        return new Pair<List<String>, Map<String, String>>(args2, env);
    }

    private static long conditionallyReadBufferFromStartOfPage(int fd, Memory buffer, LibCAPI.size_t bufSize, long bufStart, long addr) {
        if (addr < bufStart || addr - bufStart > PAGE_SIZE) {
            long newStart = Math.floorDiv(addr, PAGE_SIZE) * PAGE_SIZE;
            LibCAPI.ssize_t result = LIBC.pread(fd, (Pointer)buffer, bufSize, new NativeLong(newStart));
            if (result.longValue() < PAGE_SIZE) {
                LOG.debug("Failed to read page from address space: {} bytes read", (Object)result.longValue());
                return 0L;
            }
            return newStart;
        }
        return bufStart;
    }

    private static long getOffsetFromBuffer(Memory buffer, long offset, long increment) {
        return increment == 8L ? buffer.getLong(offset) : (long)buffer.getInt(offset);
    }

    static /* synthetic */ Map access$000() {
        return lwpsInfoOffsets;
    }

    static enum LwpsInfoT {
        PR_FLAG(4),
        PR_LWPID(4),
        PR_ADDR(Native.POINTER_SIZE),
        PR_WCHAN(Native.POINTER_SIZE),
        PR_STYPE(1),
        PR_STATE(1),
        PR_SNAME(1),
        PR_NICE(1),
        PR_SYSCALL(2),
        PR_OLDPRI(1),
        PR_CPU(1),
        PR_PRI(4),
        PR_PCTCPU(2),
        PAD(2),
        PR_START(2 * NativeLong.SIZE),
        PR_TIME(2 * NativeLong.SIZE),
        PR_CLNAME(8),
        PR_NAME(16),
        PR_ONPRO(4),
        PR_BINDPRO(4),
        PR_BINDPSET(4),
        PR_LGRP(4),
        PR_LAST_ONPROC(8),
        SIZE(0);

        private final int size;

        private LwpsInfoT(int bytes) {
            this.size = bytes;
        }

        public int size() {
            return this.size;
        }
    }

    static enum PsInfoT {
        PR_FLAG(4),
        PR_NLWP(4),
        PR_NZOMB(4),
        PR_PID(4),
        PR_PPID(4),
        PR_PGID(4),
        PR_SID(4),
        PR_UID(4),
        PR_EUID(4),
        PR_GID(4),
        PR_EGID(4),
        PAD1(Native.POINTER_SIZE - 4),
        PR_ADDR(Native.POINTER_SIZE),
        PR_SIZE(Native.SIZE_T_SIZE),
        PR_RSSIZE(Native.SIZE_T_SIZE),
        PR_TTYDEV(NativeLong.SIZE),
        PR_PCTCPU(2),
        PR_PCTMEM(2),
        PAD2(Native.POINTER_SIZE - 4),
        PR_START(2 * NativeLong.SIZE),
        PR_TIME(2 * NativeLong.SIZE),
        PR_CTIME(2 * NativeLong.SIZE),
        PR_FNAME(16),
        PR_PSARGS(80),
        PR_WSTAT(4),
        PR_ARGC(4),
        PR_ARGV(Native.POINTER_SIZE),
        PR_ENVP(Native.POINTER_SIZE),
        PR_DMODEL(1),
        PAD3(7),
        PR_LWP((Integer)PsInfo.access$000().get((Object)((Object)LwpsInfoT.SIZE))),
        PR_TASKID(4),
        PR_PROJID(4),
        PR_POOLID(4),
        PR_ZONEID(4),
        PR_CONTRACT(4),
        SIZE(0);

        private final int size;

        private PsInfoT(int bytes) {
            this.size = bytes;
        }

        public int size() {
            return this.size;
        }
    }
}

