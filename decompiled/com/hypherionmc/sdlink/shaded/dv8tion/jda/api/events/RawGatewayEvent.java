/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class RawGatewayEvent
extends Event {
    private final DataObject data;

    public RawGatewayEvent(@Nonnull JDA api, long responseNumber, @Nonnull DataObject data) {
        super(api, responseNumber);
        this.data = data;
    }

    @Nonnull
    public DataObject getPackage() {
        return this.data;
    }

    @Nonnull
    public DataObject getPayload() {
        return this.data.getObject("d");
    }

    @Nonnull
    public String getType() {
        return this.data.getString("t");
    }
}

