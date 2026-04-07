/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.sticker;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.GuildSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.sticker.GenericGuildStickerEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GuildStickerAddedEvent
extends GenericGuildStickerEvent {
    public GuildStickerAddedEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nonnull GuildSticker sticker) {
        super(api, responseNumber, guild, sticker);
    }
}

