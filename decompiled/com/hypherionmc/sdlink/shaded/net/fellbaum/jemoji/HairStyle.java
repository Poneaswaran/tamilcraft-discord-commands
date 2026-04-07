/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji;

import java.util.Arrays;
import java.util.List;

public enum HairStyle {
    RED_HAIR("\ud83e\uddb0"),
    CURLY_HAIR("\ud83e\uddb1"),
    WHITE_HAIR("\ud83e\uddb3"),
    BALD("\ud83e\uddb2");

    private static final List<HairStyle> HAIR_STYLE_LIST;
    private final String unicode;

    private HairStyle(String unicode) {
        this.unicode = unicode;
    }

    public String getUnicode() {
        return this.unicode;
    }

    public static boolean isHairStyleEmoji(String unicode) {
        return HAIR_STYLE_LIST.stream().anyMatch(hairStyle -> unicode.contains(hairStyle.unicode) && !unicode.equals(hairStyle.unicode));
    }

    public static String removeHairStyle(String unicode) {
        for (HairStyle value : HAIR_STYLE_LIST) {
            unicode = unicode.replaceAll("\u200d?" + value.getUnicode(), "");
        }
        return unicode;
    }

    static {
        HAIR_STYLE_LIST = Arrays.asList(HairStyle.values());
    }
}

