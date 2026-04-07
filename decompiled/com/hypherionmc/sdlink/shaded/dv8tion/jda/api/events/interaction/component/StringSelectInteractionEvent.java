/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.component;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.StringSelectInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class StringSelectInteractionEvent
extends GenericSelectMenuInteractionEvent<String, StringSelectMenu>
implements StringSelectInteraction {
    private final StringSelectInteraction interaction;

    public StringSelectInteractionEvent(@Nonnull JDA api, long responseNumber, @Nonnull StringSelectInteraction interaction) {
        super(api, responseNumber, interaction);
        this.interaction = interaction;
    }

    @Override
    @Nonnull
    public StringSelectInteraction getInteraction() {
        return this.interaction;
    }
}

