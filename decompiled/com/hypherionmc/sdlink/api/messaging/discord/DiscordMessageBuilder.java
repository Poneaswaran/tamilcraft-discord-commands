/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package com.hypherionmc.sdlink.api.messaging.discord;

import com.hypherionmc.sdlink.api.accounts.DiscordAuthor;
import com.hypherionmc.sdlink.api.accounts.DiscordUser;
import com.hypherionmc.sdlink.api.accounts.MinecraftAccount;
import com.hypherionmc.sdlink.api.messaging.MessageType;
import com.hypherionmc.sdlink.api.messaging.discord.DiscordMessage;
import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.core.config.impl.MessageIgnoreConfig;
import com.hypherionmc.sdlink.util.SDLinkChatUtils;
import java.util.Objects;
import lombok.Generated;

public final class DiscordMessageBuilder {
    private final MessageType messageType;
    private DiscordAuthor author;
    private String message;
    private Runnable afterSend;

    public DiscordMessageBuilder(MessageType messageType) {
        this.messageType = messageType;
    }

    public DiscordMessageBuilder author(DiscordAuthor author) {
        this.author = author;
        if (author.getUsername().equalsIgnoreCase("server")) {
            this.author = DiscordAuthor.getServer();
        }
        if (SDLinkConfig.INSTANCE.chatConfig.useLinkedNames && !Objects.equals(this.author, DiscordAuthor.getServer()) && author.getProfile() != null) {
            MinecraftAccount account = MinecraftAccount.of(author.getProfile());
            DiscordUser discordUser = account.getDiscordUser();
            if (account != null && discordUser != null) {
                String name = discordUser.getEffectiveName();
                String avatar = discordUser.getAvatarUrl();
                if (SDLinkConfig.INSTANCE.chatConfig.useLinkedAvatar) {
                    this.author.overrideData(name, avatar);
                } else {
                    this.author.overrideData(name);
                }
                this.author.setColor(discordUser.getRoleColor());
            }
        }
        if (this.messageType == MessageType.CHAT && SDLinkConfig.INSTANCE.channelsAndWebhooks.webhooks.useServerForChat && SDLinkConfig.INSTANCE.channelsAndWebhooks.webhooks.enabled && !SDLinkConfig.INSTANCE.channelsAndWebhooks.webhooks.chatWebhook.trim().isEmpty()) {
            this.author = DiscordAuthor.getServer();
        }
        return this;
    }

    public DiscordMessageBuilder message(String message) {
        this.message = SDLinkChatUtils.applyFiltering(message, i -> this.messageType == MessageType.CONSOLE ? i.appliesTo.appliesToConsole((MessageIgnoreConfig.Ignore)i) : i.appliesTo.appliesToChat((MessageIgnoreConfig.Ignore)i));
        this.message = this.message.replace("\u00a7k", "#k");
        return this;
    }

    public DiscordMessageBuilder afterSend(Runnable afterSend) {
        this.afterSend = afterSend;
        return this;
    }

    public DiscordMessage build() {
        if (this.author == null) {
            this.author = DiscordAuthor.getServer();
        }
        if (this.message == null) {
            this.message = "";
        }
        return new DiscordMessage(this);
    }

    @Generated
    public MessageType getMessageType() {
        return this.messageType;
    }

    @Generated
    public DiscordAuthor getAuthor() {
        return this.author;
    }

    @Generated
    public String getMessage() {
        return this.message;
    }

    @Generated
    public Runnable getAfterSend() {
        return this.afterSend;
    }
}

