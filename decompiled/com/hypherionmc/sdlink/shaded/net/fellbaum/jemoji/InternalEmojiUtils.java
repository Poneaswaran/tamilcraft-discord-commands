/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jspecify.annotations.Nullable
 */
package com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji;

import java.util.Map;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

final class InternalEmojiUtils {
    public static final char TEXT_VARIATION_CHARACTER = '\ufe0e';
    public static final char EMOJI_VARIATION_CHARACTER = '\ufe0f';

    private InternalEmojiUtils() {
    }

    public static int getCodePointCount(String string) {
        return string.codePointCount(0, string.length());
    }

    public static boolean isStringNullOrEmpty(@Nullable String string) {
        return null == string || string.isEmpty();
    }

    public static String removeColonFromAlias(String alias) {
        return alias.startsWith(":") && alias.endsWith(":") ? alias.substring(1, alias.length() - 1) : alias;
    }

    public static String addColonToAlias(String alias) {
        return alias.startsWith(":") && alias.endsWith(":") ? alias : ":" + alias + ":";
    }

    public static <K, V> Optional<V> findEmojiByEitherAlias(Map<K, V> map, K aliasWithColon, K aliasWithoutColon) {
        V firstValue = map.get(aliasWithColon);
        if (firstValue != null) {
            return Optional.of(firstValue);
        }
        V secondValue = map.get(aliasWithoutColon);
        if (secondValue != null) {
            return Optional.of(secondValue);
        }
        return Optional.empty();
    }

    public static int[] stringToCodePoints(String text) {
        int codePoint;
        int[] codePoints = new int[InternalEmojiUtils.getCodePointCount(text)];
        int j = 0;
        for (int i = 0; i < text.length(); i += Character.charCount(codePoint)) {
            codePoint = text.codePointAt(i);
            codePoints[j++] = codePoint;
        }
        return codePoints;
    }
}

