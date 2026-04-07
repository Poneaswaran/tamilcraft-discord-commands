/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.emoji.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.emoji.update.GenericEmojiUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class EmojiUpdateNameEvent
extends GenericEmojiUpdateEvent<String> {
    public static final String IDENTIFIER = "name";

    public EmojiUpdateNameEvent(@Nonnull JDA api, long responseNumber, @Nonnull RichCustomEmoji emoji, @Nonnull String oldName) {
        super(api, responseNumber, emoji, oldName, emoji.getName(), IDENTIFIER);
    }

    @Nonnull
    public String getOldName() {
        return this.getOldValue();
    }

    @Nonnull
    public String getNewName() {
        return this.getNewValue();
    }

    @Override
    @Nonnull
    public String getOldValue() {
        return (String)super.getOldValue();
    }

    @Override
    @Nonnull
    public String getNewValue() {
        return (String)super.getNewValue();
    }
}

