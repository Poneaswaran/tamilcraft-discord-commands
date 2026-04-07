/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.time.OffsetDateTime;

public interface ThreadMember
extends IMentionable {
    @Nonnull
    public JDA getJDA();

    @Nonnull
    public Guild getGuild();

    @Nonnull
    public ThreadChannel getThread();

    @Nonnull
    public User getUser();

    @Nonnull
    public Member getMember();

    @Nonnull
    public OffsetDateTime getTimeJoined();

    default public boolean isThreadOwner() {
        return this.getThread().getOwnerIdLong() == this.getIdLong();
    }
}

