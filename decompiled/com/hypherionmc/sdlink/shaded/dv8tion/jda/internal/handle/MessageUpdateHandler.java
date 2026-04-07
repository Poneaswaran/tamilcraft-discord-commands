/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.MessageType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.MessageUpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.ReceivedMessage;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;

public class MessageUpdateHandler
extends SocketHandler {
    public MessageUpdateHandler(JDAImpl api) {
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
            guild = this.api.getGuildById(guildId);
            if (guild == null) {
                this.api.getEventCache().cache(EventCache.Type.GUILD, guildId, this.responseNumber, this.allContent, this::handle);
                EventCache.LOG.debug("Received message for a guild that JDA does not currently have cached");
                return null;
            }
        }
        if ((content.getInt("flags", 0) & 0x40) != 0) {
            return null;
        }
        if (content.hasKey("author") && content.hasKey("type")) {
            MessageType type = MessageType.fromId(content.getInt("type"));
            if (!type.isSystem()) {
                return this.handleMessage(content, guild);
            }
            WebSocketClient.LOG.debug("JDA received a message update for an unexpected message type. Type: {} JSON: {}", (Object)type, (Object)content);
            return null;
        }
        return null;
    }

    private Long handleMessage(DataObject content, Guild guild) {
        ReceivedMessage message;
        try {
            message = this.getJDA().getEntityBuilder().createMessageWithLookup(content, guild, true);
            if (!message.hasChannel()) {
                throw new IllegalArgumentException("MISSING_CHANNEL");
            }
        }
        catch (IllegalArgumentException e) {
            switch (e.getMessage()) {
                case "MISSING_CHANNEL": {
                    GuildChannel actual;
                    long channelId = content.getUnsignedLong("channel_id");
                    if (guild != null && (actual = guild.getGuildChannelById(channelId)) != null) {
                        WebSocketClient.LOG.debug("Dropping MESSAGE_UPDATE for unexpected channel of type {}", (Object)actual.getType());
                        return null;
                    }
                    this.getJDA().getEventCache().cache(EventCache.Type.CHANNEL, channelId, this.responseNumber, this.allContent, this::handle);
                    EventCache.LOG.debug("Received a message update for a channel that JDA does not currently have cached");
                    return null;
                }
                case "MISSING_USER": {
                    long authorId = content.getObject("author").getLong("id");
                    this.getJDA().getEventCache().cache(EventCache.Type.USER, authorId, this.responseNumber, this.allContent, this::handle);
                    EventCache.LOG.debug("Received a message update for a user that JDA does not currently have cached");
                    return null;
                }
            }
            throw e;
        }
        if (message.getChannelType() == ChannelType.PRIVATE) {
            this.getJDA().usedPrivateChannel(message.getChannel().getIdLong());
        }
        this.getJDA().handleEvent(new MessageUpdateEvent(this.getJDA(), this.responseNumber, message));
        return null;
    }
}

