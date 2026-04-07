/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.sticker.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.GuildSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.sticker.update.GenericGuildStickerUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildStickerUpdateNameEvent
extends GenericGuildStickerUpdateEvent<String> {
    public static final String IDENTIFIER = "name";

    public GuildStickerUpdateNameEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nonnull GuildSticker sticker, @Nonnull String oldValue) {
        super(api, responseNumber, guild, sticker, IDENTIFIER, oldValue, sticker.getName());
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

