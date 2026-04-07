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
import java.util.ArrayList;
import java.util.List;

public final class MinecraftCommands {
    @Path(value="enabled")
    @SpecComment(value="Allow executing Minecraft commands from Discord")
    public boolean enabled = false;
    @Path(value="prefix")
    @SpecComment(value="Command Prefix. For example ?weather clear")
    public String prefix = "?";
    @Path(value="keepReplies")
    @SpecComment(value="Should command replies be deleted automatically or not")
    public boolean keepReplies = false;
    @Path(value="keepOriginal")
    @SpecComment(value="Should the original message that was sent to trigger the command be deleted automatically or not")
    public boolean keepOriginal = false;
    @Path(value="outputInvalid")
    @SpecComment(value="Should error messages be sent for invalid, or disallowed commands")
    public boolean outputInvalid = true;
    @Path(value="allowedChannels")
    @SpecComment(value="You can leave this empty, or enter the channel ID's (surrounded by \"\") of channels where linked commands can be used")
    public List<String> allowedChannels = new ArrayList<String>();
    @Path(value="permissions")
    @SpecComment(value="List of command permissions")
    public List<Command> permissions = new ArrayList<Command>();

    public static class Command {
        public String role = "0";
        public List<String> commands = new ArrayList<String>();
        public int permissionLevel = 1;
    }
}

