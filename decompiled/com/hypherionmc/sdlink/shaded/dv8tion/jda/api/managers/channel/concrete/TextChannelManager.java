/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.ISlowmodeChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.middleman.StandardGuildMessageChannelManager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface TextChannelManager
extends StandardGuildMessageChannelManager<TextChannel, TextChannelManager>,
ISlowmodeChannelManager<TextChannel, TextChannelManager> {
    @Nonnull
    @CheckReturnValue
    public TextChannelManager setType(@Nonnull ChannelType var1);
}

