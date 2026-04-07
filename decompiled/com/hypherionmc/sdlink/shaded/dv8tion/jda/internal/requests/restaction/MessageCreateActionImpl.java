/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageReference;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.GuildSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.SerializableData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.message.MessageCreateBuilderMixin;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

public class MessageCreateActionImpl
extends RestActionImpl<Message>
implements MessageCreateAction,
MessageCreateBuilderMixin<MessageCreateAction> {
    protected static final SecureRandom nonceGenerator = new SecureRandom();
    protected static boolean defaultFailOnInvalidReply = false;
    private final MessageChannel channel;
    private final MessageCreateBuilder builder = new MessageCreateBuilder();
    private final List<String> stickers = new ArrayList<String>();
    private String nonce;
    private MessageReferenceData messageReference;
    private boolean failOnInvalidReply = defaultFailOnInvalidReply;

    public static void setDefaultFailOnInvalidReply(boolean fail) {
        defaultFailOnInvalidReply = fail;
    }

    public MessageCreateActionImpl(MessageChannel channel) {
        super(channel.getJDA(), Route.Messages.SEND_MESSAGE.compile(channel.getId()));
        this.channel = channel;
    }

    @Override
    public MessageCreateBuilder getBuilder() {
        return this.builder;
    }

    @Override
    protected RequestBody finalizeData() {
        if (this.builder.isEmpty()) {
            DataObject body = DataObject.empty().put("flags", this.builder.getMessageFlagsRaw());
            this.populateBody(body);
            if (!this.stickers.isEmpty() || this.messageReference != null && this.messageReference.type == MessageReference.MessageReferenceType.FORWARD) {
                return this.getRequestBody(body);
            }
            throw new IllegalStateException("Cannot build empty messages! Must provide at least one of: content, embed, file, poll, or stickers");
        }
        try (MessageCreateData data = this.builder.build();){
            DataObject json = data.toData();
            this.populateBody(json);
            RequestBody requestBody = this.getMultipartBody(data.getFiles(), json);
            return requestBody;
        }
    }

    private void populateBody(DataObject json) {
        json.put("enforce_nonce", true);
        if (this.nonce != null && !this.nonce.isEmpty()) {
            json.put("nonce", this.nonce);
        } else {
            json.put("nonce", Long.toUnsignedString(nonceGenerator.nextLong()));
        }
        if (this.stickers != null && !this.stickers.isEmpty()) {
            json.put("sticker_ids", this.stickers);
        }
        if (this.messageReference != null) {
            json.put("message_reference", this.messageReference.toData().put("fail_if_not_exists", this.failOnInvalidReply));
        }
    }

    @Override
    protected void handleSuccess(Response response, Request<Message> request) {
        request.onSuccess(this.api.getEntityBuilder().createMessageWithChannel(response.getObject(), this.channel, false));
    }

    @Override
    @Nonnull
    public MessageCreateAction setNonce(@Nullable String nonce) {
        if (nonce != null) {
            Checks.notLonger(nonce, 25, "Nonce");
        }
        this.nonce = nonce;
        return this;
    }

    @Override
    @Nonnull
    public MessageCreateAction setMessageReference(@Nonnull MessageReference.MessageReferenceType type, @Nullable String guildId, @Nonnull String channelId, @Nonnull String messageId) {
        Checks.notNull((Object)type, "Type");
        if (guildId != null) {
            Checks.isSnowflake(guildId, "Guild ID");
        }
        Checks.isSnowflake(channelId, "Channel ID");
        Checks.isSnowflake(messageId, "Message ID");
        Checks.check(type != MessageReference.MessageReferenceType.UNKNOWN, "Cannot create a message reference of UNKNOWN type");
        this.messageReference = new MessageReferenceData(type, guildId, channelId, messageId);
        return this;
    }

    @Override
    @Nonnull
    public MessageCreateAction setMessageReference(@Nullable String messageId) {
        if (messageId == null) {
            this.messageReference = null;
            return this;
        }
        Checks.isSnowflake(messageId);
        String guildId = null;
        if (this.channel instanceof GuildChannel) {
            guildId = ((GuildChannel)((Object)this.channel)).getGuild().getId();
        }
        this.messageReference = new MessageReferenceData(MessageReference.MessageReferenceType.DEFAULT, guildId, this.channel.getId(), messageId);
        return this;
    }

    @Override
    @Nonnull
    public MessageCreateAction failOnInvalidReply(boolean fail) {
        this.failOnInvalidReply = fail;
        return this;
    }

    @Override
    @Nonnull
    public MessageCreateAction setStickers(@Nullable Collection<? extends StickerSnowflake> stickers) {
        this.stickers.clear();
        if (stickers == null || stickers.isEmpty()) {
            return this;
        }
        if (!this.channel.getType().isGuild()) {
            throw new IllegalStateException("Cannot send stickers in direct messages!");
        }
        GuildChannel guildChannel = (GuildChannel)((Object)this.channel);
        Checks.noneNull(stickers, "Stickers");
        Checks.check(stickers.size() <= 3, "Cannot send more than %d stickers in a message!", (Object)3);
        for (StickerSnowflake stickerSnowflake : stickers) {
            if (!(stickerSnowflake instanceof GuildSticker)) continue;
            GuildSticker guildSticker = (GuildSticker)stickerSnowflake;
            Checks.check(guildSticker.isAvailable(), "Cannot use unavailable sticker. The guild may have lost the boost level required to use this sticker!");
            Checks.check(guildSticker.getGuildIdLong() == guildChannel.getGuild().getIdLong(), "Sticker must be from the same guild. Cross-guild sticker posting is not supported!");
        }
        this.stickers.addAll(stickers.stream().map(ISnowflake::getId).collect(Collectors.toList()));
        return this;
    }

    @Override
    @Nonnull
    public MessageCreateAction setCheck(BooleanSupplier checks) {
        return (MessageCreateAction)super.setCheck(checks);
    }

    @Override
    @Nonnull
    public MessageCreateAction deadline(long timestamp) {
        return (MessageCreateAction)super.deadline(timestamp);
    }

    private class MessageReferenceData
    implements SerializableData {
        private final MessageReference.MessageReferenceType type;
        private final String messageId;
        private final String channelId;
        private final String guildId;

        private MessageReferenceData(MessageReference.MessageReferenceType type, String guildId, String channelId, String messageId) {
            this.type = type;
            this.messageId = messageId;
            this.guildId = guildId;
            this.channelId = channelId;
        }

        @Override
        @Nonnull
        public DataObject toData() {
            DataObject data = DataObject.empty().put("type", this.type.getId()).put("message_id", this.messageId).put("channel_id", this.channelId);
            if (this.guildId != null) {
                data.put("guild_id", this.guildId);
            }
            return data;
        }
    }
}

