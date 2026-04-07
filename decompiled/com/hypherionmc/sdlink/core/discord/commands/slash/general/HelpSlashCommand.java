/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.core.discord.commands.slash.general;

import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.core.discord.commands.CommandManager;
import com.hypherionmc.sdlink.core.discord.commands.slash.SDLinkSlashCommand;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.EmbedBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.SlashCommand;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.SlashCommandEvent;
import com.hypherionmc.sdlink.util.translations.SDText;
import java.awt.Color;
import java.util.Set;

public final class HelpSlashCommand
extends SDLinkSlashCommand {
    public HelpSlashCommand() {
        super(false);
        this.name = "help";
        this.help = SDText.translate("command.help.help").toString();
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        event.deferReply(true).queue();
        Set<SlashCommand> commands = CommandManager.INSTANCE.getCommands();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(SDText.translate("command.help.title").toString());
        builder.setColor(Color.BLUE);
        commands.forEach(cmd -> builder.addField(cmd.getName(), cmd.getHelp(), false));
        event.getHook().sendMessageEmbeds(builder.build(), new MessageEmbed[0]).setEphemeral(SDLinkConfig.INSTANCE.botConfig.silentReplies).queue();
    }
}

