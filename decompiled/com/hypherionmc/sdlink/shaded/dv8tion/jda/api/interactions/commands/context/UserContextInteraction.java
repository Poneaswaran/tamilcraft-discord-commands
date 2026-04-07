/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.context;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.context.ContextInteraction;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface UserContextInteraction
extends ContextInteraction<User> {
    @Override
    @Nonnull
    default public ContextInteraction.ContextTarget getTargetType() {
        return ContextInteraction.ContextTarget.USER;
    }

    @Nullable
    public Member getTargetMember();
}

