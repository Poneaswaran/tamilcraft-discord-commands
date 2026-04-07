/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.callbacks;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.Interaction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.interactions.AutoCompleteCallbackAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public interface IAutoCompleteCallback
extends Interaction {
    @Nonnull
    @CheckReturnValue
    public AutoCompleteCallbackAction replyChoices(@Nonnull Collection<Command.Choice> var1);

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction replyChoices(Command.Choice ... choices) {
        Checks.noneNull(choices, "Choice");
        return this.replyChoices(Arrays.asList(choices));
    }

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction replyChoice(@Nonnull String name, @Nonnull String value) {
        return this.replyChoices(new Command.Choice(name, value));
    }

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction replyChoice(@Nonnull String name, long value) {
        return this.replyChoices(new Command.Choice(name, value));
    }

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction replyChoice(@Nonnull String name, double value) {
        return this.replyChoices(new Command.Choice(name, value));
    }

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction replyChoiceStrings(String ... choices) {
        return this.replyChoices(Arrays.stream(choices).map(it -> new Command.Choice((String)it, (String)it)).collect(Collectors.toList()));
    }

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction replyChoiceStrings(@Nonnull Collection<String> choices) {
        return this.replyChoices(choices.stream().map(it -> new Command.Choice((String)it, (String)it)).collect(Collectors.toList()));
    }

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction replyChoiceLongs(long ... choices) {
        return this.replyChoices(Arrays.stream(choices).mapToObj(it -> new Command.Choice(String.valueOf(it), it)).collect(Collectors.toList()));
    }

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction replyChoiceLongs(@Nonnull Collection<Long> choices) {
        return this.replyChoices(choices.stream().map(it -> new Command.Choice(String.valueOf(it), (long)it)).collect(Collectors.toList()));
    }

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction replyChoiceDoubles(double ... choices) {
        return this.replyChoices(Arrays.stream(choices).mapToObj(it -> new Command.Choice(String.valueOf(it), it)).collect(Collectors.toList()));
    }

    @Nonnull
    @CheckReturnValue
    default public AutoCompleteCallbackAction replyChoiceDoubles(@Nonnull Collection<Double> choices) {
        return this.replyChoices(choices.stream().map(it -> new Command.Choice(String.valueOf(it), (double)it)).collect(Collectors.toList()));
    }
}

