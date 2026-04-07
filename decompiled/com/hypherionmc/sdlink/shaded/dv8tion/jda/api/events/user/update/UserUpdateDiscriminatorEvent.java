/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.GenericUserUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class UserUpdateDiscriminatorEvent
extends GenericUserUpdateEvent<String> {
    public static final String IDENTIFIER = "discriminator";

    public UserUpdateDiscriminatorEvent(@Nonnull JDA api, long responseNumber, @Nonnull User user, @Nonnull String oldDiscriminator) {
        super(api, responseNumber, user, oldDiscriminator, user.getDiscriminator(), IDENTIFIER);
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

