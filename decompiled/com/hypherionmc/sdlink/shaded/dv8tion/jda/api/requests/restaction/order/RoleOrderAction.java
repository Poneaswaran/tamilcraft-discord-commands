/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.order;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.order.OrderAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface RoleOrderAction
extends OrderAction<Role, RoleOrderAction> {
    @Nonnull
    public Guild getGuild();
}

