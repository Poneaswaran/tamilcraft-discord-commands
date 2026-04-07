/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Activity;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.GenericUserEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.GenericUserPresenceEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class UserActivityEndEvent
extends GenericUserEvent
implements GenericUserPresenceEvent {
    private final Activity oldActivity;
    private final Member member;

    public UserActivityEndEvent(@Nonnull JDA api, long responseNumber, @Nonnull Member member, @Nonnull Activity oldActivity) {
        super(api, responseNumber, member.getUser());
        this.oldActivity = oldActivity;
        this.member = member;
    }

    @Nonnull
    public Activity getOldActivity() {
        return this.oldActivity;
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

