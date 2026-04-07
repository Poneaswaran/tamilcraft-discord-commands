/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.api.commands.CraterCommand
 *  com.hypherionmc.craterlib.api.events.server.CraterRegisterCommandEvent
 *  com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile
 *  com.hypherionmc.craterlib.api.game.text.Text
 */
package com.hypherionmc.sdlink.server.commands;

import com.hypherionmc.craterlib.api.commands.CraterCommand;
import com.hypherionmc.craterlib.api.events.server.CraterRegisterCommandEvent;
import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.sdlink.api.accounts.MinecraftAccount;
import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.util.translations.SDText;

public final class WhoisCommand {
    public static void register(CraterRegisterCommandEvent event) {
        CraterCommand cmd = CraterCommand.literal((String)"whois").requiresPermission(4).withNode("sdlink.whois").withGameProfilesArgument("username", (player, profiles, ctx) -> {
            if (BotController.INSTANCE != null) {
                if (profiles.isEmpty()) {
                    ctx.sendSuccess(() -> Text.literal((String)SDText.translate("account.unlinked").toString()), true);
                    return 1;
                }
                MinecraftAccount account = MinecraftAccount.of((CraterGameProfile)profiles.stream().findFirst().get());
                String value = account.isAccountVerified() ? account.getDiscordName() : SDText.translate("account.unlinked").toString();
                ctx.sendSuccess(() -> Text.literal((String)value), true);
            }
            return 1;
        });
        event.registerCommand(cmd);
    }
}

