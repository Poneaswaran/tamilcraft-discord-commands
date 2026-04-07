/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.GuildVoiceState;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.CommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.Commands;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.ContextMenu;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.MessageContextMenuEvent;

public abstract class MessageContextMenu
extends ContextMenu {
    public final void run(MessageContextMenuEvent event) {
        if (this.ownerCommand && !event.isOwner()) {
            this.terminate(event, null);
            return;
        }
        if (this.cooldown > 0 && !event.isOwner()) {
            Permission[] key = this.getCooldownKey(event);
            int remaining = event.getClient().getRemainingCooldown((String)key);
            if (remaining > 0) {
                this.terminate(event, this.getCooldownError(event, remaining, event.getClient()));
                return;
            }
            event.getClient().applyCooldown((String)key, this.cooldown);
        }
        if (event.isFromGuild()) {
            if (this.alwaysRespectUserPermissions) {
                for (Permission p : this.userPermissions) {
                    if (event.getMember() == null) continue;
                    if (p.isChannel()) {
                        if (event.getMember().hasPermission(event.getGuildChannel(), p)) continue;
                        this.terminate(event, String.format("%s%s%s", event.getClient().getError(), p.getName(), "channel"));
                        return;
                    }
                    if (event.getMember().hasPermission(p)) continue;
                    this.terminate(event, String.format("%s%s%s", event.getClient().getError(), p.getName(), "server"));
                    return;
                }
            }
            for (Permission p : this.botPermissions) {
                Member selfMember;
                if (p == Permission.VIEW_CHANNEL || p == Permission.MESSAGE_EMBED_LINKS) continue;
                Member member = selfMember = event.getGuild() == null ? null : event.getGuild().getSelfMember();
                if (p.isChannel()) {
                    if (p.name().startsWith("VOICE")) {
                        AudioChannelUnion vc;
                        GuildVoiceState gvc = event.getMember().getVoiceState();
                        AudioChannelUnion audioChannelUnion = vc = gvc == null ? null : gvc.getChannel();
                        if (vc == null) {
                            this.terminate(event, event.getClient().getError() + " You must be in a voice channel to use that!");
                            return;
                        }
                        if (selfMember.hasPermission((GuildChannel)vc, p)) continue;
                        this.terminate(event, String.format("%s%s%s", event.getClient().getError(), p.getName(), "voice channel"));
                        return;
                    }
                    if (selfMember.hasPermission(event.getGuildChannel(), p)) continue;
                    this.terminate(event, String.format("%s%s%s", event.getClient().getError(), p.getName(), "channel"));
                    return;
                }
                if (selfMember.hasPermission(p)) continue;
                this.terminate(event, String.format("%s%s%s", event.getClient().getError(), p.getName(), "server"));
                return;
            }
        }
        try {
            this.execute(event);
        }
        catch (Throwable t) {
            if (event.getClient().getListener() != null) {
                event.getClient().getListener().onMessageContextMenuException(event, this, t);
                return;
            }
            throw t;
        }
        if (event.getClient().getListener() != null) {
            event.getClient().getListener().onCompletedMessageContextMenu(event, this);
        }
    }

    protected abstract void execute(MessageContextMenuEvent var1);

    private void terminate(MessageContextMenuEvent event, String message) {
        if (message != null) {
            event.reply(message).setEphemeral(true).queue();
        }
        if (event.getClient().getListener() != null) {
            event.getClient().getListener().onTerminatedMessageContextMenu(event, this);
        }
    }

    @Override
    public CommandData buildCommandData() {
        CommandData data = Commands.message(this.getName());
        if (this.userPermissions == null) {
            data.setDefaultPermissions(DefaultMemberPermissions.DISABLED);
        } else {
            data.setDefaultPermissions(DefaultMemberPermissions.enabledFor(this.userPermissions));
        }
        data.setGuildOnly(this.guildOnly);
        return data;
    }
}

