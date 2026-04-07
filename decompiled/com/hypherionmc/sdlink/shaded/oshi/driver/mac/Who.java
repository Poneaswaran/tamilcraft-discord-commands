/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Native
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.mac;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.jna.platform.mac.SystemB;
import com.hypherionmc.sdlink.shaded.oshi.software.os.OSSession;
import com.sun.jna.Native;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public final class Who {
    private static final SystemB SYS = SystemB.INSTANCE;

    private Who() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static synchronized List<OSSession> queryUtxent() {
        ArrayList<OSSession> whoList = new ArrayList<OSSession>();
        SYS.setutxent();
        try {
            SystemB.MacUtmpx ut;
            while ((ut = SYS.getutxent()) != null) {
                if (ut.ut_type != 7 && ut.ut_type != 6) continue;
                String user = Native.toString((byte[])ut.ut_user, (Charset)StandardCharsets.US_ASCII);
                String device = Native.toString((byte[])ut.ut_line, (Charset)StandardCharsets.US_ASCII);
                String host = Native.toString((byte[])ut.ut_host, (Charset)StandardCharsets.US_ASCII);
                long loginTime = ut.ut_tv.tv_sec.longValue() * 1000L + (long)ut.ut_tv.tv_usec / 1000L;
                if (user.isEmpty() || device.isEmpty() || loginTime < 0L || loginTime > System.currentTimeMillis()) {
                    List<OSSession> list = com.hypherionmc.sdlink.shaded.oshi.driver.unix.Who.queryWho();
                    return list;
                }
                whoList.add(new OSSession(user, device, loginTime, host));
            }
        }
        finally {
            SYS.endutxent();
        }
        return whoList;
    }
}

