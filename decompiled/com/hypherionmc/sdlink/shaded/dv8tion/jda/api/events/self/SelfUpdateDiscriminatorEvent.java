/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.self;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.self.GenericSelfUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class SelfUpdateDiscriminatorEvent
extends GenericSelfUpdateEvent<String> {
    public static final String IDENTIFIER = "discriminator";

    public SelfUpdateDiscriminatorEvent(@Nonnull JDA api, long responseNumber, @Nonnull String oldDiscriminator) {
        super(api, responseNumber, oldDiscriminator, api.getSelfUser().getDiscriminator(), IDENTIFIER);
    }

    @Nonnull
    public String getOldDiscriminator() {
        return this.getOldValue();
    }

    @Nonnull
    public String getNewDiscriminator() {
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

