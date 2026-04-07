/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ThreadMember;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.ThreadChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.UnlockHook;

public class ThreadMemberUpdateHandler
extends SocketHandler {
    public ThreadMemberUpdateHandler(JDAImpl api) {
        super(api);
    }

    @Override
    protected Long handleInternally(DataObject content) {
        long guildId = content.getLong("guild_id");
        if (this.api.getGuildSetupController().isLocked(guildId)) {
            return guildId;
        }
        long threadId = content.getLong("id");
        ThreadChannelImpl thread = (ThreadChannelImpl)this.getJDA().getThreadChannelById(threadId);
        if (thread == null) {
            this.getJDA().getEventCache().cache(EventCache.Type.CHANNEL, threadId, this.responseNumber, this.allContent, this::handle);
            EventCache.LOG.debug("THREAD_MEMBER_UPDATE attempted to update a thread that does not exist. JSON: {}", (Object)content);
            return null;
        }
        long userId = content.getLong("user_id");
        if (userId != this.getJDA().getSelfUser().getIdLong()) {
            JDAImpl.LOG.warn("Received a THREAD_MEMBER_UPDATE for a user that isn't the current bot user. This validates assumptions that THREAD_MEMBER_UPDATE would ONLY be for the current bot user. Skipping this dispatch for now. This should be reported as a bug.\nDetails: {}", (Object)content);
            return null;
        }
        CacheView.SimpleCacheView<ThreadMember> view = thread.getThreadMemberView();
        try (UnlockHook lock = view.writeLock();){
            ThreadMember threadMember = (ThreadMember)view.getMap().get(userId);
            if (threadMember == null) {
                threadMember = this.api.getEntityBuilder().createThreadMember(thread, thread.getGuild().getSelfMember(), content);
                view.getMap().put(threadMember.getIdLong(), threadMember);
            }
        }
        return null;
    }
}

