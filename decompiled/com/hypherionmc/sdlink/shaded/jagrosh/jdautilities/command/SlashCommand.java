/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.annotations.ForRemoval;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.GuildVoiceState;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.DiscordLocale;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.CommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.Commands;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.OptionData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.Command;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CommandClient;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CommandEvent;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CooldownScope;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.SlashCommandEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SlashCommand
extends Command {
    protected Map<DiscordLocale, String> nameLocalization = new HashMap<DiscordLocale, String>();
    protected Map<DiscordLocale, String> descriptionLocalization = new HashMap<DiscordLocale, String>();
    @Deprecated
    protected String requiredRole = null;
    protected boolean alwaysRespectUserPermissions = false;
    protected SlashCommand[] children = new SlashCommand[0];
    protected SubcommandGroupData subcommandGroup = null;
    protected List<OptionData> options = new ArrayList<OptionData>();
    protected CommandClient client;

    protected abstract void execute(SlashCommandEvent var1);

    public void onAutoComplete(CommandAutoCompleteInteractionEvent event) {
    }

    @Override
    protected void execute(CommandEvent event) {
    }

    public final void run(SlashCommandEvent event) {
        this.client = event.getClient();
        if (this.ownerCommand && !this.isOwner(event, this.client)) {
            this.terminate(event, "Only an owner may run this command. Sorry.", this.client);
            return;
        }
        try {
            if (!this.isAllowed(event.getTextChannel())) {
                this.terminate(event, "That command cannot be used in this channel!", this.client);
                return;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (this.requiredRole != null && (event.getChannelType() != ChannelType.TEXT || event.getMember().getRoles().stream().noneMatch(r -> r.getName().equalsIgnoreCase(this.requiredRole)))) {
            this.terminate(event, this.client.getError() + " You must have a role called `" + this.requiredRole + "` to use that!", this.client);
            return;
        }
        if (event.getChannelType() != ChannelType.PRIVATE) {
            if (this.alwaysRespectUserPermissions) {
                for (Permission p : this.userPermissions) {
                    if (event.getMember() == null) continue;
                    if (p.isChannel()) {
                        if (event.getMember().hasPermission(event.getGuildChannel(), p)) continue;
                        this.terminate(event, String.format(this.userMissingPermMessage, this.client.getError(), p.getName(), "channel"), this.client);
                        return;
                    }
                    if (event.getMember().hasPermission(p)) continue;
                    this.terminate(event, String.format(this.userMissingPermMessage, this.client.getError(), p.getName(), "server"), this.client);
                    return;
                }
            }
            for (Permission p : this.botPermissions) {
                Member selfMember;
                if (p == Permission.VIEW_CHANNEL || p == Permission.MESSAGE_EMBED_LINKS) continue;
                Member member = selfMember = event.getGuild() == null ? null : event.getGuild().getSelfMember();
                if (p.isChannel()) {
                    if (p.isVoice()) {
                        AudioChannelUnion vc;
                        GuildVoiceState gvc = event.getMember().getVoiceState();
                        AudioChannelUnion audioChannelUnion = vc = gvc == null ? null : gvc.getChannel();
                        if (vc == null) {
                            this.terminate(event, this.client.getError() + " You must be in a voice channel to use that!", this.client);
                            return;
                        }
                        if (selfMember.hasPermission((GuildChannel)vc, p)) continue;
                        this.terminate(event, String.format(this.botMissingPermMessage, this.client.getError(), p.getName(), "voice channel"), this.client);
                        return;
                    }
                    if (selfMember.hasPermission(event.getGuildChannel(), p)) continue;
                    this.terminate(event, String.format(this.botMissingPermMessage, this.client.getError(), p.getName(), "channel"), this.client);
                    return;
                }
                if (selfMember.hasPermission(p)) continue;
                this.terminate(event, String.format(this.botMissingPermMessage, this.client.getError(), p.getName(), "server"), this.client);
                return;
            }
            if (this.nsfwOnly && event.getChannelType() == ChannelType.TEXT && !event.getTextChannel().isNSFW()) {
                this.terminate(event, "This command may only be used in NSFW text channels!", this.client);
                return;
            }
        } else if (this.guildOnly) {
            this.terminate(event, this.client.getError() + " This command cannot be used in direct messages", this.client);
            return;
        }
        if (this.cooldown > 0 && !this.isOwner(event, this.client)) {
            String key = this.getCooldownKey(event);
            int remaining = this.client.getRemainingCooldown(key);
            if (remaining > 0) {
                this.terminate(event, this.getCooldownError(event, remaining, this.client), this.client);
                return;
            }
            this.client.applyCooldown(key, this.cooldown);
        }
        try {
            this.execute(event);
        }
        catch (Throwable t) {
            if (this.client.getListener() != null) {
                this.client.getListener().onSlashCommandException(event, this, t);
                return;
            }
            throw t;
        }
        if (this.client.getListener() != null) {
            this.client.getListener().onCompletedSlashCommand(event, this);
        }
    }

    public boolean isOwner(SlashCommandEvent event, CommandClient client) {
        if (event.getUser().getId().equals(client.getOwnerId())) {
            return true;
        }
        if (client.getCoOwnerIds() == null) {
            return false;
        }
        for (String id : client.getCoOwnerIds()) {
            if (!id.equals(event.getUser().getId())) continue;
            return true;
        }
        return false;
    }

    @Deprecated
    @ForRemoval(deadline="2.0.0")
    public CommandClient getClient() {
        return this.client;
    }

    public SubcommandGroupData getSubcommandGroup() {
        return this.subcommandGroup;
    }

    public List<OptionData> getOptions() {
        return this.options;
    }

    public CommandData buildCommandData() {
        SlashCommandData data = Commands.slash(this.getName(), this.getHelp());
        if (!this.getOptions().isEmpty()) {
            data.addOptions(this.getOptions());
        }
        if (!this.getNameLocalization().isEmpty()) {
            data.setNameLocalizations((Map)this.getNameLocalization());
        }
        if (!this.getDescriptionLocalization().isEmpty()) {
            data.setDescriptionLocalizations(this.getDescriptionLocalization());
        }
        if (this.children.length != 0) {
            HashMap<String, SubcommandGroupData> groupData = new HashMap<String, SubcommandGroupData>();
            for (SlashCommand child : this.children) {
                SubcommandData subcommandData = new SubcommandData(child.getName(), child.getHelp());
                if (!child.getOptions().isEmpty()) {
                    subcommandData.addOptions(child.getOptions());
                }
                if (!child.getNameLocalization().isEmpty()) {
                    subcommandData.setNameLocalizations(child.getNameLocalization());
                }
                if (!child.getDescriptionLocalization().isEmpty()) {
                    subcommandData.setDescriptionLocalizations(child.getDescriptionLocalization());
                }
                if (child.getSubcommandGroup() != null) {
                    SubcommandGroupData group = child.getSubcommandGroup();
                    SubcommandGroupData newData = groupData.getOrDefault(group.getName(), group).addSubcommands(subcommandData);
                    groupData.put(group.getName(), newData);
                    continue;
                }
                data.addSubcommands(subcommandData);
            }
            if (!groupData.isEmpty()) {
                data.addSubcommandGroups(groupData.values());
            }
        }
        if (this.getUserPermissions() == null) {
            data.setDefaultPermissions(DefaultMemberPermissions.DISABLED);
        } else {
            data.setDefaultPermissions(DefaultMemberPermissions.enabledFor(this.getUserPermissions()));
        }
        data.setGuildOnly(this.guildOnly);
        return data;
    }

    public SlashCommand[] getChildren() {
        return this.children;
    }

    private void terminate(SlashCommandEvent event, String message, CommandClient client) {
        if (message != null) {
            event.reply(message).setEphemeral(true).queue();
        }
        if (client.getListener() != null) {
            client.getListener().onTerminatedSlashCommand(event, this);
        }
    }

    public String getCooldownKey(SlashCommandEvent event) {
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
                event.getJDA().getShardInfo();
                return this.cooldownScope.genKey(this.name, event.getJDA().getShardInfo().getShardId());
            }
            case USER_SHARD: {
                event.getJDA().getShardInfo();
                return this.cooldownScope.genKey(this.name, event.getUser().getIdLong(), event.getJDA().getShardInfo().getShardId());
            }
            case GLOBAL: {
                return this.cooldownScope.genKey(this.name, 0L);
            }
        }
        return "";
    }

    public String getCooldownError(SlashCommandEvent event, int remaining, CommandClient client) {
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

    public Map<DiscordLocale, String> getNameLocalization() {
        return this.nameLocalization;
    }

    public Map<DiscordLocale, String> getDescriptionLocalization() {
        return this.descriptionLocalization;
    }
}

