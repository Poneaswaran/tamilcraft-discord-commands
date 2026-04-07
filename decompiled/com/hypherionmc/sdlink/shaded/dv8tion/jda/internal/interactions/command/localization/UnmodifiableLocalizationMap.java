/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.interactions.command.localization;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.DiscordLocale;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.localization.LocalizationMap;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Consumer;

public class UnmodifiableLocalizationMap
extends LocalizationMap {
    public static final Consumer<String> UNMODIFIABLE_CHECK = s -> {
        throw new IllegalStateException("This LocalizationMap is unmodifiable.");
    };

    public UnmodifiableLocalizationMap(@Nonnull Map<DiscordLocale, String> map) {
        super(UNMODIFIABLE_CHECK);
        this.map.putAll(map);
    }
}

