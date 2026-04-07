/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.IAgeRestrictedChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.IPostContainerManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.ISlowmodeChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.middleman.StandardGuildChannelManager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface ForumChannelManager
extends StandardGuildChannelManager<ForumChannel, ForumChannelManager>,
IPostContainerManager<ForumChannel, ForumChannelManager>,
IAgeRestrictedChannelManager<ForumChannel, ForumChannelManager>,
ISlowmodeChannelManager<ForumChannel, ForumChannelManager> {
    @Nonnull
    @CheckReturnValue
    public ForumChannelManager setDefaultLayout(@Nonnull ForumChannel.Layout var1);
}

