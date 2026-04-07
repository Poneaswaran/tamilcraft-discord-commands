/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.buttons;

import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public enum ButtonStyle {
    UNKNOWN(-1),
    PRIMARY(1),
    SECONDARY(2),
    SUCCESS(3),
    DANGER(4),
    LINK(5),
    PREMIUM(6);

    private final int key;

    private ButtonStyle(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    @Nonnull
    public static ButtonStyle fromKey(int key) {
        for (ButtonStyle style : ButtonStyle.values()) {
            if (style.key != key) continue;
            return style;
        }
        return UNKNOWN;
    }
}

