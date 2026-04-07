/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public abstract class SelectMenuImpl
implements SelectMenu {
    protected final String id;
    protected final String placeholder;
    protected final int minValues;
    protected final int maxValues;
    protected final boolean disabled;

    public SelectMenuImpl(DataObject data) {
        this(data.getString("custom_id"), data.getString("placeholder", null), data.getInt("min_values", 1), data.getInt("max_values", 1), data.getBoolean("disabled"));
    }

    public SelectMenuImpl(String id, String placeholder, int minValues, int maxValues, boolean disabled) {
        this.id = id;
        this.placeholder = placeholder;
        this.minValues = minValues;
        this.maxValues = maxValues;
        this.disabled = disabled;
    }

    @Override
    @Nullable
    public String getId() {
        return this.id;
    }

    @Override
    @Nullable
    public String getPlaceholder() {
        return this.placeholder;
    }

    @Override
    public int getMinValues() {
        return this.minValues;
    }

    @Override
    public int getMaxValues() {
        return this.maxValues;
    }

    @Override
    public boolean isDisabled() {
        return this.disabled;
    }

    @Override
    @Nonnull
    public DataObject toData() {
        DataObject data = DataObject.empty();
        data.put("custom_id", this.id);
        data.put("min_values", this.minValues);
        data.put("max_values", this.maxValues);
        data.put("disabled", this.disabled);
        if (this.placeholder != null) {
            data.put("placeholder", this.placeholder);
        }
        return data;
    }

    public String toString() {
        return new EntityString(SelectMenu.class).setType(this.getType()).addMetadata("id", this.id).addMetadata("placeholder", this.placeholder).toString();
    }
}

