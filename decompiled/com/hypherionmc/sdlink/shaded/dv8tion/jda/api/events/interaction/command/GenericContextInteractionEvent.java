/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.context.ContextInteraction;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GenericContextInteractionEvent<T>
extends GenericCommandInteractionEvent
implements ContextInteraction<T> {
    public GenericContextInteractionEvent(@Nonnull JDA api, long responseNumber, @Nonnull ContextInteraction<T> interaction) {
        super(api, responseNumber, interaction);
    }

    @Override
    @Nonnull
    public ContextInteraction<T> getInteraction() {
        return (ContextInteraction)super.getInteraction();
    }

    @Override
    @Nonnull
    public ContextInteraction.ContextTarget getTargetType() {
        return this.getInteraction().getTargetType();
    }

    @Override
    @Nonnull
    public T getTarget() {
        return this.getInteraction().getTarget();
    }
}

