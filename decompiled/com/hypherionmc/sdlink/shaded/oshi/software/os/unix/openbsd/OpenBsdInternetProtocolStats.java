/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.software.os.unix.openbsd;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.driver.unix.NetStat;
import com.hypherionmc.sdlink.shaded.oshi.software.common.AbstractInternetProtocolStats;
import com.hypherionmc.sdlink.shaded.oshi.software.os.InternetProtocolStats;

@ThreadSafe
public class OpenBsdInternetProtocolStats
extends AbstractInternetProtocolStats {
    @Override
    public InternetProtocolStats.TcpStats getTCPv4Stats() {
        return NetStat.queryTcpStats("netstat -s -p tcp");
    }

    @Override
    public InternetProtocolStats.UdpStats getUDPv4Stats() {
        return NetStat.queryUdpStats("netstat -s -p udp");
    }
}

