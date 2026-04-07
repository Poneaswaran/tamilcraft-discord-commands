/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ActionComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface SelectMenu
extends ActionComponent {
    public static final int ID_MAX_LENGTH = 100;
    public static final int PLACEHOLDER_MAX_LENGTH = 100;
    public static final int OPTIONS_MAX_AMOUNT = 25;

    @Nullable
    public String getPlaceholder();

    public int getMinValues();

    public int getMaxValues();

    @Nonnull
    @CheckReturnValue
    public Builder<? extends SelectMenu, ? extends Builder<?, ?>> createCopy();

    public static abstract class Builder<T extends SelectMenu, B extends Builder<T, B>> {
        protected String customId;
        protected String placeholder;
        protected int minValues = 1;
        protected int maxValues = 1;
        protected boolean disabled = false;

        protected Builder(@Nonnull String customId) {
            this.setId(customId);
        }

        @Nonnull
        public B setId(@Nonnull String customId) {
            Checks.notEmpty(customId, "Component ID");
            Checks.notLonger(customId, 100, "Component ID");
            this.customId = customId;
            return (B)this;
        }

        @Nonnull
        public B setPlaceholder(@Nullable String placeholder) {
            if (placeholder != null) {
                Checks.notEmpty(placeholder, "Placeholder");
                Checks.notLonger(placeholder, 100, "Placeholder");
            }
            this.placeholder = placeholder;
            return (B)this;
        }

        @Nonnull
        public B setMinValues(int minValues) {
            Checks.notNegative(minValues, "Min Values");
            Checks.check(minValues <= 25, "Min Values may not be greater than %d! Provided: %d", 25, minValues);
            this.minValues = minValues;
            return (B)this;
        }

        @Nonnull
        public B setMaxValues(int maxValues) {
            Checks.positive(maxValues, "Max Values");
            Checks.check(maxValues <= 25, "Max Values may not be greater than %d! Provided: %d", 25, maxValues);
            this.maxValues = maxValues;
            return (B)this;
        }

        @Nonnull
        public B setRequiredRange(int min, int max) {
            Checks.check(min <= max, "Min Values should be less than or equal to Max Values! Provided: [%d, %d]", min, max);
            return ((Builder)this.setMinValues(min)).setMaxValues(max);
        }

        @Nonnull
        public B setDisabled(boolean disabled) {
            this.disabled = disabled;
            return (B)this;
        }

        @Nonnull
        public String getId() {
            return this.customId;
        }

        @Nullable
        public String getPlaceholder() {
            return this.placeholder;
        }

        public int getMinValues() {
            return this.minValues;
        }

        public int getMaxValues() {
            return this.maxValues;
        }

        public boolean isDisabled() {
            return this.disabled;
        }

        @Nonnull
        public abstract T build();
    }
}

