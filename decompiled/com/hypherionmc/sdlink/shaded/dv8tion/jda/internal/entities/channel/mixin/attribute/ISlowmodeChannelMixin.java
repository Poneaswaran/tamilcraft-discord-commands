/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.ISlowmodeChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.GuildChannelMixin;

public interface ISlowmodeChannelMixin<T extends ISlowmodeChannelMixin<T>>
extends GuildChannelMixin<T>,
ISlowmodeChannel {
    public T setSlowmode(int var1);
}

