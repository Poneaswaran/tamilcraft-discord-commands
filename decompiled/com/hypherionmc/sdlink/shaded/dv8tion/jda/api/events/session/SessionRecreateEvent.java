/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session.GenericSessionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session.SessionState;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class SessionRecreateEvent
extends GenericSessionEvent {
    public SessionRecreateEvent(@Nonnull JDA api) {
        super(api, SessionState.RECREATED);
    }
}

