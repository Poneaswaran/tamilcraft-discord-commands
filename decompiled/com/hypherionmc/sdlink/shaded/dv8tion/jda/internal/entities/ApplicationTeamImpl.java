/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ApplicationTeam;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.TeamMember;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class ApplicationTeamImpl
implements ApplicationTeam {
    private final String iconId;
    private final List<TeamMember> members;
    private final long id;
    private final long ownerId;

    public ApplicationTeamImpl(String iconId, List<TeamMember> members, long id, long ownerId) {
        this.iconId = iconId;
        this.members = Collections.unmodifiableList(members);
        this.id = id;
        this.ownerId = ownerId;
    }

    @Override
    public long getOwnerIdLong() {
        return this.ownerId;
    }

    @Override
    public String getIconId() {
        return this.iconId;
    }

    @Override
    @Nonnull
    public List<TeamMember> getMembers() {
        return this.members;
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ApplicationTeamImpl)) {
            return false;
        }
        ApplicationTeamImpl app = (ApplicationTeamImpl)obj;
        return app.id == this.id;
    }

    public String toString() {
        return new EntityString(this).toString();
    }
}

