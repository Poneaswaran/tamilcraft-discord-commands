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

public final class RelayMessageConfig {
    @Path(value="relayMinecraftChats")
    @SpecComment(value="Should Minecraft Chats be relayed to all connected servers")
    public boolean relayMinecraftChats = true;
    @Path(value="relayDiscordChats")
    @SpecComment(value="Should Discord Chat Messages be relayed to all connected servers")
    public boolean relayDiscordChats = true;
    @Path(value="relayMinecraftToDiscord")
    @SpecComment(value="Should Minecraft Messages be relayed to connected discord servers")
    public boolean relayMinecraftToDiscord = true;
    @Path(value="relayDeathMessages")
    @SpecComment(value="Should player death messages be relayed to all connected servers")
    public boolean relayDeathMessages = true;
    @Path(value="relayAdvancementMessages")
    @SpecComment(value="Should player advancement messages be relayed to all connected servers")
    public boolean relayAdvancementMessages = true;
    @Path(value="relayJoinMessages")
    @SpecComment(value="Should player join messages be relayed to all connected servers")
    public boolean relayJoinMessages = true;
    @Path(value="relayLeaveMessages")
    @SpecComment(value="Should player leave messages be relayed to all connected servers")
    public boolean relayLeaveMessages = true;
    @Path(value="relayMessagePrefix")
    @SpecComment(value="The Prefix to use for messages relayed to other servers. Set this to empty to ignore it")
    public String relayMessagePrefix = "<blue>[%server_name%]</blue>";
}

