/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.entitlement;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Entitlement;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public abstract class GenericEntitlementEvent
extends Event {
    protected final Entitlement entitlement;

    protected GenericEntitlementEvent(@Nonnull JDA api, long responseNumber, @Nonnull Entitlement entitlement) {
        super(api, responseNumber);
        this.entitlement = entitlement;
    }

    @Nonnull
    public Entitlement getEntitlement() {
        return this.entitlement;
    }
}

