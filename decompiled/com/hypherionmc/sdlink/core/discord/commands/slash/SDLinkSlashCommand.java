/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.core.discord.commands.slash;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.SlashCommand;

public abstract class SDLinkSlashCommand
extends SlashCommand {
    public SDLinkSlashCommand(boolean requiresPerms) {
        this.guildOnly = true;
        if (requiresPerms) {
            this.userPermissions = new Permission[]{Permission.MANAGE_SERVER};
        }
    }
}

