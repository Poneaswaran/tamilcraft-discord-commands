/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateVoiceStatusEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.VoiceChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;

public class VoiceChannelStatusUpdateHandler
extends SocketHandler {
    public VoiceChannelStatusUpdateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        long guildId = content.getUnsignedLong("guild_id");
        if (this.getJDA().getGuildSetupController().isLocked(guildId)) {
            return guildId;
        }
        long id = content.getUnsignedLong("id");
        VoiceChannelImpl channel = (VoiceChannelImpl)this.getJDA().getVoiceChannelById(id);
        if (channel == null) {
            EventCache.LOG.debug("Caching VOICE_CHANNEL_STATUS_UPDATE for uncached channel. ID: {}", (Object)id);
            this.getJDA().getEventCache().cache(EventCache.Type.CHANNEL, id, this.responseNumber, this.allContent, this::handle);
            return null;
        }
        String newStatus = content.getString("status", "");
        if (!newStatus.equals(channel.getStatus())) {
            String oldStatus = channel.getStatus();
            channel.setStatus(newStatus);
            this.api.handleEvent(new ChannelUpdateVoiceStatusEvent(this.api, this.responseNumber, channel, oldStatus, newStatus));
        }
        return null;
    }
}

