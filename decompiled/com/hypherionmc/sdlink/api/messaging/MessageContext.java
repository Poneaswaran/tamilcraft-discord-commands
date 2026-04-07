/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.api.game.text.Text
 *  lombok.Generated
 *  net.kyori.adventure.text.Component
 *  net.kyori.adventure.text.TextComponent
 *  net.kyori.adventure.text.event.ClickEvent
 *  net.kyori.adventure.text.event.HoverEvent
 *  net.kyori.adventure.text.event.HoverEvent$Action
 *  net.kyori.adventure.text.event.HoverEventSource
 *  net.kyori.adventure.text.format.NamedTextColor
 *  net.kyori.adventure.text.format.Style
 *  net.kyori.adventure.text.format.TextColor
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.api.messaging;

import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.sdlink.SDLinkConstants;
import com.hypherionmc.sdlink.core.config.SDLinkConfig;
import com.hypherionmc.sdlink.core.config.impl.MessageIgnoreConfig;
import com.hypherionmc.sdlink.core.database.SDLinkAccount;
import com.hypherionmc.sdlink.core.discord.BotController;
import com.hypherionmc.sdlink.core.discord.SDLWebhookServerMember;
import com.hypherionmc.sdlink.core.managers.DatabaseManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageReference;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.EmojiManager;
import com.hypherionmc.sdlink.util.SDLinkChatUtils;
import com.hypherionmc.sdlink.util.translations.SDText;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Generated;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.Nullable;

public final class MessageContext {
    private final Member originalSender;
    private final Message originalMessage;
    @Nullable
    private String formattedMessage;
    @Nullable
    private String formattedReply;
    @Nullable
    private Member replyMember;
    private Member sender;
    final Pattern patternStart = Pattern.compile("%(.*?)(?:\\|(.*?))?%", 2);

    private void parseMessage() {
        String message;
        block7: {
            String attachmentText;
            this.sender = this.originalMessage.isWebhookMessage() ? SDLWebhookServerMember.of(this.originalMessage.getAuthor(), this.originalMessage.getGuild(), this.originalMessage.getJDA()) : this.originalSender;
            message = this.originalMessage.getContentDisplay();
            MessageReference messageReference = this.originalMessage.getMessageReference();
            Message replyReference = this.originalMessage.getReferencedMessage();
            if (messageReference != null && messageReference.getType() == MessageReference.MessageReferenceType.FORWARD) {
                message = this.originalMessage.getMessageSnapshots().get(0).getContentRaw();
            }
            if (!this.originalMessage.getAttachments().isEmpty()) {
                attachmentText = String.valueOf(SDText.translate("message.attachments", this.originalMessage.getAttachments().size()));
                String string = message = message.isEmpty() ? String.format("%s attachments", this.originalMessage.getAttachments().size()) : String.format("%s %s", message, attachmentText);
            }
            if (message.isEmpty()) {
                return;
            }
            if (replyReference != null) {
                try {
                    this.replyMember = replyReference.isWebhookMessage() ? SDLWebhookServerMember.of(replyReference.getAuthor(), replyReference.getGuild(), replyReference.getJDA()) : replyReference.getMember();
                    this.formattedReply = replyReference.getContentDisplay();
                    if (!replyReference.getAttachments().isEmpty()) {
                        attachmentText = String.valueOf(SDText.translate("message.attachments", replyReference.getAttachments().size()));
                        this.formattedReply = this.formattedReply.isEmpty() ? String.format("%s attachments", replyReference.getAttachments().size()) : String.format("%s %s", this.formattedReply, attachmentText);
                    }
                    this.formattedReply = EmojiManager.replaceAllEmojis(this.formattedReply, emoji -> !emoji.getDiscordAliases().isEmpty() ? emoji.getDiscordAliases().get(0) : emoji.getEmoji());
                }
                catch (Exception e) {
                    if (!SDLinkConfig.INSTANCE.generalConfig.debugging) break block7;
                    BotController.INSTANCE.getLogger().error("Failed to process reply formatting: {}", (Object)e.getMessage());
                }
            }
        }
        message = message.replace("\u00a7k", "#k");
        this.formattedMessage = EmojiManager.replaceAllEmojis(message, emoji -> !emoji.getDiscordAliases().isEmpty() ? emoji.getDiscordAliases().get(0) : emoji.getEmoji());
    }

