/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jspecify.annotations.Nullable
 */
package com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji;

import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.Emoji;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.EmojiGroup;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.EmojiLanguage;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.EmojiLoader;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.EmojiLoaderA;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.EmojiLoaderB;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.EmojiSubGroup;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.IndexedEmoji;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.InternalAliasGroup;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.InternalEmojiUtils;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jspecify.annotations.Nullable;

public final class EmojiManager {
    private static final Map<String, Emoji> EMOJI_UNICODE_TO_EMOJI;
    private static final Map<Integer, List<Emoji>> EMOJI_FIRST_CODEPOINT_TO_EMOJIS_ORDER_CODEPOINT_LENGTH_DESCENDING;
    private static final List<Emoji> EMOJIS_LENGTH_DESCENDING;
    private static final Map<InternalAliasGroup, Map<String, Emoji>> ALIAS_GROUP_TO_EMOJI_ALIAS_TO_EMOJI;
    private static @Nullable Pattern EMOJI_PATTERN;
    private static final Pattern NOT_WANTED_EMOJI_CHARACTERS;
    private static final Map<EmojiLanguage, Map<String, String>> EMOJI_DESCRIPTION_LANGUAGE_MAP;
    private static final Map<EmojiLanguage, Map<String, List<String>>> EMOJI_KEYWORD_LANGUAGE_MAP;

    private static Collector<AbstractMap.SimpleEntry<String, Emoji>, ?, LinkedHashMap<Integer, List<Emoji>>> getEmojiLinkedHashMapCollector() {
        return Collectors.groupingBy(entry -> ((Emoji)entry.getValue()).getEmoji().codePointAt(0), LinkedHashMap::new, Collectors.collectingAndThen(Collectors.mapping(Map.Entry::getValue, Collectors.toList()), list -> {
            list.sort((e1, e2) -> Integer.compare(e2.getEmoji().length(), e1.getEmoji().length()));
            return list;
        }));
    }

    private static Stream<AbstractMap.SimpleEntry<String, Emoji>> prepareEmojisStreamForInitialization(Set<Emoji> emojis) {
        return emojis.stream().flatMap(emoji -> {
            Stream.Builder<AbstractMap.SimpleEntry<String, Emoji>> streamBuilder = Stream.builder();
            streamBuilder.add(new AbstractMap.SimpleEntry<String, Emoji>(emoji.getEmoji(), (Emoji)emoji));
            if (emoji.hasVariationSelectors()) {
                emoji.getTextVariation().ifPresent(variation -> streamBuilder.add(new AbstractMap.SimpleEntry<String, Emoji>((String)variation, (Emoji)emoji)));
                emoji.getEmojiVariation().ifPresent(variation -> streamBuilder.add(new AbstractMap.SimpleEntry<String, Emoji>((String)variation, (Emoji)emoji)));
            }
            return streamBuilder.build();
        });
    }

    static Optional<String> getEmojiDescriptionForLanguageAndEmoji(EmojiLanguage language, String emoji) {
        return Optional.ofNullable(EMOJI_DESCRIPTION_LANGUAGE_MAP.computeIfAbsent(language, emojiLanguage -> (Map)EmojiLoader.readFromAllLanguageResourceFiles("/emoji_sources/description/", emojiLanguage)).get(emoji));
    }

    static Optional<List<String>> getEmojiKeywordsForLanguageAndEmoji(EmojiLanguage language, String emoji) {
        return Optional.ofNullable(EMOJI_KEYWORD_LANGUAGE_MAP.computeIfAbsent(language, emojiLanguage -> (Map)EmojiLoader.readFromAllLanguageResourceFiles("/emoji_sources/keyword/", emojiLanguage)).get(emoji));
    }

    private static Map<String, Emoji> getEmojiAliasToEmoji(InternalAliasGroup internalAliasGroup) {
        return ALIAS_GROUP_TO_EMOJI_ALIAS_TO_EMOJI.computeIfAbsent(internalAliasGroup, group -> {
            HashMap<String, Emoji> emojiAliasToEmoji = new HashMap<String, Emoji>();
            for (Emoji emoji : EMOJIS_LENGTH_DESCENDING) {
                for (String alias : group.getAliasCollectionSupplier().apply(emoji)) {
                    emojiAliasToEmoji.put(alias, emoji);
                }
            }
            return Collections.unmodifiableMap(emojiAliasToEmoji);
        });
    }

