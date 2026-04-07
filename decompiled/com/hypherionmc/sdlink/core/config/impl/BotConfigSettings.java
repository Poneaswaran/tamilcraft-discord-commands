/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment
 *  com.hypherionmc.craterlib.libs.moonconfig.core.fields.RandomArrayList
 */
package com.hypherionmc.sdlink.core.config.impl;

import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path;
import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment;
import com.hypherionmc.craterlib.libs.moonconfig.core.fields.RandomArrayList;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Activity;

public final class BotConfigSettings {
    @Path(value="botToken")
    @SpecComment(value="The token of the Discord Bot to use. This will be encrypted on first load. See https://sdlink.fdd-docs.com/installation/bot-creation/ to find this")
    public String botToken = "";
    @Path(value="printInviteLink")
    @SpecComment(value="Print the bot invite link to the console on startup")
    public boolean printInviteLink = true;
    @Path(value="silentReplies")
    @SpecComment(value="Use silent replies when Slash Commands are used")
    public boolean silentReplies = true;
    @Path(value="statusUpdateInterval")
    @SpecComment(value="How often the Bot Status will update on Discord (in Seconds). Set to 0 to disable")
    public int statusUpdateInterval = 30;
    @Path(value="botStatus")
    @SpecComment(value="Control what the Discord Bot will display as it's status message")
    public RandomArrayList<BotStatus> botStatus = RandomArrayList.of((Object[])new BotStatus[]{new BotStatus()});
    @Path(value="topicUpdates")
    @SpecComment(value="Define how the bot should handle channel topic updates on the chat channel")
    public ChannelTopic channelTopic = new ChannelTopic();
    @Path(value="invite")
    @SpecComment(value="Configure the in-game Discord Invite command")
    public DiscordInvite invite = new DiscordInvite();

    public static class BotStatus {
        @Path(value="status")
        @SpecComment(value="Do not add Playing. A status to display on the bot. You can use %players% and %maxplayers% to show the number of players on the server")
        public String botStatus = "Enjoying Minecraft with %players%/%maxplayers% players";
        @Path(value="botStatusType")
        @SpecComment(value="The type of the status displayed on the bot. Valid entries are: PLAYING, STREAMING, WATCHING, LISTENING, CUSTOM_STATUS")
        public Activity.ActivityType botStatusType = Activity.ActivityType.CUSTOM_STATUS;
        @Path(value="botStatusStreamingURL")
        @SpecComment(value="The URL that will be used when the \"botStatusType\" is set to \"STREAMING\", required to display as \"streaming\".")
        public String botStatusStreamingURL = "https://twitch.tv/twitch";
    }

    public static class ChannelTopic {
        @Path(value="doTopicUpdates")
        @SpecComment(value="Should the bot update the topic of your chat channel automatically every 6 Minutes")
        public boolean doTopicUpdates = true;
        @Path(value="updateInterval")
        @SpecComment(value="How often should the bot update the channel topic (IN MINUTES)? CANNOT BE LOWER THAN 6 MINUTES!")
        public int updateInterval = 6;
        @Path(value="channelTopic")
        @SpecComment(value="A topic for the Chat Relay channel. You can use %player%, %maxplayers%, %uptime% or just leave it empty.")
        public String channelTopic = "Playing Minecraft with %players%/%maxplayers% people | Uptime: %uptime%";
    }

    public static class DiscordInvite {
        @Path(value="inviteLink")
        @SpecComment(value="If this is defined, it will enable the in-game Discord command")
        public String inviteLink = "";
        @Path(value="inviteMessage")
        @SpecComment(value="The message to show when someone uses /discord command. You can use %inviteurl%")
        public String inviteMessage = "Hey, check out our discord server here -> %inviteurl%";
    }
}

