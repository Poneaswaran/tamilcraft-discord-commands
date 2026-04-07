/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Message;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ThreadMember;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IMemberContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.ISlowmodeChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.forums.ForumTag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.IThreadContainerUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.channel.concrete.ThreadChannelManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CacheRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.ThreadMemberPaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.OffsetDateTime;
import java.util.Formatter;
import java.util.List;
import org.jetbrains.annotations.Unmodifiable;

public interface ThreadChannel
extends GuildMessageChannel,
IMemberContainer,
ISlowmodeChannel {
    default public boolean isPublic() {
        ChannelType type = this.getType();
        return type == ChannelType.GUILD_PUBLIC_THREAD || type == ChannelType.GUILD_NEWS_THREAD;
    }

    public int getMessageCount();

    public int getTotalMessageCount();

    public int getMemberCount();

    default public boolean isJoined() {
        return this.getSelfThreadMember() != null;
    }

    public boolean isLocked();

    public boolean isInvitable();

    default public boolean isPinned() {
        return this.getFlags().contains((Object)ChannelFlag.PINNED);
    }

    @Nonnull
    public IThreadContainerUnion getParentChannel();

    @Nonnull
    default public GuildMessageChannelUnion getParentMessageChannel() {
        if (this.getParentChannel() instanceof GuildMessageChannel) {
            return (GuildMessageChannelUnion)((Object)this.getParentChannel());
        }
        throw new UnsupportedOperationException("Parent of this thread is not a MessageChannel. Parent: " + this.getParentChannel());
    }

    @Nonnull
    public @Unmodifiable List<ForumTag> getAppliedTags();

    @Nonnull
    @CheckReturnValue
    public RestAction<Message> retrieveParentMessage();

    @Nonnull
    @CheckReturnValue
    public RestAction<Message> retrieveStartMessage();

    @Nullable
    default public ThreadMember getSelfThreadMember() {
        return this.getThreadMember(this.getJDA().getSelfUser());
    }

    @Nonnull
    public List<ThreadMember> getThreadMembers();

    @Nullable
    default public ThreadMember getThreadMember(@Nonnull Member member) {
        Checks.notNull(member, "Member");
        return this.getThreadMemberById(member.getId());
    }

    @Nullable
    default public ThreadMember getThreadMember(@Nonnull User user) {
        Checks.notNull(user, "User");
        return this.getThreadMemberById(user.getId());
    }

    @Nullable
    default public ThreadMember getThreadMemberById(@Nonnull String id) {
        return this.getThreadMemberById(MiscUtil.parseSnowflake(id));
    }

    @Nullable
    public ThreadMember getThreadMemberById(long var1);

    @Nonnull
    @CheckReturnValue
    default public CacheRestAction<ThreadMember> retrieveThreadMember(@Nonnull Member member) {
        Checks.notNull(member, "Member");
        return this.retrieveThreadMemberById(member.getIdLong());
    }

    @Nonnull
    @CheckReturnValue
    default public CacheRestAction<ThreadMember> retrieveThreadMember(@Nonnull User user) {
        Checks.notNull(user, "User");
        return this.retrieveThreadMemberById(user.getIdLong());
    }

    @Nonnull
    @CheckReturnValue
    default public CacheRestAction<ThreadMember> retrieveThreadMemberById(@Nonnull String id) {
        return this.retrieveThreadMemberById(MiscUtil.parseSnowflake(id));
    }

    @Nonnull
    @CheckReturnValue
    public CacheRestAction<ThreadMember> retrieveThreadMemberById(long var1);

    @Nonnull
    @CheckReturnValue
    public ThreadMemberPaginationAction retrieveThreadMembers();

    default public boolean isOwner() {
        return this.getJDA().getSelfUser().getIdLong() == this.getOwnerIdLong();
    }

    public long getOwnerIdLong();

    @Nonnull
    default public String getOwnerId() {
        return Long.toUnsignedString(this.getOwnerIdLong());
    }

    @Nullable
    default public Member getOwner() {
        return this.getGuild().getMemberById(this.getOwnerIdLong());
    }

    @Nullable
    default public ThreadMember getOwnerThreadMember() {
        return this.getThreadMemberById(this.getOwnerIdLong());
    }

    public boolean isArchived();

    @Nonnull
    public OffsetDateTime getTimeArchiveInfoLastModified();

    @Nonnull
    public AutoArchiveDuration getAutoArchiveDuration();

    @Override
    @Nonnull
    public OffsetDateTime getTimeCreated();

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> join();

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> leave();

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> addThreadMemberById(long var1);

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> addThreadMemberById(@Nonnull String id) {
        return this.addThreadMemberById(MiscUtil.parseSnowflake(id));
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> addThreadMember(@Nonnull User user) {
        Checks.notNull(user, "User");
        return this.addThreadMemberById(user.getIdLong());
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> addThreadMember(@Nonnull Member member) {
        Checks.notNull(member, "Member");
        return this.addThreadMemberById(member.getIdLong());
    }

    @Nonnull
    @CheckReturnValue
    public RestAction<Void> removeThreadMemberById(long var1);

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> removeThreadMemberById(@Nonnull String id) {
        return this.removeThreadMemberById(MiscUtil.parseSnowflake(id));
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> removeThreadMember(@Nonnull User user) {
        Checks.notNull(user, "User");
        return this.removeThreadMemberById(user.getId());
    }

    @Nonnull
    @CheckReturnValue
    default public RestAction<Void> removeThreadMember(@Nonnull Member member) {
        Checks.notNull(member, "Member");
        return this.removeThreadMemberById(member.getIdLong());
    }

    @Override
    @Nonnull
    @CheckReturnValue
    public ThreadChannelManager getManager();

    @Override
    default public void formatTo(Formatter formatter, int flags, int width, int precision) {
        boolean alt;
        boolean leftJustified = (flags & 1) == 1;
        boolean upper = (flags & 2) == 2;
        boolean bl = alt = (flags & 4) == 4;
        String out = alt ? "#" + (upper ? this.getName().toUpperCase(formatter.locale()) : this.getName()) : this.getAsMention();
        MiscUtil.appendTo(formatter, width, precision, leftJustified, out);
    }

    public static enum AutoArchiveDuration {
        TIME_1_HOUR(60),
        TIME_24_HOURS(1440),
        TIME_3_DAYS(4320),
        TIME_1_WEEK(10080);

        private final int minutes;

        private AutoArchiveDuration(int minutes) {
            this.minutes = minutes;
        }

        public int getMinutes() {
            return this.minutes;
        }

        @Nonnull
        public static AutoArchiveDuration fromKey(int minutes) {
            for (AutoArchiveDuration duration : AutoArchiveDuration.values()) {
                if (duration.getMinutes() != minutes) continue;
                return duration;
            }
            throw new IllegalArgumentException("Provided key was not recognized. Minutes: " + minutes);
        }
    }
}

