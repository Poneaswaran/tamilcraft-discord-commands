/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.sticker;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.Sticker;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerItem;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Objects;

public class StickerItemImpl
implements StickerItem {
    protected final long id;
    protected final Sticker.StickerFormat format;
    protected String name;

    public StickerItemImpl(long id, Sticker.StickerFormat format, String name) {
        this.id = id;
        this.format = format;
        this.name = name;
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    @Override
    @Nonnull
    public Sticker.StickerFormat getFormatType() {
        return this.format;
    }

    @Override
    @Nonnull
    public String getName() {
        return this.name;
    }

    public StickerItemImpl setName(String name) {
        this.name = name;
        return this;
    }

    public String toString() {
        return new EntityString(this).setName(this.name).toString();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id, this.format, this.name});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StickerItemImpl)) {
            return false;
        }
        StickerItemImpl other = (StickerItemImpl)obj;
        return this.id == other.id && this.format == other.format && Objects.equals(this.name, other.name);
    }
}

