/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.buttons;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ActionRow;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ComponentInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ItemComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.buttons.Button;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.ArrayList;

public interface ButtonInteraction
extends ComponentInteraction {
    @Override
    @Nonnull
    default public Button getComponent() {
        return this.getButton();
    }

    @Nonnull
    public Button getButton();

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> editButton(@Nullable Button newButton) {
        Message message = this.getMessage();
        ArrayList<ActionRow> components = new ArrayList<ActionRow>(message.getActionRows());
        LayoutComponent.updateComponent(components, this.getComponentId(), (ItemComponent)newButton);
        if (this.isAcknowledged()) {
            return this.getHook().editMessageComponentsById(message.getId(), components).map(it -> null);
        }
        return this.editComponents(components).map(it -> null);
    }
}

