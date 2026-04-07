/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ThreadMember;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.concrete.ThreadChannelImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.time.OffsetDateTime;

public class ThreadMemberImpl
implements ThreadMember {
    private final JDA api;
    private final ThreadChannelImpl thread;
    private Member member;
    private long joinedTimestamp;

    public ThreadMemberImpl(Member member, ThreadChannelImpl thread) {
        this.api = member.getJDA();
        this.member = member;
        this.thread = thread;
    }

    @Override
    @Nonnull
    public JDA getJDA() {
        return this.api;
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        return this.thread.getGuild();
    }

    @Override
    @Nonnull
    public ThreadChannel getThread() {
        return this.thread;
    }

    @Override
    @Nonnull
    public User getUser() {
        return this.member.getUser();
    }

    @Override
    @Nonnull
    public Member getMember() {
        return this.member;
    }

    @Override
    @Nonnull
    public OffsetDateTime getTimeJoined() {
        return Helpers.toOffset(this.joinedTimestamp);
    }

    @Override
    @Nonnull
    public String getAsMention() {
        return this.member.getAsMention();
    }

    @Override
    public long getIdLong() {
        return this.member.getIdLong();
    }

    public ThreadMemberImpl setJoinedTimestamp(long joinedTimestamp) {
        this.joinedTimestamp = joinedTimestamp;
        return this;
    }

    public String toString() {
        return new EntityString(this).addMetadata("member", this.getMember()).toString();
    }
}

