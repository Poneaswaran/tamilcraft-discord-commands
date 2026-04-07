/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.components.ItemComponent;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface ActionComponent
extends ItemComponent {
    @Nullable
    public String getId();

    public boolean isDisabled();

    @Nonnull
    @CheckReturnValue
    default public ActionComponent asDisabled() {
        return this.withDisabled(true);
    }

    @Nonnull
    @CheckReturnValue
    default public ActionComponent asEnabled() {
        return this.withDisabled(false);
    }

    @Nonnull
    @CheckReturnValue
    public ActionComponent withDisabled(boolean var1);
}

