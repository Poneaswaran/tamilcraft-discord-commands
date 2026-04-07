/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.OnlineStatus;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.GenericUserPresenceEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.user.update.GenericUserUpdateEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class UserUpdateOnlineStatusEvent
extends GenericUserUpdateEvent<OnlineStatus>
implements GenericUserPresenceEvent {
    public static final String IDENTIFIER = "status";
    private final Guild guild;
    private final Member member;

    public UserUpdateOnlineStatusEvent(@Nonnull JDA api, long responseNumber, @Nonnull Member member, @Nonnull OnlineStatus oldOnlineStatus) {
        super(api, responseNumber, member.getUser(), oldOnlineStatus, member.getOnlineStatus(), IDENTIFIER);
        this.guild = member.getGuild();
        this.member = member;
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        return this.guild;
    }

    @Override
    @Nonnull
    public Member getMember() {
        return this.member;
    }

    @Nonnull
    public OnlineStatus getOldOnlineStatus() {
        return this.getOldValue();
    }

    @Nonnull
    public OnlineStatus getNewOnlineStatus() {
        return this.getNewValue();
    }

    @Override
    @Nonnull
    public OnlineStatus getOldValue() {
        return (OnlineStatus)((Object)super.getOldValue());
    }

    @Override
    @Nonnull
    public OnlineStatus getNewValue() {
        return (OnlineStatus)((Object)super.getNewValue());
    }
}

