/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command.GenericContextInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.context.MessageContextInteraction;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class MessageContextInteractionEvent
extends GenericContextInteractionEvent<Message>
implements MessageContextInteraction {
    public MessageContextInteractionEvent(@Nonnull JDA api, long responseNumber, @Nonnull MessageContextInteraction interaction) {
        super(api, responseNumber, interaction);
    }

    @Override
    @Nonnull
    public MessageContextInteraction getInteraction() {
        return (MessageContextInteraction)super.getInteraction();
    }

    @Override
    @Nullable
    public MessageChannelUnion getChannel() {
        return this.getInteraction().getChannel();
    }
}

