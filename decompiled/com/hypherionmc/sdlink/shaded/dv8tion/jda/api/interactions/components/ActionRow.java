/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ActionComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.Component;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ItemComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component.ButtonImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component.EntitySelectMenuImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component.StringSelectMenuImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component.TextInputImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ActionRow
implements LayoutComponent {
    private final List<ItemComponent> components = new ArrayList<ItemComponent>();

    private ActionRow() {
    }

    @Nonnull
    public static ActionRow fromData(@Nonnull DataObject data) {
        Checks.notNull(data, "Data");
        ActionRow row = new ActionRow();
        if (data.getInt("type", 0) != 1) {
            throw new IllegalArgumentException("Data has incorrect type. Expected: 1 Found: " + data.getInt("type"));
        }
        data.getArray("components").stream(DataArray::getObject).map(obj -> {
            switch (Component.Type.fromKey(obj.getInt("type"))) {
                case BUTTON: {
                    return new ButtonImpl((DataObject)obj);
                }
                case STRING_SELECT: {
                    return new StringSelectMenuImpl((DataObject)obj);
                }
                case TEXT_INPUT: {
                    return new TextInputImpl((DataObject)obj);
                }
                case USER_SELECT: 
                case ROLE_SELECT: 
                case CHANNEL_SELECT: 
                case MENTIONABLE_SELECT: {
                    return new EntitySelectMenuImpl((DataObject)obj);
                }
            }
            return null;
        }).filter(Objects::nonNull).forEach(row.components::add);
        return row;
    }

    @Nonnull
    public static ActionRow of(@Nonnull Collection<? extends ItemComponent> components) {
        Checks.noneNull(components, "Components");
        return ActionRow.of(components.toArray(new ItemComponent[0]));
    }

    @Nonnull
    public static ActionRow of(ItemComponent ... components) {
        Checks.noneNull(components, "Components");
        Checks.check(components.length > 0, "Cannot have empty row!");
        ActionRow row = new ActionRow();
        Collections.addAll(row.components, components);
        if (!row.isValid()) {
            Map<Component.Type, List<ItemComponent>> grouped = Arrays.stream(components).collect(Collectors.groupingBy(Component::getType));
            String provided = grouped.entrySet().stream().map(entry -> ((List)entry.getValue()).size() + "/" + ((Component.Type)((Object)((Object)entry.getKey()))).getMaxPerRow() + " of " + entry.getKey()).collect(Collectors.joining(", "));
            throw new IllegalArgumentException("Cannot create action row with invalid component combinations. Provided: " + provided);
        }
        return row;
    }

    @Nonnull
    public static List<ActionRow> partitionOf(@Nonnull Collection<? extends ItemComponent> components) {
        Checks.noneNull(components, "Components");
        ArrayList<ActionRow> rows = new ArrayList<ActionRow>();
        List<ItemComponent> currentRow = null;
        Component.Type type = null;
        for (ItemComponent itemComponent : components) {
            if (type != itemComponent.getType() || currentRow.size() == type.getMaxPerRow()) {
                type = itemComponent.getType();
                ActionRow row = ActionRow.of(itemComponent);
                currentRow = row.components;
                rows.add(row);
                continue;
            }
            currentRow.add(itemComponent);
        }
        return rows;
    }

    @Nonnull
    public static List<ActionRow> partitionOf(ItemComponent ... components) {
        Checks.notNull(components, "Components");
        return ActionRow.partitionOf(Arrays.asList(components));
    }

    @Override
    @Nonnull
    public List<ItemComponent> getComponents() {
        return this.components;
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public ActionRow withDisabled(boolean disabled) {
        return ActionRow.of(this.components.stream().map(c -> {
            if (c instanceof ActionComponent) {
                return ((ActionComponent)c).withDisabled(disabled);
            }
            return c;
        }).collect(Collectors.toList()));
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public ActionRow asDisabled() {
        return this.withDisabled(true);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public ActionRow asEnabled() {
        return this.withDisabled(false);
    }

    @Override
    @Nonnull
    public ActionRow createCopy() {
        return ActionRow.of(this.components);
    }

    @Override
    @Nonnull
    public Component.Type getType() {
        return Component.Type.ACTION_ROW;
    }

    @Override
    @Nonnull
    public DataObject toData() {
        return DataObject.empty().put("type", 1).put("components", DataArray.fromCollection(this.components));
    }

    @Override
    @Nonnull
    public Iterator<ItemComponent> iterator() {
        return this.components.iterator();
    }

    public String toString() {
        return new EntityString(this).addMetadata("components", this.components).toString();
    }

    public int hashCode() {
        return this.components.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ActionRow)) {
            return false;
        }
        return this.components.equals(((ActionRow)obj).components);
    }
}

