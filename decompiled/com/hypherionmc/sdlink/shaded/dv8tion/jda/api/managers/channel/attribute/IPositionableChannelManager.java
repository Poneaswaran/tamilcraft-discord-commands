/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPositionableChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.ChannelManager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface IPositionableChannelManager<T extends IPositionableChannel, M extends IPositionableChannelManager<T, M>>
extends ChannelManager<T, M> {
    @Nonnull
    @CheckReturnValue
    public M setPosition(int var1);
}

