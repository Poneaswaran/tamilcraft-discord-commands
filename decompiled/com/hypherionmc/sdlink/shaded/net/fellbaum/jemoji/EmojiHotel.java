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

interface EmojiHotel {
    public static final Emoji BELLHOP_BELL = new Emoji("\ud83d\udece\ufe0f", "\\uD83D\\uDECE\\uFE0F", Collections.unmodifiableList(Arrays.asList(":bellhop:", ":bellhop_bell:")), Collections.singletonList(":bellhop_bell:"), Collections.singletonList(":bellhop_bell:"), Collections.unmodifiableList(Arrays.asList("bell", "bellhop", "hotel")), false, false, 0.7, Qualification.fromString("fully-qualified"), "bellhop bell", EmojiGroup.TRAVEL_AND_PLACES, EmojiSubGroup.HOTEL, false);
    public static final Emoji BELLHOP_BELL_UNQUALIFIED = new Emoji("\ud83d\udece", "\\uD83D\\uDECE", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.unmodifiableList(Arrays.asList("bell", "bellhop", "hotel")), false, false, 0.7, Qualification.fromString("unqualified"), "bellhop bell", EmojiGroup.TRAVEL_AND_PLACES, EmojiSubGroup.HOTEL, true);
    public static final Emoji LUGGAGE = new Emoji("\ud83e\uddf3", "\\uD83E\\uDDF3", Collections.singletonList(":luggage:"), Collections.singletonList(":luggage:"), Collections.singletonList(":luggage:"), Collections.unmodifiableList(Arrays.asList("bag", "luggage", "packing", "roller", "suitcase", "travel")), false, false, 11.0, Qualification.fromString("fully-qualified"), "luggage", EmojiGroup.TRAVEL_AND_PLACES, EmojiSubGroup.HOTEL, false);
}

