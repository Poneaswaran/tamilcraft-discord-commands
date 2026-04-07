/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.Emoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public interface GuildMessageChannel
extends GuildChannel,
MessageChannel {
    @Override
    default public boolean canTalk() {
        return this.canTalk(this.getGuild().getSelfMember());
    }

    public boolean canTalk(@Nonnull Member var1);

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> removeReactionById(@Nonnull String var1, @Nonnull Emoji var2, @Nonnull User var3);

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> removeReactionById(long messageId, @Nonnull Emoji emoji, @Nonnull User user) {
        return this.removeReactionById(Long.toUnsignedString(messageId), emoji, user);
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> deleteMessages(@Nonnull Collection<Message> messages) {
        Checks.notEmpty(messages, "Messages collection");
        return this.deleteMessagesByIds(messages.stream().map(ISnowflake::getId).collect(Collectors.toList()));
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> deleteMessagesByIds(@Nonnull Collection<String> var1);

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> clearReactionsById(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> clearReactionsById(long messageId) {
        return this.clearReactionsById(Long.toUnsignedString(messageId));
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> clearReactionsById(@Nonnull String var1, @Nonnull Emoji var2);

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> clearReactionsById(long messageId, @Nonnull Emoji emoji) {
        return this.clearReactionsById(Long.toUnsignedString(messageId), emoji);
    }

    @Nonnull
    @CheckReturnValue
    public MessageCreateAction sendStickers(@Nonnull Collection<? extends StickerSnowflake> var1);

    @Nonnull
    @CheckReturnValue
    default public MessageCreateAction sendStickers(StickerSnowflake ... stickers) {
        Checks.notEmpty(stickers, "Stickers");
        return this.sendStickers(Arrays.asList(stickers));
    }
}

