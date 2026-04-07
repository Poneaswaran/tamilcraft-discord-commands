/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  discord4j.core.spec.MessageCreateSpec
 *  discord4j.core.spec.MessageEditSpec
 *  discord4j.discordjson.json.AllowedMentionsData
 *  discord4j.discordjson.json.MessageCreateRequest
 *  discord4j.discordjson.json.MessageEditRequest
 *  discord4j.discordjson.possible.Possible
 *  discord4j.rest.util.MultipartRequest
 *  org.javacord.api.entity.DiscordEntity
 *  org.javacord.api.entity.message.Message
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send;

import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.AllowedMentions;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.MessageAttachment;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.WebhookEmbed;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.WebhookMessage;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateData;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.core.spec.MessageEditSpec;
import discord4j.discordjson.json.AllowedMentionsData;
import discord4j.discordjson.json.MessageCreateRequest;
import discord4j.discordjson.json.MessageEditRequest;
import discord4j.discordjson.possible.Possible;
import discord4j.rest.util.MultipartRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.javacord.api.entity.DiscordEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WebhookMessageBuilder {
    protected final StringBuilder content = new StringBuilder();
    protected final List<WebhookEmbed> embeds = new LinkedList<WebhookEmbed>();
    protected final MessageAttachment[] files = new MessageAttachment[10];
    protected AllowedMentions allowedMentions = AllowedMentions.all();
    protected String username;
    protected String avatarUrl;
    protected String threadName;
    protected boolean isTTS;
    protected int flags;
    private int fileIndex = 0;

    public boolean isEmpty() {
        return this.content.length() == 0 && this.embeds.isEmpty() && this.getFileAmount() == 0;
    }

    public int getFileAmount() {
        return this.fileIndex;
    }

    @NotNull
    public WebhookMessageBuilder reset() {
        this.content.setLength(0);
        this.resetEmbeds();
        this.resetFiles();
        this.username = null;
        this.avatarUrl = null;
        this.isTTS = false;
        this.threadName = null;
        return this;
    }

    @NotNull
    public WebhookMessageBuilder resetFiles() {
        for (int i = 0; i < 10; ++i) {
            this.files[i] = null;
        }
        this.fileIndex = 0;
        return this;
    }

    @NotNull
    public WebhookMessageBuilder resetEmbeds() {
        this.embeds.clear();
        return this;
    }

    @NotNull
    public WebhookMessageBuilder setAllowedMentions(@NotNull AllowedMentions mentions) {
        this.allowedMentions = Objects.requireNonNull(mentions);
        return this;
    }

    @NotNull
    public WebhookMessageBuilder addEmbeds(WebhookEmbed ... embeds) {
        Objects.requireNonNull(embeds, "Embeds");
        if (this.embeds.size() + embeds.length > 10) {
            throw new IllegalStateException("Cannot add more than 10 embeds to a message");
        }
        for (WebhookEmbed embed : embeds) {
            Objects.requireNonNull(embed, "Embed");
            this.embeds.add(embed);
        }
        return this;
    }

    @NotNull
    public WebhookMessageBuilder addEmbeds(@NotNull Collection<? extends WebhookEmbed> embeds) {
        Objects.requireNonNull(embeds, "Embeds");
        if (this.embeds.size() + embeds.size() > 10) {
            throw new IllegalStateException("Cannot add more than 10 embeds to a message");
        }
        for (WebhookEmbed webhookEmbed : embeds) {
            Objects.requireNonNull(webhookEmbed, "Embed");
            this.embeds.add(webhookEmbed);
        }
        return this;
    }

    @NotNull
    public WebhookMessageBuilder setContent(@Nullable String content) {
        if (content != null && content.length() > 2000) {
            throw new IllegalArgumentException("Content may not exceed 2000 characters!");
        }
        this.content.setLength(0);
        if (content != null && !content.isEmpty()) {
            this.content.append(content);
        }
        return this;
    }

    @NotNull
    public WebhookMessageBuilder append(@NotNull String content) {
        Objects.requireNonNull(content, "Content");
        if (this.content.length() + content.length() > 2000) {
            throw new IllegalArgumentException("Content may not exceed 2000 characters!");
        }
        this.content.append(content);
        return this;
    }

    @NotNull
    public WebhookMessageBuilder setUsername(@Nullable String username) {
        this.username = username == null || username.trim().isEmpty() ? null : username.trim();
        return this;
    }

    @NotNull
    public WebhookMessageBuilder setAvatarUrl(@Nullable String avatarUrl) {
        this.avatarUrl = avatarUrl == null || avatarUrl.trim().isEmpty() ? null : avatarUrl.trim();
        return this;
    }

    @NotNull
    public WebhookMessageBuilder setTTS(boolean tts) {
        this.isTTS = tts;
        return this;
    }

    @NotNull
    public WebhookMessageBuilder setEphemeral(boolean ephemeral) {
        this.flags = ephemeral ? (this.flags |= 0x40) : (this.flags &= 0xFFFFFFBF);
        return this;
    }

    @NotNull
    public WebhookMessageBuilder addFile(@NotNull File file) {
        Objects.requireNonNull(file, "File");
        return this.addFile(file.getName(), file);
    }

    @NotNull
    public WebhookMessageBuilder addFile(@NotNull String name, @NotNull File file) {
        Objects.requireNonNull(file, "File");
        Objects.requireNonNull(name, "Name");
        if (!file.exists() || !file.canRead()) {
            throw new IllegalArgumentException("File must exist and be readable");
        }
        if (this.fileIndex >= 10) {
            throw new IllegalStateException("Cannot add more than 10 attachments to a message");
        }
        try {
            MessageAttachment attachment = new MessageAttachment(name, file);
            this.files[this.fileIndex++] = attachment;
            return this;
        }
        catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @NotNull
    public WebhookMessageBuilder addFile(@NotNull String name, @NotNull byte[] data) {
        Objects.requireNonNull(data, "Data");
        Objects.requireNonNull(name, "Name");
        if (this.fileIndex >= 10) {
            throw new IllegalStateException("Cannot add more than 10 attachments to a message");
        }
        MessageAttachment attachment = new MessageAttachment(name, data);
        this.files[this.fileIndex++] = attachment;
        return this;
    }

    @NotNull
    public WebhookMessageBuilder addFile(@NotNull String name, @NotNull InputStream data) {
        Objects.requireNonNull(data, "InputStream");
        Objects.requireNonNull(name, "Name");
        if (this.fileIndex >= 10) {
            throw new IllegalStateException("Cannot add more than 10 attachments to a message");
        }
        try {
            MessageAttachment attachment = new MessageAttachment(name, data);
            this.files[this.fileIndex++] = attachment;
            return this;
        }
        catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @NotNull
    public WebhookMessageBuilder setThreadName(@Nullable String name) {
        this.threadName = name;
        return this;
    }

    @NotNull
    public WebhookMessage build() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Cannot build an empty message!");
        }
        return new WebhookMessage(this.username, this.avatarUrl, this.content.toString(), this.embeds, this.isTTS, this.fileIndex == 0 ? null : Arrays.copyOf(this.files, this.fileIndex), this.allowedMentions, this.flags, this.threadName);
    }

    @NotNull
    public static WebhookMessageBuilder fromJDA(@NotNull Message message) {
        return WebhookMessageBuilder.fromJDA(MessageCreateData.fromMessage(message));
    }

    @NotNull
    public static WebhookMessageBuilder fromJDA(@NotNull MessageCreateData message) {
        WebhookMessageBuilder builder = new WebhookMessageBuilder();
        builder.setTTS(message.isTTS());
        builder.setContent(message.getContent());
        message.getEmbeds().forEach(embed -> builder.addEmbeds(WebhookEmbedBuilder.fromJDA(embed).build()));
        EnumSet<Message.MentionType> allowedMentions = message.getAllowedMentions();
        Set<String> mentionedUsers = message.getMentionedUsers();
        Set<String> mentionedRoles = message.getMentionedRoles();
        builder.setAllowedMentions(AllowedMentions.none().withUsers(mentionedUsers).withRoles(mentionedRoles).withParseEveryone(allowedMentions.contains((Object)Message.MentionType.EVERYONE)).withParseRoles(allowedMentions.contains((Object)Message.MentionType.ROLE)).withParseUsers(allowedMentions.contains((Object)Message.MentionType.USER)));
        return builder;
    }

    @NotNull
    public static WebhookMessageBuilder fromJavacord(@NotNull org.javacord.api.entity.message.Message message) {
        WebhookMessageBuilder builder = new WebhookMessageBuilder();
        builder.setTTS(message.isTts());
        builder.setContent(message.getContent());
        message.getEmbeds().forEach(embed -> builder.addEmbeds(WebhookEmbedBuilder.fromJavacord(embed).build()));
        AllowedMentions allowedMentions = AllowedMentions.none();
        allowedMentions.withUsers(message.getMentionedUsers().stream().map(DiscordEntity::getIdAsString).collect(Collectors.toList()));
        allowedMentions.withRoles(message.getMentionedRoles().stream().map(DiscordEntity::getIdAsString).collect(Collectors.toList()));
        allowedMentions.withParseEveryone(message.mentionsEveryone());
        builder.setAllowedMentions(allowedMentions);
        return builder;
    }

    @Deprecated
    @NotNull
    public static WebhookMessageBuilder fromD4J(@NotNull Consumer<? super MessageCreateSpec> callback) {
        throw new UnsupportedOperationException("Cannot build messages via consumers in Discord4J 3.2.0! Please change to fromD4J(spec)");
    }

    @NotNull
    public static WebhookMessageBuilder fromD4J(@NotNull MessageCreateSpec spec) {
        WebhookMessageBuilder builder = new WebhookMessageBuilder();
        MultipartRequest data = spec.asRequest();
        data.getFiles().forEach(tuple -> builder.addFile((String)tuple.getT1(), (InputStream)tuple.getT2()));
        MessageCreateRequest jsonPayload = (MessageCreateRequest)data.getJsonPayload();
        if (jsonPayload == null) {
            return builder;
        }
        Possible content = jsonPayload.content();
        Possible embeds = jsonPayload.embeds();
        Possible tts = jsonPayload.tts();
        Possible allowedMentions = jsonPayload.allowedMentions();
        if (!content.isAbsent()) {
            builder.setContent((String)content.get());
        }
        if (!tts.isAbsent()) {
            builder.setTTS((Boolean)tts.get());
        }
        if (!embeds.isAbsent()) {
            builder.addEmbeds(((List)embeds.get()).stream().map(WebhookEmbedBuilder::fromD4J).map(WebhookEmbedBuilder::build).collect(Collectors.toList()));
        }
        if (!allowedMentions.isAbsent()) {
            AllowedMentionsData mentions = (AllowedMentionsData)allowedMentions.get();
            AllowedMentions whitelist = AllowedMentions.none();
            if (!mentions.users().isAbsent()) {
                whitelist.withUsers((Collection)mentions.users().get());
            }
            if (!mentions.roles().isAbsent()) {
                whitelist.withRoles((Collection)mentions.roles().get());
            }
            if (!mentions.parse().isAbsent()) {
                List parse = (List)mentions.parse().get();
                whitelist.withParseRoles(parse.contains("roles"));
                whitelist.withParseEveryone(parse.contains("everyone"));
                whitelist.withParseUsers(parse.contains("users"));
            }
            builder.setAllowedMentions(whitelist);
        }
        return builder;
    }

    @NotNull
    public static WebhookMessageBuilder fromD4J(@NotNull MessageEditSpec spec) {
        WebhookMessageBuilder builder = new WebhookMessageBuilder();
        MultipartRequest data = spec.asRequest();
        data.getFiles().forEach(tuple -> builder.addFile((String)tuple.getT1(), (InputStream)tuple.getT2()));
        MessageEditRequest jsonPayload = (MessageEditRequest)data.getJsonPayload();
        if (jsonPayload == null) {
            return builder;
        }
        Possible content = jsonPayload.content();
        Possible embeds = jsonPayload.embeds();
        Possible allowedMentions = jsonPayload.allowedMentions();
        if (!content.isAbsent() && ((Optional)content.get()).isPresent()) {
            builder.setContent((String)((Optional)content.get()).get());
        }
        if (!embeds.isAbsent() && ((Optional)embeds.get()).isPresent()) {
            builder.addEmbeds(((List)((Optional)embeds.get()).get()).stream().map(WebhookEmbedBuilder::fromD4J).map(WebhookEmbedBuilder::build).collect(Collectors.toList()));
        }
        if (!allowedMentions.isAbsent() && ((Optional)allowedMentions.get()).isPresent()) {
            AllowedMentionsData mentions = (AllowedMentionsData)((Optional)allowedMentions.get()).get();
            AllowedMentions whitelist = AllowedMentions.none();
            if (!mentions.users().isAbsent()) {
                whitelist.withUsers((Collection)mentions.users().get());
            }
            if (!mentions.roles().isAbsent()) {
                whitelist.withRoles((Collection)mentions.roles().get());
            }
            if (!mentions.parse().isAbsent()) {
                List parse = (List)mentions.parse().get();
                whitelist.withParseRoles(parse.contains("roles"));
                whitelist.withParseEveryone(parse.contains("everyone"));
                whitelist.withParseUsers(parse.contains("users"));
            }
            builder.setAllowedMentions(whitelist);
        }
        return builder;
    }
}

