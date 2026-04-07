/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.emoji;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.ApplicationEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.CustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.EmojiUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.UnicodeEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class CustomEmojiImpl
implements CustomEmoji,
EmojiUnion {
    private final String name;
    private final long id;
    private final boolean animated;

    public CustomEmojiImpl(String name, long id, boolean animated) {
        this.name = name;
        this.id = id;
        this.animated = animated;
    }

    @Override
    @Nonnull
    public String getAsReactionCode() {
        return this.name + ":" + this.id;
    }

    @Override
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    @Override
    public boolean isAnimated() {
        return this.animated;
    }

    @Override
    @Nonnull
    public DataObject toData() {
        return DataObject.empty().put("name", this.name).put("id", this.id).put("animated", this.animated);
    }

    @Override
    @Nonnull
    public String getAsMention() {
        return Helpers.format("<%s:%s:%s>", this.animated ? "a" : "", this.name, this.getId());
    }

    @Override
    @Nonnull
    public String getFormatted() {
        return CustomEmoji.super.getFormatted();
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CustomEmoji)) {
            return false;
        }
        CustomEmoji other = (CustomEmoji)obj;
        return this.id == other.getIdLong();
    }

    public String toString() {
        return new EntityString(this).setName(this.name).toString();
    }

    @Override
    @Nonnull
    public UnicodeEmoji asUnicode() {
        throw new IllegalStateException("Cannot convert CustomEmoji into UnicodeEmoji!");
    }

    @Override
    @Nonnull
    public CustomEmoji asCustom() {
        return this;
    }

    @Override
    @Nonnull
    public RichCustomEmoji asRich() {
        throw new IllegalStateException("Cannot convert CustomEmoji to RichCustomEmoji!");
    }

    @Override
    @Nonnull
    public ApplicationEmoji asApplication() {
        throw new IllegalStateException("Cannot convert CustomEmoji to ApplicationEmoji!");
    }
}

