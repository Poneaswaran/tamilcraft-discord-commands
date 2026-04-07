/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.sticker.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.GuildSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.UpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.sticker.GenericGuildStickerEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public abstract class GenericGuildStickerUpdateEvent<T>
extends GenericGuildStickerEvent
implements UpdateEvent<GuildSticker, T> {
    protected final String identifier;
    protected final T previous;
    protected final T next;

    public GenericGuildStickerUpdateEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nonnull GuildSticker sticker, @Nonnull String identifier, T oldValue, T newValue) {
        super(api, responseNumber, guild, sticker);
        this.identifier = identifier;
        this.previous = oldValue;
        this.next = newValue;
    }

    @Override
    @Nonnull
    public String getPropertyIdentifier() {
        return this.identifier;
    }

    @Override
    @Nonnull
    public GuildSticker getEntity() {
        return this.getSticker();
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

