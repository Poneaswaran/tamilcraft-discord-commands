/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.DiscordLocale;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.OptionType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.OptionData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.localization.LocalizationFunction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public interface CommandCreateAction
extends RestAction<Command>,
SlashCommandData {
    @Nonnull
    @CheckReturnValue
    public CommandCreateAction setCheck(@Nullable BooleanSupplier var1);

    @Nonnull
    @CheckReturnValue
    public CommandCreateAction addCheck(@Nonnull BooleanSupplier var1);

    @Nonnull
    @CheckReturnValue
    public CommandCreateAction timeout(long var1, @Nonnull TimeUnit var3);

    @Nonnull
    @CheckReturnValue
    public CommandCreateAction deadline(long var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public CommandCreateAction setLocalizationFunction(@Nonnull LocalizationFunction var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public CommandCreateAction setName(@Nonnull String var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public CommandCreateAction setNameLocalization(@Nonnull DiscordLocale var1, @Nonnull String var2);

    @Override
    @Nonnull
    @CheckReturnValue
    public CommandCreateAction setNameLocalizations(@Nonnull Map<DiscordLocale, String> var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public CommandCreateAction setDescription(@Nonnull String var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public CommandCreateAction setDescriptionLocalization(@Nonnull DiscordLocale var1, @Nonnull String var2);

    @Override
    @Nonnull
    @CheckReturnValue
    public CommandCreateAction setDescriptionLocalizations(@Nonnull Map<DiscordLocale, String> var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public CommandCreateAction addOptions(OptionData ... var1);

    @Override
    @Nonnull
    @CheckReturnValue
    default public CommandCreateAction addOptions(@Nonnull Collection<? extends OptionData> options) {
        return (CommandCreateAction)SlashCommandData.super.addOptions(options);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public CommandCreateAction addOption(@Nonnull OptionType type, @Nonnull String name, @Nonnull String description, boolean required, boolean autoComplete) {
        return (CommandCreateAction)SlashCommandData.super.addOption(type, name, description, required, autoComplete);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public CommandCreateAction addOption(@Nonnull OptionType type, @Nonnull String name, @Nonnull String description, boolean required) {
        return (CommandCreateAction)SlashCommandData.super.addOption(type, name, description, required);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    default public CommandCreateAction addOption(@Nonnull OptionType type, @Nonnull String name, @Nonnull String description) {
        return (CommandCreateAction)SlashCommandData.super.addOption(type, name, description, false);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public CommandCreateAction addSubcommands(SubcommandData ... var1);

    @Override
    @Nonnull
    @CheckReturnValue
    default public CommandCreateAction addSubcommands(@Nonnull Collection<? extends SubcommandData> subcommands) {
        return (CommandCreateAction)SlashCommandData.super.addSubcommands(subcommands);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public CommandCreateAction addSubcommandGroups(SubcommandGroupData ... var1);

    @Override
    @Nonnull
    @CheckReturnValue
    default public CommandCreateAction addSubcommandGroups(@Nonnull Collection<? extends SubcommandGroupData> groups2) {
        return (CommandCreateAction)SlashCommandData.super.addSubcommandGroups(groups2);
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public CommandCreateAction setDefaultPermissions(@Nonnull DefaultMemberPermissions var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public CommandCreateAction setGuildOnly(boolean var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public CommandCreateAction setNSFW(boolean var1);
}

