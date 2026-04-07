/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.GuildChannelMixin;

public interface ITopicChannelMixin<T extends ITopicChannelMixin<T>>
extends GuildChannelMixin<T> {
    public T setTopic(String var1);

    public String getTopic();
}

