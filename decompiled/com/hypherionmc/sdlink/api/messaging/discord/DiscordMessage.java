/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringEscapeUtils
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.api.messaging.discord;

import com.hypherionmc.sdlink.api.accounts.DiscordAuthor;
import com.hypherionmc.sdlink.api.messaging.MessageType;
import com.hypherionmc.sdlink.api.messaging.discord.DiscordMessageBuilder;
import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.core.config.impl.MessageChannelConfig;
import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.core.managers.CacheManager;
import com.hypherionmc.sdlink.core.managers.ChannelManager;
import com.hypherionmc.sdlink.core.managers.EmbedManager;
import com.hypherionmc.sdlink.core.messaging.embeds.DiscordEmbed;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.receive.ReadonlyMessage;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.AllowedMentions;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.WebhookEmbed;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.EmbedBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.util.DestinationHolder;
import com.hypherionmc.sdlink.util.SDLinkUtils;
import java.awt.Color;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;

public final class DiscordMessage {
    private final MessageType messageType;
    private final DiscordAuthor author;
    private final String message;
    private final Runnable afterSend;

    DiscordMessage(DiscordMessageBuilder builder) {
        this.messageType = builder.getMessageType();
        this.author = builder.getAuthor();
        this.message = builder.getMessage();
        this.afterSend = builder.getAfterSend();
    }

    public void sendMessage() {
        block8: {
            if (!BotController.INSTANCE.isBotReady()) {
                return;
            }
            if (this.message.isEmpty()) {
                return;
            }
            BotController.INSTANCE.getSpamManager().receiveMessage(String.format("%s:%s", this.author.getUsername(), this.message));
            if (BotController.INSTANCE.getSpamManager().isBlocked(String.format("%s:%s", this.author.getUsername(), this.message))) {
                if (SDLinkConfig.INSTANCE.generalConfig.debugging) {
                    BotController.INSTANCE.getLogger().warn("Blocked message {} due to spam", (Object)this.message);
                }
                return;
            }
            try {
                if (this.messageType == MessageType.CONSOLE) {
                    this.sendConsoleMessage();
                } else {
                    this.sendNormalMessage();
                }
            }
            catch (Exception e) {
                this.runAfterSend();
                if (!SDLinkConfig.INSTANCE.generalConfig.debugging) break block8;
                BotController.INSTANCE.getLogger().error("Failed to send Discord Message", (Throwable)e);
            }
        }
    }

    private void sendNormalMessage() {
        DestinationHolder channel = this.resolveDestination();
        try {
            if (channel.hasWebhook() && SDLinkConfig.INSTANCE.channelsAndWebhooks.webhooks.enabled) {
                WebhookMessageBuilder builder = new WebhookMessageBuilder();
                if (this.messageType == MessageType.START || this.messageType == MessageType.STOP) {
                    builder.setAllowedMentions(AllowedMentions.all());
                } else if (this.messageType == MessageType.CHAT && SDLinkConfig.INSTANCE.chatConfig.allowMentionsFromChat && channel.channel() != null) {
                    builder.setAllowedMentions(new AllowedMentions().withParseUsers(true).withParseEveryone(false).withRoles(this.getMentionableRoles(this.message)));
                } else {
                    builder.setAllowedMentions(AllowedMentions.none());
                }
                if (this.messageType == MessageType.CHAT) {
                    builder.setUsername(SDLinkConfig.INSTANCE.channelsAndWebhooks.webhooks.webhookNameFormat.replace("%display_name%", this.author.getDisplayName().replace("\\_", "_")).replace("%mc_name%", this.author.getUsername()));
                } else {
                    builder.setUsername(this.author.getDisplayName());
                }
                if (!this.author.getAvatar().isEmpty()) {
                    builder.setAvatarUrl(this.author.getAvatar());
                }
                if (channel.useEmbed()) {
                    EmbedBuilder eb = this.buildEmbed(false, channel.embedLayout());
                    WebhookEmbed web = WebhookEmbedBuilder.fromJDA(eb.build()).build();
                    builder.addEmbeds(web);
                } else {
                    builder.setContent(this.message);
                }
                CompletableFuture<ReadonlyMessage> sender = channel.webhook().send(builder.build());
                if (this.messageType == MessageType.STOP) {
                    sender.complete(null);
                    this.runAfterSend();
                } else {
                    sender.thenRun(this::runAfterSend);
                }
            } else {
                if (channel.channel() == null) {
                    if (SDLinkConfig.INSTANCE.generalConfig.debugging) {
                        BotController.INSTANCE.getLogger().warn("Expected to get Channel for {}, but got null", (Object)this.messageType.name());
                    }
                    this.runAfterSend();
                    return;
                }
                MessageCreateBuilder builder = new MessageCreateBuilder();
                if (this.messageType == MessageType.START || this.messageType == MessageType.STOP) {
                    builder.setAllowedMentions(EnumSet.allOf(Message.MentionType.class));
                } else if (this.messageType == MessageType.CHAT && SDLinkConfig.INSTANCE.chatConfig.allowMentionsFromChat) {
                    builder.setAllowedMentions(EnumSet.of(Message.MentionType.USER));
                    builder.mentionRoles(this.getMentionableRoles(this.message));
                } else {
                    builder.setAllowedMentions(EnumSet.noneOf(Message.MentionType.class));
                }
                if (channel.useEmbed()) {
                    EmbedBuilder eb = this.buildEmbed(true, channel.embedLayout());
                    builder.setEmbeds(eb.build());
                } else {
                    String content = this.messageType == MessageType.CHAT ? SDLinkConfig.INSTANCE.messageFormatting.chat.replace("%player%", this.author.getDisplayName()).replace("%mcname%", this.author.getProfile() == null ? "Unknown" : this.author.getProfile().getName()).replace("%message%", this.message) : this.message.replace("%mcname%", this.author.getProfile() == null ? "Unknown" : this.author.getProfile().getName());
                    builder.setContent(content);
                }
                MessageCreateAction sender = channel.channel().sendMessage(builder.build());
                if (this.messageType == MessageType.STOP) {
                    sender.complete();
                    this.runAfterSend();
                } else {
                    sender.queue(success -> this.runAfterSend());
                }
            }
        }
        catch (Exception e) {
            this.runAfterSend();
        }
    }

