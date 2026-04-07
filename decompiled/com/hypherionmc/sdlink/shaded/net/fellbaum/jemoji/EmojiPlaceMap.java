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

interface EmojiPlaceMap {
    public static final Emoji GLOBE_SHOWING_EUROPE_AFRICA = new Emoji("\ud83c\udf0d", "\\uD83C\\uDF0D", Collections.singletonList(":earth_africa:"), Collections.singletonList(":earth_africa:"), Collections.singletonList(":earth_africa:"), Collections.unmodifiableList(Arrays.asList("Africa", "earth", "Europe", "Europe-Africa", "globe", "showing", "world")), false, false, 0.7, Qualification.fromString("fully-qualified"), "globe showing Europe-Africa", EmojiGroup.TRAVEL_AND_PLACES, EmojiSubGroup.PLACE_MAP, true);
    public static final Emoji GLOBE_SHOWING_AMERICAS = new Emoji("\ud83c\udf0e", "\\uD83C\\uDF0E", Collections.singletonList(":earth_americas:"), Collections.singletonList(":earth_americas:"), Collections.singletonList(":earth_americas:"), Collections.unmodifiableList(Arrays.asList("Americas", "earth", "globe", "showing", "world")), false, false, 0.7, Qualification.fromString("fully-qualified"), "globe showing Americas", EmojiGroup.TRAVEL_AND_PLACES, EmojiSubGroup.PLACE_MAP, true);
    public static final Emoji GLOBE_SHOWING_ASIA_AUSTRALIA = new Emoji("\ud83c\udf0f", "\\uD83C\\uDF0F", Collections.singletonList(":earth_asia:"), Collections.singletonList(":earth_asia:"), Collections.singletonList(":earth_asia:"), Collections.unmodifiableList(Arrays.asList("Asia", "Asia-Australia", "Australia", "earth", "globe", "showing", "world")), false, false, 0.6, Qualification.fromString("fully-qualified"), "globe showing Asia-Australia", EmojiGroup.TRAVEL_AND_PLACES, EmojiSubGroup.PLACE_MAP, true);
    public static final Emoji GLOBE_WITH_MERIDIANS = new Emoji("\ud83c\udf10", "\\uD83C\\uDF10", Collections.singletonList(":globe_with_meridians:"), Collections.singletonList(":globe_with_meridians:"), Collections.singletonList(":globe_with_meridians:"), Collections.unmodifiableList(Arrays.asList("earth", "globe", "internet", "meridians", "web", "world", "worldwide")), false, false, 1.0, Qualification.fromString("fully-qualified"), "globe with meridians", EmojiGroup.TRAVEL_AND_PLACES, EmojiSubGroup.PLACE_MAP, false);
    public static final Emoji WORLD_MAP = new Emoji("\ud83d\uddfa\ufe0f", "\\uD83D\\uDDFA\\uFE0F", Collections.unmodifiableList(Arrays.asList(":map:", ":world_map:")), Collections.singletonList(":world_map:"), Collections.singletonList(":world_map:"), Collections.unmodifiableList(Arrays.asList("map", "world")), false, false, 0.7, Qualification.fromString("fully-qualified"), "world map", EmojiGroup.TRAVEL_AND_PLACES, EmojiSubGroup.PLACE_MAP, false);
    public static final Emoji WORLD_MAP_UNQUALIFIED = new Emoji("\ud83d\uddfa", "\\uD83D\\uDDFA", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.unmodifiableList(Arrays.asList("map", "world")), false, false, 0.7, Qualification.fromString("unqualified"), "world map", EmojiGroup.TRAVEL_AND_PLACES, EmojiSubGroup.PLACE_MAP, true);
    public static final Emoji MAP_OF_JAPAN = new Emoji("\ud83d\uddfe", "\\uD83D\\uDDFE", Collections.unmodifiableList(Arrays.asList(":japan:", ":map_of_japan:")), Collections.singletonList(":japan:"), Collections.singletonList(":japan:"), Collections.unmodifiableList(Arrays.asList("Japan", "map")), false, false, 0.6, Qualification.fromString("fully-qualified"), "map of Japan", EmojiGroup.TRAVEL_AND_PLACES, EmojiSubGroup.PLACE_MAP, false);
    public static final Emoji COMPASS = new Emoji("\ud83e\udded", "\\uD83E\\uDDED", Collections.singletonList(":compass:"), Collections.singletonList(":compass:"), Collections.singletonList(":compass:"), Collections.unmodifiableList(Arrays.asList("compass", "direction", "magnetic", "navigation", "orienteering")), false, false, 11.0, Qualification.fromString("fully-qualified"), "compass", EmojiGroup.TRAVEL_AND_PLACES, EmojiSubGroup.PLACE_MAP, false);
}

