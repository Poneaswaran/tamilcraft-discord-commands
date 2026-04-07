/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.sticker;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.sticker.StickerSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;

public class StickerSnowflakeImpl
implements StickerSnowflake {
    private final long id;

    public StickerSnowflakeImpl(long id) {
        this.id = id;
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StickerSnowflakeImpl)) {
            return false;
        }
        return ((StickerSnowflakeImpl)obj).id == this.id;
    }

    public String toString() {
        return new EntityString(this).toString();
    }
}

