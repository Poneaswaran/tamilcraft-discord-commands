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

interface EmojiLock {
    public static final Emoji LOCKED = new Emoji("\ud83d\udd12", "\\uD83D\\uDD12", Collections.unmodifiableList(Arrays.asList(":lock:", ":locked:")), Collections.singletonList(":lock:"), Collections.singletonList(":lock:"), Collections.unmodifiableList(Arrays.asList("closed", "lock", "locked", "private")), false, false, 0.6, Qualification.fromString("fully-qualified"), "locked", EmojiGroup.OBJECTS, EmojiSubGroup.LOCK, true);
    public static final Emoji UNLOCKED = new Emoji("\ud83d\udd13", "\\uD83D\\uDD13", Collections.unmodifiableList(Arrays.asList(":unlock:", ":unlocked:")), Collections.singletonList(":unlock:"), Collections.singletonList(":unlock:"), Collections.unmodifiableList(Arrays.asList("cracked", "lock", "open", "unlock", "unlocked")), false, false, 0.6, Qualification.fromString("fully-qualified"), "unlocked", EmojiGroup.OBJECTS, EmojiSubGroup.LOCK, true);
    public static final Emoji LOCKED_WITH_PEN = new Emoji("\ud83d\udd0f", "\\uD83D\\uDD0F", Collections.singletonList(":lock_with_ink_pen:"), Collections.singletonList(":lock_with_ink_pen:"), Collections.singletonList(":lock_with_ink_pen:"), Collections.unmodifiableList(Arrays.asList("ink", "lock", "locked", "nib", "pen", "privacy")), false, false, 0.6, Qualification.fromString("fully-qualified"), "locked with pen", EmojiGroup.OBJECTS, EmojiSubGroup.LOCK, false);
    public static final Emoji LOCKED_WITH_KEY = new Emoji("\ud83d\udd10", "\\uD83D\\uDD10", Collections.singletonList(":closed_lock_with_key:"), Collections.singletonList(":closed_lock_with_key:"), Collections.singletonList(":closed_lock_with_key:"), Collections.unmodifiableList(Arrays.asList("bike", "closed", "key", "lock", "locked", "secure")), false, false, 0.6, Qualification.fromString("fully-qualified"), "locked with key", EmojiGroup.OBJECTS, EmojiSubGroup.LOCK, false);
    public static final Emoji KEY = new Emoji("\ud83d\udd11", "\\uD83D\\uDD11", Collections.singletonList(":key:"), Collections.singletonList(":key:"), Collections.singletonList(":key:"), Collections.unmodifiableList(Arrays.asList("key", "keys", "lock", "major", "password", "unlock")), false, false, 0.6, Qualification.fromString("fully-qualified"), "key", EmojiGroup.OBJECTS, EmojiSubGroup.LOCK, false);
    public static final Emoji OLD_KEY = new Emoji("\ud83d\udddd\ufe0f", "\\uD83D\\uDDDD\\uFE0F", Collections.unmodifiableList(Arrays.asList(":key2:", ":old_key:")), Collections.singletonList(":old_key:"), Collections.singletonList(":old_key:"), Collections.unmodifiableList(Arrays.asList("clue", "key", "lock", "old")), false, false, 0.7, Qualification.fromString("fully-qualified"), "old key", EmojiGroup.OBJECTS, EmojiSubGroup.LOCK, false);
    public static final Emoji OLD_KEY_UNQUALIFIED = new Emoji("\ud83d\udddd", "\\uD83D\\uDDDD", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.unmodifiableList(Arrays.asList("clue", "key", "lock", "old")), false, false, 0.7, Qualification.fromString("unqualified"), "old key", EmojiGroup.OBJECTS, EmojiSubGroup.LOCK, true);
}

