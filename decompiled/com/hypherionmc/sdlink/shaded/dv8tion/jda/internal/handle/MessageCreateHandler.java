/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ReceivedMessage;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.ThreadChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.MessageChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;

public class MessageCreateHandler
extends SocketHandler {
    public MessageCreateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        ReceivedMessage message;
        MessageType type = MessageType.fromId(content.getInt("type"));
        if (type == MessageType.UNKNOWN) {
            WebSocketClient.LOG.debug("JDA received a message of unknown type. Type: {}  JSON: {}", (Object)type, (Object)content);
            return null;
        }
        if ((content.getInt("flags", 0) & 0x40) != 0) {
            return null;
        }
        JDAImpl jda = this.getJDA();
        Guild guild = null;
        if (!content.isNull("guild_id")) {
            long guildId = content.getLong("guild_id");
            if (jda.getGuildSetupController().isLocked(guildId)) {
                return guildId;
            }
            guild = this.api.getGuildById(guildId);
            if (guild == null) {
                this.api.getEventCache().cache(EventCache.Type.GUILD, guildId, this.responseNumber, this.allContent, this::handle);
                EventCache.LOG.debug("Received message for a guild that JDA does not currently have cached");
                return null;
            }
        }
        try {
            message = jda.getEntityBuilder().createMessageWithLookup(content, guild, true);
            if (!message.hasChannel()) {
                throw new IllegalArgumentException("MISSING_CHANNEL");
            }
        }
        catch (IllegalArgumentException e) {
            switch (e.getMessage()) {
                case "MISSING_CHANNEL": {
                    GuildChannel actual;
                    long channelId = content.getLong("channel_id");
                    if (guild != null && (actual = guild.getGuildChannelById(channelId)) != null) {
                        WebSocketClient.LOG.debug("Dropping MESSAGE_CREATE for unexpected channel of type {}", (Object)actual.getType());
                        return null;
                    }
                    jda.getEventCache().cache(EventCache.Type.CHANNEL, channelId, this.responseNumber, this.allContent, this::handle);
                    EventCache.LOG.debug("Received a message for a channel that JDA does not currently have cached");
                    return null;
                }
                case "MISSING_USER": {
                    long authorId = content.getObject("author").getLong("id");
                    jda.getEventCache().cache(EventCache.Type.USER, authorId, this.responseNumber, this.allContent, this::handle);
                    EventCache.LOG.debug("Received a message for a user that JDA does not currently have cached");
                    return null;
                }
                case "UNKNOWN_MESSAGE_TYPE": {
                    WebSocketClient.LOG.debug("Ignoring message with unknown type: {}", (Object)content);
                    return null;
                }
            }
            throw e;
        }
        MessageChannelUnion channel = message.getChannel();
        ChannelType channelType = channel.getType();
        ((MessageChannelMixin)channel).setLatestMessageIdLong(message.getIdLong());
        if (channelType.isGuild()) {
            if (channelType.isThread()) {
                ThreadChannelImpl gThread = (ThreadChannelImpl)channel;
                gThread.setMessageCount(gThread.getMessageCount() + 1);
                gThread.setTotalMessageCount(gThread.getTotalMessageCount() + 1);
            }
        } else {
            this.api.usedPrivateChannel(channel.getIdLong());
        }
        jda.handleEvent(new MessageReceivedEvent(jda, this.responseNumber, message));
        return null;
    }
}

