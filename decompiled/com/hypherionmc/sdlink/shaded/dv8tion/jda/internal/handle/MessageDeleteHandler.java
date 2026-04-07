/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.MessageDeleteEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.ThreadChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;

public class MessageDeleteHandler
extends SocketHandler {
    public MessageDeleteHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        Guild guild = null;
        if (!content.isNull("guild_id")) {
            long guildId = content.getLong("guild_id");
            if (this.getJDA().getGuildSetupController().isLocked(guildId)) {
                return guildId;
            }
            guild = this.getJDA().getGuildById(guildId);
            if (guild == null) {
                this.getJDA().getEventCache().cache(EventCache.Type.GUILD, guildId, this.responseNumber, this.allContent, this::handle);
                EventCache.LOG.debug("Got message delete for a guild that is not yet cached. GuildId: {}", (Object)guildId);
                return null;
            }
        }
        long messageId = content.getLong("id");
        long channelId = content.getLong("channel_id");
        MessageChannel channel = this.getJDA().getChannelById(MessageChannel.class, channelId);
        if (channel == null) {
            GuildChannel actual;
            if (guild != null && (actual = guild.getGuildChannelById(channelId)) != null) {
                WebSocketClient.LOG.debug("Dropping MESSAGE_DELETE for unexpected channel of type {}", (Object)actual.getType());
                return null;
            }
            this.getJDA().getEventCache().cache(EventCache.Type.CHANNEL, channelId, this.responseNumber, this.allContent, this::handle);
            EventCache.LOG.debug("Got message delete for a channel/group that is not yet cached. ChannelId: {}", (Object)channelId);
            return null;
        }
        if (channel.getType().isThread()) {
            ThreadChannelImpl gThread = (ThreadChannelImpl)channel;
            gThread.setMessageCount(Math.max(0, gThread.getMessageCount() - 1));
        }
        this.getJDA().handleEvent(new MessageDeleteEvent(this.getJDA(), this.responseNumber, messageId, channel));
        return null;
    }
}

