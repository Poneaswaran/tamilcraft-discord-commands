/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.CommandInteractionPayload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.OptionMapping;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.List;

public interface CommandInteractionPayloadMixin
extends CommandInteractionPayload {
    public CommandInteractionPayload getCommandPayload();

    @Override
    @Nonnull
    default public Command.Type getCommandType() {
        return this.getCommandPayload().getCommandType();
    }

    @Override
    @Nonnull
    default public String getName() {
        return this.getCommandPayload().getName();
    }

    @Override
    @Nullable
    default public String getSubcommandName() {
        return this.getCommandPayload().getSubcommandName();
    }

    @Override
    @Nullable
    default public String getSubcommandGroup() {
        return this.getCommandPayload().getSubcommandGroup();
    }

    @Override
    default public long getCommandIdLong() {
        return this.getCommandPayload().getCommandIdLong();
    }

    @Override
    @Nonnull
    default public List<OptionMapping> getOptions() {
        return this.getCommandPayload().getOptions();
    }

    @Override
    default public boolean isGuildCommand() {
        return this.getCommandPayload().isGuildCommand();
    }
}

