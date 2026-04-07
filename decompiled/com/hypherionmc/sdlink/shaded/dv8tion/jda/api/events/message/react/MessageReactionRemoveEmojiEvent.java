/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.react;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageReaction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.GenericMessageEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class MessageReactionRemoveEmojiEvent
extends GenericMessageEvent {
    private final MessageReaction reaction;

    public MessageReactionRemoveEmojiEvent(@Nonnull JDA api, long responseNumber, long messageId, @Nonnull MessageChannel channel, @Nonnull MessageReaction reaction) {
        super(api, responseNumber, messageId, channel);
        this.reaction = reaction;
    }

    @Nonnull
    public MessageReaction getReaction() {
        return this.reaction;
    }

    @Nonnull
    public EmojiUnion getEmoji() {
        return this.reaction.getEmoji();
    }
}

