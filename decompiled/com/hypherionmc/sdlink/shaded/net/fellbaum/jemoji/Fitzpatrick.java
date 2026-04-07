/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji;

import java.util.Arrays;
import java.util.List;

public enum Fitzpatrick {
    LIGHT_SKIN("\ud83c\udffb"),
    MEDIUM_LIGHT_SKIN("\ud83c\udffc"),
    MEDIUM_SKIN("\ud83c\udffd"),
    MEDIUM_DARK_SKIN("\ud83c\udffe"),
    DARK_SKIN("\ud83c\udfff");

    private static final List<Fitzpatrick> FITZPATRICK_LIST;
    private final String unicode;

    private Fitzpatrick(String unicode) {
        this.unicode = unicode;
    }

    public String getUnicode() {
        return this.unicode;
    }

    public static boolean isFitzpatrickEmoji(String unicode) {
        return FITZPATRICK_LIST.stream().anyMatch(fitzpatrick -> unicode.contains(fitzpatrick.unicode) && !unicode.equals(fitzpatrick.unicode));
    }

    public static String removeFitzpatrick(String unicode) {
        for (Fitzpatrick value : FITZPATRICK_LIST) {
            unicode = unicode.replaceAll("\u200d?" + value.getUnicode(), "");
        }
        return unicode;
    }

    static {
        FITZPATRICK_LIST = Arrays.asList(Fitzpatrick.values());
    }
}

