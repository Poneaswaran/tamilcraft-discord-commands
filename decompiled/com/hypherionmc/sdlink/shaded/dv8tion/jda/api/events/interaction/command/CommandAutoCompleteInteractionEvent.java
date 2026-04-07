/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.GenericAutoCompleteInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.AutoCompleteQuery;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.OptionMapping;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.AutoCompleteCallbackAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public class CommandAutoCompleteInteractionEvent
extends GenericAutoCompleteInteractionEvent
implements CommandAutoCompleteInteraction {
    private final CommandAutoCompleteInteraction interaction;

    public CommandAutoCompleteInteractionEvent(@Nonnull JDA api, long responseNumber, @Nonnull CommandAutoCompleteInteraction interaction) {
        super(api, responseNumber, interaction);
        this.interaction = interaction;
    }

    @Override
    @Nonnull
    public CommandAutoCompleteInteraction getInteraction() {
        return this.interaction;
    }

    @Override
    @Nonnull
    public AutoCompleteQuery getFocusedOption() {
        return this.interaction.getFocusedOption();
    }

    @Override
    @Nonnull
    public Command.Type getCommandType() {
        return this.interaction.getCommandType();
    }

    @Override
    @Nonnull
    public String getName() {
        return this.interaction.getName();
    }

    @Override
    @Nullable
    public String getSubcommandName() {
        return this.interaction.getSubcommandName();
    }

    @Override
    @Nullable
    public String getSubcommandGroup() {
        return this.interaction.getSubcommandGroup();
    }

    @Override
    public long getCommandIdLong() {
        return this.interaction.getCommandIdLong();
    }

    @Override
    public boolean isGuildCommand() {
        return this.interaction.isGuildCommand();
    }

    @Override
    @Nonnull
    public List<OptionMapping> getOptions() {
        return this.interaction.getOptions();
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public AutoCompleteCallbackAction replyChoices(@Nonnull Collection<Command.Choice> choices) {
        return this.interaction.replyChoices(choices);
    }

    @Override
    @Nonnull
    public MessageChannelUnion getChannel() {
        return this.interaction.getChannel();
    }
}

