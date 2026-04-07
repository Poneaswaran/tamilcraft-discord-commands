/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;

public class ChannelCreateHandler
extends SocketHandler {
    public ChannelCreateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        Channel channel;
        ChannelType type = ChannelType.fromId(content.getInt("type"));
        long guildId = 0L;
        JDAImpl jda = this.getJDA();
        if (type.isGuild()) {
            guildId = content.getLong("guild_id");
            if (jda.getGuildSetupController().isLocked(guildId)) {
                return guildId;
            }
        }
        if ((channel = this.buildChannel(type, content, guildId)) == null) {
            WebSocketClient.LOG.debug("Discord provided an CREATE_CHANNEL event with an unknown channel type! JSON: {}", (Object)content);
            return null;
        }
        jda.handleEvent(new ChannelCreateEvent(jda, this.responseNumber, channel));
        return null;
    }

    private Channel buildChannel(ChannelType type, DataObject content, long guildId) {
        EntityBuilder builder = this.getJDA().getEntityBuilder();
        switch (type) {
            case TEXT: {
                return builder.createTextChannel(content, guildId);
            }
            case NEWS: {
                return builder.createNewsChannel(content, guildId);
            }
            case VOICE: {
                return builder.createVoiceChannel(content, guildId);
            }
            case STAGE: {
                return builder.createStageChannel(content, guildId);
            }
            case CATEGORY: {
                return builder.createCategory(content, guildId);
            }
            case FORUM: {
                return builder.createForumChannel(content, guildId);
            }
            case MEDIA: {
                return builder.createMediaChannel(content, guildId);
            }
        }
        return null;
    }
}

