/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IncomingWebhookClient;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageEmbed;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Webhook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.LayoutComponent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookMessageDeleteAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookMessageEditAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.WebhookMessageRetrieveAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.AttachedFile;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.FileUpload;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageEditData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessagePollData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.IncomingWebhookClientImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;

public interface WebhookClient<T>
extends ISnowflake {
    @Nullable
    public String getToken();

    @Nonnull
    public JDA getJDA();

    @Nonnull
    @CheckReturnValue
    public WebhookMessageCreateAction<T> sendMessage(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    public WebhookMessageCreateAction<T> sendMessage(@Nonnull MessageCreateData var1);

    @Nonnull
    @CheckReturnValue
    public WebhookMessageCreateAction<T> sendMessagePoll(@Nonnull MessagePollData var1);

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageCreateAction<T> sendMessageFormat(@Nonnull String format, Object ... args2) {
        Checks.notNull(format, "Format String");
        return this.sendMessage(String.format(format, args2));
    }

    @Nonnull
    @CheckReturnValue
    public WebhookMessageCreateAction<T> sendMessageEmbeds(@Nonnull Collection<? extends MessageEmbed> var1);

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageCreateAction<T> sendMessageEmbeds(@Nonnull MessageEmbed embed, MessageEmbed ... embeds) {
        Checks.notNull(embed, "MessageEmbeds");
        Checks.noneNull(embeds, "MessageEmbeds");
        ArrayList<MessageEmbed> embedList = new ArrayList<MessageEmbed>();
        embedList.add(embed);
        Collections.addAll(embedList, embeds);
        return this.sendMessageEmbeds(embedList);
    }

    @Nonnull
    @CheckReturnValue
    public WebhookMessageCreateAction<T> sendMessageComponents(@Nonnull Collection<? extends LayoutComponent> var1);

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageCreateAction<T> sendMessageComponents(@Nonnull LayoutComponent component, LayoutComponent ... other) {
        Checks.notNull(component, "LayoutComponents");
        Checks.noneNull(other, "LayoutComponents");
        ArrayList<LayoutComponent> embedList = new ArrayList<LayoutComponent>();
        embedList.add(component);
        Collections.addAll(embedList, other);
        return this.sendMessageComponents(embedList);
    }

    @Nonnull
    @CheckReturnValue
    public WebhookMessageCreateAction<T> sendFiles(@Nonnull Collection<? extends FileUpload> var1);

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageCreateAction<T> sendFiles(FileUpload ... files) {
        Checks.noneNull(files, "Files");
        Checks.notEmpty(files, "Files");
        return this.sendFiles(Arrays.asList(files));
    }

    @Nonnull
    @CheckReturnValue
    public WebhookMessageEditAction<T> editMessageById(@Nonnull String var1, @Nonnull String var2);

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<T> editMessageById(long messageId, @Nonnull String content) {
        return this.editMessageById(Long.toUnsignedString(messageId), content);
    }

    @Nonnull
    @CheckReturnValue
    public WebhookMessageEditAction<T> editMessageById(@Nonnull String var1, @Nonnull MessageEditData var2);

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<T> editMessageById(long messageId, MessageEditData message) {
        return this.editMessageById(Long.toUnsignedString(messageId), message);
    }

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<T> editMessageFormatById(@Nonnull String messageId, @Nonnull String format, Object ... args2) {
        Checks.notNull(format, "Format String");
        return this.editMessageById(messageId, String.format(format, args2));
    }

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<T> editMessageFormatById(long messageId, @Nonnull String format, Object ... args2) {
        return this.editMessageFormatById(Long.toUnsignedString(messageId), format, args2);
    }

    @Nonnull
    @CheckReturnValue
    public WebhookMessageEditAction<T> editMessageEmbedsById(@Nonnull String var1, @Nonnull Collection<? extends MessageEmbed> var2);

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<T> editMessageEmbedsById(long messageId, @Nonnull Collection<? extends MessageEmbed> embeds) {
        return this.editMessageEmbedsById(Long.toUnsignedString(messageId), embeds);
    }

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<T> editMessageEmbedsById(@Nonnull String messageId, MessageEmbed ... embeds) {
        Checks.noneNull(embeds, "MessageEmbeds");
        return this.editMessageEmbedsById(messageId, Arrays.asList(embeds));
    }

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<T> editMessageEmbedsById(long messageId, MessageEmbed ... embeds) {
        return this.editMessageEmbedsById(Long.toUnsignedString(messageId), embeds);
    }

    @Nonnull
    @CheckReturnValue
    public WebhookMessageEditAction<T> editMessageComponentsById(@Nonnull String var1, @Nonnull Collection<? extends LayoutComponent> var2);

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<T> editMessageComponentsById(long messageId, @Nonnull Collection<? extends LayoutComponent> components) {
        return this.editMessageComponentsById(Long.toUnsignedString(messageId), components);
    }

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<T> editMessageComponentsById(@Nonnull String messageId, LayoutComponent ... components) {
        Checks.noneNull(components, "LayoutComponents");
        return this.editMessageComponentsById(messageId, Arrays.asList(components));
    }

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<T> editMessageComponentsById(long messageId, LayoutComponent ... components) {
        return this.editMessageComponentsById(Long.toUnsignedString(messageId), components);
    }

    @Nonnull
    @CheckReturnValue
    public WebhookMessageEditAction<T> editMessageAttachmentsById(@Nonnull String var1, @Nonnull Collection<? extends AttachedFile> var2);

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<T> editMessageAttachmentsById(@Nonnull String messageId, AttachedFile ... attachments) {
        Checks.noneNull(attachments, "Attachments");
        return this.editMessageAttachmentsById(messageId, Arrays.asList(attachments));
    }

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<T> editMessageAttachmentsById(long messageId, @Nonnull Collection<? extends AttachedFile> attachments) {
        return this.editMessageAttachmentsById(Long.toUnsignedString(messageId), attachments);
    }

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageEditAction<T> editMessageAttachmentsById(long messageId, AttachedFile ... attachments) {
        return this.editMessageAttachmentsById(Long.toUnsignedString(messageId), attachments);
    }

    @Nonnull
    @CheckReturnValue
    public WebhookMessageDeleteAction deleteMessageById(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    default public WebhookMessageDeleteAction deleteMessageById(long messageId) {
        return this.deleteMessageById(Long.toUnsignedString(messageId));
    }

    @Nonnull
    @CheckReturnValue
    public WebhookMessageRetrieveAction retrieveMessageById(@Nonnull String var1);

    @Nonnull
    public static IncomingWebhookClient createClient(@Nonnull JDA api, @Nonnull String url) {
        Checks.notNull(url, "URL");
        Matcher matcher = Webhook.WEBHOOK_URL.matcher(url);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Provided invalid webhook URL");
        }
        String id = matcher.group(1);
        String token = matcher.group(2);
        return WebhookClient.createClient(api, id, token);
    }

    @Nonnull
    public static IncomingWebhookClient createClient(@Nonnull JDA api, @Nonnull String webhookId, @Nonnull String webhookToken) {
        Checks.notNull(api, "JDA");
        Checks.notBlank(webhookToken, "Token");
        return new IncomingWebhookClientImpl(MiscUtil.parseSnowflake(webhookId), webhookToken, api);
    }
}

