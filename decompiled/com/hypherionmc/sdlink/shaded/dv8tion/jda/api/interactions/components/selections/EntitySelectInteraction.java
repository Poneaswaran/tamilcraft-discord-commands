/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Mentions;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.SelectMenuInteraction;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface EntitySelectInteraction
extends SelectMenuInteraction<IMentionable, EntitySelectMenu> {
    @Nonnull
    public Mentions getMentions();
}

