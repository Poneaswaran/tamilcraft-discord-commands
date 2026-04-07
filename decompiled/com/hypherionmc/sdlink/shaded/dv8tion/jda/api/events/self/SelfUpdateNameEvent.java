/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.self;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.self.GenericSelfUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class SelfUpdateNameEvent
extends GenericSelfUpdateEvent<String> {
    public static final String IDENTIFIER = "name";

    public SelfUpdateNameEvent(@Nonnull JDA api, long responseNumber, @Nonnull String oldName) {
        super(api, responseNumber, oldName, api.getSelfUser().getName(), IDENTIFIER);
    }

    @Nonnull
    public String getOldName() {
        return this.getOldValue();
    }

    @Nonnull
    public String getNewName() {
        return this.getNewValue();
    }

    @Override
    @Nonnull
    public String getOldValue() {
        return (String)super.getOldValue();
    }

    @Override
    @Nonnull
    public String getNewValue() {
        return (String)super.getNewValue();
    }
}

