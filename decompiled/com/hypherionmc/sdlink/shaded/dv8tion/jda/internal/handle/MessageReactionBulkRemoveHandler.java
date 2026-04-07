/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IGuildChannelContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.message.react.MessageReactionRemoveAllEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;

public class MessageReactionBulkRemoveHandler
extends SocketHandler {
    public MessageReactionBulkRemoveHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        MessageChannel channel;
        long messageId = content.getLong("message_id");
        long channelId = content.getLong("channel_id");
        JDAImpl jda = this.getJDA();
        IGuildChannelContainer guild = null;
        if (!content.isNull("guild_id")) {
            long guildId = content.getUnsignedLong("guild_id");
            if (this.api.getGuildSetupController().isLocked(guildId)) {
                return guildId;
            }
            guild = this.getJDA().getGuildById(guildId);
            if (guild == null) {
                jda.getEventCache().cache(EventCache.Type.GUILD, guildId, this.responseNumber, this.allContent, this::handle);
                EventCache.LOG.debug("Got MESSAGE_REACTION_REMOVE_ALL for a guild that is not yet cached. GuildId: {}", (Object)guildId);
                return null;
            }
        }
        if ((channel = jda.getChannelById(MessageChannel.class, channelId)) == null) {
            GuildChannel actual;
            if (guild != null && (actual = guild.getGuildChannelById(channelId)) != null) {
                WebSocketClient.LOG.debug("Dropping MESSAGE_REACTION_REMOVE_ALL for unexpected channel of type {}", (Object)actual.getType());
                return null;
            }
            jda.getEventCache().cache(EventCache.Type.CHANNEL, channelId, this.responseNumber, this.allContent, this::handle);
            EventCache.LOG.debug("Received a reaction for a channel that JDA does not currently have cached channel_id: {} message_id: {}", (Object)channelId, (Object)messageId);
            return null;
        }
        jda.handleEvent(new MessageReactionRemoveAllEvent(jda, this.responseNumber, messageId, channel));
        return null;
    }
}

