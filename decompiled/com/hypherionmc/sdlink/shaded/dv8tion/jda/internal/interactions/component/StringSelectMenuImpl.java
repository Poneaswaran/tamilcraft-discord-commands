/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.Component;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.SelectOption;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component.SelectMenuImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StringSelectMenuImpl
extends SelectMenuImpl
implements StringSelectMenu {
    private final List<SelectOption> options;

    public StringSelectMenuImpl(DataObject data) {
        super(data);
        this.options = StringSelectMenuImpl.parseOptions(data.getArray("options"));
    }

    public StringSelectMenuImpl(String id, String placeholder, int minValues, int maxValues, boolean disabled, List<SelectOption> options) {
        super(id, placeholder, minValues, maxValues, disabled);
        this.options = options;
    }

    private static List<SelectOption> parseOptions(DataArray array) {
        ArrayList<SelectOption> options = new ArrayList<SelectOption>(array.length());
        array.stream(DataArray::getObject).map(SelectOption::fromData).forEach(options::add);
        return options;
    }

    @Override
    @Nonnull
    public Component.Type getType() {
        return Component.Type.STRING_SELECT;
    }

    @Override
    @Nonnull
    public List<SelectOption> getOptions() {
        return Collections.unmodifiableList(this.options);
    }

    @Override
    @Nonnull
    public DataObject toData() {
        return super.toData().put("type", Component.Type.STRING_SELECT.getKey()).put("options", DataArray.fromCollection(this.options));
    }

    public int hashCode() {
        return Objects.hash(this.id, this.placeholder, this.minValues, this.maxValues, this.disabled, this.options);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StringSelectMenu)) {
            return false;
        }
        StringSelectMenu other = (StringSelectMenu)obj;
        return Objects.equals(this.id, other.getId()) && Objects.equals(this.placeholder, other.getPlaceholder()) && this.minValues == other.getMinValues() && this.maxValues == other.getMaxValues() && this.disabled == other.isDisabled() && Objects.equals(this.options, other.getOptions());
    }
}

