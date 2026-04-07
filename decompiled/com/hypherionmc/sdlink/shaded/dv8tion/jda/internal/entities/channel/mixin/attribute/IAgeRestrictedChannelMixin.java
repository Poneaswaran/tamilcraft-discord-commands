/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IAgeRestrictedChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.GuildChannelMixin;

public interface IAgeRestrictedChannelMixin<T extends IAgeRestrictedChannelMixin<T>>
extends GuildChannelMixin<T>,
IAgeRestrictedChannel {
    public T setNSFW(boolean var1);
}

