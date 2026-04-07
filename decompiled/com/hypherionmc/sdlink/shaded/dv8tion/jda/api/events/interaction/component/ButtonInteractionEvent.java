/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.component;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.buttons.Button;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ButtonInteractionEvent
extends GenericComponentInteractionCreateEvent
implements ButtonInteraction {
    private final ButtonInteraction interaction;

    public ButtonInteractionEvent(@Nonnull JDA api, long responseNumber, @Nonnull ButtonInteraction interaction) {
        super(api, responseNumber, interaction);
        this.interaction = interaction;
    }

    @Override
    @Nonnull
    public ButtonInteraction getInteraction() {
        return this.interaction;
    }

    @Override
    @Nonnull
    public Button getComponent() {
        return this.interaction.getComponent();
    }

    @Override
    @Nonnull
    public Button getButton() {
        return this.interaction.getButton();
    }
}

