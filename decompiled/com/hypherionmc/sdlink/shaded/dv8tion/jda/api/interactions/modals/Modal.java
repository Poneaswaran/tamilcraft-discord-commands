/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.modals;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ActionRow;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ItemComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.SerializableData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.modal.ModalImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public interface Modal
extends SerializableData {
    public static final int MAX_COMPONENTS = 5;
    public static final int MAX_ID_LENGTH = 100;
    public static final int MAX_TITLE_LENGTH = 45;

    @Nonnull
    public String getId();

    @Nonnull
    public String getTitle();

    @Nonnull
    public List<LayoutComponent> getComponents();

    @Nonnull
    default public Builder createCopy() {
        return new Builder(this.getId(), this.getTitle()).addComponents(this.getComponents());
    }

    @Nonnull
    @CheckReturnValue
    public static Builder create(@Nonnull String customId, @Nonnull String title) {
        return new Builder(customId, title);
    }

    public static class Builder {
        private final List<LayoutComponent> components = new ArrayList<LayoutComponent>(5);
        private String id;
        private String title;

        protected Builder(@Nonnull String customId, @Nonnull String title) {
            this.setId(customId);
            this.setTitle(title);
        }

        @Nonnull
        public Builder setId(@Nonnull String customId) {
            Checks.notBlank(customId, "ID");
            Checks.notLonger(customId, 100, "ID");
            this.id = customId;
            return this;
        }

        @Nonnull
        public Builder setTitle(@Nonnull String title) {
            Checks.notBlank(title, "Title");
            Checks.notLonger(title, 45, "Title");
            this.title = title;
            return this;
        }

        @Nonnull
        public Builder addComponents(LayoutComponent ... components) {
            Checks.noneNull(components, "Action Rows");
            return this.addComponents(Arrays.asList(components));
        }

        @Nonnull
        public Builder addComponents(@Nonnull Collection<? extends LayoutComponent> components) {
            Checks.noneNull(components, "Components");
            Checks.checkComponents("Some components are incompatible with Modals", components, component -> component.getType().isModalCompatible());
            this.components.addAll(components);
            return this;
        }

        @Nonnull
        public Builder addActionRow(@Nonnull Collection<? extends ItemComponent> components) {
            return this.addComponents(ActionRow.of(components));
        }

        @Nonnull
        public Builder addActionRow(ItemComponent ... components) {
            return this.addComponents(ActionRow.of(components));
        }

        @Nonnull
        public List<LayoutComponent> getComponents() {
            return this.components;
        }

        @Nonnull
        public String getTitle() {
            return this.title;
        }

        @Nonnull
        public String getId() {
            return this.id;
        }

        @Nonnull
        public Modal build() {
            Checks.check(!this.components.isEmpty(), "Cannot make a modal without components!");
            Checks.check(this.components.size() <= 5, "Cannot make a modal with more than 5 components!");
            return new ModalImpl(this.id, this.title, this.components);
        }
    }
}

