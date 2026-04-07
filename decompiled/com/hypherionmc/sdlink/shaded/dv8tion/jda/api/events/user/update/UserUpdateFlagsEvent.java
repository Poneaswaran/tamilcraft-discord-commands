/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.GenericUserUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collection;
import java.util.EnumSet;

public class UserUpdateFlagsEvent
extends GenericUserUpdateEvent<EnumSet<User.UserFlag>> {
    public static final String IDENTIFIER = "public_flags";

    public UserUpdateFlagsEvent(@Nonnull JDA api, long responseNumber, @Nonnull User user, @Nonnull EnumSet<User.UserFlag> oldFlags) {
        super(api, responseNumber, user, oldFlags, user.getFlags(), IDENTIFIER);
    }

    @Nonnull
    public EnumSet<User.UserFlag> getOldFlags() {
        return (EnumSet)this.getOldValue();
    }

    public int getOldFlagsRaw() {
        return User.UserFlag.getRaw((Collection)this.previous);
    }

    @Nonnull
    public EnumSet<User.UserFlag> getNewFlags() {
        return (EnumSet)this.getNewValue();
    }

    public int getNewFlagsRaw() {
        return User.UserFlag.getRaw((Collection)this.next);
    }
}