    private EmojiManager() {
    }

    public static Optional<Emoji> getEmoji(String emoji) {
        if (InternalEmojiUtils.isStringNullOrEmpty(emoji)) {
            return Optional.empty();
        }
        return Optional.ofNullable(EMOJI_UNICODE_TO_EMOJI.get(emoji));
    }

    public static boolean isEmoji(String emoji) {
        if (InternalEmojiUtils.isStringNullOrEmpty(emoji)) {
            return false;
        }
        return EMOJI_UNICODE_TO_EMOJI.containsKey(emoji);
    }

    public static Set<Emoji> getAllEmojis() {
        return new HashSet<Emoji>(EMOJIS_LENGTH_DESCENDING);
    }

    public static Map<EmojiGroup, Set<Emoji>> getAllEmojisGrouped() {
        return EMOJIS_LENGTH_DESCENDING.stream().collect(Collectors.groupingBy(Emoji::getGroup, Collectors.toSet()));
    }

    public static Map<EmojiSubGroup, Set<Emoji>> getAllEmojisSubGrouped() {
        return EMOJIS_LENGTH_DESCENDING.stream().collect(Collectors.groupingBy(Emoji::getSubgroup, Collectors.toSet()));
    }

    public static Set<Emoji> getAllEmojisByGroup(EmojiGroup group) {
        return EMOJIS_LENGTH_DESCENDING.stream().filter(emoji -> emoji.getGroup() == group).collect(Collectors.toSet());
    }

    public static Set<Emoji> getAllEmojisBySubGroup(EmojiSubGroup subgroup) {
        return EMOJIS_LENGTH_DESCENDING.stream().filter(emoji -> emoji.getSubgroup() == subgroup).collect(Collectors.toSet());
    }

    public static List<Emoji> getAllEmojisLengthDescending() {
        return EMOJIS_LENGTH_DESCENDING;
    }

    public static Optional<Emoji> getByAlias(String alias) {
        if (InternalEmojiUtils.isStringNullOrEmpty(alias)) {
            return Optional.empty();
        }
        String aliasWithoutColon = InternalEmojiUtils.removeColonFromAlias(alias);
        String aliasWithColon = InternalEmojiUtils.addColonToAlias(alias);
        return Arrays.stream(InternalAliasGroup.values()).map(EmojiManager::getEmojiAliasToEmoji).filter(m -> m.containsKey(aliasWithColon) || m.containsKey(aliasWithoutColon)).map(m -> InternalEmojiUtils.findEmojiByEitherAlias(m, aliasWithColon, aliasWithoutColon)).findAny().flatMap(Function.identity());
    }

    public static Optional<Emoji> getByDiscordAlias(String alias) {
        if (InternalEmojiUtils.isStringNullOrEmpty(alias)) {
            return Optional.empty();
        }
        String aliasWithoutColon = InternalEmojiUtils.removeColonFromAlias(alias);
        String aliasWithColon = InternalEmojiUtils.addColonToAlias(alias);
        return InternalEmojiUtils.findEmojiByEitherAlias(EmojiManager.getEmojiAliasToEmoji(InternalAliasGroup.DISCORD), aliasWithColon, aliasWithoutColon);
    }

    public static Optional<Emoji> getByGithubAlias(String alias) {
        if (InternalEmojiUtils.isStringNullOrEmpty(alias)) {
            return Optional.empty();
        }
        String aliasWithoutColon = InternalEmojiUtils.removeColonFromAlias(alias);
        String aliasWithColon = InternalEmojiUtils.addColonToAlias(alias);
        return InternalEmojiUtils.findEmojiByEitherAlias(EmojiManager.getEmojiAliasToEmoji(InternalAliasGroup.GITHUB), aliasWithColon, aliasWithoutColon);
    }

    public static Optional<Emoji> getBySlackAlias(String alias) {
        if (InternalEmojiUtils.isStringNullOrEmpty(alias)) {
            return Optional.empty();
        }
        String aliasWithoutColon = InternalEmojiUtils.removeColonFromAlias(alias);
        String aliasWithColon = InternalEmojiUtils.addColonToAlias(alias);
        return InternalEmojiUtils.findEmojiByEitherAlias(EmojiManager.getEmojiAliasToEmoji(InternalAliasGroup.SLACK), aliasWithColon, aliasWithoutColon);
    }

