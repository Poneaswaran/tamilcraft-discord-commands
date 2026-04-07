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

interface EmojiAwardMedal {
    public static final Emoji MILITARY_MEDAL = new Emoji("\ud83c\udf96\ufe0f", "\\uD83C\\uDF96\\uFE0F", Collections.singletonList(":military_medal:"), Collections.singletonList(":medal:"), Collections.singletonList(":medal_military:"), Collections.unmodifiableList(Arrays.asList("award", "celebration", "medal", "military")), false, false, 0.7, Qualification.fromString("fully-qualified"), "military medal", EmojiGroup.ACTIVITIES, EmojiSubGroup.AWARD_MEDAL, false);
    public static final Emoji MILITARY_MEDAL_UNQUALIFIED = new Emoji("\ud83c\udf96", "\\uD83C\\uDF96", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.unmodifiableList(Arrays.asList("award", "celebration", "medal", "military")), false, false, 0.7, Qualification.fromString("unqualified"), "military medal", EmojiGroup.ACTIVITIES, EmojiSubGroup.AWARD_MEDAL, true);
    public static final Emoji TROPHY = new Emoji("\ud83c\udfc6", "\\uD83C\\uDFC6", Collections.singletonList(":trophy:"), Collections.singletonList(":trophy:"), Collections.singletonList(":trophy:"), Collections.unmodifiableList(Arrays.asList("champion", "champs", "prize", "slay", "sport", "trophy", "victory", "win", "winning")), false, false, 0.6, Qualification.fromString("fully-qualified"), "trophy", EmojiGroup.ACTIVITIES, EmojiSubGroup.AWARD_MEDAL, true);
    public static final Emoji SPORTS_MEDAL = new Emoji("\ud83c\udfc5", "\\uD83C\\uDFC5", Collections.unmodifiableList(Arrays.asList(":medal:", ":sports_medal:")), Collections.singletonList(":sports_medal:"), Collections.singletonList(":medal_sports:"), Collections.unmodifiableList(Arrays.asList("award", "gold", "medal", "sports", "winner")), false, false, 1.0, Qualification.fromString("fully-qualified"), "sports medal", EmojiGroup.ACTIVITIES, EmojiSubGroup.AWARD_MEDAL, false);
    public static final Emoji FIRST_PLACE_MEDAL = new Emoji("\ud83e\udd47", "\\uD83E\\uDD47", Collections.unmodifiableList(Arrays.asList(":first_place:", ":first_place_medal:")), Collections.singletonList(":first_place_medal:"), Collections.singletonList(":1st_place_medal:"), Collections.unmodifiableList(Arrays.asList("1st", "first", "gold", "medal", "place")), false, false, 3.0, Qualification.fromString("fully-qualified"), "1st place medal", EmojiGroup.ACTIVITIES, EmojiSubGroup.AWARD_MEDAL, false);
    public static final Emoji SECOND_PLACE_MEDAL = new Emoji("\ud83e\udd48", "\\uD83E\\uDD48", Collections.unmodifiableList(Arrays.asList(":second_place:", ":second_place_medal:")), Collections.singletonList(":second_place_medal:"), Collections.singletonList(":2nd_place_medal:"), Collections.unmodifiableList(Arrays.asList("2nd", "medal", "place", "second", "silver")), false, false, 3.0, Qualification.fromString("fully-qualified"), "2nd place medal", EmojiGroup.ACTIVITIES, EmojiSubGroup.AWARD_MEDAL, false);
    public static final Emoji THIRD_PLACE_MEDAL = new Emoji("\ud83e\udd49", "\\uD83E\\uDD49", Collections.unmodifiableList(Arrays.asList(":third_place:", ":third_place_medal:")), Collections.singletonList(":third_place_medal:"), Collections.singletonList(":3rd_place_medal:"), Collections.unmodifiableList(Arrays.asList("3rd", "bronze", "medal", "place", "third")), false, false, 3.0, Qualification.fromString("fully-qualified"), "3rd place medal", EmojiGroup.ACTIVITIES, EmojiSubGroup.AWARD_MEDAL, false);
}

