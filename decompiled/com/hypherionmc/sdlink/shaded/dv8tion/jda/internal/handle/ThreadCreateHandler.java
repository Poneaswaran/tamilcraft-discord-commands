/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.thread.ThreadRevealedEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;

public class ThreadCreateHandler
extends SocketHandler {
    public ThreadCreateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        long guildId = content.getLong("guild_id");
        if (this.api.getGuildSetupController().isLocked(guildId)) {
            return guildId;
        }
        try {
            if (this.api.getThreadChannelById(content.getUnsignedLong("id")) != null) {
                return null;
            }
            ThreadChannel thread = this.api.getEntityBuilder().createThreadChannel(content, guildId);
            if (content.getBoolean("newly_created")) {
                this.api.handleEvent(new ChannelCreateEvent(this.api, this.responseNumber, thread));
            } else {
                this.api.handleEvent(new ThreadRevealedEvent(this.api, this.responseNumber, thread));
            }
        }
        catch (IllegalArgumentException ex) {
            if (!"MISSING_CHANNEL".equals(ex.getMessage())) {
                throw ex;
            }
            long parentId = content.getUnsignedLong("parent_id", 0L);
            EventCache.LOG.debug("Caching THREAD_CREATE_EVENT for channel with uncached parent. Parent ID: {}", (Object)parentId);
            this.api.getEventCache().cache(EventCache.Type.CHANNEL, parentId, this.responseNumber, this.allContent, this::handle);
        }
        return null;
    }
}

