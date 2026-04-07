/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.core.event.CraterEvent
 *  com.hypherionmc.craterlib.core.event.CraterEventBus
 *  lombok.Generated
 */
package com.hypherionmc.sdlink.core.discord.commands;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.sdlink.api.events.SlashCommandRegistrationEvent;
import com.hypherionmc.sdlink.core.discord.commands.slash.general.HelpSlashCommand;
import com.hypherionmc.sdlink.core.discord.commands.slash.general.PlayerListSlashCommand;
import com.hypherionmc.sdlink.core.discord.commands.slash.general.ServerStatusSlashCommand;
import com.hypherionmc.sdlink.core.discord.commands.slash.hide.HiddenPlayersCommand;
import com.hypherionmc.sdlink.core.discord.commands.slash.hide.HidePlayerCommand;
import com.hypherionmc.sdlink.core.discord.commands.slash.hide.UnhidePlayerCommand;
import com.hypherionmc.sdlink.core.discord.commands.slash.setup.ReloadCacheCommand;
import com.hypherionmc.sdlink.core.discord.commands.slash.setup.SetChannelCommand;
import com.hypherionmc.sdlink.core.discord.commands.slash.verification.StaffUnverifyCommand;
import com.hypherionmc.sdlink.core.discord.commands.slash.verification.StaffVerifyAccountCommand;
import com.hypherionmc.sdlink.core.discord.commands.slash.verification.UnverifyAccountSlashCommand;
import com.hypherionmc.sdlink.core.discord.commands.slash.verification.VerifyAccountCommand;
import com.hypherionmc.sdlink.core.discord.commands.slash.verification.ViewVerifiedAccounts;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CommandClient;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.SlashCommand;
import java.util.HashSet;
import java.util.Set;
import lombok.Generated;

public final class CommandManager {
    public static final CommandManager INSTANCE = new CommandManager();
    private final Set<SlashCommand> commands = new HashSet<SlashCommand>();

    private CommandManager() {
        this.addCommands();
    }

    private void addCommands() {
        this.commands.clear();
        this.commands.add(new VerifyAccountCommand());
        this.commands.add(new UnverifyAccountSlashCommand());
        this.commands.add(new StaffUnverifyCommand());
        this.commands.add(new StaffVerifyAccountCommand());
        this.commands.add(new ViewVerifiedAccounts());
        this.commands.add(new ServerStatusSlashCommand());
        this.commands.add(new PlayerListSlashCommand());
        this.commands.add(new HelpSlashCommand());
        this.commands.add(new SetChannelCommand());
        this.commands.add(new ReloadCacheCommand());
        this.commands.add(new HidePlayerCommand());
        this.commands.add(new HiddenPlayersCommand());
        this.commands.add(new UnhidePlayerCommand());
    }

    public void register(CommandClient client) {
        SlashCommandRegistrationEvent event = new SlashCommandRegistrationEvent();
        CraterEventBus.INSTANCE.postEvent((CraterEvent)event);
        this.commands.addAll(event.getCommands());
        this.commands.forEach(client::addSlashCommand);
    }

    @Generated
    public Set<SlashCommand> getCommands() {
        return this.commands;
    }
}

