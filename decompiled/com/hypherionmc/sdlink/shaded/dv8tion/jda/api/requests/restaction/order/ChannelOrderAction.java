/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.order;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.Category;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.order.OrderAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.EnumSet;

public interface ChannelOrderAction
extends OrderAction<GuildChannel, ChannelOrderAction> {
    @Nonnull
    public Guild getGuild();

    public int getSortBucket();

    @Nonnull
    default public EnumSet<ChannelType> getChannelTypes() {
        return ChannelType.fromSortBucket(this.getSortBucket());
    }

    @Nonnull
    @CheckReturnValue
    public ChannelOrderAction setCategory(@Nullable Category var1, boolean var2);

    @Nonnull
    @CheckReturnValue
    default public ChannelOrderAction setCategory(@Nullable Category category) {
        return this.setCategory(category, false);
    }
}

