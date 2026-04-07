/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.localization;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.DiscordLocale;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.SerializableData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.slf4j.Logger;

public class LocalizationMap
implements SerializableData {
    public static final Logger LOG = JDALogger.getLog(LocalizationMap.class);
    protected final Map<DiscordLocale, String> map = new HashMap<DiscordLocale, String>();
    private final Consumer<String> checkConsumer;

    public LocalizationMap(@Nonnull Consumer<String> checkConsumer) {
        this.checkConsumer = checkConsumer;
    }

    private void putTranslation(DiscordLocale locale, String translation) {
        Checks.check(locale != DiscordLocale.UNKNOWN, "Cannot put an 'UNKNOWN' DiscordLocale");
        this.map.put(locale, translation);
    }

    @Override
    @Nonnull
    public DataObject toData() {
        DataObject data = DataObject.empty();
        this.map.forEach((locale, localizedString) -> data.put(locale.getLocale(), localizedString));
        return data;
    }

    public void setTranslation(@Nonnull DiscordLocale locale, @Nonnull String localizedString) {
        Checks.notNull((Object)locale, "Locale");
        Checks.notNull(localizedString, "Localized string");
        this.checkConsumer.accept(localizedString);
        this.putTranslation(locale, localizedString);
    }

    public void setTranslations(@Nonnull Map<DiscordLocale, String> map) {
        Checks.notNull(map, "Map");
        map.forEach((discordLocale, localizedString) -> {
            this.checkConsumer.accept((String)localizedString);
            this.putTranslation((DiscordLocale)((Object)discordLocale), (String)localizedString);
        });
    }

    @Nullable
    public String get(@Nonnull DiscordLocale locale) {
        Checks.notNull((Object)locale, "Locale");
        return this.map.get((Object)locale);
    }

    @Nonnull
    public Map<DiscordLocale, String> toMap() {
        return Collections.unmodifiableMap(this.map);
    }
}

