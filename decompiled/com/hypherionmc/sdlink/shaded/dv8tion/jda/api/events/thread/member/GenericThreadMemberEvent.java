/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.thread.member;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ThreadMember;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.thread.GenericThreadEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public class GenericThreadMemberEvent
extends GenericThreadEvent {
    protected final long threadMemberId;
    protected final ThreadMember threadMember;

    public GenericThreadMemberEvent(@Nonnull JDA api, long responseNumber, ThreadChannel thread, long threadMemberId, ThreadMember threadMember) {
        super(api, responseNumber, thread);
        this.threadMemberId = threadMemberId;
        this.threadMember = threadMember;
    }

    @Nonnull
    public String getThreadMemberId() {
        return Long.toUnsignedString(this.getThreadMemberIdLong());
    }

    public long getThreadMemberIdLong() {
        return this.threadMemberId;
    }

    @Nullable
    public ThreadMember getThreadMember() {
        return this.threadMember;
    }

    @Nullable
    public Member getMember() {
        return this.getGuild().getMemberById(this.threadMemberId);
    }
}