    public Text getFormattedMessageComponent() {
        AtomicReference<String> user;
        block12: {
            this.parseMessage();
            if (SDLinkConfig.INSTANCE.generalConfig.debugging) {
                SDLinkConstants.LOGGER.info("Got message {} from {}", (Object)this.formattedMessage, (Object)this.sender.getEffectiveName());
            }
            user = new AtomicReference<String>(this.sender.getEffectiveName());
            try {
                if (SDLinkConfig.INSTANCE.chatConfig.useLinkedNames) {
                    List<SDLinkAccount> accounts = DatabaseManager.INSTANCE.getCollection(SDLinkAccount.class);
                    accounts.stream().filter(a -> a.getDiscordID() != null && a.getDiscordID().equals(this.sender.getId())).findFirst().ifPresent(u -> user.set(u.getInGameName()));
                }
            }
            catch (Exception e) {
                if (!SDLinkConfig.INSTANCE.generalConfig.debugging) break block12;
                SDLinkConstants.LOGGER.error("Failed to load account database: {}", (Object)e.getMessage());
            }
        }
        String mainPrefix = SDLinkConfig.INSTANCE.messageFormatting.mcPrefix.replace("%user%", user.get()).replace("%role%", this.sender.getRoles().isEmpty() ? "No Role" : this.sender.getRoles().get(0).getName());
        String prefix = SDLinkChatUtils.applyFiltering(mainPrefix, i -> i.appliesTo.isMinecraft() && i.appliesTo.appliesToUsername((MessageIgnoreConfig.Ignore)i));
        if (prefix.isEmpty()) {
            prefix = mainPrefix;
        }
        Style baseStyle = Style.empty();
        Text component = this.parsePlaceholders(Text.formatted((String)prefix), baseStyle, this.sender);
        this.formattedMessage = SDLinkChatUtils.applyFiltering(this.formattedMessage, i -> i.appliesTo.isMinecraft() && i.appliesTo.appliesToChat((MessageIgnoreConfig.Ignore)i));
        if (this.formattedMessage.isEmpty()) {
            return null;
        }
        Text finalComponent = component.append(SDLinkChatUtils.parseChatLinks(this.formattedMessage));
        if (this.formattedReply != null && !this.formattedReply.isEmpty() && this.replyMember != null) {
            String newReply = SDLinkChatUtils.applyFiltering(this.formattedReply, i -> i.appliesTo.isMinecraft() && i.appliesTo.appliesToChat((MessageIgnoreConfig.Ignore)i));
            if (newReply != null && !newReply.isEmpty()) {
                this.formattedReply = newReply;
            }
            finalComponent = this.parsePlaceholders(Text.formatted((String)SDLinkConfig.INSTANCE.messageFormatting.mcReplyFormatting), Style.style().build(), this.replyMember, this.formattedReply).append(finalComponent);
            if (this.formattedReply.length() > 30) {
                finalComponent.hoverEvent((HoverEventSource)HoverEvent.hoverEvent((HoverEvent.Action)HoverEvent.Action.SHOW_TEXT, (Object)SDLinkChatUtils.parseChatLinks(this.formattedReply).getComponent()));
            }
            try {
                finalComponent.clickEvent(ClickEvent.openUrl((URL)new URL(this.getOriginalMessage().getReferencedMessage().getJumpUrl())));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if ((this.formattedReply == null || this.formattedReply.isEmpty()) && SDLinkConfig.INSTANCE.chatConfig.showDiscordInfo) {
            this.appendDiscordInfo(this.sender, finalComponent);
        }
        return finalComponent;
    }

    private Text parsePlaceholders(Text inComponent, Style baseStyle, Member member) {
        return this.parsePlaceholders(inComponent, baseStyle, member, null);
    }

    private Text parsePlaceholders(Text component, Style baseStyle, Member member, @Nullable String message) {
        Text result = Text.empty();
        Component component2 = component.getComponent();
        if (component2 instanceof TextComponent) {
            TextComponent textComponent = (TextComponent)component2;
            String content = textComponent.content();
            int lastIndex = 0;
            Matcher matcher = this.patternStart.matcher(content);
            while (matcher.find()) {
                String var;
                if (matcher.start() > lastIndex) {
                    result.append(Text.literal((String)content.substring(lastIndex, matcher.start())).style(baseStyle.merge(textComponent.style())));
                }
                if ((var = matcher.group(1)) != null) {
                    switch (var) {
                        case "color": {
                            baseStyle = baseStyle.color(TextColor.color((int)member.getColorRaw()));
                            break;
                        }
                        case "end_color": {
                            baseStyle = baseStyle.color((TextColor)NamedTextColor.WHITE);
                            break;
                        }
                        case "replier_name": {
                            result.append(Text.literal((String)member.getEffectiveName()).style(baseStyle.merge(textComponent.style())));
                            break;
                        }
                        case "message_summary": {
                            if (message == null) break;
                            Text msgComponent = SDLinkChatUtils.parseChatLinks(message);
                            if (message.length() > 30) {
                                msgComponent = Text.literal((String)(msgComponent.asString().substring(0, 30) + "...")).style(msgComponent.style());
                            }
                            result.append(msgComponent.applyFallbackStyle(baseStyle.merge(textComponent.style())));
                            break;
                        }
                        default: {
                            result.append(Text.literal((String)("%" + var + "%")).style(baseStyle.merge(textComponent.style())));
                        }
                    }
                }
                lastIndex = matcher.end();
            }
            if (lastIndex < content.length()) {
                result.append(Text.literal((String)content.substring(lastIndex)).style(baseStyle.merge(textComponent.style())));
            }
        }
        for (Text child : component.children()) {
            result.append(this.parsePlaceholders(child, baseStyle.merge(component.style()), member, message));
        }
        return result;
    }

    private Text appendDiscordInfo(Member member, Text currentComponent) {
        Text memberDetails = Text.empty();
        memberDetails.append(Text.literal((String)(SDText.translate("tooltip.display_name") + ": ")).style(Style.style().color((TextColor)NamedTextColor.YELLOW).build()).append(Text.literal((String)member.getEffectiveName()).style(Style.style().color((TextColor)NamedTextColor.WHITE).build())).appendNewline()).append(Text.literal((String)(SDText.translate("tooltip.username") + ": ")).style(Style.style().color((TextColor)NamedTextColor.YELLOW).build()).append(Text.literal((String)member.getUser().getName()).style(Style.style().color((TextColor)NamedTextColor.WHITE).build())).appendNewline()).append(Text.literal((String)(SDText.translate("tooltip.roles") + ": ")).style(Style.style().color((TextColor)NamedTextColor.YELLOW).build()).append(Text.literal((String)String.join((CharSequence)", ", member.getRoles().stream().map(Role::getName).toList())).style(Style.style().color((TextColor)NamedTextColor.WHITE).build())));
        currentComponent.hoverEvent((HoverEventSource)HoverEvent.hoverEvent((HoverEvent.Action)HoverEvent.Action.SHOW_TEXT, (Object)memberDetails.getComponent()));
        return currentComponent;
    }

    @Generated
    private MessageContext(Member originalSender, Message originalMessage) {
        this.originalSender = originalSender;
        this.originalMessage = originalMessage;
    }

    @Generated
    public static MessageContext of(Member originalSender, Message originalMessage) {
        return new MessageContext(originalSender, originalMessage);
    }

    @Generated
    public Member getOriginalSender() {
        return this.originalSender;
    }

    @Generated
    public Message getOriginalMessage() {
        return this.originalMessage;
    }

    @Nullable
    @Generated
    public String getFormattedMessage() {
        return this.formattedMessage;
    }

    @Nullable
    @Generated
    public String getFormattedReply() {
        return this.formattedReply;
    }

    @Nullable
    @Generated
    public Member getReplyMember() {
        return this.replyMember;
    }

    @Generated
    public Member getSender() {
        return this.sender;
    }

    @Generated
    public Pattern getPatternStart() {
        return this.patternStart;
    }
}

