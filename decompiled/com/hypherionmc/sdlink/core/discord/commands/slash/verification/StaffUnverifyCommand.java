/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.core.discord.commands.slash.verification;

import com.hypherionmc.sdlink.api.accounts.MinecraftAccount;
import com.hypherionmc.sdlink.api.messaging.Result;
import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.core.database.SDLinkAccount;
import com.hypherionmc.sdlink.core.discord.commands.slash.SDLinkSlashCommand;
import com.hypherionmc.sdlink.core.managers.DatabaseManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.OptionType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.OptionData;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.SlashCommandEvent;
import com.hypherionmc.sdlink.util.translations.SDText;
import java.util.ArrayList;
import java.util.List;

public final class StaffUnverifyCommand
extends SDLinkSlashCommand {
    public StaffUnverifyCommand() {
        super(true);
        this.name = "staffunverify";
        this.help = SDText.translate("command.staffunverify.help").toString();
        this.options = new ArrayList<OptionData>(){
            {
                this.add(new OptionData(OptionType.USER, "discorduser", "The discord user the minecraft account belongs to").setRequired(true));
                this.add(new OptionData(OptionType.STRING, "mcname", "The minecraft account of the linked user").setRequired(true));
            }
        };
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        event.deferReply(SDLinkConfig.INSTANCE.botConfig.silentReplies).queue();
        List<SDLinkAccount> accounts = DatabaseManager.INSTANCE.findAll(SDLinkAccount.class);
        if (accounts.isEmpty()) {
            event.getHook().sendMessage(SDText.translate("error.no_db_accounts").toString()).setEphemeral(SDLinkConfig.INSTANCE.botConfig.silentReplies).queue();
            return;
        }
        String mcname = event.getOption("mcname").getAsString();
        User user = event.getOption("discorduser").getAsUser();
        Member member = event.getGuild().getMemberById(user.getId());
        if (member == null) {
            event.getHook().sendMessage(SDText.translate("error.not_a_member", user.getEffectiveName()).toString()).setEphemeral(SDLinkConfig.INSTANCE.botConfig.silentReplies).queue();
            return;
        }
        SDLinkAccount account = accounts.stream().filter(a -> a.getUsername().equalsIgnoreCase(mcname) || a.getInGameName().equalsIgnoreCase(mcname)).findFirst().orElse(null);
        if (account == null) {
            event.getHook().editOriginal(SDText.translate("error.not_match_found", mcname).toString()).queue();
            return;
        }
        MinecraftAccount minecraftAccount = MinecraftAccount.of(account);
        Result result = minecraftAccount.unverifyAccount(member, event.getGuild());
        event.getHook().editOriginal(result.getMessage()).queue();
    }
}

