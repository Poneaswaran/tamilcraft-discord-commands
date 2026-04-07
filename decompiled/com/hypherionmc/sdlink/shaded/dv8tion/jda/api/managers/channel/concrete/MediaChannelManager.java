/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.MediaChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.IAgeRestrictedChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.IPostContainerManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.ISlowmodeChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.middleman.StandardGuildChannelManager;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface MediaChannelManager
extends StandardGuildChannelManager<MediaChannel, MediaChannelManager>,
IPostContainerManager<MediaChannel, MediaChannelManager>,
IAgeRestrictedChannelManager<MediaChannel, MediaChannelManager>,
ISlowmodeChannelManager<MediaChannel, MediaChannelManager> {
    @Nonnull
    @CheckReturnValue
    public MediaChannelManager setHideMediaDownloadOption(boolean var1);
}

