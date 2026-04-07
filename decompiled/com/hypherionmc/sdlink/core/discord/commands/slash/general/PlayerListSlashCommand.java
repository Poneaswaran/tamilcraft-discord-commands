/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.core.discord.commands.slash.general;

import com.hypherionmc.sdlink.api.accounts.MinecraftAccount;
import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.core.discord.commands.slash.SDLinkSlashCommand;
import com.hypherionmc.sdlink.core.services.SDLinkPlatform;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.EmbedBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.SlashCommandEvent;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.menu.ButtonEmbedPaginator;
import com.hypherionmc.sdlink.util.MessageUtil;
import com.hypherionmc.sdlink.util.translations.SDText;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class PlayerListSlashCommand
extends SDLinkSlashCommand {
    public PlayerListSlashCommand() {
        super(false);
        this.name = "playerlist";
        this.help = SDText.translate("command.playerlist.help").toString();
        this.guildOnly = true;
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        event.deferReply(SDLinkConfig.INSTANCE.botConfig.silentReplies).queue();
        try {
            List<MinecraftAccount> players = SDLinkPlatform.minecraftHelper.getOnlinePlayers();
            EmbedBuilder builder = new EmbedBuilder();
            ArrayList<MessageEmbed> pages = new ArrayList<MessageEmbed>();
            AtomicInteger count = new AtomicInteger();
            if (players.isEmpty()) {
                builder.setTitle(SDText.translate("command.playerlist.title").toString());
                builder.setColor(Color.RED);
                builder.setDescription(SDText.translate("command.playerlist.no_online"));
                event.getHook().sendMessageEmbeds(builder.build(), new MessageEmbed[0]).setEphemeral(true).queue();
                return;
            }
            ButtonEmbedPaginator.Builder paginator = MessageUtil.defaultPaginator();
            MessageUtil.listBatches(players, 10).forEach(p -> {
                StringBuilder sb = new StringBuilder();
                count.getAndIncrement();
                builder.clear();
                builder.setTitle(SDText.translate("command.playerlist.title_page", count.get(), (int)Math.ceil((float)players.size() / 10.0f)).toString());
                builder.setColor(Color.GREEN);
                builder.setFooter(SDText.translate("command.playerlist.footer", SDLinkPlatform.minecraftHelper.getPlayerCounts().getLeft(), SDLinkPlatform.minecraftHelper.getPlayerCounts().getRight()).toString());
                p.forEach(account -> {
                    sb.append("`").append(account.getUsername()).append("`");
                    if ((SDLinkConfig.INSTANCE.accessControl.enabled || SDLinkConfig.INSTANCE.accessControl.optionalVerification) && account.getDiscordUser() != null) {
                        sb.append(" - ").append(account.getDiscordUser().getAsMention());
                    }
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
            BotController.INSTANCE.getLogger().error("Failed to run player list command", (Throwable)e);
        }
    }
}

