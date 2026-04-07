/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji;

import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.Emoji;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.EmojiGroup;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.EmojiSubGroup;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.Qualification;
import java.util.Arrays;
import java.util.Collections;

interface EmojiMath {
    public static final Emoji MULTIPLY = new Emoji("\u2716\ufe0f", "\\u2716\\uFE0F", Collections.singletonList(":heavy_multiplication_x:"), Collections.singletonList(":heavy_multiplication_x:"), Collections.singletonList(":heavy_multiplication_x:"), Collections.unmodifiableList(Arrays.asList("\u00d7", "cancel", "multiplication", "multiply", "sign", "x")), false, false, 0.6, Qualification.fromString("fully-qualified"), "multiply", EmojiGroup.SYMBOLS, EmojiSubGroup.MATH, false);
    public static final Emoji MULTIPLY_UNQUALIFIED = new Emoji("\u2716", "\\u2716", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.unmodifiableList(Arrays.asList("\u00d7", "cancel", "multiplication", "multiply", "sign", "x")), false, false, 0.6, Qualification.fromString("unqualified"), "multiply", EmojiGroup.SYMBOLS, EmojiSubGroup.MATH, true);
    public static final Emoji PLUS = new Emoji("\u2795", "\\u2795", Collections.singletonList(":heavy_plus_sign:"), Collections.singletonList(":heavy_plus_sign:"), Collections.singletonList(":heavy_plus_sign:"), Collections.unmodifiableList(Arrays.asList("+", "plus")), false, false, 0.6, Qualification.fromString("fully-qualified"), "plus", EmojiGroup.SYMBOLS, EmojiSubGroup.MATH, true);
    public static final Emoji MINUS = new Emoji("\u2796", "\\u2796", Collections.singletonList(":heavy_minus_sign:"), Collections.singletonList(":heavy_minus_sign:"), Collections.singletonList(":heavy_minus_sign:"), Collections.unmodifiableList(Arrays.asList("-", "\u2212", "heavy", "math", "minus", "sign")), false, false, 0.6, Qualification.fromString("fully-qualified"), "minus", EmojiGroup.SYMBOLS, EmojiSubGroup.MATH, true);
    public static final Emoji DIVIDE = new Emoji("\u2797", "\\u2797", Collections.singletonList(":heavy_division_sign:"), Collections.singletonList(":heavy_division_sign:"), Collections.singletonList(":heavy_division_sign:"), Collections.unmodifiableList(Arrays.asList("\u00f7", "divide", "division", "heavy", "math", "sign")), false, false, 0.6, Qualification.fromString("fully-qualified"), "divide", EmojiGroup.SYMBOLS, EmojiSubGroup.MATH, true);
    public static final Emoji HEAVY_EQUALS_SIGN = new Emoji("\ud83d\udff0", "\\uD83D\\uDFF0", Collections.singletonList(":heavy_equals_sign:"), Collections.singletonList(":heavy_equals_sign:"), Collections.singletonList(":heavy_equals_sign:"), Collections.unmodifiableList(Arrays.asList("answer", "equal", "equality", "equals", "heavy", "math", "sign")), false, false, 14.0, Qualification.fromString("fully-qualified"), "heavy equals sign", EmojiGroup.SYMBOLS, EmojiSubGroup.MATH, false);
    public static final Emoji INFINITY = new Emoji("\u267e\ufe0f", "\\u267E\\uFE0F", Collections.singletonList(":infinity:"), Collections.singletonList(":infinity:"), Collections.singletonList(":infinity:"), Collections.unmodifiableList(Arrays.asList("forever", "infinity", "unbounded", "universal")), false, false, 11.0, Qualification.fromString("fully-qualified"), "infinity", EmojiGroup.SYMBOLS, EmojiSubGroup.MATH, false);
    public static final Emoji INFINITY_UNQUALIFIED = new Emoji("\u267e", "\\u267E", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.unmodifiableList(Arrays.asList("forever", "infinity", "unbounded", "universal")), false, false, 11.0, Qualification.fromString("unqualified"), "infinity", EmojiGroup.SYMBOLS, EmojiSubGroup.MATH, true);
}

