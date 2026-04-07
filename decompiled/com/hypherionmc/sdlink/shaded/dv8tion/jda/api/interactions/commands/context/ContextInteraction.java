/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.context;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.CommandInteraction;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface ContextInteraction<T>
extends CommandInteraction {
    @Nonnull
    public ContextTarget getTargetType();

    @Nonnull
    public T getTarget();

    public static enum ContextTarget {
        USER,
        MESSAGE;

    }
}

