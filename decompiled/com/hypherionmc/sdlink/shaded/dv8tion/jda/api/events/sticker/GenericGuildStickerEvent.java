/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.sticker;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.GuildSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public abstract class GenericGuildStickerEvent
extends Event {
    protected final Guild guild;
    protected final GuildSticker sticker;

    public GenericGuildStickerEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nonnull GuildSticker sticker) {
        super(api, responseNumber);
        this.guild = guild;
        this.sticker = sticker;
    }

    @Nonnull
    public GuildSticker getSticker() {
        return this.sticker;
    }

    @Nonnull
    public Guild getGuild() {
        return this.guild;
    }
}

