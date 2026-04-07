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

public final class ChannelWebhookConfig {
    @Path(value="serverAvatar")
    @SpecComment(value="A DIRECT link to an image to use as the avatar for server messages. Also used for embeds")
    public String serverAvatar = "";
    @Path(value="serverName")
    @SpecComment(value="The name to display for Server messages when using Webhooks")
    public String serverName = "Minecraft Server";
    @Path(value="channels")
    @SpecComment(value="Config relating to the discord channels to use with the mod")
    public Channels channels = new Channels();
    @Path(value="webhooks")
    @SpecComment(value="Config relating to the discord Webhooks to use with the mod")
    public Webhooks webhooks = new Webhooks();

    public static class Channels {
        @Path(value="chatChannelID")
        @SpecComment(value="REQUIRED! The ID of the channel to post in and relay messages from. This is still needed, even in webhook mode")
        public String chatChannelID = "0";
        @Path(value="eventsChannelID")
        @SpecComment(value="If this ID is set, event messages will be posted in this channel instead of the chat channel")
        public String eventsChannelID = "0";
        @Path(value="consoleChannelID")
        @SpecComment(value="If this ID is set, console messages sent after the bot started will be relayed here")
        public String consoleChannelID = "0";
    }

    public static class Webhooks {
        @Path(value="enabled")
        @SpecComment(value="Prefer Webhook Messages over Standard Bot Messages")
        public boolean enabled = false;
        @Path(value="webhookNameFormat")
        @SpecComment(value="Change how the webhook name is displayed in discord. Available placeholders: %display_name%, %mc_name%")
        public String webhookNameFormat = "%display_name%";
        @Path(value="useServerForChat")
        @SpecComment(value="Use Server Author for chat messages, instead of the real author information")
        public boolean useServerForChat = false;
        @Path(value="chatWebhook")
        @SpecComment(value="The URL of the channel webhook to use for Chat Messages. Will be encrypted on first run")
        public String chatWebhook = "";
        @Path(value="eventsWebhook")
        @SpecComment(value="The URL of the channel webhook to use for Server Messages. Will be encrypted on first run")
        public String eventsWebhook = "";
        @Path(value="consoleWebhook")
        @SpecComment(value="The URL of the channel webhook to use for Console Messages. DOES NOT WORK FOR CONSOLE RELAY! Will be encrypted on first run")
        public String consoleWebhook = "";
    }
}

