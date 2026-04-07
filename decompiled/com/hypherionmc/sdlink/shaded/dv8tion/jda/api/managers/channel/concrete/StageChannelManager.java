/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.IAgeRestrictedChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.ISlowmodeChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.middleman.AudioChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.middleman.StandardGuildChannelManager;

public interface StageChannelManager
extends AudioChannelManager<StageChannel, StageChannelManager>,
StandardGuildChannelManager<StageChannel, StageChannelManager>,
IAgeRestrictedChannelManager<StageChannel, StageChannelManager>,
ISlowmodeChannelManager<StageChannel, StageChannelManager> {
}

