/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.Component;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.SelectMenuInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component.ComponentInteractionImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public abstract class SelectMenuInteractionImpl<T, S extends SelectMenu>
extends ComponentInteractionImpl
implements SelectMenuInteraction<T, S> {
    private final S menu;

    public SelectMenuInteractionImpl(JDAImpl jda, Class<S> type, DataObject data) {
        super(jda, data);
        this.menu = this.message != null ? (SelectMenu)this.message.getActionRows().stream().flatMap(row -> row.getComponents().stream()).filter(type::isInstance).map(type::cast).filter(c -> this.customId.equals(c.getId())).findFirst().orElse(null) : null;
    }

    @Override
    @Nonnull
    public S getComponent() {
        return this.menu;
    }

    @Override
    @Nonnull
    public Component.Type getComponentType() {
        return this.menu.getType();
    }
}

