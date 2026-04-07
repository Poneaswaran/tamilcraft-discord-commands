/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.SerializableData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class SelectOption
implements SerializableData {
    public static final int LABEL_MAX_LENGTH = 100;
    public static final int VALUE_MAX_LENGTH = 100;
    public static final int DESCRIPTION_MAX_LENGTH = 100;
    private final String label;
    private final String value;
    private final String description;
    private final boolean isDefault;
    private final EmojiUnion emoji;

    protected SelectOption(@Nonnull String label, @Nonnull String value) {
        this(label, value, null, false, null);
    }

    protected SelectOption(@Nonnull String label, @Nonnull String value, @Nullable String description, boolean isDefault, @Nullable Emoji emoji) {
        Checks.notEmpty(label, "Label");
        Checks.notEmpty(value, "Value");
        Checks.notLonger(label, 100, "Label");
        Checks.notLonger(value, 100, "Value");
        if (description != null) {
            Checks.notLonger(description, 100, "Description");
        }
        this.label = label;
        this.value = value;
        this.description = description;
        this.isDefault = isDefault;
        this.emoji = (EmojiUnion)emoji;
    }

    @Nonnull
    @CheckReturnValue
    public static SelectOption of(@Nonnull String label, @Nonnull String value) {
        return new SelectOption(label, value);
    }

    @Nonnull
    @CheckReturnValue
    public SelectOption withLabel(@Nonnull String label) {
        return new SelectOption(label, this.value, this.description, this.isDefault, this.emoji);
    }

    @Nonnull
    @CheckReturnValue
    public SelectOption withValue(@Nonnull String value) {
        return new SelectOption(this.label, value, this.description, this.isDefault, this.emoji);
    }

    @Nonnull
    @CheckReturnValue
    public SelectOption withDescription(@Nullable String description) {
        return new SelectOption(this.label, this.value, description, this.isDefault, this.emoji);
    }

    @Nonnull
    @CheckReturnValue
    public SelectOption withDefault(boolean isDefault) {
        return new SelectOption(this.label, this.value, this.description, isDefault, this.emoji);
    }

    @Nonnull
    @CheckReturnValue
    public SelectOption withEmoji(@Nullable Emoji emoji) {
        return new SelectOption(this.label, this.value, this.description, this.isDefault, emoji);
    }

    @Nonnull
    public String getLabel() {
        return this.label;
    }

    @Nonnull
    public String getValue() {
        return this.value;
    }

    @Nullable
    public String getDescription() {
        return this.description;
    }

    public boolean isDefault() {
        return this.isDefault;
    }

    @Nullable
    public EmojiUnion getEmoji() {
        return this.emoji;
    }

    @Override
    @Nonnull
    public DataObject toData() {
        DataObject object = DataObject.empty();
        object.put("label", this.label);
        object.put("value", this.value);
        object.put("default", this.isDefault);
        if (this.emoji != null) {
            object.put("emoji", this.emoji);
        }
        if (this.description != null && !this.description.isEmpty()) {
            object.put("description", this.description);
        }
        return object;
    }

    @Nonnull
    @CheckReturnValue
    public static SelectOption fromData(@Nonnull DataObject data) {
        Checks.notNull(data, "DataObject");
        return new SelectOption(data.getString("label"), data.getString("value"), data.getString("description", null), data.getBoolean("default", false), data.optObject("emoji").map(EntityBuilder::createEmoji).orElse(null));
    }
}

