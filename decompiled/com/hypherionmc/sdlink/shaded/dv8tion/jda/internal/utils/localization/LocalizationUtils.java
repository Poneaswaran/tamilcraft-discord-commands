/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.localization;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.DiscordLocale;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.localization.LocalizationMap;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.localization.UnmodifiableLocalizationMap;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;

public class LocalizationUtils {
    public static final Logger LOG = JDALogger.getLog(LocalizationUtils.class);

    @Nonnull
    public static Map<DiscordLocale, String> mapFromData(@Nonnull DataObject data) {
        Checks.notNull(data, "Data");
        HashMap<DiscordLocale, String> map = new HashMap<DiscordLocale, String>();
        for (String key : data.keys()) {
            DiscordLocale locale = DiscordLocale.from(key);
            if (locale == DiscordLocale.UNKNOWN) {
                LOG.debug("Discord provided an unknown locale, locale tag: {}", (Object)key);
                continue;
            }
            map.put(locale, data.getString(key));
        }
        return map;
    }

    @Nonnull
    public static Map<DiscordLocale, String> mapFromProperty(@Nonnull DataObject json, @Nonnull String localizationProperty) {
        return json.optObject(localizationProperty).map(LocalizationUtils::mapFromData).orElse(Collections.emptyMap());
    }

    @Nonnull
    public static LocalizationMap unmodifiableFromProperty(@Nonnull DataObject json, @Nonnull String localizationProperty) {
        return new UnmodifiableLocalizationMap(LocalizationUtils.mapFromProperty(json, localizationProperty));
    }
}

