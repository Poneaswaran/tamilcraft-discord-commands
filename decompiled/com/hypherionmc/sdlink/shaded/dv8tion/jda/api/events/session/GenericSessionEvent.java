/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session.SessionState;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public abstract class GenericSessionEvent
extends Event {
    protected final SessionState state;

    public GenericSessionEvent(@Nonnull JDA api, @Nonnull SessionState state) {
        super(api);
        this.state = state;
    }

    @Nonnull
    public SessionState getState() {
        return this.state;
    }
}

