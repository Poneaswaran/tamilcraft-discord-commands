/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface GuildSettingsManager<T> {
    @Nullable
    public T getSettings(Guild var1);

    default public void init() {
    }

    default public void shutdown() {
    }
}

