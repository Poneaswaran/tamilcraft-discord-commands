/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageReference;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.FluentRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.messages.MessageCreateRequest;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.MessageCreateActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;

public interface MessageCreateAction
extends MessageCreateRequest<MessageCreateAction>,
FluentRestAction<Message, MessageCreateAction> {
    public static void setDefaultFailOnInvalidReply(boolean fail) {
        MessageCreateActionImpl.setDefaultFailOnInvalidReply(fail);
    }

    @Nonnull
    @CheckReturnValue
    public MessageCreateAction setNonce(@Nullable String var1);

    @Nonnull
    @CheckReturnValue
    public MessageCreateAction setMessageReference(@Nonnull MessageReference.MessageReferenceType var1, @Nullable String var2, @Nonnull String var3, @Nonnull String var4);

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction setMessageReference(@Nonnull MessageReference.MessageReferenceType type, long guildId, long channelId, long messageId) {
        return this.setMessageReference(type, Long.toUnsignedString(guildId), Long.toUnsignedString(channelId), Long.toUnsignedString(messageId));
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction setMessageReference(@Nonnull MessageReference.MessageReferenceType type, @Nonnull Message message) {
        Checks.notNull(message, "Message");
        return this.setMessageReference(type, message.getGuildId(), message.getChannel().getId(), message.getId());
    }

    @Nonnull
    @CheckReturnValue
    public MessageCreateAction setMessageReference(@Nullable String var1);

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction setMessageReference(long messageId) {
        return this.setMessageReference(Long.toUnsignedString(messageId));
    }

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction setMessageReference(@Nullable Message message) {
        return this.setMessageReference(message == null ? null : message.getId());
    }

    @Nonnull
    @CheckReturnValue
    public MessageCreateAction failOnInvalidReply(boolean var1);

    @Nonnull
    @CheckReturnValue
    public MessageCreateAction setStickers(@Nullable Collection<? extends StickerSnowflake> var1);

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction setStickers(StickerSnowflake ... stickers) {
        if (stickers != null) {
            Checks.noneNull(stickers, "Sticker");
        }
        return this.setStickers(stickers == null ? null : Arrays.asList(stickers));
    }
}

