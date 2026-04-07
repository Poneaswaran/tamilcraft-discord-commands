/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.hooks;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.GenericEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

@FunctionalInterface
public interface EventListener {
    public void onEvent(@Nonnull GenericEvent var1);
}

