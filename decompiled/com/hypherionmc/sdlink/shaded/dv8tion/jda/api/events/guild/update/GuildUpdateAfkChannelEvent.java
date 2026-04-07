/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class GuildUpdateAfkChannelEvent
extends GenericGuildUpdateEvent<VoiceChannel> {
    public static final String IDENTIFIER = "afk_channel";

    public GuildUpdateAfkChannelEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nullable VoiceChannel oldAfkChannel) {
        super(api, responseNumber, guild, oldAfkChannel, guild.getAfkChannel(), IDENTIFIER);
    }

    @Nullable
    public VoiceChannel getOldAfkChannel() {
        return (VoiceChannel)this.getOldValue();
    }

    @Nullable
    public VoiceChannel getNewAfkChannel() {
        return (VoiceChannel)this.getNewValue();
    }
}

