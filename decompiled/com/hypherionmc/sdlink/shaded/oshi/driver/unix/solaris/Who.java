/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Native
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.unix.solaris;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.jna.platform.unix.solaris.SolarisLibc;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSSession;
import com.sun.jna.Native;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public final class Who {
    private static final SolarisLibc LIBC = SolarisLibc.INSTANCE;

    private Who() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static synchronized List<OSSession> queryUtxent() {
        ArrayList<OSSession> whoList = new ArrayList<OSSession>();
        LIBC.setutxent();
        try {
            SolarisLibc.SolarisUtmpx ut;
            while ((ut = LIBC.getutxent()) != null) {
                String user;
                if (ut.ut_type != 7 && ut.ut_type != 6 || "LOGIN".equals(user = Native.toString((byte[])ut.ut_user, (Charset)StandardCharsets.US_ASCII))) continue;
                String device = Native.toString((byte[])ut.ut_line, (Charset)StandardCharsets.US_ASCII);
                String host = Native.toString((byte[])ut.ut_host, (Charset)StandardCharsets.US_ASCII);
                long loginTime = ut.ut_tv.tv_sec.longValue() * 1000L + ut.ut_tv.tv_usec.longValue() / 1000L;
                if (user.isEmpty() || device.isEmpty() || loginTime < 0L || loginTime > System.currentTimeMillis()) {
                    List<OSSession> list = com.hypherionmc.sdlink.shaded.oshi.driver.unix.Who.queryWho();
                    return list;
                }
                whoList.add(new OSSession(user, device, loginTime, host));
            }
            return whoList;
        }
        finally {
            LIBC.endutxent();
        }
    }
}

