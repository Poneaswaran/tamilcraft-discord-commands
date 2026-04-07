/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.UpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class StatusChangeEvent
extends Event
implements UpdateEvent<JDA, JDA.Status> {
    public static final String IDENTIFIER = "status";
    protected final JDA.Status newStatus;
    protected final JDA.Status oldStatus;

    public StatusChangeEvent(@Nonnull JDA api, @Nonnull JDA.Status newStatus, @Nonnull JDA.Status oldStatus) {
        super(api);
        this.newStatus = newStatus;
        this.oldStatus = oldStatus;
    }

    @Nonnull
    public JDA.Status getNewStatus() {
        return this.newStatus;
    }

    @Nonnull
    public JDA.Status getOldStatus() {
        return this.oldStatus;
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
    public JDA.Status getOldValue() {
        return this.oldStatus;
    }

    @Override
    @Nonnull
    public JDA.Status getNewValue() {
        return this.newStatus;
    }
}

