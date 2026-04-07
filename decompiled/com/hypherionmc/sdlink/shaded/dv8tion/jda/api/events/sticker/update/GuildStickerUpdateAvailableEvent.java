/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.sticker.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.GuildSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.sticker.update.GenericGuildStickerUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildStickerUpdateAvailableEvent
extends GenericGuildStickerUpdateEvent<Boolean> {
    public static final String IDENTIFIER = "available";

    public GuildStickerUpdateAvailableEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nonnull GuildSticker sticker, boolean oldValue) {
        super(api, responseNumber, guild, sticker, IDENTIFIER, oldValue, sticker.isAvailable());
    }

    @Override
    @Nonnull
    public Boolean getOldValue() {
        return (Boolean)super.getOldValue();
    }

    @Override
    @Nonnull
    public Boolean getNewValue() {
        return (Boolean)super.getNewValue();
    }
}

