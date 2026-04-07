/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ScheduledEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.scheduledevent.GenericScheduledEventGatewayEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CacheRestAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public abstract class GenericScheduledEventUserEvent
extends GenericScheduledEventGatewayEvent {
    private final long userId;

    public GenericScheduledEventUserEvent(@Nonnull JDA api, long responseNumber, @Nonnull ScheduledEvent scheduledEvent, long userId) {
        super(api, responseNumber, scheduledEvent);
        this.userId = userId;
    }

    public long getUserIdLong() {
        return this.userId;
    }

    @Nonnull
    public String getUserId() {
        return Long.toUnsignedString(this.userId);
    }

    @Nullable
    public User getUser() {
        return this.api.getUserById(this.userId);
    }

    @Nullable
    public Member getMember() {
        return this.guild.getMemberById(this.userId);
    }

    @Nonnull
    @CheckReturnValue
    public CacheRestAction<User> retrieveUser() {
        return this.getJDA().retrieveUserById(this.getUserIdLong());
    }

    @Nonnull
    @CheckReturnValue
    public CacheRestAction<Member> retrieveMember() {
        return this.getGuild().retrieveMemberById(this.getUserIdLong());
    }
}

