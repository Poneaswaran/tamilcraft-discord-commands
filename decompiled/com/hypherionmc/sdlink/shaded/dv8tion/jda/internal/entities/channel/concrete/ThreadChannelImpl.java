/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ThreadMember;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IThreadContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumTag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.IThreadContainerUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.concrete.ThreadChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CacheRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.ThreadMemberPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.TimeUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.middleman.AbstractGuildChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.ISlowmodeChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.middleman.GuildMessageChannelMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.channel.concrete.ThreadChannelManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.DeferredRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination.ThreadMemberPaginationActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.gnu.trove.set.TLongSet;
import com.hypherionmc.sdlink.shaded.gnu.trove.set.hash.TLongHashSet;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.LongStream;

public class ThreadChannelImpl
extends AbstractGuildChannelImpl<ThreadChannelImpl>
implements ThreadChannel,
GuildMessageChannelMixin<ThreadChannelImpl>,
ISlowmodeChannelMixin<ThreadChannelImpl> {
    private final ChannelType type;
    private final CacheView.SimpleCacheView<ThreadMember> threadMembers = new CacheView.SimpleCacheView<ThreadMember>(ThreadMember.class, null);
    private TLongSet appliedTags = new TLongHashSet(5);
    private ThreadChannel.AutoArchiveDuration autoArchiveDuration;
    private IThreadContainerUnion parentChannel;
    private boolean locked;
    private boolean archived;
    private boolean invitable;
    private long archiveTimestamp;
    private long creationTimestamp;
    private long ownerId;
    private long latestMessageId;
    private int messageCount;
    private int totalMessageCount;
    private int memberCount;
    private int slowmode;
    private int flags;

    public ThreadChannelImpl(long id, GuildImpl guild, ChannelType type) {
        super(id, guild);
        this.type = type;
    }

    @Override
    @Nonnull
    public EnumSet<ChannelFlag> getFlags() {
        return ChannelFlag.fromRaw(this.flags);
    }

    @Override
    @Nonnull
    public ChannelType getType() {
        return this.type;
    }

    @Override
    public long getLatestMessageIdLong() {
        return this.latestMessageId;
    }

    @Override
    public int getMessageCount() {
        return this.messageCount;
    }

    @Override
    public int getTotalMessageCount() {
        return this.totalMessageCount;
    }

    @Override
    public int getMemberCount() {
        return this.memberCount;
    }

    @Override
    public boolean isLocked() {
        return this.locked;
    }

    @Override
    public boolean canTalk(@Nonnull Member member) {
        Checks.notNull(member, "Member");
        if (this.type == ChannelType.GUILD_PRIVATE_THREAD && this.threadMembers.get(member.getIdLong()) == null) {
            return member.hasPermission((GuildChannel)this.getParentChannel(), Permission.MANAGE_THREADS, Permission.MESSAGE_SEND_IN_THREADS);
        }
        return member.hasPermission((GuildChannel)this.getParentChannel(), Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND_IN_THREADS);
    }

    @Override
    @Nonnull
    public List<Member> getMembers() {
        return Collections.emptyList();
    }

    @Override
    @Nonnull
    public IThreadContainerUnion getParentChannel() {
        IThreadContainer realChannel = this.getGuild().getChannelById(IThreadContainer.class, this.parentChannel.getIdLong());
        if (realChannel != null) {
            this.parentChannel = (IThreadContainerUnion)realChannel;
        }
        return this.parentChannel;
    }

    @Override
    @Nonnull
    public List<ForumTag> getAppliedTags() {
        IThreadContainerUnion parent = this.getParentChannel();
        if (parent.getType() != ChannelType.FORUM) {
            return Collections.emptyList();
        }
        return parent.asForumChannel().getAvailableTagCache().stream().filter(tag -> this.appliedTags.contains(tag.getIdLong())).collect(Helpers.toUnmodifiableList());
    }

    @Override
    @Nonnull
    public RestAction<Message> retrieveParentMessage() {
        return this.getParentMessageChannel().retrieveMessageById(this.getIdLong());
    }

    @Override
    @Nonnull
    public RestAction<Message> retrieveStartMessage() {
        return this.retrieveMessageById(this.getId());
    }

    @Override
    @Nonnull
    public IPermissionContainer getPermissionContainer() {
        return this.getParentChannel();
    }

    @Override
    @Nonnull
    public List<ThreadMember> getThreadMembers() {
        return this.threadMembers.asList();
    }

    @Override
    @Nullable
    public ThreadMember getThreadMemberById(long id) {
        return (ThreadMember)this.threadMembers.get(id);
    }

    @Override
    @Nonnull
    public CacheRestAction<ThreadMember> retrieveThreadMemberById(long id) {
        JDAImpl jda = (JDAImpl)this.getJDA();
        return new DeferredRestAction<ThreadMember, RestActionImpl>(jda, ThreadMember.class, () -> this.getThreadMemberById(id), () -> {
            Route.CompiledRoute route = Route.Channels.GET_THREAD_MEMBER.compile(this.getId(), Long.toUnsignedString(id)).withQueryParams("with_member", "true");
            return new RestActionImpl<ThreadMember>((JDA)jda, route, (resp, req) -> jda.getEntityBuilder().createThreadMember(this.getGuild(), this, resp.getObject()));
        });
    }

    @Override
    @Nonnull
    public ThreadMemberPaginationAction retrieveThreadMembers() {
        return new ThreadMemberPaginationActionImpl(this);
    }

    @Override
    public long getOwnerIdLong() {
        return this.ownerId;
    }

    @Override
    public boolean isArchived() {
        return this.archived;
    }

    @Override
    public boolean isInvitable() {
        if (this.type != ChannelType.GUILD_PRIVATE_THREAD) {
            throw new UnsupportedOperationException("Only private threads support the concept of invitable.");
        }
        return this.invitable;
    }

    @Override
    @Nonnull
    public OffsetDateTime getTimeArchiveInfoLastModified() {
        return Helpers.toOffset(this.archiveTimestamp);
    }

    @Override
    @Nonnull
    public ThreadChannel.AutoArchiveDuration getAutoArchiveDuration() {
        return this.autoArchiveDuration;
    }

    @Override
    @Nonnull
    public OffsetDateTime getTimeCreated() {
        return this.creationTimestamp == 0L ? TimeUtil.getTimeCreated(this.getIdLong()) : Helpers.toOffset(this.creationTimestamp);
    }

    @Override
    public int getSlowmode() {
        return this.slowmode;
    }

    @Override
    @Nonnull
    public RestAction<Void> join() {
        this.checkUnarchived();
        Route.CompiledRoute route = Route.Channels.JOIN_THREAD.compile(this.getId());
        return new RestActionImpl<Void>(this.api, route);
    }

    @Override
    @Nonnull
    public RestAction<Void> leave() {
        this.checkUnarchived();
        Route.CompiledRoute route = Route.Channels.LEAVE_THREAD.compile(this.getId());
        return new RestActionImpl<Void>(this.api, route);
    }

    @Override
    @Nonnull
    public RestAction<Void> addThreadMemberById(long id) {
        this.checkUnarchived();
        this.checkInvitable();
        this.checkPermission(Permission.MESSAGE_SEND_IN_THREADS);
        Route.CompiledRoute route = Route.Channels.ADD_THREAD_MEMBER.compile(this.getId(), Long.toUnsignedString(id));
        return new RestActionImpl<Void>(this.api, route);
    }

    @Override
    @Nonnull
    public RestAction<Void> removeThreadMemberById(long id) {
        boolean privateThreadOwner;
        this.checkUnarchived();
        boolean bl = privateThreadOwner = this.type == ChannelType.GUILD_PRIVATE_THREAD && this.ownerId == this.api.getSelfUser().getIdLong();
        if (!privateThreadOwner) {
            this.checkPermission(Permission.MANAGE_THREADS);
        }
        Route.CompiledRoute route = Route.Channels.REMOVE_THREAD_MEMBER.compile(this.getId(), Long.toUnsignedString(id));
        return new RestActionImpl<Void>(this.api, route);
    }

    @Override
    @Nonnull
    public ThreadChannelManager getManager() {
        return new ThreadChannelManagerImpl(this);
    }

    @Override
    public void checkCanManage() {
        if (this.isOwner()) {
            return;
        }
        this.checkPermission(Permission.MANAGE_THREADS);
    }

    public CacheView.SimpleCacheView<ThreadMember> getThreadMemberView() {
        return this.threadMembers;
    }

    @Override
    public ThreadChannelImpl setLatestMessageIdLong(long latestMessageId) {
        this.latestMessageId = latestMessageId;
        return this;
    }

    public ThreadChannelImpl setAutoArchiveDuration(ThreadChannel.AutoArchiveDuration autoArchiveDuration) {
        this.autoArchiveDuration = autoArchiveDuration;
        return this;
    }

    public ThreadChannelImpl setParentChannel(IThreadContainer channel) {
        this.parentChannel = (IThreadContainerUnion)channel;
        return this;
    }

    public ThreadChannelImpl setLocked(boolean locked) {
        this.locked = locked;
        return this;
    }

    public ThreadChannelImpl setArchived(boolean archived) {
        this.archived = archived;
        return this;
    }

    public ThreadChannelImpl setInvitable(boolean invitable) {
        this.invitable = invitable;
        return this;
    }

    public ThreadChannelImpl setArchiveTimestamp(long archiveTimestamp) {
        this.archiveTimestamp = archiveTimestamp;
        return this;
    }

    public ThreadChannelImpl setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
        return this;
    }

    public ThreadChannelImpl setOwnerId(long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public ThreadChannelImpl setMessageCount(int messageCount) {
        this.messageCount = messageCount;
        return this;
    }

    public ThreadChannelImpl setTotalMessageCount(int messageCount) {
        this.totalMessageCount = Math.max(messageCount, this.messageCount);
        return this;
    }

    public ThreadChannelImpl setMemberCount(int memberCount) {
        this.memberCount = memberCount;
        return this;
    }

    @Override
    public ThreadChannelImpl setSlowmode(int slowmode) {
        this.slowmode = slowmode;
        return this;
    }

    public ThreadChannelImpl setAppliedTags(LongStream tags) {
        TLongHashSet set = new TLongHashSet(5);
        tags.forEach(set::add);
        this.appliedTags = set;
        return this;
    }

    public ThreadChannelImpl setFlags(int flags) {
        this.flags = flags;
        return this;
    }

    public long getArchiveTimestamp() {
        return this.archiveTimestamp;
    }

    public TLongSet getAppliedTagsSet() {
        return this.appliedTags;
    }

    public int getRawFlags() {
        return this.flags;
    }

    private void checkUnarchived() {
        if (this.archived) {
            throw new IllegalStateException("Cannot modify a ThreadChannel while it is archived!");
        }
    }

    private void checkInvitable() {
        if (this.ownerId == this.api.getSelfUser().getIdLong()) {
            return;
        }
        if (!this.isPublic() && !this.isInvitable()) {
            this.checkPermission(Permission.MANAGE_THREADS);
        }
    }
}

