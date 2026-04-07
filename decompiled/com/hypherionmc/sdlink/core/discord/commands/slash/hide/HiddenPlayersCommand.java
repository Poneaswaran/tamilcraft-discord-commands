/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.core.discord.commands.slash.hide;

import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.core.database.HiddenPlayers;
import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.core.discord.commands.slash.SDLinkSlashCommand;
import com.hypherionmc.sdlink.core.managers.HiddenPlayersManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.EmbedBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.SlashCommandEvent;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.menu.ButtonEmbedPaginator;
import com.hypherionmc.sdlink.util.MessageUtil;
import com.hypherionmc.sdlink.util.translations.SDText;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class HiddenPlayersCommand
extends SDLinkSlashCommand {
    public HiddenPlayersCommand() {
        super(true);
        this.name = "hiddenplayers";
        this.help = SDText.translate("command.hiddenplayers.help").toString();
        this.guildOnly = true;
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        event.deferReply(SDLinkConfig.INSTANCE.botConfig.silentReplies).queue();
        try {
            HashMap<String, HiddenPlayers> hiddenPlayers = HiddenPlayersManager.INSTANCE.getHiddenPlayers();
            EmbedBuilder builder = new EmbedBuilder();
            ArrayList<MessageEmbed> pages = new ArrayList<MessageEmbed>();
            AtomicInteger count = new AtomicInteger();
            if (hiddenPlayers.isEmpty()) {
                builder.setTitle(SDText.translate("command.hiddenplayers.title").toString());
                builder.setColor(Color.RED);
                builder.setDescription(SDText.translate("command.hiddenplayers.no_players"));
                event.getHook().sendMessageEmbeds(builder.build(), new MessageEmbed[0]).setEphemeral(true).queue();
                return;
            }
            ButtonEmbedPaginator.Builder paginator = MessageUtil.defaultPaginator();
            MessageUtil.listBatches(hiddenPlayers.values().stream().toList(), 10).forEach(p -> {
                StringBuilder sb = new StringBuilder();
                count.getAndIncrement();
                builder.clear();
                builder.setTitle(SDText.translate("command.hiddenplayers.title_page", count.get(), (int)Math.ceil((float)hiddenPlayers.size() / 10.0f)).toString());
                builder.setColor(Color.GREEN);
                p.forEach(account -> {
                    sb.append("`").append(account.getDisplayName()).append("`");
                    sb.append(" - ").append(account.getType());
                    sb.append("\r\n");
                });
                builder.setDescription(sb.toString());
                pages.add(builder.build());
            });
            paginator.setItems(pages);
            ButtonEmbedPaginator embedPaginator = paginator.build();
            event.getHook().sendMessageEmbeds((MessageEmbed)pages.get(0), new MessageEmbed[0]).setEphemeral(false).queue(success -> embedPaginator.paginate((Message)success, 1));
        }
        catch (Exception e) {
            event.getHook().sendMessage(SDText.translate("error.command_failed").toString()).setEphemeral(SDLinkConfig.INSTANCE.botConfig.silentReplies).queue();
            BotController.INSTANCE.getLogger().error("Failed to run hidden player list command", (Throwable)e);
        }
    }
}

