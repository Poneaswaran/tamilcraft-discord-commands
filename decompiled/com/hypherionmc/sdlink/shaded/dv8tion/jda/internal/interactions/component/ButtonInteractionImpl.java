/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.Component;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.buttons.Button;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component.ComponentInteractionImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ButtonInteractionImpl
extends ComponentInteractionImpl
implements ButtonInteraction {
    private final Button button;

    public ButtonInteractionImpl(JDAImpl jda, DataObject data) {
        super(jda, data);
        this.button = this.message != null ? this.message.getButtonById(this.customId) : null;
    }

    @Override
    @Nonnull
    public Component.Type getComponentType() {
        return Component.Type.BUTTON;
    }

    @Override
    @Nonnull
    public Button getButton() {
        return this.button;
    }
}

