/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.self;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.self.GenericSelfUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class SelfUpdateMFAEvent
extends GenericSelfUpdateEvent<Boolean> {
    public static final String IDENTIFIER = "mfa_enabled";

    public SelfUpdateMFAEvent(@Nonnull JDA api, long responseNumber, boolean wasMfaEnabled) {
        super(api, responseNumber, wasMfaEnabled, !wasMfaEnabled, IDENTIFIER);
    }

    public boolean wasMfaEnabled() {
        return this.getOldValue();
    }

    @Override
    @Nonnull
    public Boolean getOldValue() {
        return (Boolean)super.getOldValue();
    }

    @Override
    @Nonnull
    public Boolean getNewValue() {
        return (Boolean)super.getNewValue();
    }
}

