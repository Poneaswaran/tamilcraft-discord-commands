/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.IAgeRestrictedChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.attribute.ISlowmodeChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.middleman.AudioChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.middleman.StandardGuildChannelManager;

public interface VoiceChannelManager
extends AudioChannelManager<VoiceChannel, VoiceChannelManager>,
StandardGuildChannelManager<VoiceChannel, VoiceChannelManager>,
IAgeRestrictedChannelManager<VoiceChannel, VoiceChannelManager>,
ISlowmodeChannelManager<VoiceChannel, VoiceChannelManager> {
}

