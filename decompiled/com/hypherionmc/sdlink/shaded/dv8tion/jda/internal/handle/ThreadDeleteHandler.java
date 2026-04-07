/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.WebSocketClient;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.ChannelCacheViewImpl;

public class ThreadDeleteHandler
extends SocketHandler {
    public ThreadDeleteHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        long guildId = content.getLong("guild_id");
        if (this.getJDA().getGuildSetupController().isLocked(guildId)) {
            return guildId;
        }
        GuildImpl guild = (GuildImpl)this.getJDA().getGuildById(guildId);
        long threadId = content.getLong("id");
        ChannelCacheViewImpl<Channel> channelsView = this.getJDA().getChannelsView();
        ThreadChannel thread = (ThreadChannel)((ChannelCacheViewImpl.FilteredCacheView)channelsView.ofType(ThreadChannel.class)).getElementById(threadId);
        if (thread == null || guild == null) {
            WebSocketClient.LOG.debug("THREAD_DELETE attempted to delete a thread that is not yet cached. JSON: {}", (Object)content);
            return null;
        }
        channelsView.remove(thread.getType(), threadId);
        guild.getChannelView().remove(thread);
        this.getJDA().handleEvent(new ChannelDeleteEvent(this.getJDA(), this.responseNumber, thread));
        this.getJDA().getEventCache().clear(EventCache.Type.CHANNEL, threadId);
        return null;
    }
}

