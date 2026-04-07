/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.text;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ActionComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.Component;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component.TextInputImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface TextInput
extends ActionComponent {
    public static final int MAX_VALUE_LENGTH = 4000;
    public static final int MAX_ID_LENGTH = 100;
    public static final int MAX_PLACEHOLDER_LENGTH = 100;
    public static final int MAX_LABEL_LENGTH = 45;

    @Nonnull
    public TextInputStyle getStyle();

    @Override
    @Nonnull
    public String getId();

    @Nonnull
    public String getLabel();

    public int getMinLength();

    public int getMaxLength();

    public boolean isRequired();

    @Nullable
    public String getValue();

    @Nullable
    public String getPlaceHolder();

    @Override
    default public boolean isDisabled() {
        return false;
    }

    @Override
    @Nonnull
    default public ActionComponent withDisabled(boolean disabled) {
        throw new UnsupportedOperationException("TextInputs cannot be disabled!");
    }

    @Override
    @Nonnull
    default public Component.Type getType() {
        return Component.Type.TEXT_INPUT;
    }

    @Nonnull
    public static Builder create(@Nonnull String id, @Nonnull String label, @Nonnull TextInputStyle style) {
        return new Builder(id, label, style);
    }

    public static class Builder {
        private String id;
        private String label;
        private String value;
        private String placeholder;
        private int minLength = -1;
        private int maxLength = -1;
        private TextInputStyle style;
        private boolean required = true;

        protected Builder(String id, String label, TextInputStyle style) {
            this.setId(id);
            this.setLabel(label);
            this.setStyle(style);
        }

        @Nonnull
        public Builder setId(@Nonnull String id) {
            Checks.notBlank(id, "ID");
            Checks.notLonger(id, 100, "ID");
            this.id = id;
            return this;
        }

        @Nonnull
        public Builder setLabel(@Nonnull String label) {
            Checks.notBlank(label, "Label");
            Checks.notLonger(label, 45, "Label");
            this.label = label;
            return this;
        }

        @Nonnull
        public Builder setStyle(TextInputStyle style) {
            Checks.notNull((Object)style, "Style");
            Checks.check(style != TextInputStyle.UNKNOWN, "TextInputStyle cannot be UNKNOWN!");
            this.style = style;
            return this;
        }

        @Nonnull
        public Builder setRequired(boolean required) {
            this.required = required;
            return this;
        }

        @Nonnull
        public Builder setMinLength(int minLength) {
            if (minLength != -1) {
                Checks.notNegative(minLength, "Minimum length");
                Checks.check(minLength <= 4000, "Minimum length cannot be longer than %d characters!", (Object)4000);
            }
            this.minLength = minLength;
            return this;
        }

        @Nonnull
        public Builder setMaxLength(int maxLength) {
            if (maxLength != -1) {
                Checks.check(maxLength >= 1, "Maximum length cannot be smaller than 1 character!");
                Checks.check(maxLength <= 4000, "Maximum length cannot be longer than %d characters!", (Object)4000);
            }
            this.maxLength = maxLength;
            return this;
        }

        @Nonnull
        public Builder setRequiredRange(int min, int max) {
            if (min != -1 && max != -1 && min > max) {
                throw new IllegalArgumentException("minimum cannot be greater than maximum!");
            }
            this.setMinLength(min);
            this.setMaxLength(max);
            return this;
        }

        @Nonnull
        public Builder setValue(@Nullable String value) {
            if (value != null) {
                Checks.notLonger(value, 4000, "Value");
                Checks.notBlank(value, "Value");
            }
            this.value = value;
            return this;
        }

        @Nonnull
        public Builder setPlaceholder(@Nullable String placeholder) {
            if (placeholder != null) {
                Checks.notLonger(placeholder, 100, "Placeholder");
                Checks.notBlank(placeholder, "Placeholder");
            }
            this.placeholder = placeholder;
            return this;
        }

        public int getMinLength() {
            return this.minLength;
        }

        public int getMaxLength() {
            return this.maxLength;
        }

        @Nonnull
        public String getId() {
            return this.id;
        }

        @Nonnull
        public String getLabel() {
            return this.label;
        }

        @Nonnull
        public TextInputStyle getStyle() {
            return this.style;
        }

        @Nullable
        public String getPlaceholder() {
            return this.placeholder;
        }

        @Nullable
        public String getValue() {
            return this.value;
        }

        public boolean isRequired() {
            return this.required;
        }

        @Nonnull
        public TextInput build() {
            if (this.maxLength < this.minLength && this.maxLength != -1) {
                throw new IllegalStateException("maxLength cannot be smaller than minLength!");
            }
            return new TextInputImpl(this.id, this.style, this.label, this.minLength, this.maxLength, this.required, this.value, this.placeholder);
        }
    }
}

