/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.DiscordLocale;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.OptionType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.OptionData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.localization.LocalizationFunction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.localization.LocalizationMap;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.SerializableData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.localization.LocalizationMapper;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CommandDataImpl
implements SlashCommandData {
    protected final List<SerializableData> options = new ArrayList<SerializableData>(25);
    protected String name;
    protected String description = "";
    private LocalizationMapper localizationMapper;
    private final LocalizationMap nameLocalizations = new LocalizationMap(this::checkName);
    private final LocalizationMap descriptionLocalizations = new LocalizationMap(this::checkDescription);
    private boolean allowSubcommands = true;
    private boolean allowOption = true;
    private boolean allowRequired = true;
    private boolean guildOnly = false;
    private boolean nsfw = false;
    private DefaultMemberPermissions defaultMemberPermissions = DefaultMemberPermissions.ENABLED;
    private final Command.Type type;

    public CommandDataImpl(@Nonnull String name, @Nonnull String description) {
        this.type = Command.Type.SLASH;
        this.setName(name);
        this.setDescription(description);
    }

    public CommandDataImpl(@Nonnull Command.Type type, @Nonnull String name) {
        this.type = type;
        Checks.notNull((Object)type, "Command Type");
        Checks.check(type != Command.Type.SLASH, "Cannot create slash command without description. Use `new CommandDataImpl(name, description)` instead.");
        this.setName(name);
    }

    protected void checkType(Command.Type required, String action) {
        if (required != this.type) {
            throw new IllegalStateException("Cannot " + action + " for commands of type " + (Object)((Object)this.type));
        }
    }

    public void checkName(@Nonnull String name) {
        Checks.inRange(name, 1, 32, "Name");
        if (this.type == Command.Type.SLASH) {
            Checks.matches(name, Checks.ALPHANUMERIC_WITH_DASH, "Name");
            Checks.isLowercase(name, "Name");
        }
    }

    public void checkDescription(@Nonnull String description) {
        this.checkType(Command.Type.SLASH, "set description");
        Checks.inRange(description, 1, 100, "Description");
    }

    @Override
    @Nonnull
    public DataObject toData() {
        DataArray options = DataArray.fromCollection(this.options);
        if (this.localizationMapper != null) {
            this.localizationMapper.localizeCommand(this, options);
        }
        DataObject json = DataObject.empty().put("type", this.type.getId()).put("name", this.name).put("nsfw", this.nsfw).put("options", options).put("dm_permission", !this.guildOnly).put("default_member_permissions", this.defaultMemberPermissions == DefaultMemberPermissions.ENABLED ? null : Long.toUnsignedString(this.defaultMemberPermissions.getPermissionsRaw())).put("name_localizations", this.nameLocalizations);
        if (this.type == Command.Type.SLASH) {
            json.put("description", this.description).put("description_localizations", this.descriptionLocalizations);
        }
        return json;
    }

    @Override
    @Nonnull
    public Command.Type getType() {
        return this.type;
    }

    @Override
    @Nonnull
    public DefaultMemberPermissions getDefaultPermissions() {
        return this.defaultMemberPermissions;
    }

    @Override
    public boolean isGuildOnly() {
        return this.guildOnly;
    }

    @Override
    public boolean isNSFW() {
        return this.nsfw;
    }

    @Override
    @Nonnull
    public List<OptionData> getOptions() {
        return this.options.stream().filter(OptionData.class::isInstance).map(OptionData.class::cast).collect(Helpers.toUnmodifiableList());
    }

    @Override
    @Nonnull
    public List<SubcommandData> getSubcommands() {
        return this.options.stream().filter(SubcommandData.class::isInstance).map(SubcommandData.class::cast).collect(Helpers.toUnmodifiableList());
    }

    @Override
    @Nonnull
    public List<SubcommandGroupData> getSubcommandGroups() {
        return this.options.stream().filter(SubcommandGroupData.class::isInstance).map(SubcommandGroupData.class::cast).collect(Helpers.toUnmodifiableList());
    }

    @Override
    @Nonnull
    public CommandDataImpl setDefaultPermissions(@Nonnull DefaultMemberPermissions permissions) {
        Checks.notNull(permissions, "Permissions");
        this.defaultMemberPermissions = permissions;
        return this;
    }

    @Override
    @Nonnull
    public CommandDataImpl setGuildOnly(boolean guildOnly) {
        this.guildOnly = guildOnly;
        return this;
    }

    @Override
    @Nonnull
    public CommandDataImpl setNSFW(boolean nsfw) {
        this.nsfw = nsfw;
        return this;
    }

    @Override
    @Nonnull
    public CommandDataImpl addOptions(OptionData ... options) {
        Checks.noneNull(options, "Option");
        if (options.length == 0) {
            return this;
        }
        this.checkType(Command.Type.SLASH, "add options");
        Checks.check(options.length + this.options.size() <= 25, "Cannot have more than %d options for a command!", (Object)25);
        Checks.check(this.allowOption, "You cannot mix options with subcommands/groups.");
        boolean allowRequired = this.allowRequired;
        for (OptionData option : options) {
            Checks.check(option.getType() != OptionType.SUB_COMMAND, "Cannot add a subcommand with addOptions(...). Use addSubcommands(...) instead!");
            Checks.check(option.getType() != OptionType.SUB_COMMAND_GROUP, "Cannot add a subcommand group with addOptions(...). Use addSubcommandGroups(...) instead!");
            Checks.check(allowRequired || !option.isRequired(), "Cannot add required options after non-required options!");
            allowRequired = option.isRequired();
        }
        Checks.checkUnique(Stream.concat(this.getOptions().stream(), Arrays.stream(options)).map(OptionData::getName), "Cannot have multiple options with the same name. Name: \"%s\" appeared %d times!", (count, value) -> new Object[]{value, count});
        this.allowSubcommands = false;
        this.allowRequired = allowRequired;
        Collections.addAll(this.options, options);
        return this;
    }

    @Override
    @Nonnull
    public CommandDataImpl addSubcommands(SubcommandData ... subcommands) {
        Checks.noneNull(subcommands, "Subcommands");
        if (subcommands.length == 0) {
            return this;
        }
        this.checkType(Command.Type.SLASH, "add subcommands");
        if (!this.allowSubcommands) {
            throw new IllegalArgumentException("You cannot mix options with subcommands/groups.");
        }
        Checks.check(subcommands.length + this.options.size() <= 25, "Cannot have more than %d subcommands for a command!", (Object)25);
        Checks.checkUnique(Stream.concat(this.getSubcommands().stream(), Arrays.stream(subcommands)).map(SubcommandData::getName), "Cannot have multiple subcommands with the same name. Name: \"%s\" appeared %d times!", (count, value) -> new Object[]{value, count});
        this.allowOption = false;
        Collections.addAll(this.options, subcommands);
        return this;
    }

    @Override
    @Nonnull
    public CommandDataImpl addSubcommandGroups(SubcommandGroupData ... groups2) {
        Checks.noneNull(groups2, "SubcommandGroups");
        if (groups2.length == 0) {
            return this;
        }
        this.checkType(Command.Type.SLASH, "add subcommand groups");
        if (!this.allowSubcommands) {
            throw new IllegalArgumentException("You cannot mix options with subcommands/groups.");
        }
        Checks.check(groups2.length + this.options.size() <= 25, "Cannot have more than %d subcommand groups for a command!", (Object)25);
        Checks.checkUnique(Stream.concat(this.getSubcommandGroups().stream(), Arrays.stream(groups2)).map(SubcommandGroupData::getName), "Cannot have multiple subcommand groups with the same name. Name: \"%s\" appeared %d times!", (count, value) -> new Object[]{value, count});
        this.allowOption = false;
        Collections.addAll(this.options, groups2);
        return this;
    }

    @Override
    @Nonnull
    public CommandDataImpl setLocalizationFunction(@Nonnull LocalizationFunction localizationFunction) {
        Checks.notNull(localizationFunction, "Localization function");
        this.localizationMapper = LocalizationMapper.fromFunction(localizationFunction);
        return this;
    }

    @Override
    @Nonnull
    public CommandDataImpl setName(@Nonnull String name) {
        this.checkName(name);
        this.name = name;
        return this;
    }

    @Override
    @Nonnull
    public CommandDataImpl setNameLocalization(@Nonnull DiscordLocale locale, @Nonnull String name) {
        this.nameLocalizations.setTranslation(locale, name);
        return this;
    }

    @Override
    @Nonnull
    public CommandDataImpl setNameLocalizations(@Nonnull Map<DiscordLocale, String> map) {
        this.nameLocalizations.setTranslations(map);
        return this;
    }

    @Override
    @Nonnull
    public CommandDataImpl setDescription(@Nonnull String description) {
        this.checkDescription(description);
        this.description = description;
        return this;
    }

    @Override
    @Nonnull
    public CommandDataImpl setDescriptionLocalization(@Nonnull DiscordLocale locale, @Nonnull String description) {
        this.descriptionLocalizations.setTranslation(locale, description);
        return this;
    }

    @Override
    @Nonnull
    public CommandDataImpl setDescriptionLocalizations(@Nonnull Map<DiscordLocale, String> map) {
        this.descriptionLocalizations.setTranslations(map);
        return this;
    }

    @Override
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Override
    @Nonnull
    public LocalizationMap getNameLocalizations() {
        return this.nameLocalizations;
    }

    @Override
    @Nonnull
    public String getDescription() {
        return this.description;
    }

    @Override
    @Nonnull
    public LocalizationMap getDescriptionLocalizations() {
        return this.descriptionLocalizations;
    }

    @Override
    public boolean removeOptions(@Nonnull Predicate<? super OptionData> condition) {
        Checks.notNull(condition, "Condition");
        boolean modified = this.options.removeIf(o -> o instanceof OptionData && condition.test((OptionData)o));
        if (modified) {
            this.updateAllowedOptions();
        }
        return modified;
    }

    @Override
    public boolean removeSubcommands(@Nonnull Predicate<? super SubcommandData> condition) {
        Checks.notNull(condition, "Condition");
        boolean modified = this.options.removeIf(o -> o instanceof SubcommandData && condition.test((SubcommandData)o));
        if (modified) {
            this.updateAllowedOptions();
        }
        return modified;
    }

    @Override
    public boolean removeSubcommandGroups(@Nonnull Predicate<? super SubcommandGroupData> condition) {
        Checks.notNull(condition, "Condition");
        boolean modified = this.options.removeIf(o -> o instanceof SubcommandGroupData && condition.test((SubcommandGroupData)o));
        if (modified) {
            this.updateAllowedOptions();
        }
        return modified;
    }

    private void updateAllowedOptions() {
        if (this.options.isEmpty()) {
            this.allowSubcommands = true;
            this.allowOption = true;
            this.allowRequired = true;
            return;
        }
        SerializableData last = this.options.get(this.options.size() - 1);
        this.allowOption = last instanceof OptionData;
        this.allowRequired = this.allowOption && ((OptionData)last).isRequired();
        this.allowSubcommands = !this.allowOption;
    }
}

