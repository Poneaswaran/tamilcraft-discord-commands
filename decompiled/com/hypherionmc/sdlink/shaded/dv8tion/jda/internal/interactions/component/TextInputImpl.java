/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.text.TextInput;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class TextInputImpl
implements TextInput {
    private final String id;
    private final TextInputStyle style;
    private final String label;
    private final int minLength;
    private final int maxLength;
    private final boolean required;
    private final String value;
    private final String placeholder;

    public TextInputImpl(DataObject object) {
        this(object.getString("custom_id"), TextInputStyle.fromKey(object.getInt("style", -1)), object.getString("label", null), object.getInt("min_length", -1), object.getInt("max_length", -1), object.getBoolean("required", true), object.getString("value", null), object.getString("placeholder", null));
    }

    public TextInputImpl(String id, TextInputStyle style, String label, int minLength, int maxLength, boolean required, String value, String placeholder) {
        this.id = id;
        this.style = style;
        this.label = label;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.required = required;
        this.value = value;
        this.placeholder = placeholder;
    }

    @Override
    @Nonnull
    public TextInputStyle getStyle() {
        return this.style;
    }

    @Override
    @Nonnull
    public String getId() {
        return this.id;
    }

    @Override
    @Nonnull
    public String getLabel() {
        return this.label;
    }

    @Override
    public int getMinLength() {
        return this.minLength;
    }

    @Override
    public int getMaxLength() {
        return this.maxLength;
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    @Override
    @Nullable
    public String getValue() {
        return this.value;
    }

    @Override
    @Nullable
    public String getPlaceHolder() {
        return this.placeholder;
    }

    @Override
    @Nonnull
    public DataObject toData() {
        DataObject obj = DataObject.empty().put("type", this.getType().getKey()).put("custom_id", this.id).put("style", this.style.getRaw()).put("required", this.required).put("label", this.label);
        if (this.minLength != -1) {
            obj.put("min_length", this.minLength);
        }
        if (this.maxLength != -1) {
            obj.put("max_length", this.maxLength);
        }
        if (this.value != null) {
            obj.put("value", this.value);
        }
        if (this.placeholder != null) {
            obj.put("placeholder", this.placeholder);
        }
        return obj;
    }

    public String toString() {
        return new EntityString(this).setType(this.style).addMetadata("id", this.id).addMetadata("value", this.value).toString();
    }
}

