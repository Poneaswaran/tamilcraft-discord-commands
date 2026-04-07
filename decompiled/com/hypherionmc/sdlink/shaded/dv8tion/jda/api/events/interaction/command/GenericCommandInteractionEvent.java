/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.InteractionHook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.CommandInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.OptionMapping;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.modals.Modal;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.ModalCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.PremiumRequiredCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.List;

public class GenericCommandInteractionEvent
extends GenericInteractionCreateEvent
implements CommandInteraction {
    public GenericCommandInteractionEvent(@Nonnull JDA api, long responseNumber, @Nonnull CommandInteraction interaction) {
        super(api, responseNumber, interaction);
    }

    @Override
    @Nonnull
    public CommandInteraction getInteraction() {
        return (CommandInteraction)super.getInteraction();
    }

    @Override
    @Nonnull
    public Command.Type getCommandType() {
        return this.getInteraction().getCommandType();
    }

    @Override
    @Nonnull
    public String getName() {
        return this.getInteraction().getName();
    }

    @Override
    @Nullable
    public String getSubcommandName() {
        return this.getInteraction().getSubcommandName();
    }

    @Override
    @Nullable
    public String getSubcommandGroup() {
        return this.getInteraction().getSubcommandGroup();
    }

    @Override
    public long getCommandIdLong() {
        return this.getInteraction().getCommandIdLong();
    }

    @Override
    public boolean isGuildCommand() {
        return this.getInteraction().isGuildCommand();
    }

    @Override
    @Nonnull
    public List<OptionMapping> getOptions() {
        return this.getInteraction().getOptions();
    }

    @Override
    @Nonnull
    public InteractionHook getHook() {
        return this.getInteraction().getHook();
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public ReplyCallbackAction deferReply() {
        return this.getInteraction().deferReply();
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public ModalCallbackAction replyModal(@Nonnull Modal modal) {
        return this.getInteraction().replyModal(modal);
    }

    @Override
    @Nonnull
    @Deprecated
    @CheckReturnValue
    public PremiumRequiredCallbackAction replyWithPremiumRequired() {
        return this.getInteraction().replyWithPremiumRequired();
    }
}

