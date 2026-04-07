/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.Interaction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks.IAutoCompleteCallback;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.AutoCompleteCallbackAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collection;

public class GenericAutoCompleteInteractionEvent
extends GenericInteractionCreateEvent
implements IAutoCompleteCallback {
    public GenericAutoCompleteInteractionEvent(@Nonnull JDA api, long responseNumber, @Nonnull Interaction interaction) {
        super(api, responseNumber, interaction);
    }

    @Override
    @Nonnull
    public IAutoCompleteCallback getInteraction() {
        return (IAutoCompleteCallback)super.getInteraction();
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public AutoCompleteCallbackAction replyChoices(@Nonnull Collection<Command.Choice> choices) {
        return this.getInteraction().replyChoices(choices);
    }
}

