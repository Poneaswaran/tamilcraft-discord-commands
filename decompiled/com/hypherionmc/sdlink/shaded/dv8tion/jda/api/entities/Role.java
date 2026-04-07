/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IPermissionHolder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.RoleIcon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.RoleManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.RoleAction;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.awt.Color;

public interface Role
extends IMentionable,
IPermissionHolder,
Comparable<Role> {
    public static final int DEFAULT_COLOR_RAW = 0x1FFFFFFF;

    public int getPosition();

    public int getPositionRaw();

    @Nonnull
    public String getName();

    public boolean isManaged();

    public boolean isHoisted();

    public boolean isMentionable();

    public long getPermissionsRaw();

    @Nullable
    public Color getColor();

    public int getColorRaw();

    public boolean isPublicRole();

    public boolean canInteract(@Nonnull Role var1);

    @Override
    @Nonnull
    public Guild getGuild();

    @Nonnull
    @CheckReturnValue
    public RoleAction createCopy(@Nonnull Guild var1);

    @Nonnull
    @CheckReturnValue
    default public RoleAction createCopy() {
        return this.createCopy(this.getGuild());
    }

    @Nonnull
    @CheckReturnValue
    public RoleManager getManager();

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> delete();

    @Nonnull
    public JDA getJDA();

    @Nonnull
    public RoleTags getTags();

    @Nullable
    public RoleIcon getIcon();

    public static interface RoleTags {
        public boolean isBot();

        public long getBotIdLong();

        @Nullable
        default public String getBotId() {
            return this.isBot() ? Long.toUnsignedString(this.getBotIdLong()) : null;
        }

        public boolean isBoost();

        public boolean isIntegration();

        public long getIntegrationIdLong();

        @Nullable
        default public String getIntegrationId() {
            return this.isIntegration() ? Long.toUnsignedString(this.getIntegrationIdLong()) : null;
        }

        default public boolean hasSubscriptionListing() {
            return this.getSubscriptionIdLong() != 0L;
        }

        public long getSubscriptionIdLong();

        @Nullable
        default public String getSubscriptionId() {
            return this.hasSubscriptionListing() ? Long.toUnsignedString(this.getSubscriptionIdLong()) : null;
        }

        public boolean isAvailableForPurchase();

        public boolean isLinkedRole();
    }
}

