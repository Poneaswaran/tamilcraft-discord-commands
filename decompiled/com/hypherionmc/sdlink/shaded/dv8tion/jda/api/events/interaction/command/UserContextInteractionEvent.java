/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.interaction.command.GenericContextInteractionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.interactions.commands.context.UserContextInteraction;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class UserContextInteractionEvent
extends GenericContextInteractionEvent<User>
implements UserContextInteraction {
    public UserContextInteractionEvent(@Nonnull JDA api, long responseNumber, @Nonnull UserContextInteraction interaction) {
        super(api, responseNumber, interaction);
    }

    @Override
    @Nonnull
    public UserContextInteraction getInteraction() {
        return (UserContextInteraction)super.getInteraction();
    }

    @Override
    @Nullable
    public Member getTargetMember() {
        return this.getInteraction().getTargetMember();
    }
}

