/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.emoji;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.emoji.GenericEmojiEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class EmojiRemovedEvent
extends GenericEmojiEvent {
    public EmojiRemovedEvent(@Nonnull JDA api, long responseNumber, @Nonnull RichCustomEmoji emoji) {
        super(api, responseNumber, emoji);
    }
}

