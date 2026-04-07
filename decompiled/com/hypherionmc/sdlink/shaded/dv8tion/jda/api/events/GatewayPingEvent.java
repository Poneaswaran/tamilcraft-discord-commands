/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.UpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class GatewayPingEvent
extends Event
implements UpdateEvent<JDA, Long> {
    public static final String IDENTIFIER = "gateway-ping";
    private final long next;
    private final long prev;

    public GatewayPingEvent(@Nonnull JDA api, long old) {
        super(api);
        this.next = api.getGatewayPing();
        this.prev = old;
    }

    public long getNewPing() {
        return this.next;
    }

    public long getOldPing() {
        return this.prev;
    }

    @Override
    @Nonnull
    public String getPropertyIdentifier() {
        return IDENTIFIER;
    }

    @Override
    @Nonnull
    public JDA getEntity() {
        return this.getJDA();
    }

    @Override
    @Nonnull
    public Long getOldValue() {
        return this.prev;
    }

    @Override
    @Nonnull
    public Long getNewValue() {
        return this.next;
    }
}

