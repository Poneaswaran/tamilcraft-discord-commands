/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.hooks;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.GenericEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.List;

public interface IEventManager {
    public void register(@Nonnull Object var1);

    public void unregister(@Nonnull Object var1);

    public void handle(@Nonnull GenericEvent var1);

    @Nonnull
    public List<Object> getRegisteredListeners();
}

