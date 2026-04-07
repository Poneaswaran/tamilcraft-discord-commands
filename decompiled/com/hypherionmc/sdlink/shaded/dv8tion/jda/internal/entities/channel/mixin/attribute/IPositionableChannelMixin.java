/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPositionableChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.GuildChannelMixin;

public interface IPositionableChannelMixin<T extends IPositionableChannelMixin<T>>
extends IPositionableChannel,
GuildChannelMixin<T> {
    public T setPosition(int var1);
}

