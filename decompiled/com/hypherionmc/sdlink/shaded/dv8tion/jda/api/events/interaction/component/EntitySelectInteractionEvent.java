/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.component;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Mentions;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.EntitySelectInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class EntitySelectInteractionEvent
extends GenericSelectMenuInteractionEvent<IMentionable, EntitySelectMenu>
implements EntitySelectInteraction {
    private final EntitySelectInteraction interaction;

    public EntitySelectInteractionEvent(@Nonnull JDA api, long responseNumber, @Nonnull EntitySelectInteraction interaction) {
        super(api, responseNumber, interaction);
        this.interaction = interaction;
    }

    @Override
    @Nonnull
    public EntitySelectInteraction getInteraction() {
        return this.interaction;
    }

    @Override
    @Nonnull
    public Mentions getMentions() {
        return this.interaction.getMentions();
    }
}