    private Set<String> getMentionableRoles(String message) {
        return Message.MentionType.ROLE.getPattern().matcher(message).results().map(match -> match.group(1)).filter(this::isRoleMentionable).distinct().limit(100L).collect(Collectors.toSet());
    }

    private boolean isRoleMentionable(String roleId) {
        Role role;
        try {
            role = this.resolveDestination().channel().getGuild().getRoleById(roleId);
        }
        catch (NumberFormatException e) {
            return false;
        }
        if (role == null) {
            return false;
        }
        return role.isMentionable();
    }

    private void runAfterSend() {
        if (this.afterSend != null) {
            this.afterSend.run();
        }
    }

    private void sendConsoleMessage() {
        block5: {
            try {
                if (!BotController.INSTANCE.isBotReady() || !SDLinkConfig.INSTANCE.chatConfig.sendConsoleMessages) {
                    return;
                }
                GuildMessageChannel channel = ChannelManager.getConsoleChannel();
                if (channel != null) {
                    channel.sendMessage(((MessageCreateBuilder)((MessageCreateBuilder)new MessageCreateBuilder().setAllowedMentions(EnumSet.noneOf(Message.MentionType.class))).setContent(this.message)).build()).queue();
                }
            }
            catch (Exception e) {
                if (!SDLinkConfig.INSTANCE.generalConfig.debugging) break block5;
                BotController.INSTANCE.getLogger().error("Failed to send console message", (Throwable)e);
            }
        }
        if (this.afterSend != null) {
            this.afterSend.run();
        }
    }

    private EmbedBuilder buildEmbed(boolean withAuthor, String key) {
        String embedJson = EmbedManager.getEmbed(key);
        if (embedJson == null || embedJson.isEmpty()) {
            EmbedBuilder builder = new EmbedBuilder();
            if (withAuthor) {
                builder.setAuthor(this.author.getDisplayName(), null, this.author.getAvatar().isEmpty() ? null : this.author.getAvatar());
            }
            builder.setDescription(this.message);
            return builder;
        }
        embedJson = embedJson.replace("%author%", StringEscapeUtils.escapeJson((String)this.author.getDisplayName().replace("\\_", "_"))).replace("%avatar%", this.author.getAvatar()).replace("%message_contents%", StringEscapeUtils.escapeJson((String)this.message)).replace("%player_avatar%", this.author.getRealPlayerAvatar()).replace("%role_color%", String.valueOf(this.author.getColor())).replace("%player_name%", StringEscapeUtils.escapeJson((String)this.author.getRealPlayerName().replace("\\_", "_"))).replace("%current_time%", String.valueOf(Instant.now().getEpochSecond())).replace("%username%", StringEscapeUtils.escapeJson((String)this.author.getUsername().replace("\\_", "_")));
        DiscordEmbed embed = (DiscordEmbed)EmbedManager.gson.fromJson(embedJson, DiscordEmbed.class);
        return this.fromData(embed);
    }

    private DestinationHolder resolveDestination() {
        MessageChannelConfig.DestinationObject destinationObject = CacheManager.messageDestinations.get((Object)this.messageType);
        if (destinationObject != null) {
            return destinationObject.toHolder(this.messageType);
        }
        return SDLinkConfig.INSTANCE.messageDestinations.chat.toHolder(MessageType.CHAT);
    }

    @NotNull
    private EmbedBuilder fromData(@NotNull DiscordEmbed data) {
        Checks.notNull(data, "embed");
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(SDLinkUtils.getOrElse(data.title, null));
        builder.setUrl(SDLinkUtils.getOrElse(data.url, null));
        builder.setDescription(SDLinkUtils.getOrElse(data.description, null));
        if (data.timestamp == 1) {
            builder.setTimestamp(Instant.now());
        } else if (data.timestamp != 0) {
            builder.setTimestamp(OffsetDateTime.parse(String.valueOf(data.timestamp)));
        }
        if (SDLinkUtils.getOrElse(data.color, "#000000").startsWith("#")) {
            builder.setColor(Color.decode(SDLinkUtils.getOrElse(data.color, "#000000")));
        } else {
            builder.setColor(Integer.parseInt(SDLinkUtils.getOrElse(data.color, "#000000"), 16));
        }
        if (data.thumbnail != null) {
            builder.setThumbnail(SDLinkUtils.getOrElse(data.thumbnail.url, null));
        }
        if (data.author != null) {
            builder.setAuthor(SDLinkUtils.getOrElse(data.author.name, null), SDLinkUtils.getOrElse(data.author.url, null), SDLinkUtils.getOrElse(data.author.icon_url, null));
        }
        if (data.footer != null) {
            builder.setFooter(SDLinkUtils.getOrElse(data.footer.text, ""), SDLinkUtils.getOrElse(data.footer.icon_url, null));
        }
        if (data.image != null) {
            builder.setImage(SDLinkUtils.getOrElse(data.image.url, null));
        }
        if (data.fields != null && !data.fields.isEmpty()) {
            for (DiscordEmbed.Field field : data.fields) {
                builder.addField(SDLinkUtils.getOrElse(field.name, "\u200e"), SDLinkUtils.getOrElse(field.value, "\u200e"), field.inline);
            }
        }
        return builder;
    }
}

