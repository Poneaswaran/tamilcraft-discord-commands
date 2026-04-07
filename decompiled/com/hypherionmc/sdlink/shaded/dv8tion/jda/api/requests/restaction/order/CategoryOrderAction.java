/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.order;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.order.ChannelOrderAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface CategoryOrderAction
extends ChannelOrderAction {
    @Nonnull
    public Category getCategory();
}

