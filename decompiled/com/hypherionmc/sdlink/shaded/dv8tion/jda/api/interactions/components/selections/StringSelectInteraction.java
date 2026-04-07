/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.SelectMenuInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.SelectOption;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.List;
import org.jetbrains.annotations.Unmodifiable;

public interface StringSelectInteraction
extends SelectMenuInteraction<String, StringSelectMenu> {
    @Override
    @Nonnull
    public @Unmodifiable List<String> getValues();

    @Nonnull
    default public @Unmodifiable List<SelectOption> getSelectedOptions() {
        StringSelectMenu menu = (StringSelectMenu)this.getComponent();
        List<String> values = this.getValues();
        return menu.getOptions().stream().filter(it -> values.contains(it.getValue())).collect(Helpers.toUnmodifiableList());
    }
}

