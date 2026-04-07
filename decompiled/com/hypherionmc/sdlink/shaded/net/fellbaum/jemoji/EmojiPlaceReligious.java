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

interface EmojiPlaceReligious {
    public static final Emoji CHURCH = new Emoji("\u26ea", "\\u26EA", Collections.singletonList(":church:"), Collections.singletonList(":church:"), Collections.singletonList(":church:"), Collections.unmodifiableList(Arrays.asList("bless", "chapel", "Christian", "church", "cross", "religion")), false, false, 0.6, Qualification.fromString("fully-qualified"), "church", EmojiGroup.TRAVEL_AND_PLACES, EmojiSubGroup.PLACE_RELIGIOUS, true);
    public static final Emoji MOSQUE = new Emoji("\ud83d\udd4c", "\\uD83D\\uDD4C", Collections.singletonList(":mosque:"), Collections.singletonList(":mosque:"), Collections.singletonList(":mosque:"), Collections.unmodifiableList(Arrays.asList("islam", "masjid", "mosque", "Muslim", "religion")), false, false, 1.0, Qualification.fromString("fully-qualified"), "mosque", EmojiGroup.TRAVEL_AND_PLACES, EmojiSubGroup.PLACE_RELIGIOUS, false);
    public static final Emoji HINDU_TEMPLE = new Emoji("\ud83d\uded5", "\\uD83D\\uDED5", Collections.singletonList(":hindu_temple:"), Collections.singletonList(":hindu_temple:"), Collections.singletonList(":hindu_temple:"), Collections.unmodifiableList(Arrays.asList("hindu", "temple")), false, false, 12.0, Qualification.fromString("fully-qualified"), "hindu temple", EmojiGroup.TRAVEL_AND_PLACES, EmojiSubGroup.PLACE_RELIGIOUS, false);
    public static final Emoji SYNAGOGUE = new Emoji("\ud83d\udd4d", "\\uD83D\\uDD4D", Collections.singletonList(":synagogue:"), Collections.singletonList(":synagogue:"), Collections.singletonList(":synagogue:"), Collections.unmodifiableList(Arrays.asList("Jew", "Jewish", "judaism", "religion", "synagogue", "temple")), false, false, 1.0, Qualification.fromString("fully-qualified"), "synagogue", EmojiGroup.TRAVEL_AND_PLACES, EmojiSubGroup.PLACE_RELIGIOUS, false);
    public static final Emoji SHINTO_SHRINE = new Emoji("\u26e9\ufe0f", "\\u26E9\\uFE0F", Collections.singletonList(":shinto_shrine:"), Collections.singletonList(":shinto_shrine:"), Collections.singletonList(":shinto_shrine:"), Collections.unmodifiableList(Arrays.asList("religion", "shinto", "shrine")), false, false, 0.7, Qualification.fromString("fully-qualified"), "shinto shrine", EmojiGroup.TRAVEL_AND_PLACES, EmojiSubGroup.PLACE_RELIGIOUS, false);
    public static final Emoji SHINTO_SHRINE_UNQUALIFIED = new Emoji("\u26e9", "\\u26E9", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.unmodifiableList(Arrays.asList("religion", "shinto", "shrine")), false, false, 0.7, Qualification.fromString("unqualified"), "shinto shrine", EmojiGroup.TRAVEL_AND_PLACES, EmojiSubGroup.PLACE_RELIGIOUS, true);
    public static final Emoji KAABA = new Emoji("\ud83d\udd4b", "\\uD83D\\uDD4B", Collections.singletonList(":kaaba:"), Collections.singletonList(":kaaba:"), Collections.singletonList(":kaaba:"), Collections.unmodifiableList(Arrays.asList("hajj", "islam", "kaaba", "Muslim", "religion", "umrah")), false, false, 1.0, Qualification.fromString("fully-qualified"), "kaaba", EmojiGroup.TRAVEL_AND_PLACES, EmojiSubGroup.PLACE_RELIGIOUS, false);
}

