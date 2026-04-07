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
package com.hypherionmc.sdlink.shaded.oshi.driver.unix.aix;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.jna.platform.unix.aix.AixLibc;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Pair;
import com.hypherionmc.sdlink.shaded.oshi.util.tuples.Triplet;
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
    private static final AixLibc LIBC = AixLibc.INSTANCE;
    private static final long PAGE_SIZE = 4096L;
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
    public static Triplet<Integer, Long, Long> queryArgsEnvAddrs(int pid) {
        File procpsinfo = new File("/proc/" + pid + "/psinfo");
        try (RandomAccessFile psinfo = new RandomAccessFile(procpsinfo, "r");
             FileChannel chan = psinfo.getChannel();
             ByteArrayOutputStream out = new ByteArrayOutputStream();){
            ByteBuffer buf;
            int bufferSize = psInfoOffsets.get((Object)PsInfoT.SIZE);
            if ((long)bufferSize > chan.size()) {
                bufferSize = (int)chan.size();
            }
            if (chan.read(buf = ByteBuffer.allocate(bufferSize)) < psInfoOffsets.get((Object)PsInfoT.PR_FNAME)) return null;
            int argc = buf.getInt(psInfoOffsets.get((Object)PsInfoT.PR_ARGC));
            long argv = buf.getLong(psInfoOffsets.get((Object)PsInfoT.PR_ARGV));
            long envp = buf.getLong(psInfoOffsets.get((Object)PsInfoT.PR_ENVP));
            Triplet<Integer, Long, Long> triplet = new Triplet<Integer, Long, Long>(argc, argv, envp);
            return triplet;
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
        Triplet<Integer, Long, Long> addrs = PsInfo.queryArgsEnvAddrs(pid);
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
                long increment = (envp - argv) / (long)(argc + 1);
                long bufStart = 0L;
                Memory buffer = new Memory(8192L);
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
        if (addr < bufStart || addr - bufStart > 4096L) {
            long newStart = Math.floorDiv(addr, 4096L) * 4096L;
            LibCAPI.ssize_t result = LIBC.pread(fd, (Pointer)buffer, bufSize, new NativeLong(newStart));
            if (result.longValue() < 4096L) {
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
        PR_LWPID(8),
        PR_ADDR(8),
        PR_WCHAN(8),
        PR_FLAG(4),
        PR_WTYPE(1),
        PR_STATE(1),
        PR_SNAME(1),
        PR_NICE(1),
        PR_PRI(4),
        PR_POLICY(4),
        PR_CLNAME(8),
        PR_ONPRO(Native.POINTER_SIZE),
        PR_BINDPRO(Native.POINTER_SIZE),
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
        PR_FLAG2(4),
        PR_NLWP(4),
        PR_PAD1(4),
        PR_UID(8),
        PR_EUID(8),
        PR_GID(8),
        PR_EGID(8),
        PR_PID(8),
        PR_PPID(8),
        PR_PGID(8),
        PR_SID(8),
        PR_TTYDEV(8),
        PR_ADDR(8),
        PR_SIZE(8),
        PR_RSSIZE(8),
        PR_START(16),
        PR_TIME(16),
        PR_CID(2),
        PR_PAD2(2),
        PR_ARGC(4),
        PR_ARGV(8),
        PR_ENVP(8),
        PR_FNAME(16),
        PR_PSARGS(80),
        PR_PAD(64),
        PR_LWP((Integer)PsInfo.access$000().get((Object)((Object)LwpsInfoT.SIZE))),
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

