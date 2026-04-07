/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands;

import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public enum OptionType {
    UNKNOWN(-1),
    SUB_COMMAND(1),
    SUB_COMMAND_GROUP(2),
    STRING(3, true),
    INTEGER(4, true),
    BOOLEAN(5),
    USER(6),
    CHANNEL(7),
    ROLE(8),
    MENTIONABLE(9),
    NUMBER(10, true),
    ATTACHMENT(11);

    private final int raw;
    private final boolean supportsChoices;

    private OptionType(int raw) {
        this(raw, false);
    }

    private OptionType(int raw, boolean supportsChoices) {
        this.raw = raw;
        this.supportsChoices = supportsChoices;
    }

    public int getKey() {
        return this.raw;
    }

    public boolean canSupportChoices() {
        return this.supportsChoices;
    }

    @Nonnull
    public static OptionType fromKey(int key) {
        for (OptionType type : OptionType.values()) {
            if (type.raw != key) continue;
            return type;
        }
        return UNKNOWN;
    }
}

