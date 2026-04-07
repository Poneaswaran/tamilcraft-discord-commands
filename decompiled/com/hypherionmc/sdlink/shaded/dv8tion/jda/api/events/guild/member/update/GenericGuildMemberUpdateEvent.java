/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.update;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.UpdateEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.GenericGuildMemberEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public abstract class GenericGuildMemberUpdateEvent<T>
extends GenericGuildMemberEvent
implements UpdateEvent<Member, T> {
    protected final T previous;
    protected final T next;
    protected final String identifier;

    public GenericGuildMemberUpdateEvent(@Nonnull JDA api, long responseNumber, @Nonnull Member member, @Nullable T previous, @Nullable T next, @Nonnull String identifier) {
        super(api, responseNumber, member);
        this.previous = previous;
        this.next = next;
        this.identifier = identifier;
    }

    @Override
    @Nonnull
    public String getPropertyIdentifier() {
        return this.identifier;
    }

    @Override
    @Nonnull
    public Member getEntity() {
        return this.getMember();
    }

    @Override
    @Nullable
    public T getOldValue() {
        return this.previous;
    }

    @Override
    @Nullable
    public T getNewValue() {
        return this.next;
    }
}

