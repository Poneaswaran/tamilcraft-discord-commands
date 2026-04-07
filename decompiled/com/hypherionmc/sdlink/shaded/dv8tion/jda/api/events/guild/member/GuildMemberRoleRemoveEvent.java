/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.guild.member.GenericGuildMemberEvent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class GuildMemberRoleRemoveEvent
extends GenericGuildMemberEvent {
    private final List<Role> removedRoles;

    public GuildMemberRoleRemoveEvent(@Nonnull JDA api, long responseNumber, @Nonnull Member member, @Nonnull List<Role> removedRoles) {
        super(api, responseNumber, member);
        this.removedRoles = Collections.unmodifiableList(removedRoles);
    }

    @Nonnull
    public List<Role> getRoles() {
        return this.removedRoles;
    }
}

