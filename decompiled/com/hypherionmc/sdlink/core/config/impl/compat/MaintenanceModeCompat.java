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
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.OnlineStatus;

public final class MaintenanceModeCompat {
    @Path(value="enabled")
    @SpecComment(value="Should integration with MaintenanceMode be enabled")
    public boolean enabled = true;
    @Path(value="maintenanceOnlineStatus")
    @SpecComment(value="Change the Bot Online Status during Maintenance Mode. Valid options are ONLINE, IDLE, DO_NOT_DISTURB, OFFLINE")
    public OnlineStatus onlineStatus = OnlineStatus.DO_NOT_DISTURB;
    @Path(value="updateChannelTopic")
    @SpecComment(value="Update channel topic with server MOTD during maintenance mode")
    public boolean updateChannelTopic = true;
    @Path(value="updateBotStatus")
    @SpecComment(value="Update the bot status with the server MOTD during maintenance mode")
    public boolean updateBotStatus = false;
    @Path(value="sendMaintenanceStart")
    @SpecComment(value="Send a message to discord when maintenance starts")
    public boolean sendMaintenanceStart = true;
    @Path(value="sendMaintenanceEnd")
    @SpecComment(value="Send a message to discord when maintenance ends")
    public boolean sendMaintenanceEnd = true;
    @Path(value="maintenanceStartMessage")
    @SpecComment(value="The message to send to discord when maintenance has started")
    public String maintenanceStartMessage = "Maintenance has started";
    @Path(value="maintenanceEndMessage")
    @SpecComment(value="The message to send to discord when maintenance has ended")
    public String maintenanceEndMessage = "Maintenance has ended";
}

