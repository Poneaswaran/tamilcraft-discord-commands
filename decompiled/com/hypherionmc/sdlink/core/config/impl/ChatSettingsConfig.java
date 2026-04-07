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
import com.hypherionmc.sdlink.core.config.AvatarType;
import com.hypherionmc.sdlink.core.config.TriBoolean;
import java.util.ArrayList;
import java.util.List;

public final class ChatSettingsConfig {
    @Path(value="useLinkedNames")
    @SpecComment(value="Use linked account names in Discord/Minecraft messages, instead of the default ones")
    public boolean useLinkedNames = true;
    @Path(value="useLinkedAvatar")
    @SpecComment(value="Use linked account avatar in Discord messages, instead of custom avatar")
    public boolean useLinkedAvatar = true;
    @Path(value="showDiscordInfo")
    @SpecComment(value="Show the discord name, username and role of the user that sent a message in Minecraft when the message is hovered")
    public boolean showDiscordInfo = false;
    @Path(value="formatting")
    @SpecComment(value="Convert Discord to MC, and MC to Discord Formatting")
    public boolean formatting = true;
    @Path(value="sendConsoleMessages")
    @SpecComment(value="Should console messages be sent to the Console Channel")
    public boolean sendConsoleMessages = false;
    @Path(value="customAvatarService")
    @SpecComment(value="Add your own Avatar service URL here. Use {uuid} to replace the player ID in the URL")
    public String customAvatarService = "https://crafatar.com/avatars/{uuid}";
    @Path(value="playerAvatarType")
    @SpecComment(value="The type of image to use as the player icon in messages. Valid entries are: AVATAR, HEAD, BODY, COMBO, CUSTOM")
    public AvatarType playerAvatarType = AvatarType.HEAD;
    @Path(value="relayTellRaw")
    @SpecComment(value="Should messages sent with TellRaw be sent to discord as a chat? (Experimental)")
    public boolean relayTellRaw = true;
    @Path(value="relayFullCommands")
    @SpecComment(value="Should the entire command executed be relayed to discord, or only the name of the command")
    public boolean relayFullCommands = false;
    @Path(value="ignoreBots")
    @SpecComment(value="Should messages from bots be relayed")
    public boolean ignoreBots = true;
    @Path(value="pluralKitCompat")
    @SpecComment(value="Compatibility with PluralKit (can introduce a very slight delay in messages being sent from Discord to Minecraft)")
    public boolean pluralKitCompat = false;
    @Path(value="pluralKitCompatMessageDelay")
    @SpecComment(value="Amount of time to delay messages by before sending them to Minecraft in milliseconds. Too low may make unproxied messages visible and too high will cause noticeable delay.")
    public int pluralKitCompatMessageDelay = 500;
    @Path(value="serverStarting")
    @SpecComment(value="Should SERVER STARTING messages be shown")
    public boolean serverStarting = true;
    @Path(value="serverStarted")
    @SpecComment(value="Should SERVER STARTED messages be shown")
    public boolean serverStarted = true;
    @Path(value="serverStopping")
    @SpecComment(value="Should SERVER STOPPING messages be shown")
    public boolean serverStopping = true;
    @Path(value="serverStopped")
    @SpecComment(value="Should SERVER STOPPED messages be shown")
    public boolean serverStopped = true;
    @Path(value="playerMessages")
    @SpecComment(value="Should the chat be relayed")
    public boolean playerMessages = true;
    @Path(value="discordMessages")
    @SpecComment(value="Should discord messages be relayed to Minecraft")
    public boolean discordMessages = true;
    @Path(value="playerJoin")
    @SpecComment(value="Should Player Join messages be posted")
    public boolean playerJoin = true;
    @Path(value="playerLeave")
    @SpecComment(value="Should Player Leave messages be posted")
    public boolean playerLeave = true;
    @Path(value="advancementMessages")
    @SpecComment(value="Should Advancement messages be posted. Valid values are ALWAYS, NEVER or GAMERULE")
    public TriBoolean advancementMessages = TriBoolean.ALWAYS;
    @Path(value="deathMessages")
    @SpecComment(value="Should Death Announcements be posted. Valid values are ALWAYS, NEVER or GAMERULE")
    public TriBoolean deathMessages = TriBoolean.ALWAYS;
    @Path(value="sendSayCommand")
    @SpecComment(value="Should Messages from the /say command be posted")
    public boolean sendSayCommand = true;
    @Path(value="broadcastCommands")
    @SpecComment(value="Should commands be posted to discord")
    public boolean broadcastCommands = true;
    @Path(value="whitelistChanged")
    @SpecComment(value="Should whitelist changes be posted to discord")
    public boolean whitelistChanged = false;
    @Path(value="ignoredCommands")
    @SpecComment(value="Commands that should not be broadcast to discord")
    public List<String> ignoredCommands = new ArrayList<String>(){
        {
            this.add("particle");
            this.add("login");
            this.add("execute");
            this.add("sdconfigeditor");
        }
    };
    @Path(value="allowMentionsFromChat")
    @SpecComment(value="Allow mentioning discord roles and users from Minecraft Chat")
    public boolean allowMentionsFromChat = false;
}

