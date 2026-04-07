/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment
 */
package com.hypherionmc.sdlink.core.config.impl;

import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path;
import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment;

public final class RelayServerConfig {
    @Path(value="enabled")
    @SpecComment(value="Enable or Disable the relay server")
    public boolean enabled = false;
    @Path(value="relayServerUrl")
    @SpecComment(value="The Relay Server to connect to. If you use an IP address, include the port, like 127.0.0.1:1234")
    public String relayServerUrl = "sdlinkrelay.firstdark.dev";
    @Path(value="relayToken")
    @SpecComment(value="A secret, password if you will, to connect your servers with. This has to match on all connected servers")
    public String relayToken = "";
}

