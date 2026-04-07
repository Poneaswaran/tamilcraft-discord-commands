/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.software.os.unix.openbsd;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.software.common.AbstractNetworkParams;
import com.hypherionmc.sdlink.shaded.oshi.util.ExecutingCommand;

@ThreadSafe
public class OpenBsdNetworkParams
extends AbstractNetworkParams {
    @Override
    public String getIpv4DefaultGateway() {
        return OpenBsdNetworkParams.searchGateway(ExecutingCommand.runNative("route -n get default"));
    }

    @Override
    public String getIpv6DefaultGateway() {
        return OpenBsdNetworkParams.searchGateway(ExecutingCommand.runNative("route -n get default"));
    }
}

