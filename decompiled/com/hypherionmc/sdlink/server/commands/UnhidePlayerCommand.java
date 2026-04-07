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
import com.hypherionmc.sdlink.api.messaging.Result;
import com.hypherionmc.sdlink.core.managers.HiddenPlayersManager;
import com.hypherionmc.sdlink.util.translations.SDText;

public final class UnhidePlayerCommand {
    public static void register(CraterRegisterCommandEvent event) {
        CraterCommand command = CraterCommand.literal((String)"unhideplayer").requiresPermission(4).withNode("sdlink.unmuteplayer").withGameProfilesArgument("username", (player, profiles, ctx) -> {
            if (profiles.isEmpty()) {
                ctx.sendSuccess(() -> Text.literal((String)SDText.translate("error.hiding.unhide_no_account_provided").toString()), true);
                return 1;
            }
            CraterGameProfile profile = (CraterGameProfile)profiles.get(0);
            Result result = HiddenPlayersManager.INSTANCE.unhidePlayer(profile.getId().toString());
            ctx.sendSuccess(() -> Text.literal((String)result.getMessage()), true);
            return 1;
        });
        event.registerCommand(command);
    }
}

