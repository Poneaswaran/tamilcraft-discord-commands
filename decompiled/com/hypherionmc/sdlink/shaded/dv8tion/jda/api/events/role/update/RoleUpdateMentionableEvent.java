/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.update.GenericRoleUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class RoleUpdateMentionableEvent
extends GenericRoleUpdateEvent<Boolean> {
    public static final String IDENTIFIER = "mentionable";

    public RoleUpdateMentionableEvent(@Nonnull JDA api, long responseNumber, @Nonnull Role role, boolean wasMentionable) {
        super(api, responseNumber, role, wasMentionable, !wasMentionable, IDENTIFIER);
    }

    public boolean wasMentionable() {
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

