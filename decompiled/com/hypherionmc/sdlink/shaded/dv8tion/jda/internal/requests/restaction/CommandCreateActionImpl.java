/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.DiscordLocale;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.Command;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.OptionData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.localization.LocalizationFunction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.localization.LocalizationMap;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.CommandDataImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.CommandImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

public class CommandCreateActionImpl
extends RestActionImpl<Command>
implements CommandCreateAction {
    private final Guild guild;
    private final CommandDataImpl data;

    public CommandCreateActionImpl(JDAImpl api, CommandDataImpl command) {
        super(api, Route.Interactions.CREATE_COMMAND.compile(api.getSelfUser().getApplicationId()));
        this.guild = null;
        this.data = command;
    }

    public CommandCreateActionImpl(Guild guild, CommandDataImpl command) {
        super(guild.getJDA(), Route.Interactions.CREATE_GUILD_COMMAND.compile(guild.getJDA().getSelfUser().getApplicationId(), guild.getId()));
        this.guild = guild;
        this.data = command;
    }

    @Override
    @Nonnull
    public CommandCreateAction addCheck(@Nonnull BooleanSupplier checks) {
        return (CommandCreateAction)super.addCheck(checks);
    }

    @Override
    @Nonnull
    public CommandCreateAction setCheck(BooleanSupplier checks) {
        return (CommandCreateAction)super.setCheck(checks);
    }

    @Override
    @Nonnull
    public CommandCreateAction deadline(long timestamp) {
        return (CommandCreateAction)super.deadline(timestamp);
    }

    @Override
    @Nonnull
    public CommandCreateAction setDefaultPermissions(@Nonnull DefaultMemberPermissions permission) {
        this.data.setDefaultPermissions(permission);
        return this;
    }

    @Override
    @Nonnull
    public CommandCreateAction setGuildOnly(boolean guildOnly) {
        this.data.setGuildOnly(guildOnly);
        return this;
    }

    @Override
    @Nonnull
    public CommandCreateAction setNSFW(boolean nsfw) {
        this.data.setNSFW(nsfw);
        return this;
    }

    @Override
    @Nonnull
    public CommandCreateAction setLocalizationFunction(@Nonnull LocalizationFunction localizationFunction) {
        this.data.setLocalizationFunction(localizationFunction);
        return this;
    }

    @Override
    @Nonnull
    public String getName() {
        return this.data.getName();
    }

    @Override
    @Nonnull
    public LocalizationMap getNameLocalizations() {
        return this.data.getNameLocalizations();
    }

    @Override
    @Nonnull
    public Command.Type getType() {
        return this.data.getType();
    }

    @Override
    @Nonnull
    public DefaultMemberPermissions getDefaultPermissions() {
        return this.data.getDefaultPermissions();
    }

    @Override
    public boolean isGuildOnly() {
        return this.data.isGuildOnly();
    }

    @Override
    public boolean isNSFW() {
        return this.data.isNSFW();
    }

    @Override
    @Nonnull
    public CommandCreateAction timeout(long timeout2, @Nonnull TimeUnit unit) {
        return (CommandCreateAction)super.timeout(timeout2, unit);
    }

    @Override
    @Nonnull
    public CommandCreateAction setName(@Nonnull String name) {
        this.data.setName(name);
        return this;
    }

    @Override
    @Nonnull
    public CommandCreateAction setNameLocalization(@Nonnull DiscordLocale locale, @Nonnull String name) {
        this.data.setNameLocalization(locale, name);
        return this;
    }

    @Override
    @Nonnull
    public CommandCreateAction setNameLocalizations(@Nonnull Map<DiscordLocale, String> map) {
        this.data.setNameLocalizations((Map)map);
        return this;
    }

    @Override
    @Nonnull
    public CommandCreateAction setDescription(@Nonnull String description) {
        this.data.setDescription(description);
        return this;
    }

    @Override
    @Nonnull
    public CommandCreateAction setDescriptionLocalization(@Nonnull DiscordLocale locale, @Nonnull String description) {
        this.data.setDescriptionLocalization(locale, description);
        return this;
    }

    @Override
    @Nonnull
    public CommandCreateAction setDescriptionLocalizations(@Nonnull Map<DiscordLocale, String> map) {
        this.data.setDescriptionLocalizations((Map)map);
        return this;
    }

    @Override
    @Nonnull
    public String getDescription() {
        return this.data.getDescription();
    }

    @Override
    @Nonnull
    public LocalizationMap getDescriptionLocalizations() {
        return this.data.getDescriptionLocalizations();
    }

    @Override
    public boolean removeOptions(@Nonnull Predicate<? super OptionData> condition) {
        return this.data.removeOptions(condition);
    }

    @Override
    public boolean removeSubcommands(@Nonnull Predicate<? super SubcommandData> condition) {
        return this.data.removeSubcommands(condition);
    }

    @Override
    public boolean removeSubcommandGroups(@Nonnull Predicate<? super SubcommandGroupData> condition) {
        return this.data.removeSubcommandGroups(condition);
    }

    @Override
    @Nonnull
    public List<SubcommandData> getSubcommands() {
        return this.data.getSubcommands();
    }

    @Override
    @Nonnull
    public List<SubcommandGroupData> getSubcommandGroups() {
        return this.data.getSubcommandGroups();
    }

    @Override
    @Nonnull
    public List<OptionData> getOptions() {
        return this.data.getOptions();
    }

    @Override
    @Nonnull
    public CommandCreateAction addOptions(OptionData ... options) {
        this.data.addOptions(options);
        return this;
    }

    @Override
    @Nonnull
    public CommandCreateAction addSubcommands(SubcommandData ... subcommand) {
        this.data.addSubcommands(subcommand);
        return this;
    }

    @Override
    @Nonnull
    public CommandCreateAction addSubcommandGroups(SubcommandGroupData ... group) {
        this.data.addSubcommandGroups(group);
        return this;
    }

    @Override
    public RequestBody finalizeData() {
        return this.getRequestBody(this.data.toData());
    }

    @Override
    protected void handleSuccess(Response response, Request<Command> request) {
        DataObject json = response.getObject();
        request.onSuccess(new CommandImpl(this.api, this.guild, json));
    }

    @Override
    @Nonnull
    public DataObject toData() {
        return this.data.toData();
    }
}

