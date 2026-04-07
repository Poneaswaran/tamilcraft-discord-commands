/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.api.commands.CraterCommand
 *  com.hypherionmc.craterlib.api.events.server.CraterRegisterCommandEvent
 *  com.hypherionmc.craterlib.api.game.text.Text
 */
package com.hypherionmc.sdlink.server.commands;

import com.hypherionmc.craterlib.api.commands.CraterCommand;
import com.hypherionmc.craterlib.api.events.server.CraterRegisterCommandEvent;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.sdlink.server.ServerEvents;
import com.hypherionmc.sdlink.util.translations.SDText;

public final class ReloadBotCommand {
    public static void register(CraterRegisterCommandEvent event) {
        CraterCommand cmd = CraterCommand.literal((String)"reloadbot").requiresPermission(4).withNode("sdlink.reloadbot").execute(ctx -> {
            ServerEvents.reloadBot(true);
            ctx.sendSuccess(() -> Text.literal((String)SDText.translate("feedback.bot_reloaded").toString()), true);
            return 1;
        });
        event.registerCommand(cmd);
    }
}

