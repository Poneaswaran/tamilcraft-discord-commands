/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.role.update.GenericRoleUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.awt.Color;

public class RoleUpdateColorEvent
extends GenericRoleUpdateEvent<Integer> {
    public static final String IDENTIFIER = "color";

    public RoleUpdateColorEvent(@Nonnull JDA api, long responseNumber, @Nonnull Role role, int oldColor) {
        super(api, responseNumber, role, oldColor, role.getColorRaw(), IDENTIFIER);
    }

    @Nullable
    public Color getOldColor() {
        return (Integer)this.previous != 0x1FFFFFFF ? new Color((Integer)this.previous) : null;
    }

    public int getOldColorRaw() {
        return this.getOldValue();
    }

    @Nullable
    public Color getNewColor() {
        return (Integer)this.next != 0x1FFFFFFF ? new Color((Integer)this.next) : null;
    }

    public int getNewColorRaw() {
        return this.getNewValue();
    }

    @Override
    @Nonnull
    public Integer getOldValue() {
        return (Integer)super.getOldValue();
    }

    @Override
    @Nonnull
    public Integer getNewValue() {
        return (Integer)super.getNewValue();
    }
}

