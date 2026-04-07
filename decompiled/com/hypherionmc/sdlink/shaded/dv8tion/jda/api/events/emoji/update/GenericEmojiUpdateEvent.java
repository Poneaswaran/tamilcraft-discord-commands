/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.emoji.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.UpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.emoji.GenericEmojiEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public abstract class GenericEmojiUpdateEvent<T>
extends GenericEmojiEvent
implements UpdateEvent<RichCustomEmoji, T> {
    protected final T previous;
    protected final T next;
    protected final String identifier;

    public GenericEmojiUpdateEvent(@Nonnull JDA api, long responseNumber, @Nonnull RichCustomEmoji emoji, @Nullable T previous, @Nullable T next, @Nonnull String identifier) {
        super(api, responseNumber, emoji);
        this.previous = previous;
        this.next = next;
        this.identifier = identifier;
    }

    @Override
    @Nonnull
    public RichCustomEmoji getEntity() {
        return this.getEmoji();
    }

    @Override
    @Nonnull
    public String getPropertyIdentifier() {
        return this.identifier;
    }

    @Override
    @Nullable
    public T getOldValue() {
        return this.previous;
    }

    @Override
    @Nullable
    public T getNewValue() {
        return this.next;
    }
}

