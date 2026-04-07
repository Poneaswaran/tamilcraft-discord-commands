/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IAgeRestrictedChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.ChannelManager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface IAgeRestrictedChannelManager<T extends IAgeRestrictedChannel, M extends IAgeRestrictedChannelManager<T, M>>
extends ChannelManager<T, M> {
    @Nonnull
    @CheckReturnValue
    public M setNSFW(boolean var1);
}

