/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Level
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.core.Appender
 *  org.apache.logging.log4j.core.Filter
 *  org.apache.logging.log4j.core.LogEvent
 *  org.apache.logging.log4j.core.Logger
 *  org.apache.logging.log4j.core.appender.AbstractAppender
 *  org.apache.logging.log4j.core.config.Property
 *  org.apache.logging.log4j.core.config.plugins.Plugin
 *  org.apache.logging.log4j.core.config.plugins.PluginAttribute
 *  org.apache.logging.log4j.core.config.plugins.PluginElement
 *  org.apache.logging.log4j.core.config.plugins.PluginFactory
 */
package com.hypherionmc.sdlink.util;

import com.hypherionmc.sdlink.api.accounts.DiscordAuthor;
import com.hypherionmc.sdlink.api.messaging.MessageType;
import com.hypherionmc.sdlink.api.messaging.discord.DiscordMessage;
import com.hypherionmc.sdlink.api.messaging.discord.DiscordMessageBuilder;
import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.core.discord.BotController;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name="SDLinkLogging", category="Core", elementType="appender")
public final class LogReader
extends AbstractAppender {
    public static String logs = "";
    private static boolean isDevEnv = false;
    private long time;
    private Thread messageScheduler;
    private static LogReader da;

    private LogReader(String name, Filter filter) {
        super(name, filter, null, true, new Property[0]);
    }

    @PluginFactory
    public static LogReader createAppender(@PluginAttribute(value="name") String name, @PluginElement(value="Filter") Filter filter) {
        return new LogReader(name, filter);
    }

    public static void init(boolean isDev) {
        isDevEnv = isDev;
        da = LogReader.createAppender("SDLinkLogging", null);
        ((Logger)LogManager.getRootLogger()).addAppender((Appender)da);
        da.start();
    }

    public static void destroy() {
        da.stop();
        ((Logger)LogManager.getRootLogger()).removeAppender((Appender)da);
        LogReader.da.messageScheduler.stop();
    }

    public void append(LogEvent event) {
        if (BotController.INSTANCE.isBotReady() && event.getLevel().intLevel() < Level.DEBUG.intLevel()) {
            logs = logs + this.formatMessage(event) + "\n";
            this.scheduleMessage();
        }
    }

    private String formatMessage(LogEvent event) {
        String devString = "**[" + this.formatTime(event.getTimeMillis()) + "]** **[" + event.getThreadName() + "/" + event.getLevel().name() + "]** **(" + event.getLoggerName().substring(event.getLoggerName().lastIndexOf(".") + 1) + ")** *" + event.getMessage().getFormattedMessage() + "*";
        String prodString = "**[" + this.formatTime(event.getTimeMillis()) + "]** **[" + event.getThreadName() + "/" + event.getLevel().name() + "]** *" + event.getMessage().getFormattedMessage() + "*";
        return isDevEnv ? devString : prodString;
    }

    private String formatTime(long millis) {
        SimpleDateFormat obj = new SimpleDateFormat("HH:mm:ss");
        Date res = new Date(millis);
        return obj.format(res);
    }

    private void scheduleMessage() {
        this.time = System.currentTimeMillis();
        if (this.messageScheduler == null || !this.messageScheduler.isAlive()) {
            this.messageScheduler = new Thread(() -> {
                while (BotController.INSTANCE.isBotReady()) {
                    if (System.currentTimeMillis() - this.time > 250L) {
                        logs = logs.replaceAll("\\b(?:(?:2(?:[0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9])\\.){3}(?:(?:2([0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9]))\\b", "[REDACTED]");
                        if ((logs = logs.replaceAll("https:\\/\\/editor\\.firstdark\\.dev\\/[a-zA-Z0-9]+", "[REDACTED]")).length() > 2000) {
                            logs = logs.substring(0, 1999);
                        }
                        DiscordMessage discordMessage = new DiscordMessageBuilder(MessageType.CONSOLE).message(logs).author(DiscordAuthor.getServer()).build();
                        if (SDLinkConfig.INSTANCE.chatConfig.sendConsoleMessages) {
                            discordMessage.sendMessage();
                        }
                        logs = "";
                        break;
                    }
                    try {
                        Thread.sleep(30L);
                    }
                    catch (InterruptedException e) {
                        if (!SDLinkConfig.INSTANCE.generalConfig.debugging) continue;
                        BotController.INSTANCE.getLogger().error("Failed to send console message: {}", (Object)e.getMessage());
                    }
                }
            });
            this.messageScheduler.start();
        }
    }
}

