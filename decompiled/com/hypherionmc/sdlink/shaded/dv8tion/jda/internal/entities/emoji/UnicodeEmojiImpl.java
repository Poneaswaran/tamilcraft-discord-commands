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
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EncodingUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Objects;

public class UnicodeEmojiImpl
implements UnicodeEmoji,
EmojiUnion {
    private final String name;

    public UnicodeEmojiImpl(String name) {
        this.name = name;
    }

    @Override
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Override
    @Nonnull
    public String getAsReactionCode() {
        return this.name;
    }

    @Override
    @Nonnull
    public String getAsCodepoints() {
        return EncodingUtil.encodeCodepoints(this.name);
    }

    @Override
    @Nonnull
    public DataObject toData() {
        return DataObject.empty().put("name", this.name);
    }

    public int hashCode() {
        return Objects.hash(this.name);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UnicodeEmoji)) {
            return false;
        }
        return this.name.equals(((UnicodeEmoji)obj).getName());
    }

    public String toString() {
        return new EntityString(this).addMetadata("codepoints", this.getAsCodepoints()).toString();
    }

    @Override
    @Nonnull
    public UnicodeEmoji asUnicode() {
        return this;
    }

    @Override
    @Nonnull
    public CustomEmoji asCustom() {
        throw new IllegalStateException("Cannot convert UnicodeEmoji into CustomEmoji!");
    }

    @Override
    @Nonnull
    public RichCustomEmoji asRich() {
        throw new IllegalStateException("Cannot convert UnicodeEmoji into RichCustomEmoji!");
    }

    @Override
    @Nonnull
    public ApplicationEmoji asApplication() {
        throw new IllegalStateException("Cannot convert UnicodeEmoji to ApplicationEmoji!");
    }
}

