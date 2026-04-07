/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ActionComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.buttons.Button;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ComponentsUtil {
    public static boolean isSameIdentifier(@Nonnull ActionComponent component, @Nonnull String identifier) {
        if (identifier.equals(component.getId())) {
            return true;
        }
        if (component instanceof Button) {
            Button button = (Button)component;
            if (identifier.equals(button.getUrl())) {
                return true;
            }
            if (button.getSku() != null) {
                return identifier.equals(button.getSku().getId());
            }
        }
        return false;
    }
}

