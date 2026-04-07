/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.component;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.SelectMenuInteraction;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.List;

public class GenericSelectMenuInteractionEvent<T, S extends SelectMenu>
extends GenericComponentInteractionCreateEvent
implements SelectMenuInteraction<T, S> {
    private final SelectMenuInteraction<T, S> menuInteraction;

    public GenericSelectMenuInteractionEvent(@Nonnull JDA api, long responseNumber, @Nonnull SelectMenuInteraction<T, S> interaction) {
        super(api, responseNumber, interaction);
        this.menuInteraction = interaction;
    }

    @Override
    @Nonnull
    public SelectMenuInteraction<T, S> getInteraction() {
        return this.menuInteraction;
    }

    @Override
    @Nonnull
    public S getComponent() {
        return (S)this.menuInteraction.getComponent();
    }

    @Override
    @Nonnull
    public List<T> getValues() {
        return this.menuInteraction.getValues();
    }
}

