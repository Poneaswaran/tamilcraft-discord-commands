/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji;

import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.Emoji;

public final class IndexedEmoji {
    private final Emoji emoji;
    private final int charIndex;
    private final int codePointIndex;

    IndexedEmoji(Emoji emoji, int charIndex, int codePointIndex) {
        this.emoji = emoji;
        this.charIndex = charIndex;
        this.codePointIndex = codePointIndex;
    }

    public Emoji getEmoji() {
        return this.emoji;
    }

    public int getCharIndex() {
        return this.charIndex;
    }

    public int getCodePointIndex() {
        return this.codePointIndex;
    }

    public String toString() {
        return "IndexedEmoji{emoji=" + this.emoji + ", charIndex=" + this.charIndex + ", codePointIndex=" + this.codePointIndex + '}';
    }
}

