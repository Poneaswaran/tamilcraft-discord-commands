/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji;

import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.EmojiGroup;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.EmojiLanguage;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.EmojiManager;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.EmojiSubGroup;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.Fitzpatrick;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.HairStyle;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.InternalEmojiUtils;
import com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.Qualification;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class Emoji
implements Comparable<Emoji> {
    private final String emoji;
    private final String unicode;
    private final List<String> discordAliases;
    private final List<String> githubAliases;
    private final List<String> slackAliases;
    private final List<String> keywords;
    private final boolean hasFitzpatrick;
    private final boolean hasHairStyle;
    private final double version;
    private final Qualification qualification;
    private final String description;
    private final EmojiGroup group;
    private final EmojiSubGroup subgroup;
    private final boolean hasVariationSelectors;
    private final List<String> allAliases;

    Emoji(String emoji, String unicode, List<String> discordAliases, List<String> slackAliases, List<String> githubAliases, List<String> keywords, boolean hasFitzpatrick, boolean hasHairStyle, double version, Qualification qualification, String description, EmojiGroup group, EmojiSubGroup subgroup, boolean hasVariationSelectors) {
        this.emoji = emoji;
        this.unicode = unicode;
        this.discordAliases = discordAliases;
        this.githubAliases = githubAliases;
        this.slackAliases = slackAliases;
        this.keywords = keywords;
        this.hasFitzpatrick = hasFitzpatrick;
        this.hasHairStyle = hasHairStyle;
        this.version = version;
        this.qualification = qualification;
        this.description = description;
        this.group = group;
        this.subgroup = subgroup;
        this.hasVariationSelectors = hasVariationSelectors;
        HashSet<String> aliases = new HashSet<String>();
        aliases.addAll(this.getDiscordAliases());
        aliases.addAll(this.getGithubAliases());
        aliases.addAll(this.getSlackAliases());
        this.allAliases = Collections.unmodifiableList(new ArrayList(aliases));
    }

    public String getEmoji() {
        return this.emoji;
    }

    public String getUnicode() {
        return this.unicode;
    }

    public String getHtmlDecimalCode() {
        return this.getEmoji().codePoints().mapToObj(operand -> "&#" + operand).collect(Collectors.joining(";")) + ";";
    }

    public String getHtmlHexadecimalCode() {
        return this.getEmoji().codePoints().mapToObj(operand -> "&#x" + Integer.toHexString(operand).toUpperCase()).collect(Collectors.joining(";")) + ";";
    }

    public List<Emoji> getVariations() {
        String baseEmoji = HairStyle.removeHairStyle(Fitzpatrick.removeFitzpatrick(this.emoji));
        return EmojiManager.getAllEmojis().parallelStream().filter(emoji -> HairStyle.removeHairStyle(Fitzpatrick.removeFitzpatrick(emoji.getEmoji())).equals(baseEmoji)).filter(emoji -> !emoji.equals(this)).collect(Collectors.toList());
    }

    public String getURLEncoded() {
        try {
            return URLEncoder.encode(this.getEmoji(), StandardCharsets.UTF_8.toString());
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getDiscordAliases() {
        return this.discordAliases;
    }

    public List<String> getGithubAliases() {
        return this.githubAliases;
    }

    public List<String> getSlackAliases() {
        return this.slackAliases;
    }

    public List<String> getAllAliases() {
        return this.allAliases;
    }

    public boolean hasFitzpatrickComponent() {
        return this.hasFitzpatrick;
    }

    public boolean hasHairStyleComponent() {
        return this.hasHairStyle;
    }

    public double getVersion() {
        return this.version;
    }

    public Qualification getQualification() {
        return this.qualification;
    }

    public String getDescription() {
        return this.description;
    }

    public Optional<String> getDescription(EmojiLanguage emojiLanguage) {
        return EmojiManager.getEmojiDescriptionForLanguageAndEmoji(emojiLanguage, this.emoji);
    }

    public List<String> getKeywords() {
        return this.keywords;
    }

    public Optional<List<String>> getKeywords(EmojiLanguage emojiLanguage) {
        return EmojiManager.getEmojiKeywordsForLanguageAndEmoji(emojiLanguage, this.emoji);
    }

    public EmojiGroup getGroup() {
        return this.group;
    }

    public EmojiSubGroup getSubgroup() {
        return this.subgroup;
    }

    public boolean hasVariationSelectors() {
        return this.hasVariationSelectors;
    }

    public Optional<String> getTextVariation() {
        return this.hasVariationSelectors() ? Optional.of(this.emoji + '\ufe0e') : Optional.empty();
    }

    public Optional<String> getEmojiVariation() {
        return this.hasVariationSelectors() ? Optional.of(this.emoji + '\ufe0f') : Optional.empty();
    }

    @Override
    public int compareTo(Emoji o) {
        int comparedValue = Integer.compare(InternalEmojiUtils.getCodePointCount(this.getEmoji()), InternalEmojiUtils.getCodePointCount(o.getEmoji()));
        if (comparedValue != 0) {
            return comparedValue;
        }
        return this.getEmoji().compareTo(o.getEmoji());
    }

    public String toString() {
        return "Emoji{emoji='" + this.emoji + '\'' + ", unicode='" + this.unicode + '\'' + ", discordAliases=" + this.discordAliases + ", githubAliases=" + this.githubAliases + ", slackAliases=" + this.slackAliases + ", hasFitzpatrick=" + this.hasFitzpatrick + ", hasHairStyle=" + this.hasHairStyle + ", version=" + this.version + ", qualification=" + (Object)((Object)this.qualification) + ", description='" + this.description + '\'' + ", group=" + (Object)((Object)this.group) + ", subgroup=" + (Object)((Object)this.subgroup) + ", hasVariationSelectors=" + this.hasVariationSelectors + ", allAliases=" + this.allAliases + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Emoji emoji1 = (Emoji)o;
        return this.emoji.equals(emoji1.emoji);
    }

    public int hashCode() {
        return this.emoji.hashCode();
    }
}

