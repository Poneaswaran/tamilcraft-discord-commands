/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.software.os;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface NetworkParams {
    public String getHostName();

    public String getDomainName();

    public String[] getDnsServers();

    public String getIpv4DefaultGateway();

    public String getIpv6DefaultGateway();
}

