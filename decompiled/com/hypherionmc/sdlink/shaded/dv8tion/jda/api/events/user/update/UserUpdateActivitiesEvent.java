/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Activity;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.GenericUserPresenceEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.GenericUserUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.List;

public class UserUpdateActivitiesEvent
extends GenericUserUpdateEvent<List<Activity>>
implements GenericUserPresenceEvent {
    public static final String IDENTIFIER = "activities";
    private final Member member;

    public UserUpdateActivitiesEvent(@Nonnull JDA api, long responseNumber, @Nonnull Member member, @Nullable List<Activity> previous) {
        super(api, responseNumber, member.getUser(), previous, member.getActivities(), IDENTIFIER);
        this.member = member;
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        return this.member.getGuild();
    }

    @Override
    @Nonnull
    public Member getMember() {
        return this.member;
    }
}

