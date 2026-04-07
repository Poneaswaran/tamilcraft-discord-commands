/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.DiscordLocale;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.CommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.Commands;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CommandClient;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CooldownScope;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.Interaction;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.MessageContextMenu;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.UserContextMenu;
import java.util.HashMap;
import java.util.Map;

public abstract class ContextMenu
extends Interaction {
    protected String name = "null";
    protected Map<DiscordLocale, String> nameLocalization = new HashMap<DiscordLocale, String>();
    protected boolean alwaysRespectUserPermissions = false;

    public String getName() {
        return this.name;
    }

    public Map<DiscordLocale, String> getNameLocalization() {
        return this.nameLocalization;
    }

    public Command.Type getType() {
        if (this instanceof MessageContextMenu) {
            return Command.Type.MESSAGE;
        }
        if (this instanceof UserContextMenu) {
            return Command.Type.USER;
        }
        return Command.Type.UNKNOWN;
    }

    public String getCooldownKey(GenericCommandInteractionEvent event) {
        switch (this.cooldownScope) {
            case USER: {
                return this.cooldownScope.genKey(this.name, event.getUser().getIdLong());
            }
            case USER_GUILD: {
                return event.getGuild() != null ? this.cooldownScope.genKey(this.name, event.getUser().getIdLong(), event.getGuild().getIdLong()) : CooldownScope.USER_CHANNEL.genKey(this.name, event.getUser().getIdLong(), event.getChannel().getIdLong());
            }
            case USER_CHANNEL: {
                return this.cooldownScope.genKey(this.name, event.getUser().getIdLong(), event.getChannel().getIdLong());
            }
            case GUILD: {
                return event.getGuild() != null ? this.cooldownScope.genKey(this.name, event.getGuild().getIdLong()) : CooldownScope.CHANNEL.genKey(this.name, event.getChannel().getIdLong());
            }
            case CHANNEL: {
                return this.cooldownScope.genKey(this.name, event.getChannel().getIdLong());
            }
            case SHARD: {
                return event.getJDA().getShardInfo() != JDA.ShardInfo.SINGLE ? this.cooldownScope.genKey(this.name, event.getJDA().getShardInfo().getShardId()) : CooldownScope.GLOBAL.genKey(this.name, 0L);
            }
            case USER_SHARD: {
                return event.getJDA().getShardInfo() != JDA.ShardInfo.SINGLE ? this.cooldownScope.genKey(this.name, event.getUser().getIdLong(), event.getJDA().getShardInfo().getShardId()) : CooldownScope.USER.genKey(this.name, event.getUser().getIdLong());
            }
            case GLOBAL: {
                return this.cooldownScope.genKey(this.name, 0L);
            }
        }
        return "";
    }

    public String getCooldownError(GenericCommandInteractionEvent event, int remaining, CommandClient client) {
        if (remaining <= 0) {
            return null;
        }
        String front = client.getWarning() + " That command is on cooldown for " + remaining + " more seconds";
        if (this.cooldownScope.equals((Object)CooldownScope.USER)) {
            return front + "!";
        }
        if (this.cooldownScope.equals((Object)CooldownScope.USER_GUILD) && event.getGuild() == null) {
            return front + " " + CooldownScope.USER_CHANNEL.errorSpecification + "!";
        }
        if (this.cooldownScope.equals((Object)CooldownScope.GUILD) && event.getGuild() == null) {
            return front + " " + CooldownScope.CHANNEL.errorSpecification + "!";
        }
        return front + " " + this.cooldownScope.errorSpecification + "!";
    }

    public CommandData buildCommandData() {
        CommandData data = Commands.context(this.getType(), this.name);
        if (this.userPermissions == null) {
            data.setDefaultPermissions(DefaultMemberPermissions.DISABLED);
        } else {
            data.setDefaultPermissions(DefaultMemberPermissions.enabledFor(this.userPermissions));
        }
        data.setGuildOnly(this.guildOnly);
        if (!this.getNameLocalization().isEmpty()) {
            data.setNameLocalizations(this.getNameLocalization());
        }
        return data;
    }
}

