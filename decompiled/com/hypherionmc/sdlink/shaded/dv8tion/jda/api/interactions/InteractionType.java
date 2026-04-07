/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions;

import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public enum InteractionType {
    UNKNOWN(-1),
    PING(1),
    COMMAND(2),
    COMPONENT(3),
    COMMAND_AUTOCOMPLETE(4),
    MODAL_SUBMIT(5);

    private final int key;

    private InteractionType(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    @Nonnull
    @CheckReturnValue
    public static InteractionType fromKey(int key) {
        switch (key) {
            case 1: {
                return PING;
            }
            case 2: {
                return COMMAND;
            }
            case 3: {
                return COMPONENT;
            }
            case 4: {
                return COMMAND_AUTOCOMPLETE;
            }
            case 5: {
                return MODAL_SUBMIT;
            }
        }
        return UNKNOWN;
    }
}

