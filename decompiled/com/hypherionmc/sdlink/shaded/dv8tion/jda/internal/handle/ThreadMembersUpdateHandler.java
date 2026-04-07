/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ThreadMember;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.thread.member.ThreadMemberJoinEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.thread.member.ThreadMemberLeaveEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.ThreadChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.UnlockHook;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.hash.TLongObjectHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ThreadMembersUpdateHandler
extends SocketHandler {
    public ThreadMembersUpdateHandler(JDAImpl api) {
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
            EventCache.LOG.debug("THREAD_MEMBERS_UPDATE attempted to update a thread that does not exist. JSON: {}", (Object)content);
            return null;
        }
        if (!content.isNull("added_members")) {
            DataArray addedMembersJson = content.getArray("added_members");
            this.handleAddedThreadMembers(thread, addedMembersJson);
        }
        if (!content.isNull("removed_member_ids")) {
            List<Long> removedMemberIds = content.getArray("removed_member_ids").stream(DataArray::getString).map(MiscUtil::parseSnowflake).collect(Collectors.toList());
            this.handleRemovedThreadMembers(thread, removedMemberIds);
        }
        return null;
    }

    private void handleAddedThreadMembers(ThreadChannelImpl thread, DataArray addedMembersJson) {
        EntityBuilder entityBuilder = this.api.getEntityBuilder();
        CacheView.SimpleCacheView<ThreadMember> view = thread.getThreadMemberView();
        ArrayList<ThreadMember> addedThreadMembers = new ArrayList<ThreadMember>();
        for (int i = 0; i < addedMembersJson.length(); ++i) {
            DataObject threadMemberJson = addedMembersJson.getObject(i);
            ThreadMember threadMember = entityBuilder.createThreadMember(thread.getGuild(), thread, threadMemberJson);
            addedThreadMembers.add(threadMember);
        }
        try (UnlockHook lock = view.writeLock();){
            for (ThreadMember threadMember : addedThreadMembers) {
                view.getMap().put(threadMember.getIdLong(), threadMember);
            }
        }
        for (ThreadMember threadMember : addedThreadMembers) {
            this.api.handleEvent(new ThreadMemberJoinEvent(this.api, this.responseNumber, thread, threadMember));
        }
    }

    private void handleRemovedThreadMembers(ThreadChannelImpl thread, List<Long> removedMemberIds) {
        CacheView.SimpleCacheView<ThreadMember> view = thread.getThreadMemberView();
        TLongObjectHashMap<ThreadMember> removedThreadMembers = new TLongObjectHashMap<ThreadMember>();
        try (UnlockHook lock = view.writeLock();){
            for (long threadMemberId : removedMemberIds) {
                ThreadMember threadMember = (ThreadMember)view.getMap().remove(threadMemberId);
                removedThreadMembers.put(threadMemberId, threadMember);
            }
        }
        for (long threadMemberId : removedMemberIds) {
            this.api.handleEvent(new ThreadMemberLeaveEvent(this.api, this.responseNumber, thread, threadMemberId, (ThreadMember)removedThreadMembers.remove(threadMemberId)));
        }
    }
}

