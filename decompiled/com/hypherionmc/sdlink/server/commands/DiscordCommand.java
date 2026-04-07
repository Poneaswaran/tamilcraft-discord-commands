/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.api.commands.CraterCommand
 *  com.hypherionmc.craterlib.api.events.server.CraterRegisterCommandEvent
 */
package com.hypherionmc.sdlink.server.commands;

import com.hypherionmc.craterlib.api.commands.CraterCommand;
import com.hypherionmc.craterlib.api.events.server.CraterRegisterCommandEvent;
import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.util.SDLinkChatUtils;

public final class DiscordCommand {
    public static void register(CraterRegisterCommandEvent event) {
        CraterCommand cmd = CraterCommand.literal((String)"discord").requiresPermission(0).withNode("sdlink.discordinvite").execute(ctx -> {
            if (SDLinkConfig.INSTANCE.botConfig.invite.inviteLink != null && !SDLinkConfig.INSTANCE.botConfig.invite.inviteLink.isEmpty()) {
                String invite = SDLinkConfig.INSTANCE.botConfig.invite.inviteMessage.replace("%inviteurl%", SDLinkConfig.INSTANCE.botConfig.invite.inviteLink);
                ctx.sendSuccess(() -> SDLinkChatUtils.parseChatLinks(invite), false);
            }
            return 1;
        });
        event.registerCommand(cmd);
    }
}

