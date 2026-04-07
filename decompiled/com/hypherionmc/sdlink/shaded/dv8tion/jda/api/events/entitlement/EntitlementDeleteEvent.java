/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.entitlement;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Entitlement;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.entitlement.GenericEntitlementEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class EntitlementDeleteEvent
extends GenericEntitlementEvent {
    public EntitlementDeleteEvent(@Nonnull JDA api, long responseNumber, @Nonnull Entitlement entitlement) {
        super(api, responseNumber, entitlement);
    }
}

