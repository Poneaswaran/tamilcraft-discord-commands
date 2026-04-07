/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateAppliedTagsEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateArchiveTimestampEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateArchivedEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateAutoArchiveDurationEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateFlagsEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateInvitableEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateLockedEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateNameEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.channel.update.ChannelUpdateSlowmodeEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.ThreadChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.EventCache;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.handle.SocketHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.ChannelCacheViewImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.SortedChannelCacheViewImpl;
import com.hypherionmc.sdlink.shaded.gnu.trove.set.TLongSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.LongStream;

public class ThreadUpdateHandler
extends SocketHandler {
    public ThreadUpdateHandler(JDAImpl api) {
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
            if (content.getObject("thread_metadata").getBoolean("archived")) {
                return null;
            }
            try {
                thread = (ThreadChannelImpl)this.api.getEntityBuilder().createThreadChannel(content, guildId);
                this.api.handleEvent(new ChannelUpdateArchivedEvent(this.api, this.responseNumber, thread, true, false));
            }
            catch (IllegalArgumentException ex) {
                if ("MISSING_CHANNEL".equals(ex.getMessage())) {
                    long parentId = content.getUnsignedLong("parent_id", 0L);
                    EventCache.LOG.debug("Caching THREAD_UPDATE for a thread with uncached parent. Parent ID: {} JSON: {}", (Object)parentId, (Object)content);
                    this.api.getEventCache().cache(EventCache.Type.CHANNEL, parentId, this.responseNumber, this.allContent, this::handle);
                    return null;
                }
                throw ex;
            }
            return null;
        }
        DataObject threadMetadata = content.getObject("thread_metadata");
        String name = content.getString("name");
        int flags = content.getInt("flags", 0);
        ThreadChannel.AutoArchiveDuration autoArchiveDuration = ThreadChannel.AutoArchiveDuration.fromKey(threadMetadata.getInt("auto_archive_duration"));
        boolean locked = threadMetadata.getBoolean("locked");
        boolean archived = threadMetadata.getBoolean("archived");
        boolean invitable = threadMetadata.getBoolean("invitable");
        long archiveTimestamp = Helpers.toTimestamp(threadMetadata.getString("archive_timestamp"));
        int slowmode = content.getInt("rate_limit_per_user", 0);
        String oldName = thread.getName();
        ThreadChannel.AutoArchiveDuration oldAutoArchiveDuration = thread.getAutoArchiveDuration();
        boolean oldLocked = thread.isLocked();
        boolean oldArchived = thread.isArchived();
        boolean oldInvitable = !thread.isPublic() && thread.isInvitable();
        long oldArchiveTimestamp = thread.getArchiveTimestamp();
        int oldSlowmode = thread.getSlowmode();
        int oldFlags = thread.getRawFlags();
        if (!Objects.equals(oldName, name)) {
            thread.setName(name);
            this.api.handleEvent(new ChannelUpdateNameEvent(this.getJDA(), this.responseNumber, thread, oldName, name));
        }
        if (oldFlags != flags) {
            thread.setFlags(flags);
            this.api.handleEvent(new ChannelUpdateFlagsEvent(this.getJDA(), this.responseNumber, thread, ChannelFlag.fromRaw(oldFlags), ChannelFlag.fromRaw(flags)));
        }
        if (oldSlowmode != slowmode) {
            thread.setSlowmode(slowmode);
            this.api.handleEvent(new ChannelUpdateSlowmodeEvent(this.api, this.responseNumber, thread, oldSlowmode, slowmode));
        }
        if (oldAutoArchiveDuration != autoArchiveDuration) {
            thread.setAutoArchiveDuration(autoArchiveDuration);
            this.api.handleEvent(new ChannelUpdateAutoArchiveDurationEvent(this.api, this.responseNumber, thread, oldAutoArchiveDuration, autoArchiveDuration));
        }
        if (oldLocked != locked) {
            thread.setLocked(locked);
            this.api.handleEvent(new ChannelUpdateLockedEvent(this.api, this.responseNumber, thread, oldLocked, locked));
        }
        if (oldArchived != archived) {
            thread.setArchived(archived);
            this.api.handleEvent(new ChannelUpdateArchivedEvent(this.api, this.responseNumber, thread, oldArchived, archived));
        }
        if (oldArchiveTimestamp != archiveTimestamp) {
            thread.setArchiveTimestamp(archiveTimestamp);
            this.api.handleEvent(new ChannelUpdateArchiveTimestampEvent(this.api, this.responseNumber, thread, oldArchiveTimestamp, archiveTimestamp));
        }
        if (oldInvitable != invitable) {
            thread.setInvitable(invitable);
            this.api.handleEvent(new ChannelUpdateInvitableEvent(this.api, this.responseNumber, thread, oldInvitable, invitable));
        }
        if (this.api.isCacheFlagSet(CacheFlag.FORUM_TAGS) && !content.isNull("applied_tags")) {
            TLongSet oldTags = thread.getAppliedTagsSet();
            thread.setAppliedTags(content.getArray("applied_tags").stream(DataArray::getUnsignedLong).mapToLong(Long::longValue));
            TLongSet tags = thread.getAppliedTagsSet();
            if (!oldTags.equals(tags)) {
                List<Long> oldTagList = LongStream.of(oldTags.toArray()).boxed().collect(Helpers.toUnmodifiableList());
                List<Long> newTagList = LongStream.of(tags.toArray()).boxed().collect(Helpers.toUnmodifiableList());
                this.api.handleEvent(new ChannelUpdateAppliedTagsEvent(this.api, this.responseNumber, thread, oldTagList, newTagList));
            }
        }
        if (thread.isArchived()) {
            SortedChannelCacheViewImpl<GuildChannel> guildView = thread.getGuild().getChannelView();
            ChannelCacheViewImpl<Channel> globalView = this.api.getChannelsView();
            guildView.remove(thread);
            globalView.remove(thread);
        }
        return null;
    }
}

