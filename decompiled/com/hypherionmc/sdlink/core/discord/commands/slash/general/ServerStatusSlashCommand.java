/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.core.discord.commands.slash.general;

import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.core.discord.commands.slash.SDLinkSlashCommand;
import com.hypherionmc.sdlink.core.services.SDLinkPlatform;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.EmbedBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.buttons.Button;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.SlashCommandEvent;
import com.hypherionmc.sdlink.shaded.oshi.SystemInfo;
import com.hypherionmc.sdlink.shaded.oshi.hardware.CentralProcessor;
import com.hypherionmc.sdlink.shaded.oshi.hardware.HardwareAbstractionLayer;
import com.hypherionmc.sdlink.util.SystemUtils;

public final class ServerStatusSlashCommand
extends SDLinkSlashCommand {
    public ServerStatusSlashCommand() {
        super(true);
        this.name = "status";
        this.help = "View information about your server";
        this.guildOnly = true;
    }

    public static MessageEmbed runStatusCommand() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        CentralProcessor cpu = hal.getProcessor();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Server Information / Status");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("**__System Information__**\r\n\r\n");
        stringBuilder.append("**CPU:**\r\n```\r\n").append(cpu.toString()).append("```").append("\r\n");
        try {
            stringBuilder.append("**Memory:**\r\n```\r\n").append(SystemUtils.byteToHuman(hal.getMemory().getAvailable())).append(" free of ").append(SystemUtils.byteToHuman(hal.getMemory().getTotal())).append("```\r\n");
        }
        catch (Exception exception) {
            // empty catch block
        }
        stringBuilder.append("**OS:**\r\n```\r\n").append(systemInfo.getOperatingSystem().toString()).append(" (").append(systemInfo.getOperatingSystem().getBitness()).append(" bit)\r\n").append("Version: ").append(systemInfo.getOperatingSystem().getVersionInfo().getVersion()).append("```\r\n");
        stringBuilder.append("**System Uptime:**\r\n```\r\n").append(SystemUtils.secondsToTimestamp(systemInfo.getOperatingSystem().getSystemUptime())).append("```\r\n");
        stringBuilder.append("**__Minecraft Information__**\r\n\r\n");
        stringBuilder.append("**Server Uptime:**\r\n```\r\n").append(SystemUtils.secondsToTimestamp(SDLinkPlatform.minecraftHelper.getServerUptime())).append("```\r\n");
        stringBuilder.append("**Server Version:**\r\n```\r\n").append(SDLinkPlatform.minecraftHelper.getServerVersion()).append("```\r\n");
        stringBuilder.append("**Players Online:**\r\n```\r\n").append(SDLinkPlatform.minecraftHelper.getPlayerCounts().getLeft()).append("/").append(SDLinkPlatform.minecraftHelper.getPlayerCounts().getRight()).append("```\r\n");
        stringBuilder.append("**Whitelisting:**\r\n```\r\n").append(!SDLinkPlatform.minecraftHelper.checkWhitelisting().isError() ? "Enabled" : "Disabled").append("```\r\n");
        builder.setDescription(stringBuilder.toString());
        return builder.build();
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        event.deferReply(SDLinkConfig.INSTANCE.botConfig.silentReplies).queue();
        Button refreshBtn = Button.danger("sdrefreshbtn", "Refresh");
        ((WebhookMessageCreateAction)event.getHook().sendMessageEmbeds(ServerStatusSlashCommand.runStatusCommand(), new MessageEmbed[0]).addActionRow(refreshBtn)).queue();
    }
}

