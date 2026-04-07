/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.GenericUserUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class UserUpdateGlobalNameEvent
extends GenericUserUpdateEvent<String> {
    public static final String IDENTIFIER = "global_name";

    public UserUpdateGlobalNameEvent(JDA api, long responseNumber, User user, String oldName) {
        super(api, responseNumber, user, oldName, user.getGlobalName(), IDENTIFIER);
    }

    @Nullable
    public String getOldGlobalName() {
        return (String)this.getOldValue();
    }

    @Nullable
    public String getNewGlobalName() {
        return (String)this.getNewValue();
    }

    @Override
    public String toString() {
        return "UserUpdateGlobalName(" + (String)this.getOldValue() + "->" + (String)this.getNewValue() + ')';
    }
}

