/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class GuildUpdateCommunityUpdatesChannelEvent
extends GenericGuildUpdateEvent<TextChannel> {
    public static final String IDENTIFIER = "community_updates_channel";

    public GuildUpdateCommunityUpdatesChannelEvent(@Nonnull JDA api, long responseNumber, @Nonnull Guild guild, @Nullable TextChannel oldCommunityUpdatesChannel) {
        super(api, responseNumber, guild, oldCommunityUpdatesChannel, guild.getCommunityUpdatesChannel(), IDENTIFIER);
    }

    @Nullable
    public TextChannel getOldCommunityUpdatesChannel() {
        return (TextChannel)this.getOldValue();
    }

    @Nullable
    public TextChannel getNewCommunityUpdatesChannel() {
        return (TextChannel)this.getNewValue();
    }
}

