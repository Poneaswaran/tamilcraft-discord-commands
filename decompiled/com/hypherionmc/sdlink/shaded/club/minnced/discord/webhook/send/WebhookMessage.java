/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send;

import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.IOUtil;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.receive.ReadonlyMessage;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.AllowedMentions;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.MessageAttachment;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.WebhookEmbed;
import com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.hypherionmc.sdlink.shaded.json.JSONArray;
import com.hypherionmc.sdlink.shaded.json.JSONObject;
import com.hypherionmc.sdlink.shaded.okhttp3.MultipartBody;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WebhookMessage {
    public static final int MAX_FILES = 10;
    public static final int MAX_EMBEDS = 10;
    protected final String username;
    protected final String avatarUrl;
    protected final String content;
    protected final List<WebhookEmbed> embeds;
    protected final boolean isTTS;
    protected final MessageAttachment[] attachments;
    protected final AllowedMentions allowedMentions;
    protected final int flags;
    protected final String threadName;

    protected WebhookMessage(String username, String avatarUrl, String content, List<WebhookEmbed> embeds, boolean isTTS, MessageAttachment[] files, AllowedMentions allowedMentions, int flags, String threadName) {
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.content = content;
        this.embeds = embeds;
        this.isTTS = isTTS;
        this.attachments = files;
        this.allowedMentions = allowedMentions;
        this.flags = flags;
        this.threadName = threadName;
    }

    @Nullable
    public String getUsername() {
        return this.username;
    }

    @Nullable
    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    @Nullable
    public String getContent() {
        return this.content;
    }

    @NotNull
    public List<WebhookEmbed> getEmbeds() {
        return this.embeds == null ? Collections.emptyList() : this.embeds;
    }

    @Nullable
    public MessageAttachment[] getAttachments() {
        return this.attachments;
    }

    public boolean isTTS() {
        return this.isTTS;
    }

    public int getFlags() {
        return this.flags;
    }

    @NotNull
    public WebhookMessage asEphemeral(boolean ephemeral) {
        int flags = this.flags;
        flags = ephemeral ? (flags |= 0x40) : (flags &= 0xFFFFFFBF);
        return new WebhookMessage(this.username, this.avatarUrl, this.content, this.embeds, this.isTTS, this.attachments, this.allowedMentions, flags, this.threadName);
    }

    @NotNull
    public static WebhookMessage from(@NotNull ReadonlyMessage message) {
        Objects.requireNonNull(message, "Message");
        WebhookMessageBuilder builder = new WebhookMessageBuilder();
        builder.setAvatarUrl(message.getAuthor().getAvatarId());
        builder.setUsername(message.getAuthor().getName());
        builder.setContent(message.getContent());
        builder.setTTS(message.isTTS());
        builder.setEphemeral((message.getFlags() & 0x40) != 0);
        builder.addEmbeds(message.getEmbeds());
        return builder.build();
    }

    @NotNull
    public static WebhookMessage embeds(@NotNull WebhookEmbed first, WebhookEmbed ... embeds) {
        Objects.requireNonNull(embeds, "Embeds");
        if (embeds.length >= 10) {
            throw new IllegalArgumentException("Cannot add more than 10 embeds to a message");
        }
        for (WebhookEmbed e : embeds) {
            Objects.requireNonNull(e);
        }
        ArrayList<WebhookEmbed> list = new ArrayList<WebhookEmbed>(1 + embeds.length);
        list.add(first);
        Collections.addAll(list, embeds);
        return new WebhookMessage(null, null, null, list, false, null, AllowedMentions.all(), 0, null);
    }

    @NotNull
    public static WebhookMessage embeds(@NotNull Collection<WebhookEmbed> embeds) {
        Objects.requireNonNull(embeds, "Embeds");
        if (embeds.size() > 10) {
            throw new IllegalArgumentException("Cannot add more than 10 embeds to a message");
        }
        if (embeds.isEmpty()) {
            throw new IllegalArgumentException("Cannot build an empty message");
        }
        embeds.forEach(Objects::requireNonNull);
        return new WebhookMessage(null, null, null, new ArrayList<WebhookEmbed>(embeds), false, null, AllowedMentions.all(), 0, null);
    }

    @NotNull
    public static WebhookMessage files(@NotNull Map<String, ?> attachments) {
        Objects.requireNonNull(attachments, "Attachments");
        int fileAmount = attachments.size();
        if (fileAmount == 0) {
            throw new IllegalArgumentException("Cannot build an empty message");
        }
        if (fileAmount > 10) {
            throw new IllegalArgumentException("Cannot add more than 10 files to a message");
        }
        Set<Map.Entry<String, ?>> entries = attachments.entrySet();
        MessageAttachment[] files = new MessageAttachment[fileAmount];
        int i = 0;
        for (Map.Entry<String, ?> attachment : entries) {
            String name = attachment.getKey();
            Objects.requireNonNull(name, "Name");
            Object data = attachment.getValue();
            files[i++] = WebhookMessage.convertAttachment(name, data);
        }
        return new WebhookMessage(null, null, null, null, false, files, AllowedMentions.all(), 0, null);
    }

    @NotNull
    public static WebhookMessage files(@NotNull String name1, @NotNull Object data1, Object ... attachments) {
        Objects.requireNonNull(name1, "Name");
        Objects.requireNonNull(data1, "Data");
        Objects.requireNonNull(attachments, "Attachments");
        if (attachments.length % 2 != 0) {
            throw new IllegalArgumentException("Must provide even number of varargs arguments");
        }
        int fileAmount = 1 + attachments.length / 2;
        if (fileAmount > 10) {
            throw new IllegalArgumentException("Cannot add more than 10 files to a message");
        }
        MessageAttachment[] files = new MessageAttachment[fileAmount];
        files[0] = WebhookMessage.convertAttachment(name1, data1);
        int j = 1;
        for (int i = 0; i < attachments.length; i += 2) {
            Object name = attachments[i];
            Object data = attachments[i + 1];
            if (!(name instanceof String)) {
                throw new IllegalArgumentException("Provided arguments must be pairs for (String, Data). Expected String and found " + (name == null ? null : name.getClass().getName()));
            }
            files[j] = WebhookMessage.convertAttachment((String)name, data);
            ++j;
        }
        return new WebhookMessage(null, null, null, null, false, files, AllowedMentions.all(), 0, null);
    }

    public boolean isFile() {
        return this.attachments != null;
    }

    @NotNull
    public RequestBody getBody() {
        JSONObject payload = new JSONObject();
        payload.put("content", this.content);
        if (this.embeds != null && !this.embeds.isEmpty()) {
            JSONArray array = new JSONArray();
            for (WebhookEmbed embed : this.embeds) {
                array.put(embed.reduced());
            }
            payload.put("embeds", array);
        } else {
            payload.put("embeds", new JSONArray());
        }
        if (this.avatarUrl != null) {
            payload.put("avatar_url", this.avatarUrl);
        }
        if (this.username != null) {
            payload.put("username", this.username);
        }
        payload.put("tts", this.isTTS);
        payload.put("allowed_mentions", this.allowedMentions);
        payload.put("flags", this.flags);
        if (this.threadName != null) {
            payload.put("thread_name", this.threadName);
        }
        String json = payload.toString();
        if (this.isFile()) {
            MessageAttachment attachment;
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (int i = 0; i < this.attachments.length && (attachment = this.attachments[i]) != null; ++i) {
                builder.addFormDataPart("file" + i, attachment.getName(), new IOUtil.OctetBody(attachment.getData()));
            }
            return builder.addFormDataPart("payload_json", json).build();
        }
        return RequestBody.create(IOUtil.JSON, json);
    }

    @NotNull
    private static MessageAttachment convertAttachment(@NotNull String name, @NotNull Object data) {
        Objects.requireNonNull(name, "Name");
        Objects.requireNonNull(data, "Data");
        try {
            MessageAttachment a;
            if (data instanceof File) {
                a = new MessageAttachment(name, (File)data);
            } else if (data instanceof InputStream) {
                a = new MessageAttachment(name, (InputStream)data);
            } else if (data instanceof byte[]) {
                a = new MessageAttachment(name, (byte[])data);
            } else {
                throw new IllegalArgumentException("Provided arguments must be pairs for (String, Data). Unexpected data type " + data.getClass().getName());
            }
            return a;
        }
        catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}

