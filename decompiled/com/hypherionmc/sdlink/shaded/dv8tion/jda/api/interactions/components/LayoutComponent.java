/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ActionComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.Component;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ItemComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.buttons.Button;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.SerializableData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.ComponentsUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Unmodifiable;

public interface LayoutComponent
extends SerializableData,
Iterable<ItemComponent>,
Component {
    @Nonnull
    public List<ItemComponent> getComponents();

    @Override
    default public boolean isMessageCompatible() {
        if (!this.getType().isMessageCompatible()) {
            return false;
        }
        return this.getComponents().stream().allMatch(ItemComponent::isMessageCompatible);
    }

    @Override
    default public boolean isModalCompatible() {
        if (!this.getType().isModalCompatible()) {
            return false;
        }
        return this.getComponents().stream().allMatch(ItemComponent::isModalCompatible);
    }

    @Nonnull
    default public @Unmodifiable List<ActionComponent> getActionComponents() {
        return this.getComponents().stream().filter(ActionComponent.class::isInstance).map(ActionComponent.class::cast).collect(Helpers.toUnmodifiableList());
    }

    @Nonnull
    default public @Unmodifiable List<Button> getButtons() {
        return this.getComponents().stream().filter(Button.class::isInstance).map(Button.class::cast).collect(Helpers.toUnmodifiableList());
    }

    default public boolean isDisabled() {
        return this.getActionComponents().stream().allMatch(ActionComponent::isDisabled);
    }

    default public boolean isEnabled() {
        return this.getActionComponents().stream().noneMatch(ActionComponent::isDisabled);
    }

    @Nonnull
    @CheckReturnValue
    public LayoutComponent withDisabled(boolean var1);

    @Nonnull
    @CheckReturnValue
    public LayoutComponent asDisabled();

    @Nonnull
    @CheckReturnValue
    public LayoutComponent asEnabled();

    default public boolean isEmpty() {
        return this.getComponents().isEmpty();
    }

    default public boolean isValid() {
        if (this.isEmpty()) {
            return false;
        }
        List<ItemComponent> components = this.getComponents();
        Map<Component.Type, List<ItemComponent>> groups2 = components.stream().collect(Collectors.groupingBy(Component::getType));
        if (groups2.size() > 1) {
            return false;
        }
        for (Map.Entry<Component.Type, List<ItemComponent>> entry : groups2.entrySet()) {
            Component.Type type = entry.getKey();
            List<ItemComponent> list = entry.getValue();
            if (list.size() <= type.getMaxPerRow()) continue;
            return false;
        }
        return true;
    }

    @Nonnull
    public LayoutComponent createCopy();

    @Nullable
    default public ItemComponent updateComponent(@Nonnull String id, @Nullable ItemComponent newComponent) {
        Checks.notNull(id, "ID");
        List<ItemComponent> list = this.getComponents();
        ListIterator<ItemComponent> it = list.listIterator();
        while (it.hasNext()) {
            ActionComponent action;
            ItemComponent component = it.next();
            if (!(component instanceof ActionComponent) || !ComponentsUtil.isSameIdentifier(action = (ActionComponent)component, id)) continue;
            if (newComponent == null) {
                it.remove();
            } else {
                it.set(newComponent);
            }
            return component;
        }
        return null;
    }

    public static boolean updateComponent(@Nonnull List<? extends LayoutComponent> layouts, @Nonnull String id, @Nullable ItemComponent newComponent) {
        Checks.notNull(layouts, "LayoutComponent");
        Checks.notEmpty(id, "ID or URL");
        Iterator<? extends LayoutComponent> it = layouts.iterator();
        while (it.hasNext()) {
            LayoutComponent components = it.next();
            ItemComponent oldComponent = components.updateComponent(id, newComponent);
            if (oldComponent == null) continue;
            if (components.getComponents().isEmpty()) {
                it.remove();
            } else if (!components.isValid() && newComponent != null) {
                throw new IllegalArgumentException("Cannot replace " + (Object)((Object)oldComponent.getType()) + " with " + (Object)((Object)newComponent.getType()) + " due to a violation of the layout maximum. The resulting LayoutComponent is invalid!");
            }
            return !Objects.equals(oldComponent, newComponent);
        }
        return false;
    }

    @Nullable
    default public ItemComponent updateComponent(@Nonnull ItemComponent component, @Nullable ItemComponent newComponent) {
        Checks.notNull(component, "Component to replace");
        List<ItemComponent> list = this.getComponents();
        ListIterator<ItemComponent> it = list.listIterator();
        while (it.hasNext()) {
            ItemComponent item = it.next();
            if (!component.equals(item)) continue;
            if (newComponent == null) {
                it.remove();
            } else {
                it.set(newComponent);
            }
            return component;
        }
        return null;
    }

    public static boolean updateComponent(@Nonnull List<? extends LayoutComponent> layouts, @Nonnull ItemComponent component, @Nullable ItemComponent newComponent) {
        Checks.notNull(layouts, "LayoutComponent");
        Checks.notNull(component, "Component to replace");
        Iterator<? extends LayoutComponent> it = layouts.iterator();
        while (it.hasNext()) {
            LayoutComponent components = it.next();
            ItemComponent oldComponent = components.updateComponent(component, newComponent);
            if (oldComponent == null) continue;
            if (components.getComponents().isEmpty()) {
                it.remove();
            } else if (!components.isValid() && newComponent != null) {
                throw new IllegalArgumentException("Cannot replace " + (Object)((Object)oldComponent.getType()) + " with " + (Object)((Object)newComponent.getType()) + " due to a violation of the layout maximum. The resulting LayoutComponent is invalid!");
            }
            return !Objects.equals(oldComponent, newComponent);
        }
        return false;
    }
}

