/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface ICommandReference
extends IMentionable {
    @Nonnull
    public String getName();

    @Nonnull
    public String getFullCommandName();

    @Override
    @Nonnull
    default public String getAsMention() {
        return "</" + this.getFullCommandName() + ":" + this.getIdLong() + ">";
    }
}

