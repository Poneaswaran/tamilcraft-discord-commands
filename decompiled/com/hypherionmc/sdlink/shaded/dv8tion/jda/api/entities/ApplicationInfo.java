/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ApplicationTeam;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ImageProxy;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import org.jetbrains.annotations.Unmodifiable;

public interface ApplicationInfo
extends ISnowflake {
    public boolean doesBotRequireCodeGrant();

    @Nonnull
    public String getDescription();

    @Nullable
    public String getTermsOfServiceUrl();

    @Nullable
    public String getPrivacyPolicyUrl();

    @Nullable
    public String getIconId();

    @Nullable
    public String getIconUrl();

    @Nullable
    default public ImageProxy getIcon() {
        String iconUrl = this.getIconUrl();
        return iconUrl == null ? null : new ImageProxy(iconUrl);
    }

    @Nullable
    public ApplicationTeam getTeam();

    @Nonnull
    default public ApplicationInfo setRequiredScopes(String ... scopes) {
        Checks.noneNull(scopes, "Scopes");
        return this.setRequiredScopes(Arrays.asList(scopes));
    }

    @Nonnull
    public ApplicationInfo setRequiredScopes(@Nonnull Collection<String> var1);

    @Nonnull
    default public String getInviteUrl(@Nullable Collection<Permission> permissions) {
        return this.getInviteUrl(null, permissions);
    }

    @Nonnull
    default public String getInviteUrl(Permission ... permissions) {
        return this.getInviteUrl((String)null, permissions);
    }

    @Nonnull
    public String getInviteUrl(@Nullable String var1, @Nullable Collection<Permission> var2);

    @Nonnull
    default public String getInviteUrl(long guildId, @Nullable Collection<Permission> permissions) {
        return this.getInviteUrl(Long.toUnsignedString(guildId), permissions);
    }

    @Nonnull
    default public String getInviteUrl(@Nullable String guildId, Permission ... permissions) {
        return this.getInviteUrl(guildId, permissions == null ? null : Arrays.asList(permissions));
    }

    @Nonnull
    default public String getInviteUrl(long guildId, Permission ... permissions) {
        return this.getInviteUrl(Long.toUnsignedString(guildId), permissions);
    }

    @Nonnull
    public JDA getJDA();

    @Nonnull
    public String getName();

    @Nonnull
    public User getOwner();

    public boolean isBotPublic();

    @Nonnull
    public @Unmodifiable List<String> getTags();

    @Nonnull
    public @Unmodifiable List<String> getRedirectUris();

    @Nullable
    public String getInteractionsEndpointUrl();

    @Nullable
    public String getRoleConnectionsVerificationUrl();

    @Nullable
    public String getCustomAuthorizationUrl();

    @Nonnull
    public @Unmodifiable List<String> getScopes();

    @Nonnull
    public EnumSet<Permission> getPermissions();

    public long getPermissionsRaw();

    @Nonnull
    default public EnumSet<Flag> getFlags() {
        return Flag.fromRaw(this.getFlagsRaw());
    }

    public long getFlagsRaw();

    public static enum Flag {
        GATEWAY_PRESENCE(4096L),
        GATEWAY_PRESENCE_LIMITED(8192L),
        GATEWAY_GUILD_MEMBERS(16384L),
        GATEWAY_GUILD_MEMBERS_LIMITED(32768L),
        VERIFICATION_PENDING_GUILD_LIMIT(65536L),
        EMBEDDED(131072L),
        GATEWAY_MESSAGE_CONTENT(262144L),
        GATEWAY_MESSAGE_CONTENT_LIMITED(524288L);

        private final long value;

        private Flag(long value) {
            this.value = value;
        }

        @Nonnull
        public static EnumSet<Flag> fromRaw(long raw) {
            EnumSet<Flag> set = EnumSet.noneOf(Flag.class);
            for (Flag flag : Flag.values()) {
                if ((raw & flag.value) == 0L) continue;
                set.add(flag);
            }
            return set;
        }
    }
}

