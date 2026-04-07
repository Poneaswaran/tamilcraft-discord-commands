/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.ISlowmodeChannelManager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface ISlowmodeChannel
extends GuildChannel {
    public static final int MAX_SLOWMODE = 21600;

    public int getSlowmode();

    @Nonnull
    @CheckReturnValue
    public ISlowmodeChannelManager<?, ?> getManager();
}

