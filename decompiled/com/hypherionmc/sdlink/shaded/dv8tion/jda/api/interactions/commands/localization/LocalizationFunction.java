/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.localization;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.DiscordLocale;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Map;

public interface LocalizationFunction {
    @Nonnull
    public Map<DiscordLocale, String> apply(@Nonnull String var1);
}

