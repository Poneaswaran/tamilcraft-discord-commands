/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Native
 *  com.sun.jna.Pointer
 *  com.sun.jna.platform.unix.solaris.LibKstat
 *  com.sun.jna.platform.unix.solaris.LibKstat$Kstat
 *  com.sun.jna.platform.unix.solaris.LibKstat$KstatCtl
 *  com.sun.jna.platform.unix.solaris.LibKstat$KstatNamed
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.hypherionmc.sdlink.shaded.oshi.util.platform.unix.solaris;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.FormatUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.Util;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.unix.solaris.LibKstat;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ThreadSafe
public final class KstatUtil {
    private static final Logger LOG = LoggerFactory.getLogger(KstatUtil.class);
    private static final LibKstat KS = LibKstat.INSTANCE;
    private static final LibKstat.KstatCtl KC = KS.kstat_open();
    private static final ReentrantLock CHAIN = new ReentrantLock();

    private KstatUtil() {
    }

    public static KstatChain openChain() {
        return new KstatChain();
    }

    public static String dataLookupString(LibKstat.Kstat ksp, String name) {
        if (ksp.ks_type != 1 && ksp.ks_type != 4) {
            throw new IllegalArgumentException("Not a kstat_named or kstat_timer kstat.");
        }
        Pointer p = KS.kstat_data_lookup(ksp, name);
        if (p == null) {
            LOG.debug("Failed to lookup kstat value for key {}", (Object)name);
            return "";
        }
        LibKstat.KstatNamed data = new LibKstat.KstatNamed(p);
        switch (data.data_type) {
            case 0: {
                return Native.toString((byte[])data.value.charc, (Charset)StandardCharsets.UTF_8);
            }
            case 1: {
                return Integer.toString(data.value.i32);
            }
            case 2: {
                return FormatUtil.toUnsignedString(data.value.ui32);
            }
            case 3: {
                return Long.toString(data.value.i64);
            }
            case 4: {
                return FormatUtil.toUnsignedString(data.value.ui64);
            }
            case 9: {
                return data.value.str.addr.getString(0L);
            }
        }
        LOG.error("Unimplemented kstat data type {}", (Object)data.data_type);
        return "";
    }

    public static long dataLookupLong(LibKstat.Kstat ksp, String name) {
        if (ksp.ks_type != 1 && ksp.ks_type != 4) {
            throw new IllegalArgumentException("Not a kstat_named or kstat_timer kstat.");
        }
        Pointer p = KS.kstat_data_lookup(ksp, name);
        if (p == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Failed lo lookup kstat value on {}:{}:{} for key {}", new Object[]{Native.toString((byte[])ksp.ks_module, (Charset)StandardCharsets.US_ASCII), ksp.ks_instance, Native.toString((byte[])ksp.ks_name, (Charset)StandardCharsets.US_ASCII), name});
            }
            return 0L;
        }
        LibKstat.KstatNamed data = new LibKstat.KstatNamed(p);
        switch (data.data_type) {
            case 1: {
                return data.value.i32;
            }
            case 2: {
                return FormatUtil.getUnsignedInt(data.value.ui32);
            }
            case 3: {
                return data.value.i64;
            }
            case 4: {
                return data.value.ui64;
            }
        }
        LOG.error("Unimplemented or non-numeric kstat data type {}", (Object)data.data_type);
        return 0L;
    }

    public static final class KstatChain
    implements AutoCloseable {
        private KstatChain() {
            CHAIN.lock();
            KstatChain.update();
        }

        public static boolean read(LibKstat.Kstat ksp) {
            int retry = 0;
            while (0 > KS.kstat_read(KC, ksp, null)) {
                if (11 != Native.getLastError() || 5 <= ++retry) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Failed to read kstat {}:{}:{}", new Object[]{Native.toString((byte[])ksp.ks_module, (Charset)StandardCharsets.US_ASCII), ksp.ks_instance, Native.toString((byte[])ksp.ks_name, (Charset)StandardCharsets.US_ASCII)});
                    }
                    return false;
                }
                Util.sleep(8 << retry);
            }
            return true;
        }

        public static LibKstat.Kstat lookup(String module, int instance, String name) {
            return KS.kstat_lookup(KC, module, instance, name);
        }

        public static List<LibKstat.Kstat> lookupAll(String module, int instance, String name) {
            ArrayList<LibKstat.Kstat> kstats = new ArrayList<LibKstat.Kstat>();
            for (LibKstat.Kstat ksp = KS.kstat_lookup(KC, module, instance, name); ksp != null; ksp = ksp.next()) {
                if (module != null && !module.equals(Native.toString((byte[])ksp.ks_module, (Charset)StandardCharsets.US_ASCII)) || instance >= 0 && instance != ksp.ks_instance || name != null && !name.equals(Native.toString((byte[])ksp.ks_name, (Charset)StandardCharsets.US_ASCII))) continue;
                kstats.add(ksp);
            }
            return kstats;
        }

        public static int update() {
            return KS.kstat_chain_update(KC);
        }

        @Override
        public void close() {
            CHAIN.unlock();
        }
    }
}

