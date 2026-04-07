/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.sticker.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.GuildSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.sticker.update.GenericGuildStickerUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Set;

public class GuildStickerUpdateTagsEvent
extends GenericGuildStickerUpdateEvent<Set<String>> {
    public static final String IDENTIFIER = "tags";

    public GuildStickerUpdateTagsEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nonnull GuildSticker sticker, @Nonnull Set<String> oldValue) {
        super(api, responseNumber, guild, sticker, IDENTIFIER, oldValue, sticker.getTags());
    }

    @Override
    @Nonnull
    public Set<String> getOldValue() {
        return (Set)super.getOldValue();
    }

    @Override
    @Nonnull
    public Set<String> getNewValue() {
        return (Set)super.getNewValue();
    }
}