    public static Pattern getEmojiPattern() {
        if (EMOJI_PATTERN == null) {
            EMOJI_PATTERN = Pattern.compile(EMOJIS_LENGTH_DESCENDING.stream().map(s -> "(" + Pattern.quote(s.getEmoji()) + ")").collect(Collectors.joining("|")));
        }
        return EMOJI_PATTERN;
    }

    public static boolean containsEmoji(String text) {
        if (InternalEmojiUtils.isStringNullOrEmpty(text)) {
            return false;
        }
        ArrayList emojis = new ArrayList();
        int[] textCodePointsArray = InternalEmojiUtils.stringToCodePoints(text);
        long textCodePointsLength = textCodePointsArray.length;
        int textIndex = 0;
        while ((long)textIndex < textCodePointsLength) {
            List<Emoji> emojisByCodePoint = EMOJI_FIRST_CODEPOINT_TO_EMOJIS_ORDER_CODEPOINT_LENGTH_DESCENDING.get(textCodePointsArray[textIndex]);
            if (emojisByCodePoint != null) {
                for (Emoji emoji : emojisByCodePoint) {
                    int[] emojiCodePointsArray = InternalEmojiUtils.stringToCodePoints(emoji.getEmoji());
                    int emojiCodePointsLength = emojiCodePointsArray.length;
                    if ((long)(textIndex + emojiCodePointsLength) > textCodePointsLength) continue;
                    for (int i = 0; i < emojiCodePointsLength && textCodePointsArray[textIndex + i] == emojiCodePointsArray[i]; ++i) {
                        if (i != emojiCodePointsLength - 1) continue;
                        return true;
                    }
                }
            }
            ++textIndex;
        }
        return false;
    }

    public static List<Emoji> extractEmojisInOrder(String text) {
        if (InternalEmojiUtils.isStringNullOrEmpty(text)) {
            return Collections.emptyList();
        }
        ArrayList<Emoji> emojis = new ArrayList<Emoji>();
        int[] textCodePointsArray = InternalEmojiUtils.stringToCodePoints(text);
        long textCodePointsLength = textCodePointsArray.length;
        int textIndex = 0;
        while ((long)textIndex < textCodePointsLength) {
            int currentCodepoint = textCodePointsArray[textIndex];
            List<Emoji> emojisByCodePoint = EMOJI_FIRST_CODEPOINT_TO_EMOJIS_ORDER_CODEPOINT_LENGTH_DESCENDING.get(currentCodepoint);
            if (emojisByCodePoint != null) {
                block1: for (Emoji emoji : emojisByCodePoint) {
                    int[] emojiCodePointsArray = InternalEmojiUtils.stringToCodePoints(emoji.getEmoji());
                    int emojiCodePointsLength = emojiCodePointsArray.length;
                    if ((long)(textIndex + emojiCodePointsLength) > textCodePointsLength) continue;
                    for (int emojiCodePointIndex = 0; emojiCodePointIndex < emojiCodePointsLength && textCodePointsArray[textIndex + emojiCodePointIndex] == emojiCodePointsArray[emojiCodePointIndex]; ++emojiCodePointIndex) {
                        if (emojiCodePointIndex != emojiCodePointsLength - 1) continue;
                        emojis.add(emoji);
                        textIndex += emojiCodePointsLength - 1;
                        break block1;
                    }
                }
            }
            ++textIndex;
        }
        return Collections.unmodifiableList(emojis);
    }

