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

public final class MessageFormatting {
    @Path(value="mcPrefix")
    @SpecComment(value="Prefix to add to Minecraft when a message is relayed from Discord. Supports MiniMessage formatting. Use %user% for the Discord Username")
    public String mcPrefix = "<yellow>[Discord]<reset> %user%: ";
    @Path(value="mcReplyFormatting")
    @SpecComment(value="How messages relayed from discord that are replies to other messages are formatted. Supports MiniMessage formatting")
    public String mcReplyFormatting = "    <b>\u250c\u2500\u2500\u2500\u2500<reset> %color%@%replier_name%%end_color% <gray>%message_summary%<newline><reset>";
    @Path(value="serverStarting")
    @SpecComment(value="Server Starting Message")
    public String serverStarting = "*Server is starting...*";
    @Path(value="serverStarted")
    @SpecComment(value="Server Started Message")
    public String serverStarted = "*Server has started. Enjoy!*";
    @Path(value="serverStopping")
    @SpecComment(value="Server Stopping Message")
    public String serverStopping = "*Server is stopping...*";
    @Path(value="serverStopped")
    @SpecComment(value="Server Stopped Message")
    public String serverStopped = "*Server has stopped...*";
    @Path(value="playerJoined")
    @SpecComment(value="Player Joined Message. Use %player% to display the player name")
    public String playerJoined = "*%player% has joined the server!*";
    @Path(value="playerLeft")
    @SpecComment(value="Player Left Message. Use %player% to display the player name")
    public String playerLeft = "*%player% has left the server!*";
    @Path(value="advancements")
    @SpecComment(value="Advancement Messages. Available variables: %player%, %title%, %description%")
    public String achievements = "*%player% has made the advancement [%title%]: %description%*";
    @Path(value="chat")
    @SpecComment(value="Chat Messages. THIS DOES NOT APPLY TO EMBED OR WEBHOOK MESSAGES. Available variables: %player%, %message%, %mcname%")
    public String chat = "%player%: %message%";
    @Path(value="death")
    @SpecComment(value="Death Messages. Available variables: %player%, %message%")
    public String death = "%player% %message%";
    @Path(value="whitelistAdded")
    @SpecComment(value="Message to be sent when a player is added to the whitelist")
    public String whitelistAdded = "%player% has been whitelisted!";
    @Path(value="whitelistRemoved")
    @SpecComment(value="Message to be sent when a player is removed from the whitelist")
    public String whitelistRemoved = "%player% has been removed from the whitelist!";
    @Path(value="commands")
    @SpecComment(value="Command Messages. Available variables: %player%, %command%")
    public String commands = "%player% **executed command**: *%command%*";
}

