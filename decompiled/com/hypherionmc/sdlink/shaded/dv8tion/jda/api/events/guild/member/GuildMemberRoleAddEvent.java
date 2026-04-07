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

public class GuildMemberRoleAddEvent
extends GenericGuildMemberEvent {
    private final List<Role> addedRoles;

    public GuildMemberRoleAddEvent(@Nonnull JDA api, long responseNumber, @Nonnull Member member, @Nonnull List<Role> addedRoles) {
        super(api, responseNumber, member);
        this.addedRoles = Collections.unmodifiableList(addedRoles);
    }

    @Nonnull
    public List<Role> getRoles() {
        return this.addedRoles;
    }
}

