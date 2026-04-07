/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.util.translations;

import com.hypherionmc.sdlink.util.translations.TranslationManager;
import org.jetbrains.annotations.NotNull;

public final class SDText
implements CharSequence {
    private final String translatedText;

    private SDText(String key, Object ... args2) {
        this.translatedText = args2 == null || args2.length == 0 ? TranslationManager.INSTANCE.translate(key) : String.format(TranslationManager.INSTANCE.translate(key), args2);
    }

    public static SDText translate(@NotNull String key) {
        return new SDText(key, new Object[0]);
    }

    public static SDText translate(@NotNull String key, Object ... args2) {
        return new SDText(key, args2);
    }

    @Override
    public int length() {
        return this.translatedText.length();
    }

    @Override
    public char charAt(int index) {
        return this.translatedText.charAt(index);
    }

    @Override
    @NotNull
    public CharSequence subSequence(int start, int end) {
        return this.translatedText.subSequence(start, end);
    }

    @Override
    @NotNull
    public String toString() {
        return this.translatedText;
    }
}

