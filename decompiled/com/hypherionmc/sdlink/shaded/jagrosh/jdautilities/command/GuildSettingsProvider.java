/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command;

import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collection;

public interface GuildSettingsProvider {
    @Nullable
    default public Collection<String> getPrefixes() {
        return null;
    }
}

