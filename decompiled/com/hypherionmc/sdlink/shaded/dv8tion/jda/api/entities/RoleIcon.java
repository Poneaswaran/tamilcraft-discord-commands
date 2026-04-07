/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ImageProxy;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Objects;

public class RoleIcon {
    public static final String ICON_URL = "https://cdn.discordapp.com/role-icons/%s/%s.png";
    private final String iconId;
    private final String emoji;
    private final long roleId;

    public RoleIcon(String iconId, String emoji, long roleId) {
        this.iconId = iconId;
        this.emoji = emoji;
        this.roleId = roleId;
    }

    @Nullable
    public String getIconId() {
        return this.iconId;
    }

    @Nullable
    public String getIconUrl() {
        String iconId = this.getIconId();
        return iconId == null ? null : String.format(ICON_URL, this.roleId, iconId);
    }

    @Nullable
    public ImageProxy getIcon() {
        String iconUrl = this.getIconUrl();
        return iconUrl == null ? null : new ImageProxy(iconUrl);
    }

    @Nullable
    public String getEmoji() {
        return this.emoji;
    }

    public boolean isEmoji() {
        return this.emoji != null;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RoleIcon)) {
            return false;
        }
        RoleIcon icon = (RoleIcon)obj;
        return Objects.equals(icon.iconId, this.iconId) && Objects.equals(icon.emoji, this.emoji);
    }

    public int hashCode() {
        return Objects.hash(this.iconId, this.emoji);
    }
}

