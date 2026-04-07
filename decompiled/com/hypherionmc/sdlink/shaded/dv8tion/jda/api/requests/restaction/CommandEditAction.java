/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.OptionType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.CommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.OptionData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public interface CommandEditAction
extends RestAction<Command> {
    @Nonnull
    @CheckReturnValue
    public CommandEditAction setCheck(@Nullable BooleanSupplier var1);

    @Nonnull
    @CheckReturnValue
    public CommandEditAction addCheck(@Nonnull BooleanSupplier var1);

    @Nonnull
    @CheckReturnValue
    public CommandEditAction timeout(long var1, @Nonnull TimeUnit var3);

    @Nonnull
    @CheckReturnValue
    public CommandEditAction deadline(long var1);

    @Nonnull
    @CheckReturnValue
    public CommandEditAction apply(@Nonnull CommandData var1);

    @Nonnull
    @CheckReturnValue
    public CommandEditAction setName(@Nullable String var1);

    @Nonnull
    @CheckReturnValue
    public CommandEditAction setGuildOnly(boolean var1);

    @Nonnull
    @CheckReturnValue
    public CommandEditAction setNSFW(boolean var1);

    @Nonnull
    @CheckReturnValue
    public CommandEditAction setDefaultPermissions(@Nonnull DefaultMemberPermissions var1);

    @Nonnull
    @CheckReturnValue
    public CommandEditAction setDescription(@Nullable String var1);

    @Nonnull
    @CheckReturnValue
    public CommandEditAction clearOptions();

    @Nonnull
    @CheckReturnValue
    public CommandEditAction addOptions(OptionData ... var1);

    @Nonnull
    @CheckReturnValue
    default public CommandEditAction addOptions(@Nonnull Collection<? extends OptionData> options) {
        Checks.noneNull(options, "Options");
        return this.addOptions(options.toArray(new OptionData[0]));
    }

    @Nonnull
    @CheckReturnValue
    default public CommandEditAction addOption(@Nonnull OptionType type, @Nonnull String name, @Nonnull String description, boolean required) {
        return this.addOptions(new OptionData(type, name, description).setRequired(required));
    }

    @Nonnull
    @CheckReturnValue
    default public CommandEditAction addOption(@Nonnull OptionType type, @Nonnull String name, @Nonnull String description) {
        return this.addOption(type, name, description, false);
    }

    @Nonnull
    @CheckReturnValue
    public CommandEditAction addSubcommands(SubcommandData ... var1);

    @Nonnull
    @CheckReturnValue
    default public CommandEditAction addSubcommands(@Nonnull Collection<? extends SubcommandData> subcommands) {
        Checks.noneNull(subcommands, "Subcommands");
        return this.addSubcommands(subcommands.toArray(new SubcommandData[0]));
    }

    @Nonnull
    @CheckReturnValue
    public CommandEditAction addSubcommandGroups(SubcommandGroupData ... var1);

    @Nonnull
    @CheckReturnValue
    default public CommandEditAction addSubcommandGroups(@Nonnull Collection<? extends SubcommandGroupData> groups2) {
        Checks.noneNull(groups2, "SubcommandGroups");
        return this.addSubcommandGroups(groups2.toArray(new SubcommandGroupData[0]));
    }
}

