/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.thread.member;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ThreadMember;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.thread.member.GenericThreadMemberEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class ThreadMemberLeaveEvent
extends GenericThreadMemberEvent {
    public ThreadMemberLeaveEvent(@Nonnull JDA api, long responseNumber, ThreadChannel thread, long threadMemberId, ThreadMember threadMember) {
        super(api, responseNumber, thread, threadMemberId, threadMember);
    }

    @Override
    @Nonnull
    public ThreadMember getThreadMember() {
        return super.getThreadMember();
    }

    @Override
    @Nonnull
    public Member getMember() {
        return this.getThreadMember().getMember();
    }
}

