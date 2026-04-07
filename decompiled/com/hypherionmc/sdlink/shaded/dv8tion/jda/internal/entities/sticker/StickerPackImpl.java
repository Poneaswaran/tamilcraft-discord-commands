/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.sticker;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StandardSticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerPack;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class StickerPackImpl
implements StickerPack {
    private final long id;
    private final List<StandardSticker> stickers;
    private final String name;
    private final String description;
    private final long coverId;
    private final long bannerId;
    private final long skuId;

    public StickerPackImpl(long id, List<StandardSticker> stickers, String name, String description, long coverId, long bannerId, long skuId) {
        this.id = id;
        this.stickers = Collections.unmodifiableList(stickers);
        this.name = name;
        this.description = description;
        this.coverId = coverId;
        this.bannerId = bannerId;
        this.skuId = skuId;
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    @Override
    @Nonnull
    public List<StandardSticker> getStickers() {
        return this.stickers;
    }

    @Override
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Override
    @Nonnull
    public String getDescription() {
        return this.description;
    }

    @Override
    public long getCoverIdLong() {
        return this.coverId;
    }

    @Override
    public long getBannerIdLong() {
        return this.bannerId;
    }

    @Override
    public long getSkuIdLong() {
        return this.skuId;
    }

    public String toString() {
        return new EntityString(this).setName(this.name).toString();
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StickerPackImpl)) {
            return false;
        }
        StickerPackImpl other = (StickerPackImpl)obj;
        return this.id == other.id;
    }
}

