/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Contract
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.annotations.UnknownNullability;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.Interaction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.OptionMapping;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.OptionType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Contract;

public interface CommandInteractionPayload
extends Interaction {
    @Nonnull
    public Command.Type getCommandType();

    @Nonnull
    public String getName();

    @Nullable
    public String getSubcommandName();

    @Nullable
    public String getSubcommandGroup();

    @Nonnull
    default public String getFullCommandName() {
        StringBuilder builder = new StringBuilder(this.getName());
        if (this.getSubcommandGroup() != null) {
            builder.append(' ').append(this.getSubcommandGroup());
        }
        if (this.getSubcommandName() != null) {
            builder.append(' ').append(this.getSubcommandName());
        }
        return builder.toString();
    }

    @Nonnull
    default public String getCommandString() {
        if (this.getCommandType() != Command.Type.SLASH) {
            return this.getName();
        }
        StringBuilder builder = new StringBuilder();
        builder.append("/").append(this.getName());
        if (this.getSubcommandGroup() != null) {
            builder.append(" ").append(this.getSubcommandGroup());
        }
        if (this.getSubcommandName() != null) {
            builder.append(" ").append(this.getSubcommandName());
        }
        block9: for (OptionMapping o : this.getOptions()) {
            builder.append(" ").append(o.getName()).append(": ");
            if (this instanceof CommandAutoCompleteInteraction) {
                switch (o.getType()) {
                    case CHANNEL: 
                    case USER: 
                    case ROLE: 
                    case MENTIONABLE: {
                        builder.append(o.getAsLong());
                        continue block9;
                    }
                }
            }
            switch (o.getType()) {
                case CHANNEL: {
                    builder.append("#").append(o.getAsChannel().getName());
                    continue block9;
                }
                case USER: {
                    builder.append("@").append(o.getAsUser().getName());
                    continue block9;
                }
                case ROLE: {
                    builder.append("@").append(o.getAsRole().getName());
                    continue block9;
                }
                case MENTIONABLE: {
                    if (o.getAsMentionable() instanceof Role) {
                        builder.append("@").append(o.getAsRole().getName());
                        continue block9;
                    }
                    if (o.getAsMentionable() instanceof Member) {
                        builder.append("@").append(o.getAsUser().getName());
                        continue block9;
                    }
                    if (o.getAsMentionable() instanceof User) {
                        builder.append("@").append(o.getAsUser().getName());
                        continue block9;
                    }
                    builder.append("@").append(o.getAsMentionable().getIdLong());
                    continue block9;
                }
            }
            builder.append(o.getAsString());
        }
        return builder.toString();
    }

    public long getCommandIdLong();

    @Nonnull
    default public String getCommandId() {
        return Long.toUnsignedString(this.getCommandIdLong());
    }

    public boolean isGuildCommand();

    default public boolean isGlobalCommand() {
        return !this.isGuildCommand();
    }

    @Nonnull
    public List<OptionMapping> getOptions();

    @Nonnull
    default public List<OptionMapping> getOptionsByName(@Nonnull String name) {
        Checks.notNull(name, "Name");
        return this.getOptions().stream().filter(opt -> opt.getName().equals(name)).collect(Collectors.toList());
    }

    @Nonnull
    default public List<OptionMapping> getOptionsByType(@Nonnull OptionType type) {
        Checks.notNull((Object)type, "Type");
        return this.getOptions().stream().filter(it -> it.getType() == type).collect(Collectors.toList());
    }

    @Nullable
    default public OptionMapping getOption(@Nonnull String name) {
        List<OptionMapping> options = this.getOptionsByName(name);
        return options.isEmpty() ? null : options.get(0);
    }

    @Nullable
    default public <T> T getOption(@Nonnull String name, @Nonnull Function<? super OptionMapping, ? extends T> resolver) {
        return this.getOption(name, null, resolver);
    }

    @Contract(value="_,null,_->null")
    default public <T> T getOption(@Nonnull String name, @Nullable T fallback, @Nonnull Function<? super OptionMapping, ? extends T> resolver) {
        Checks.notNull(resolver, "Resolver");
        OptionMapping mapping = this.getOption(name);
        if (mapping != null) {
            return resolver.apply(mapping);
        }
        return fallback;
    }

    @UnknownNullability
    default public <T> T getOption(@Nonnull String name, @Nullable Supplier<? extends T> fallback, @Nonnull Function<? super OptionMapping, ? extends T> resolver) {
        Checks.notNull(resolver, "Resolver");
        OptionMapping mapping = this.getOption(name);
        if (mapping != null) {
            return resolver.apply(mapping);
        }
        return fallback == null ? null : (T)fallback.get();
    }
}

