/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.OptionType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.FluentRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.InteractionCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public interface AutoCompleteCallbackAction
extends InteractionCallbackAction<Void>,
FluentRestAction<Void, AutoCompleteCallbackAction> {
    @Nonnull
    public OptionType getOptionType();

    @Nonnull
    @CheckReturnValue
    public AutoCompleteCallbackAction addChoices(@Nonnull Collection<Command.Choice> var1);

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction addChoices(Command.Choice ... choices) {
        Checks.noneNull(choices, "Choices");
        return this.addChoices(Arrays.asList(choices));
    }

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction addChoice(@Nonnull String name, @Nonnull String value) {
        return this.addChoices(new Command.Choice(name, value));
    }

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction addChoice(@Nonnull String name, long value) {
        return this.addChoices(new Command.Choice(name, value));
    }

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction addChoice(@Nonnull String name, double value) {
        return this.addChoices(new Command.Choice(name, value));
    }

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction addChoiceStrings(String ... choices) {
        return this.addChoices(Arrays.stream(choices).map((? super T it) -> new Command.Choice((String)it, (String)it)).collect(Collectors.toList()));
    }

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction addChoiceStrings(@Nonnull Collection<String> choices) {
        return this.addChoices(choices.stream().map((? super T it) -> new Command.Choice((String)it, (String)it)).collect(Collectors.toList()));
    }

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction addChoiceLongs(long ... choices) {
        return this.addChoices(Arrays.stream(choices).mapToObj(it -> new Command.Choice(String.valueOf(it), it)).collect(Collectors.toList()));
    }

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction addChoiceLongs(@Nonnull Collection<Long> choices) {
        return this.addChoices(choices.stream().map((? super T it) -> new Command.Choice(String.valueOf(it), (long)it)).collect(Collectors.toList()));
    }

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction addChoiceDoubles(double ... choices) {
        return this.addChoices(Arrays.stream(choices).mapToObj(it -> new Command.Choice(String.valueOf(it), it)).collect(Collectors.toList()));
    }

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction addChoiceDoubles(@Nonnull Collection<Double> choices) {
        return this.addChoices(choices.stream().map((? super T it) -> new Command.Choice(String.valueOf(it), (double)it)).collect(Collectors.toList()));
    }
}

