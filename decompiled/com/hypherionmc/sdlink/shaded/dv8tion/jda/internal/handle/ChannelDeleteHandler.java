/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;

public class ChannelDeleteHandler
extends SocketHandler {
    public ChannelDeleteHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        ChannelType type = ChannelType.fromId(content.getInt("type"));
        long guildId = 0L;
        if (type.isGuild()) {
            guildId = content.getLong("guild_id");
            if (this.getJDA().getGuildSetupController().isLocked(guildId)) {
                return guildId;
            }
        }
        GuildImpl guild = (GuildImpl)this.getJDA().getGuildById(guildId);
        long channelId = content.getLong("id");
        if (guild == null) {
            PrivateChannel channel = (PrivateChannel)this.getJDA().getChannelsView().remove(ChannelType.PRIVATE, channelId);
            if (channel == null) {
                WebSocketClient.LOG.debug("CHANNEL_DELETE attempted to delete a private channel that is not yet cached. JSON: {}", (Object)content);
            }
            return null;
        }
        GuildChannel channel = guild.getChannelById(GuildChannel.class, channelId);
        if (channel == null) {
            WebSocketClient.LOG.debug("CHANNEL_DELETE attempted to delete a guild channel that is not yet cached. JSON: {}", (Object)content);
            return null;
        }
        guild.uncacheChannel(channel, false);
        this.getJDA().handleEvent(new ChannelDeleteEvent(this.getJDA(), this.responseNumber, channel));
        String location = Long.toUnsignedString(channelId);
        guild.getScheduledEventsView().stream().filter(scheduledEvent -> scheduledEvent.getType().isChannel() && scheduledEvent.getLocation().equals(location)).forEach(scheduledEvent -> guild.getScheduledEventsView().remove(scheduledEvent.getIdLong()));
        this.getJDA().getEventCache().clear(EventCache.Type.CHANNEL, channelId);
        return null;
    }
}

