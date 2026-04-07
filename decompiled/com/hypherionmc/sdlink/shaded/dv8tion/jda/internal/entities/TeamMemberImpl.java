/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.TeamMember;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Objects;

public class TeamMemberImpl
implements TeamMember {
    private final User user;
    private final TeamMember.MembershipState state;
    private final TeamMember.RoleType roleType;
    private final long teamId;

    public TeamMemberImpl(User user, TeamMember.MembershipState state, TeamMember.RoleType roleType, long teamId) {
        this.user = user;
        this.state = state;
        this.roleType = roleType;
        this.teamId = teamId;
    }

    @Override
    @Nonnull
    public User getUser() {
        return this.user;
    }

    @Override
    @Nonnull
    public TeamMember.MembershipState getMembershipState() {
        return this.state;
    }

    @Override
    @Nonnull
    public TeamMember.RoleType getRoleType() {
        return this.roleType;
    }

    @Override
    public long getTeamIdLong() {
        return this.teamId;
    }

    public int hashCode() {
        return Objects.hash(this.user, this.teamId);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TeamMemberImpl)) {
            return false;
        }
        TeamMemberImpl member = (TeamMemberImpl)obj;
        return member.teamId == this.teamId && member.user.equals(this.user);
    }

    public String toString() {
        return new EntityString(this).addMetadata("teamId", this.getTeamId()).addMetadata("user", this.user).toString();
    }
}

