/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.poll.MessagePollVoteAddEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.poll.MessagePollVoteRemoveEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;

public class MessagePollVoteHandler
extends SocketHandler {
    private final boolean add;

    public MessagePollVoteHandler(JDAImpl api, boolean add) {
        super(api);
        this.add = add;
    }

    @Override
    protected Long handleInternally(DataObject content) {
        long answerId = content.getLong("answer_id");
        long userId = content.getUnsignedLong("user_id");
        long messageId = content.getUnsignedLong("message_id");
        long channelId = content.getUnsignedLong("channel_id");
        long guildId = content.getUnsignedLong("guild_id", 0L);
        if (this.api.getGuildSetupController().isLocked(guildId)) {
            return guildId;
        }
        Guild guild = this.api.getGuildById(guildId);
        MessageChannel channel = this.api.getChannelById(MessageChannel.class, channelId);
        if (channel == null) {
            GuildChannel actual;
            if (guild != null && (actual = guild.getGuildChannelById(channelId)) != null) {
                WebSocketClient.LOG.debug("Dropping message poll vote event for unexpected channel of type {}", (Object)actual.getType());
                return null;
            }
            if (guildId != 0L) {
                this.api.getEventCache().cache(EventCache.Type.CHANNEL, channelId, this.responseNumber, this.allContent, this::handle);
                EventCache.LOG.debug("Received a vote for a channel that JDA does not currently have cached");
                return null;
            }
            channel = this.getJDA().getEntityBuilder().createPrivateChannel(DataObject.empty().put("id", channelId));
        }
        if (this.add) {
            this.api.handleEvent(new MessagePollVoteAddEvent(channel, this.responseNumber, messageId, userId, answerId));
        } else {
            this.api.handleEvent(new MessagePollVoteRemoveEvent(channel, this.responseNumber, messageId, userId, answerId));
        }
        return null;
    }
}

