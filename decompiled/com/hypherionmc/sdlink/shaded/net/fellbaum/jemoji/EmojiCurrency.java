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

interface EmojiCurrency {
    public static final Emoji CURRENCY_EXCHANGE = new Emoji("\ud83d\udcb1", "\\uD83D\\uDCB1", Collections.singletonList(":currency_exchange:"), Collections.singletonList(":currency_exchange:"), Collections.singletonList(":currency_exchange:"), Collections.unmodifiableList(Arrays.asList("bank", "currency", "exchange", "money")), false, false, 0.6, Qualification.fromString("fully-qualified"), "currency exchange", EmojiGroup.SYMBOLS, EmojiSubGroup.CURRENCY, false);
    public static final Emoji HEAVY_DOLLAR_SIGN = new Emoji("\ud83d\udcb2", "\\uD83D\\uDCB2", Collections.singletonList(":heavy_dollar_sign:"), Collections.singletonList(":heavy_dollar_sign:"), Collections.singletonList(":heavy_dollar_sign:"), Collections.unmodifiableList(Arrays.asList("billion", "cash", "charge", "currency", "dollar", "heavy", "million", "money", "pay", "sign")), false, false, 0.6, Qualification.fromString("fully-qualified"), "heavy dollar sign", EmojiGroup.SYMBOLS, EmojiSubGroup.CURRENCY, false);
}

