/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.sticker;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.GuildSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.RichSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StandardSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.Sticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.sticker.StickerItemImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

public abstract class RichStickerImpl
extends StickerItemImpl
implements RichSticker,
StickerUnion {
    protected Set<String> tags;
    protected String description;

    public RichStickerImpl(long id, Sticker.StickerFormat format, String name, Set<String> tags, String description) {
        super(id, format, name);
        this.tags = Collections.unmodifiableSet(tags);
        this.description = description;
    }

    @Override
    @Nonnull
    public StandardSticker asStandardSticker() {
        throw new IllegalStateException("Cannot convert sticker of type " + (Object)((Object)this.getType()) + " to StandardSticker!");
    }

    @Override
    @Nonnull
    public GuildSticker asGuildSticker() {
        throw new IllegalStateException("Cannot convert sticker of type " + (Object)((Object)this.getType()) + " to GuildSticker!");
    }

    @Override
    @Nonnull
    public Set<String> getTags() {
        return this.tags;
    }

    @Override
    @Nonnull
    public String getDescription() {
        return this.description;
    }

    public RichStickerImpl setTags(Set<String> tags) {
        this.tags = Collections.unmodifiableSet(tags);
        return this;
    }

    public RichStickerImpl setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return new EntityString(this).setType(this.getType()).setName(this.name).toString();
    }
}

