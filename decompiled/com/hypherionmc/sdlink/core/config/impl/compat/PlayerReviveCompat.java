/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment
 */
package com.hypherionmc.sdlink.core.config.impl.compat;

import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path;
import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment;

public final class PlayerReviveCompat {
    @Path(value="enabled")
    @SpecComment(value="Should integration with Player Revive Mod be enabled")
    public boolean enabled = true;
    @Path(value="reviveWaitingMessage")
    @SpecComment(value="Message to be sent to discord, while the player is waiting to be revived")
    public String reviveWaitingMessage = "%player% is bleeding out and may need your help";
    @Path(value="revivedMessage")
    @SpecComment(value="Message to be sent to discord, when the player is revived")
    public String revivedMessage = "%player% has been revived";
    @Path(value="playerBledOutMessage")
    @SpecComment(value="Message to be sent to discord, when the player dies for real")
    public String playerBledOutMessage = "%player% %message%";
}

