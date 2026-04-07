/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.sticker;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StandardSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.Sticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.sticker.RichStickerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Set;

public class StandardStickerImpl
extends RichStickerImpl
implements StandardSticker {
    private final long packId;
    private final int sortValue;

    public StandardStickerImpl(long id, Sticker.StickerFormat format, String name, Set<String> tags, String description, long packId, int sortValue) {
        super(id, format, name, tags, description);
        this.packId = packId;
        this.sortValue = sortValue;
    }

    @Override
    @Nonnull
    public StandardSticker asStandardSticker() {
        return this;
    }

    @Override
    public long getPackIdLong() {
        return this.packId;
    }

    @Override
    public int getSortValue() {
        return this.sortValue;
    }

    @Override
    public String toString() {
        return new EntityString(this).setName(this.name).addMetadata("pack", this.getPackId()).toString();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StandardStickerImpl)) {
            return false;
        }
        StandardStickerImpl other = (StandardStickerImpl)obj;
        return this.id == other.id;
    }
}

