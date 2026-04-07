/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ExceptionEvent
extends Event {
    protected final Throwable throwable;
    protected final boolean logged;

    public ExceptionEvent(@Nonnull JDA api, @Nonnull Throwable throwable, boolean logged) {
        super(api);
        this.throwable = throwable;
        this.logged = logged;
    }

    public boolean isLogged() {
        return this.logged;
    }

    @Nonnull
    public Throwable getCause() {
        return this.throwable;
    }
}

