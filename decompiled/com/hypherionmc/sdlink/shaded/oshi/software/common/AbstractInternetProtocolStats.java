/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.software.common;

import com.hypherionmc.sdlink.shaded.oshi.driver.unix.NetStat;
import com.hypherionmc.sdlink.shaded.oshi.software.os.InternetProtocolStats;
import java.util.List;

public abstract class AbstractInternetProtocolStats
implements InternetProtocolStats {
    @Override
    public InternetProtocolStats.TcpStats getTCPv6Stats() {
        return new InternetProtocolStats.TcpStats(0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L);
    }

    @Override
    public InternetProtocolStats.UdpStats getUDPv6Stats() {
        return new InternetProtocolStats.UdpStats(0L, 0L, 0L, 0L);
    }

    @Override
    public List<InternetProtocolStats.IPConnection> getConnections() {
        return NetStat.queryNetstat();
    }
}

