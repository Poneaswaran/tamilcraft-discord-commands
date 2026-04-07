/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.TeamMember;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ImageProxy;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.List;
import org.jetbrains.annotations.Unmodifiable;

public interface ApplicationTeam
extends ISnowflake {
    public static final String ICON_URL = "https://cdn.discordapp.com/team-icons/%s/%s.png";

    @Nullable
    default public TeamMember getOwner() {
        return this.getMemberById(this.getOwnerIdLong());
    }

    @Nonnull
    default public String getOwnerId() {
        return Long.toUnsignedString(this.getOwnerIdLong());
    }

    public long getOwnerIdLong();

    @Nullable
    public String getIconId();

    @Nullable
    default public String getIconUrl() {
        String iconId = this.getIconId();
        return iconId == null ? null : String.format(ICON_URL, this.getId(), iconId);
    }

    @Nullable
    default public ImageProxy getIcon() {
        String iconUrl = this.getIconUrl();
        return iconUrl == null ? null : new ImageProxy(iconUrl);
    }

    @Nonnull
    public @Unmodifiable List<TeamMember> getMembers();

    default public boolean isMember(@Nonnull User user) {
        return this.getMember(user) != null;
    }

    @Nullable
    default public TeamMember getMember(@Nonnull User user) {
        Checks.notNull(user, "User");
        return this.getMemberById(user.getIdLong());
    }

    @Nullable
    default public TeamMember getMemberById(@Nonnull String userId) {
        return this.getMemberById(MiscUtil.parseSnowflake(userId));
    }

    @Nullable
    default public TeamMember getMemberById(long userId) {
        for (TeamMember member : this.getMembers()) {
            if (member.getUser().getIdLong() != userId) continue;
            return member;
        }
        return null;
    }
}

