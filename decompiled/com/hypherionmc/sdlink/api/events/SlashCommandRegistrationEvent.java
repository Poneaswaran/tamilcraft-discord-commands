/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.core.event.CraterEvent
 *  lombok.Generated
 */
package com.hypherionmc.sdlink.api.events;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.sdlink.core.discord.commands.slash.SDLinkSlashCommand;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class SlashCommandRegistrationEvent
extends CraterEvent {
    private final List<SDLinkSlashCommand> commands = new ArrayList<SDLinkSlashCommand>();

    public void addCommand(SDLinkSlashCommand command) {
        this.commands.add(command);
    }

    @Generated
    public List<SDLinkSlashCommand> getCommands() {
        return this.commands;
    }
}