    public static List<IndexedEmoji> extractEmojisInOrderWithIndex(String text) {
        if (InternalEmojiUtils.isStringNullOrEmpty(text)) {
            return Collections.emptyList();
        }
        ArrayList<IndexedEmoji> emojis = new ArrayList<IndexedEmoji>();
        int[] textCodePointsArray = InternalEmojiUtils.stringToCodePoints(text);
        long textCodePointsLength = textCodePointsArray.length;
        int charIndex = 0;
        int textIndex = 0;
        while ((long)textIndex < textCodePointsLength) {
            block6: {
                int currentCodepoint = textCodePointsArray[textIndex];
                List<Emoji> emojisByCodePoint = EMOJI_FIRST_CODEPOINT_TO_EMOJIS_ORDER_CODEPOINT_LENGTH_DESCENDING.get(currentCodepoint);
                if (emojisByCodePoint == null) {
                    charIndex += Character.charCount(currentCodepoint);
                } else {
                    for (Emoji emoji : emojisByCodePoint) {
                        int[] emojiCodePointsArray = InternalEmojiUtils.stringToCodePoints(emoji.getEmoji());
                        int emojiCodePointsLength = emojiCodePointsArray.length;
                        if ((long)(textIndex + emojiCodePointsLength) > textCodePointsLength) continue;
                        for (int emojiCodePointIndex = 0; emojiCodePointIndex < emojiCodePointsLength && textCodePointsArray[textIndex + emojiCodePointIndex] == emojiCodePointsArray[emojiCodePointIndex]; ++emojiCodePointIndex) {
                            if (emojiCodePointIndex != emojiCodePointsLength - 1) continue;
                            emojis.add(new IndexedEmoji(emoji, charIndex, textIndex));
                            textIndex += emojiCodePointsLength - 1;
                            charIndex += emoji.getEmoji().length();
                            break block6;
                        }
                    }
                    charIndex += Character.charCount(currentCodepoint);
                }
            }
            ++textIndex;
        }
        return Collections.unmodifiableList(emojis);
    }

    public static Set<Emoji> extractEmojis(String text) {
        return Collections.unmodifiableSet(new HashSet<Emoji>(EmojiManager.extractEmojisInOrder(text)));
    }

    public static String removeAllEmojis(String text) {
        return EmojiManager.removeAllEmojisExcept(text, Collections.emptyList());
    }

    public static String removeEmojis(String text, Emoji ... emojisToRemove) {
        return EmojiManager.removeEmojis(text, Arrays.asList(emojisToRemove));
    }

    public static String removeEmojis(String text, Collection<Emoji> emojisToRemove) {
        HashSet<Emoji> emojis = new HashSet<Emoji>(EMOJIS_LENGTH_DESCENDING);
        emojis.removeAll(emojisToRemove);
        return EmojiManager.removeAllEmojisExcept(text, emojis);
    }

    public static String removeAllEmojisExcept(String text, Emoji ... emojisToKeep) {
        return EmojiManager.removeAllEmojisExcept(text, Arrays.asList(emojisToKeep));
    }

    public static String removeAllEmojisExcept(String text, Collection<Emoji> emojisToKeep) {
        if (InternalEmojiUtils.isStringNullOrEmpty(text)) {
            return "";
        }
        int[] textCodePointsArray = InternalEmojiUtils.stringToCodePoints(text);
        long textCodePointsLength = textCodePointsArray.length;
        StringBuilder sb = new StringBuilder();
        int textIndex = 0;
        while ((long)textIndex < textCodePointsLength) {
            int currentCodepoint = textCodePointsArray[textIndex];
            sb.appendCodePoint(currentCodepoint);
            List<Emoji> emojisByCodePoint = EMOJI_FIRST_CODEPOINT_TO_EMOJIS_ORDER_CODEPOINT_LENGTH_DESCENDING.get(currentCodepoint);
            if (emojisByCodePoint != null) {
                block1: for (Emoji emoji : emojisByCodePoint) {
                    int[] emojiCodePointsArray = InternalEmojiUtils.stringToCodePoints(emoji.getEmoji());
                    int emojiCodePointsLength = emojiCodePointsArray.length;
                    if ((long)(textIndex + emojiCodePointsLength) > textCodePointsLength) continue;
                    for (int emojiCodePointIndex = 0; emojiCodePointIndex < emojiCodePointsLength && textCodePointsArray[textIndex + emojiCodePointIndex] == emojiCodePointsArray[emojiCodePointIndex]; ++emojiCodePointIndex) {
                        if (emojiCodePointIndex != emojiCodePointsLength - 1) continue;
                        textIndex += emojiCodePointsLength - 1;
                        sb.delete(sb.length() - Character.charCount(currentCodepoint), sb.length());
                        if (!emojisToKeep.contains(emoji)) break block1;
                        sb.append(emoji.getEmoji());
                        break block1;
                    }
                }
            }
            ++textIndex;
        }
        return sb.toString();
    }

    public static String replaceAllEmojis(String text, String replacementString) {
        return EmojiManager.replaceEmojis(text, replacementString, EMOJIS_LENGTH_DESCENDING);
    }

