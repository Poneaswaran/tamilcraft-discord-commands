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
import com.hypherionmc.sdlink.core.managers.EmbedManager;

public final class ReloadEmbedsCommand {
    public static void register(CraterRegisterCommandEvent event) {
        CraterCommand cmd = CraterCommand.literal((String)"reloadembeds").requiresPermission(4).withNode("sdlink.reloadembeds").execute(ctx -> {
            EmbedManager.init();
            ctx.sendSuccess(() -> Text.literal((String)"Reloaded Embeds"), false);
            return 1;
        });
        event.registerCommand(cmd);
    }
}

