/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.hypherionmc.sdlink.shaded.oshi.software.common;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.software.os.NetworkParams;
import com.hypherionmc.sdlink.shaded.oshi.util.FileUtil;
import com.hypherionmc.sdlink.shaded.oshi.util.ParseUtil;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ThreadSafe
public abstract class AbstractNetworkParams
implements NetworkParams {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractNetworkParams.class);
    private static final String NAMESERVER = "nameserver";

    @Override
    public String getDomainName() {
        try {
            return InetAddress.getLocalHost().getCanonicalHostName();
        }
        catch (UnknownHostException e) {
            LOG.error("Unknown host exception when getting address of local host: {}", (Object)e.getMessage());
            return "";
        }
    }

    @Override
    public String getHostName() {
        try {
            String hn = InetAddress.getLocalHost().getHostName();
            int dot = hn.indexOf(46);
            if (dot == -1) {
                return hn;
            }
            return hn.substring(0, dot);
        }
        catch (UnknownHostException e) {
            LOG.error("Unknown host exception when getting address of local host: {}", (Object)e.getMessage());
            return "";
        }
    }

    @Override
    public String[] getDnsServers() {
        List<String> resolv = FileUtil.readFile("/etc/resolv.conf");
        String key = NAMESERVER;
        int maxNameServer = 3;
        ArrayList<String> servers = new ArrayList<String>();
        for (int i = 0; i < resolv.size() && servers.size() < maxNameServer; ++i) {
            String value;
            String line = resolv.get(i);
            if (!line.startsWith(key) || (value = line.substring(key.length()).replaceFirst("^[ \t]+", "")).length() == 0 || value.charAt(0) == '#' || value.charAt(0) == ';') continue;
            String val = value.split("[ \t#;]", 2)[0];
            servers.add(val);
        }
        return servers.toArray(new String[0]);
    }

    protected static String searchGateway(List<String> lines) {
        for (String line : lines) {
            String leftTrimmed = line.replaceFirst("^\\s+", "");
            if (!leftTrimmed.startsWith("gateway:")) continue;
            String[] split = ParseUtil.whitespaces.split(leftTrimmed);
            if (split.length < 2) {
                return "";
            }
            return split[1].split("%")[0];
        }
        return "";
    }

    public String toString() {
        return String.format("Host name: %s, Domain name: %s, DNS servers: %s, IPv4 Gateway: %s, IPv6 Gateway: %s", this.getHostName(), this.getDomainName(), Arrays.toString(this.getDnsServers()), this.getIpv4DefaultGateway(), this.getIpv6DefaultGateway());
    }
}

