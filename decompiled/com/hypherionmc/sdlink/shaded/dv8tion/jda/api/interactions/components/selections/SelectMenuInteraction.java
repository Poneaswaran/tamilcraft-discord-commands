/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ActionRow;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ComponentInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ItemComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public interface SelectMenuInteraction<T, S extends SelectMenu>
extends ComponentInteraction {
    @Nonnull
    public S getComponent();

    @Nonnull
    default public S getSelectMenu() {
        return (S)this.getComponent();
    }

    @Nonnull
    public List<T> getValues();

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> editSelectMenu(@Nullable SelectMenu newMenu) {
        Message message = this.getMessage();
        ArrayList<ActionRow> components = new ArrayList<ActionRow>(message.getActionRows());
        LayoutComponent.updateComponent(components, this.getComponentId(), (ItemComponent)newMenu);
        if (this.isAcknowledged()) {
            return this.getHook().editMessageComponentsById(message.getId(), components).map(it -> null);
        }
        return this.editComponents(components).map(it -> null);
    }
}

