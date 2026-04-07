/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public abstract class GenericUserEvent
extends Event {
    private final User user;

    public GenericUserEvent(@Nonnull JDA api, long responseNumber, @Nonnull User user) {
        super(api, responseNumber);
        this.user = user;
    }

    @Nonnull
    public User getUser() {
        return this.user;
    }
}

