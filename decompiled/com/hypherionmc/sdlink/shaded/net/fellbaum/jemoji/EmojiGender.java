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

interface EmojiGender {
    public static final Emoji FEMALE_SIGN = new Emoji("\u2640\ufe0f", "\\u2640\\uFE0F", Collections.singletonList(":female_sign:"), Collections.singletonList(":female_sign:"), Collections.singletonList(":female_sign:"), Collections.unmodifiableList(Arrays.asList("female", "sign", "woman")), false, false, 4.0, Qualification.fromString("fully-qualified"), "female sign", EmojiGroup.SYMBOLS, EmojiSubGroup.GENDER, false);
    public static final Emoji FEMALE_SIGN_UNQUALIFIED = new Emoji("\u2640", "\\u2640", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.unmodifiableList(Arrays.asList("female", "sign", "woman")), false, false, 4.0, Qualification.fromString("unqualified"), "female sign", EmojiGroup.SYMBOLS, EmojiSubGroup.GENDER, true);
    public static final Emoji MALE_SIGN = new Emoji("\u2642\ufe0f", "\\u2642\\uFE0F", Collections.singletonList(":male_sign:"), Collections.singletonList(":male_sign:"), Collections.singletonList(":male_sign:"), Collections.unmodifiableList(Arrays.asList("male", "man", "sign")), false, false, 4.0, Qualification.fromString("fully-qualified"), "male sign", EmojiGroup.SYMBOLS, EmojiSubGroup.GENDER, false);
    public static final Emoji MALE_SIGN_UNQUALIFIED = new Emoji("\u2642", "\\u2642", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.unmodifiableList(Arrays.asList("male", "man", "sign")), false, false, 4.0, Qualification.fromString("unqualified"), "male sign", EmojiGroup.SYMBOLS, EmojiSubGroup.GENDER, true);
    public static final Emoji TRANSGENDER_SYMBOL = new Emoji("\u26a7\ufe0f", "\\u26A7\\uFE0F", Collections.emptyList(), Collections.singletonList(":transgender_symbol:"), Collections.singletonList(":transgender_symbol:"), Collections.unmodifiableList(Arrays.asList("symbol", "transgender")), false, false, 13.0, Qualification.fromString("fully-qualified"), "transgender symbol", EmojiGroup.SYMBOLS, EmojiSubGroup.GENDER, false);
    public static final Emoji TRANSGENDER_SYMBOL_UNQUALIFIED = new Emoji("\u26a7", "\\u26A7", Collections.singletonList(":transgender_symbol:"), Collections.emptyList(), Collections.emptyList(), Collections.unmodifiableList(Arrays.asList("symbol", "transgender")), false, false, 13.0, Qualification.fromString("unqualified"), "transgender symbol", EmojiGroup.SYMBOLS, EmojiSubGroup.GENDER, true);
}

