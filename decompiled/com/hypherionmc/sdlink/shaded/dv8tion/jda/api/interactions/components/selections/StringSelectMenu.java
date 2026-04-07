/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.selections.SelectOption;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.component.StringSelectMenuImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public interface StringSelectMenu
extends SelectMenu {
    @Override
    @Nonnull
    default public StringSelectMenu asDisabled() {
        return this.withDisabled(true);
    }

    @Override
    @Nonnull
    default public StringSelectMenu asEnabled() {
        return this.withDisabled(false);
    }

    @Override
    @Nonnull
    default public StringSelectMenu withDisabled(boolean disabled) {
        return ((Builder)this.createCopy().setDisabled(disabled)).build();
    }

    @Nonnull
    public List<SelectOption> getOptions();

    @Nonnull
    @CheckReturnValue
    default public Builder createCopy() {
        Builder builder = StringSelectMenu.create(this.getId());
        builder.setRequiredRange(this.getMinValues(), this.getMaxValues());
        builder.setPlaceholder(this.getPlaceholder());
        builder.addOptions(this.getOptions());
        builder.setDisabled(this.isDisabled());
        return builder;
    }

    @Nonnull
    @CheckReturnValue
    public static Builder create(@Nonnull String customId) {
        return new Builder(customId);
    }

    public static class Builder
    extends SelectMenu.Builder<StringSelectMenu, Builder> {
        private final List<SelectOption> options = new ArrayList<SelectOption>();

        protected Builder(@Nonnull String customId) {
            super(customId);
        }

        @Nonnull
        public Builder addOptions(SelectOption ... options) {
            Checks.noneNull(options, "Options");
            Checks.check(this.options.size() + options.length <= 25, "Cannot have more than %d options for a select menu!", (Object)25);
            Collections.addAll(this.options, options);
            return this;
        }

        @Nonnull
        public Builder addOptions(@Nonnull Collection<? extends SelectOption> options) {
            Checks.noneNull(options, "Options");
            Checks.check(this.options.size() + options.size() <= 25, "Cannot have more than %d options for a select menu!", (Object)25);
            this.options.addAll(options);
            return this;
        }

        @Nonnull
        public Builder addOption(@Nonnull String label, @Nonnull String value) {
            return this.addOptions(new SelectOption(label, value));
        }

        @Nonnull
        public Builder addOption(@Nonnull String label, @Nonnull String value, @Nonnull Emoji emoji) {
            return this.addOption(label, value, null, emoji);
        }

        @Nonnull
        public Builder addOption(@Nonnull String label, @Nonnull String value, @Nonnull String description) {
            return this.addOption(label, value, description, null);
        }

        @Nonnull
        public Builder addOption(@Nonnull String label, @Nonnull String value, @Nullable String description, @Nullable Emoji emoji) {
            return this.addOptions(new SelectOption(label, value, description, false, emoji));
        }

        @Nonnull
        public List<SelectOption> getOptions() {
            return this.options;
        }

        @Nonnull
        public Builder setDefaultValues(@Nonnull Collection<String> values) {
            Checks.noneNull(values, "Values");
            HashSet<String> set = new HashSet<String>(values);
            ListIterator<SelectOption> it = this.getOptions().listIterator();
            while (it.hasNext()) {
                SelectOption option = it.next();
                it.set(option.withDefault(set.contains(option.getValue())));
            }
            return this;
        }

        @Nonnull
        public Builder setDefaultValues(String ... values) {
            Checks.noneNull(values, "Values");
            return this.setDefaultValues(Arrays.asList(values));
        }

        @Nonnull
        public Builder setDefaultOptions(@Nonnull Collection<? extends SelectOption> values) {
            Checks.noneNull(values, "Values");
            return this.setDefaultValues(values.stream().map(SelectOption::getValue).collect(Collectors.toSet()));
        }

        @Nonnull
        public Builder setDefaultOptions(SelectOption ... values) {
            Checks.noneNull(values, "Values");
            return this.setDefaultOptions(Arrays.asList(values));
        }

        @Override
        @Nonnull
        public StringSelectMenu build() {
            Checks.check(this.minValues <= this.maxValues, "Min values cannot be greater than max values!");
            Checks.check(!this.options.isEmpty(), "Cannot build a select menu without options. Add at least one option!");
            Checks.check(this.options.size() <= 25, "Cannot build a select menu with more than %d options.", (Object)25);
            int min = Math.min(this.minValues, this.options.size());
            int max = Math.min(this.maxValues, this.options.size());
            return new StringSelectMenuImpl(this.customId, this.placeholder, min, max, this.disabled, this.options);
        }
    }
}

