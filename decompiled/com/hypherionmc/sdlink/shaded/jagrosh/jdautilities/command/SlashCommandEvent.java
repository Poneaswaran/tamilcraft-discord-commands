/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Contract
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.OptionMapping;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CommandClient;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SlashCommandEvent
extends SlashCommandInteractionEvent {
    private final CommandClient client;

    public SlashCommandEvent(SlashCommandInteractionEvent event, CommandClient client) {
        super(event.getJDA(), event.getResponseNumber(), event);
        this.client = client;
    }

    public CommandClient getClient() {
        return this.client;
    }

    @Nullable
    public String optString(@NotNull String key) {
        return this.optString(key, null);
    }

    @Nullable
    @Contract(value="_, !null -> !null")
    public String optString(@NotNull String key, @Nullable String defaultValue) {
        return this.getOption(key, defaultValue, OptionMapping::getAsString);
    }

    public boolean optBoolean(@NotNull String key) {
        return this.optBoolean(key, false);
    }

    public boolean optBoolean(@NotNull String key, boolean defaultValue) {
        return this.getOption(key, defaultValue, OptionMapping::getAsBoolean);
    }

    public long optLong(@NotNull String key) {
        return this.optLong(key, 0L);
    }

    public long optLong(@NotNull String key, long defaultValue) {
        return this.getOption(key, defaultValue, OptionMapping::getAsLong);
    }

    public double optDouble(@NotNull String key) {
        return this.optDouble(key, 0.0);
    }

    public double optDouble(@NotNull String key, double defaultValue) {
        return this.getOption(key, defaultValue, OptionMapping::getAsDouble);
    }

    @Nullable
    public GuildChannel optGuildChannel(@NotNull String key) {
        return this.optGuildChannel(key, null);
    }

    @Nullable
    @Contract(value="_, !null -> !null")
    public GuildChannel optGuildChannel(@NotNull String key, @Nullable GuildChannel defaultValue) {
        if (!this.isFromGuild()) {
            return defaultValue;
        }
        return this.getOption(key, defaultValue, (? super OptionMapping optionMapping) -> optionMapping.getAsChannel().asStandardGuildChannel());
    }

    @Nullable
    public Member optMember(@NotNull String key) {
        return this.optMember(key, null);
    }

    @Nullable
    @Contract(value="_, !null -> !null")
    public Member optMember(@NotNull String key, @Nullable Member defaultValue) {
        if (!this.isFromGuild()) {
            return defaultValue;
        }
        return this.getOption(key, defaultValue, OptionMapping::getAsMember);
    }

    @Nullable
    public IMentionable optMentionable(@NotNull String key) {
        return this.optMentionable(key, null);
    }

    @Nullable
    @Contract(value="_, !null -> !null")
    public IMentionable optMentionable(@NotNull String key, @Nullable IMentionable defaultValue) {
        return this.getOption(key, defaultValue, OptionMapping::getAsMentionable);
    }

    @Nullable
    public Role optRole(@NotNull String key) {
        return this.optRole(key, null);
    }

    @Nullable
    @Contract(value="_, !null -> !null")
    public Role optRole(@NotNull String key, @Nullable Role defaultValue) {
        if (!this.isFromGuild()) {
            return defaultValue;
        }
        return this.getOption(key, defaultValue, OptionMapping::getAsRole);
    }

    @Nullable
    public User optUser(@NotNull String key) {
        return this.optUser(key, null);
    }

    @Nullable
    @Contract(value="_, !null -> !null")
    public User optUser(@NotNull String key, @Nullable User defaultValue) {
        return this.getOption(key, defaultValue, OptionMapping::getAsUser);
    }

    @Nullable
    public MessageChannel optMessageChannel(@NotNull String key) {
        return this.optMessageChannel(key, null);
    }

    @Nullable
    @Contract(value="_, !null -> !null")
    public MessageChannel optMessageChannel(@NotNull String key, @Nullable MessageChannel defaultValue) {
        return this.getOption(key, defaultValue, (? super OptionMapping optionMapping) -> optionMapping.getAsChannel().asGuildMessageChannel());
    }

    @Nullable
    public Message.Attachment optAttachment(@NotNull String key) {
        return this.optAttachment(key, null);
    }

    @Nullable
    @Contract(value="_, !null -> !null")
    public Message.Attachment optAttachment(@NotNull String key, @Nullable Message.Attachment defaultValue) {
        return this.getOption(key, defaultValue, OptionMapping::getAsAttachment);
    }

    public boolean hasOption(@NotNull String key) {
        return this.getOption(key) != null;
    }

    public boolean isFromType(ChannelType channelType) {
        return this.getChannelType() == channelType;
    }

    public TextChannel getTextChannel() {
        return this.getChannel().asTextChannel();
    }
}

