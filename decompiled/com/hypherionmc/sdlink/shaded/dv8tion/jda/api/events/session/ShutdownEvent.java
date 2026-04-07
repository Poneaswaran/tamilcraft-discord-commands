/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session.GenericSessionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session.SessionState;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.CloseCode;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.OffsetDateTime;

public class ShutdownEvent
extends GenericSessionEvent {
    protected final OffsetDateTime shutdownTime;
    protected final int code;

    public ShutdownEvent(@Nonnull JDA api, @Nonnull OffsetDateTime shutdownTime, int code) {
        super(api, SessionState.SHUTDOWN);
        this.shutdownTime = shutdownTime;
        this.code = code;
    }

    @Nonnull
    public OffsetDateTime getTimeShutdown() {
        return this.shutdownTime;
    }

    @Nullable
    public CloseCode getCloseCode() {
        return CloseCode.from(this.code);
    }

    public int getCode() {
        return this.code;
    }
}

