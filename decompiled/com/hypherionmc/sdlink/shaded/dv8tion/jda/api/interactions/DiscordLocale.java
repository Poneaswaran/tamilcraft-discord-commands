/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Locale;

public enum DiscordLocale {
    BULGARIAN("bg", "Bulgarian", "\u0431\u044a\u043b\u0433\u0430\u0440\u0441\u043a\u0438"),
    CHINESE_CHINA("zh-CN", "Chinese, China", "\u4e2d\u6587"),
    CHINESE_TAIWAN("zh-TW", "Chinese, Taiwan", "\u7e41\u9ad4\u4e2d\u6587"),
    CROATIAN("hr", "Croatian", "Hrvatski"),
    CZECH("cs", "Czech", "\u010ce\u0161tina"),
    DANISH("da", "Danish", "Dansk"),
    DUTCH("nl", "Dutch", "Nederlands"),
    ENGLISH_UK("en-GB", "English, UK", "English, UK"),
    ENGLISH_US("en-US", "English, US", "English, US"),
    FINNISH("fi", "Finnish", "Suomi"),
    FRENCH("fr", "French", "Fran\u00e7ais"),
    GERMAN("de", "German", "Deutsch"),
    GREEK("el", "Greek", "\u0395\u03bb\u03bb\u03b7\u03bd\u03b9\u03ba\u03ac"),
    HINDI("hi", "Hindi", "\u0939\u093f\u0928\u094d\u0926\u0940"),
    HUNGARIAN("hu", "Hungarian", "Magyar"),
    INDONESIAN("id", "Indonesian", "Bahasa Indonesia"),
    ITALIAN("it", "Italian", "Italiano"),
    JAPANESE("ja", "Japanese", "\u65e5\u672c\u8a9e"),
    KOREAN("ko", "Korean", "\ud55c\uad6d\uc5b4"),
    LITHUANIAN("lt", "Lithuanian", "Lietuvi\u0161kai"),
    NORWEGIAN("no", "Norwegian", "Norsk"),
    POLISH("pl", "Polish", "Polski"),
    PORTUGUESE_BRAZILIAN("pt-BR", "Portuguese, Brazilian", "Portugu\u00eas do Brasil"),
    ROMANIAN_ROMANIA("ro", "Romanian, Romania", "Rom\u00e2n\u0103"),
    RUSSIAN("ru", "Russian", "P\u0443\u0441\u0441\u043a\u0438\u0439"),
    SPANISH("es-ES", "Spanish", "Espa\u00f1ol"),
    SPANISH_LATAM("es-419", "Spanish, LATAM", "Espa\u00f1ol, LATAM"),
    SWEDISH("sv-SE", "Swedish", "Svenska"),
    THAI("th", "Thai", "\u0e44\u0e17\u0e22"),
    TURKISH("tr", "Turkish", "T\u00fcrk\u00e7e"),
    UKRAINIAN("uk", "Ukrainian", "\u0423\u043a\u0440\u0430\u0457\u043d\u0441\u044c\u043a\u0430"),
    VIETNAMESE("vi", "Vietnamese", "Ti\u1ebfng Vi\u1ec7t"),
    UNKNOWN("unknown", "Unknown", "Unknown");

    private final String locale;
    private final String languageName;
    private final String nativeName;

    private DiscordLocale(String locale, String languageName, String nativeName) {
        this.locale = locale;
        this.languageName = languageName;
        this.nativeName = nativeName;
    }

    @Nonnull
    public String getLocale() {
        return this.locale;
    }

    @Nonnull
    public Locale toLocale() {
        return Locale.forLanguageTag(this.getLocale());
    }

    @Nonnull
    public String getLanguageName() {
        return this.languageName;
    }

    @Nonnull
    public String getNativeName() {
        return this.nativeName;
    }

    @Nonnull
    public static DiscordLocale from(@Nonnull String localeTag) {
        Checks.notNull(localeTag, "Locale tag");
        for (DiscordLocale discordLocale : DiscordLocale.values()) {
            if (!discordLocale.locale.equals(localeTag)) continue;
            return discordLocale;
        }
        return UNKNOWN;
    }

    @Nonnull
    public static DiscordLocale from(@Nonnull Locale locale) {
        Checks.notNull(locale, "Locale");
        return DiscordLocale.from(locale.toLanguageTag());
    }
}

