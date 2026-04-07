/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class SlashCommandInteractionEvent
extends GenericCommandInteractionEvent
implements SlashCommandInteraction {
    private final SlashCommandInteraction interaction;

    public SlashCommandInteractionEvent(@Nonnull JDA api, long responseNumber, @Nonnull SlashCommandInteraction interaction) {
        super(api, responseNumber, interaction);
        this.interaction = interaction;
    }

    @Override
    @Nonnull
    public SlashCommandInteraction getInteraction() {
        return this.interaction;
    }

    @Override
    @Nonnull
    public MessageChannelUnion getChannel() {
        return this.interaction.getChannel();
    }
}

