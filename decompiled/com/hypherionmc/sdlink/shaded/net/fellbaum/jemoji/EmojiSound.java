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

interface EmojiSound {
    public static final Emoji MUTED_SPEAKER = new Emoji("\ud83d\udd07", "\\uD83D\\uDD07", Collections.unmodifiableList(Arrays.asList(":mute:", ":muted_speaker:")), Collections.singletonList(":mute:"), Collections.singletonList(":mute:"), Collections.unmodifiableList(Arrays.asList("mute", "muted", "quiet", "silent", "sound", "speaker")), false, false, 1.0, Qualification.fromString("fully-qualified"), "muted speaker", EmojiGroup.OBJECTS, EmojiSubGroup.SOUND, false);
    public static final Emoji SPEAKER_LOW_VOLUME = new Emoji("\ud83d\udd08", "\\uD83D\\uDD08", Collections.singletonList(":speaker:"), Collections.singletonList(":speaker:"), Collections.singletonList(":speaker:"), Collections.unmodifiableList(Arrays.asList("low", "soft", "sound", "speaker", "volume")), false, false, 0.7, Qualification.fromString("fully-qualified"), "speaker low volume", EmojiGroup.OBJECTS, EmojiSubGroup.SOUND, true);
    public static final Emoji SPEAKER_MEDIUM_VOLUME = new Emoji("\ud83d\udd09", "\\uD83D\\uDD09", Collections.singletonList(":sound:"), Collections.singletonList(":sound:"), Collections.singletonList(":sound:"), Collections.unmodifiableList(Arrays.asList("medium", "sound", "speaker", "volume")), false, false, 1.0, Qualification.fromString("fully-qualified"), "speaker medium volume", EmojiGroup.OBJECTS, EmojiSubGroup.SOUND, false);
    public static final Emoji SPEAKER_HIGH_VOLUME = new Emoji("\ud83d\udd0a", "\\uD83D\\uDD0A", Collections.singletonList(":loud_sound:"), Collections.singletonList(":loud_sound:"), Collections.singletonList(":loud_sound:"), Collections.unmodifiableList(Arrays.asList("high", "loud", "music", "sound", "speaker", "volume")), false, false, 0.6, Qualification.fromString("fully-qualified"), "speaker high volume", EmojiGroup.OBJECTS, EmojiSubGroup.SOUND, false);
    public static final Emoji LOUDSPEAKER = new Emoji("\ud83d\udce2", "\\uD83D\\uDCE2", Collections.singletonList(":loudspeaker:"), Collections.singletonList(":loudspeaker:"), Collections.singletonList(":loudspeaker:"), Collections.unmodifiableList(Arrays.asList("address", "communication", "loud", "loudspeaker", "public", "sound")), false, false, 0.6, Qualification.fromString("fully-qualified"), "loudspeaker", EmojiGroup.OBJECTS, EmojiSubGroup.SOUND, false);
    public static final Emoji MEGAPHONE = new Emoji("\ud83d\udce3", "\\uD83D\\uDCE3", Collections.unmodifiableList(Arrays.asList(":mega:", ":megaphone:")), Collections.singletonList(":mega:"), Collections.singletonList(":mega:"), Collections.unmodifiableList(Arrays.asList("cheering", "megaphone", "sound")), false, false, 0.6, Qualification.fromString("fully-qualified"), "megaphone", EmojiGroup.OBJECTS, EmojiSubGroup.SOUND, false);
    public static final Emoji POSTAL_HORN = new Emoji("\ud83d\udcef", "\\uD83D\\uDCEF", Collections.singletonList(":postal_horn:"), Collections.singletonList(":postal_horn:"), Collections.singletonList(":postal_horn:"), Collections.unmodifiableList(Arrays.asList("horn", "post", "postal")), false, false, 1.0, Qualification.fromString("fully-qualified"), "postal horn", EmojiGroup.OBJECTS, EmojiSubGroup.SOUND, false);
    public static final Emoji BELL = new Emoji("\ud83d\udd14", "\\uD83D\\uDD14", Collections.singletonList(":bell:"), Collections.singletonList(":bell:"), Collections.singletonList(":bell:"), Collections.unmodifiableList(Arrays.asList("bell", "break", "church", "sound")), false, false, 0.6, Qualification.fromString("fully-qualified"), "bell", EmojiGroup.OBJECTS, EmojiSubGroup.SOUND, false);
    public static final Emoji BELL_WITH_SLASH = new Emoji("\ud83d\udd15", "\\uD83D\\uDD15", Collections.singletonList(":no_bell:"), Collections.singletonList(":no_bell:"), Collections.singletonList(":no_bell:"), Collections.unmodifiableList(Arrays.asList("bell", "forbidden", "mute", "no", "not", "prohibited", "quiet", "silent", "slash", "sound")), false, false, 1.0, Qualification.fromString("fully-qualified"), "bell with slash", EmojiGroup.OBJECTS, EmojiSubGroup.SOUND, false);
}