    public static String replaceAllEmojis(String text, Function<Emoji, String> replacementFunction) {
        return EmojiManager.replaceEmojis(text, replacementFunction, EMOJIS_LENGTH_DESCENDING);
    }

    public static String replaceEmojis(String text, String replacementString, Collection<Emoji> emojisToReplace) {
        return EmojiManager.replaceEmojis(text, (Emoji emoji) -> replacementString, emojisToReplace);
    }

    public static String replaceEmojis(String text, String replacementString, Emoji ... emojisToReplace) {
        return EmojiManager.replaceEmojis(text, (Emoji emoji) -> replacementString, Arrays.asList(emojisToReplace));
    }

    public static String replaceEmojis(String text, Function<Emoji, String> replacementFunction, Collection<Emoji> emojisToReplace) {
        if (InternalEmojiUtils.isStringNullOrEmpty(text)) {
            return "";
        }
        int[] textCodePointsArray = InternalEmojiUtils.stringToCodePoints(text);
        long textCodePointsLength = textCodePointsArray.length;
        StringBuilder sb = new StringBuilder();
        int textIndex = 0;
        while ((long)textIndex < textCodePointsLength) {
            int currentCodepoint = textCodePointsArray[textIndex];
            sb.appendCodePoint(currentCodepoint);
            List<Emoji> emojisByCodePoint = EMOJI_FIRST_CODEPOINT_TO_EMOJIS_ORDER_CODEPOINT_LENGTH_DESCENDING.get(currentCodepoint);
            if (emojisByCodePoint != null) {
                block1: for (Emoji emoji : emojisByCodePoint) {
                    int[] emojiCodePointsArray = InternalEmojiUtils.stringToCodePoints(emoji.getEmoji());
                    int emojiCodePointsLength = emojiCodePointsArray.length;
                    if ((long)(textIndex + emojiCodePointsLength) > textCodePointsLength) continue;
                    for (int emojiCodePointIndex = 0; emojiCodePointIndex < emojiCodePointsLength && textCodePointsArray[textIndex + emojiCodePointIndex] == emojiCodePointsArray[emojiCodePointIndex]; ++emojiCodePointIndex) {
                        if (emojiCodePointIndex != emojiCodePointsLength - 1) continue;
                        textIndex += emojiCodePointsLength - 1;
                        sb.delete(sb.length() - Character.charCount(currentCodepoint), sb.length());
                        if (emojisToReplace.contains(emoji)) {
                            sb.append(replacementFunction.apply(emoji));
                            break block1;
                        }
                        sb.append(emoji.getEmoji());
                        break block1;
                    }
                }
            }
            ++textIndex;
        }
        return sb.toString();
    }

    public static String replaceEmojis(String text, Function<Emoji, String> replacementFunction, Emoji ... emojisToReplace) {
        return EmojiManager.replaceEmojis(text, replacementFunction, Arrays.asList(emojisToReplace));
    }

    static {
        ALIAS_GROUP_TO_EMOJI_ALIAS_TO_EMOJI = new EnumMap<InternalAliasGroup, Map<String, Emoji>>(InternalAliasGroup.class);
        NOT_WANTED_EMOJI_CHARACTERS = Pattern.compile("[\\p{Alpha}\\p{Z}]");
        EMOJI_DESCRIPTION_LANGUAGE_MAP = new HashMap<EmojiLanguage, Map<String, String>>();
        EMOJI_KEYWORD_LANGUAGE_MAP = new HashMap<EmojiLanguage, Map<String, List<String>>>();
        HashSet<Emoji> emojis = new HashSet<Emoji>();
        emojis.addAll(EmojiLoaderA.EMOJI_LIST);
        emojis.addAll(EmojiLoaderB.EMOJI_LIST);
        EMOJI_UNICODE_TO_EMOJI = Collections.unmodifiableMap(EmojiManager.prepareEmojisStreamForInitialization(emojis).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue, (existing, replacement) -> existing)));
        EMOJIS_LENGTH_DESCENDING = Collections.unmodifiableList(emojis.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
        EMOJI_FIRST_CODEPOINT_TO_EMOJIS_ORDER_CODEPOINT_LENGTH_DESCENDING = Collections.unmodifiableMap((Map)EmojiManager.prepareEmojisStreamForInitialization(emojis).collect(EmojiManager.getEmojiLinkedHashMapCollector()));
    }
}

