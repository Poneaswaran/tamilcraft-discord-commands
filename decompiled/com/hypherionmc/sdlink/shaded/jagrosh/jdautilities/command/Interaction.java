/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CooldownScope;

public abstract class Interaction {
    protected boolean guildOnly = true;
    protected Permission[] userPermissions = new Permission[0];
    protected Permission[] botPermissions = new Permission[0];
    protected boolean alwaysRespectUserPermissions = true;
    protected boolean ownerCommand = false;
    protected int cooldown = 0;
    protected CooldownScope cooldownScope = CooldownScope.USER;
    protected String botMissingPermMessage = "%s I need the %s permission in this %s!";
    protected String userMissingPermMessage = "%s You must have the %s permission in this %s to use that!";

    public int getCooldown() {
        return this.cooldown;
    }

    public CooldownScope getCooldownScope() {
        return this.cooldownScope;
    }

    public Permission[] getUserPermissions() {
        return this.userPermissions;
    }

    public Permission[] getBotPermissions() {
        return this.botPermissions;
    }

    public boolean isOwnerCommand() {
        return this.ownerCommand;
    }
}

