/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.SerializableData;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface Component
extends SerializableData {
    @Nonnull
    public Type getType();

    public boolean isMessageCompatible();

    public boolean isModalCompatible();

    public static enum Type {
        UNKNOWN(-1, 0, false, false),
        ACTION_ROW(1, 0, true, true),
        BUTTON(2, 5, true, false),
        STRING_SELECT(3, 1, true, false),
        TEXT_INPUT(4, 1, false, true),
        USER_SELECT(5, 1, true, false),
        ROLE_SELECT(6, 1, true, false),
        MENTIONABLE_SELECT(7, 1, true, false),
        CHANNEL_SELECT(8, 1, true, false);

        private final int key;
        private final int maxPerRow;
        private final boolean messageCompatible;
        private final boolean modalCompatible;

        private Type(int key, int maxPerRow, boolean messageCompatible, boolean modalCompatible) {
            this.key = key;
            this.maxPerRow = maxPerRow;
            this.messageCompatible = messageCompatible;
            this.modalCompatible = modalCompatible;
        }

        public int getMaxPerRow() {
            return this.maxPerRow;
        }

        public int getKey() {
            return this.key;
        }

        public boolean isMessageCompatible() {
            return this.messageCompatible;
        }

        public boolean isModalCompatible() {
            return this.modalCompatible;
        }

        @Nonnull
        public static Type fromKey(int type) {
            for (Type t : Type.values()) {
                if (t.key != type) continue;
                return t;
            }
            return UNKNOWN;
        }
    }
}

