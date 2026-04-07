/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji;

import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.EmojiSubGroup;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public enum EmojiGroup {
    ACTIVITIES("Activities"),
    ANIMALS_AND_NATURE("Animals & Nature"),
    COMPONENT("Component"),
    FLAGS("Flags"),
    FOOD_AND_DRINK("Food & Drink"),
    OBJECTS("Objects"),
    PEOPLE_AND_BODY("People & Body"),
    SMILEYS_AND_EMOTION("Smileys & Emotion"),
    SYMBOLS("Symbols"),
    TRAVEL_AND_PLACES("Travel & Places");

    private static final List<EmojiGroup> EMOJI_GROUPS;
    private final String name;

    private EmojiGroup(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static List<EmojiGroup> getGroups() {
        return EMOJI_GROUPS;
    }

    public EnumSet<EmojiSubGroup> getEmojiSubGroups() {
        return EnumSet.copyOf(EmojiSubGroup.getSubGroups().stream().filter(subgroup -> subgroup.getGroup() == this).collect(Collectors.toList()));
    }

    public static EmojiGroup fromString(String name) {
        for (EmojiGroup emojiGroup : EMOJI_GROUPS) {
            if (!emojiGroup.getName().equals(name)) continue;
            return emojiGroup;
        }
        throw new IllegalArgumentException("No EmojiGroup found for name " + name);
    }

    static {
        EMOJI_GROUPS = Arrays.asList(EmojiGroup.values());
    }
}

