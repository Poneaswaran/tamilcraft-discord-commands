/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ApplicationInfo;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ApplicationTeam;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class ApplicationInfoImpl
implements ApplicationInfo {
    private final JDA api;
    private final boolean doesBotRequireCodeGrant;
    private final boolean isBotPublic;
    private final long id;
    private final long flags;
    private final String iconId;
    private final String description;
    private final String termsOfServiceUrl;
    private final String privacyPolicyUrl;
    private final String name;
    private final User owner;
    private final ApplicationTeam team;
    private final List<String> tags;
    private final List<String> redirectUris;
    private final String interactionsEndpointUrl;
    private final String roleConnectionsVerificationUrl;
    private final String customAuthUrl;
    private final long defaultAuthUrlPerms;
    private final List<String> defaultAuthUrlScopes;
    private String scopes = "bot";

    public ApplicationInfoImpl(JDA api, String description, boolean doesBotRequireCodeGrant, String iconId, long id, long flags, boolean isBotPublic, String name, String termsOfServiceUrl, String privacyPolicyUrl, User owner, ApplicationTeam team, List<String> tags, List<String> redirectUris, String interactionsEndpointUrl, String roleConnectionsVerificationUrl, String customAuthUrl, long defaultAuthUrlPerms, List<String> defaultAuthUrlScopes) {
        this.api = api;
        this.description = description;
        this.doesBotRequireCodeGrant = doesBotRequireCodeGrant;
        this.iconId = iconId;
        this.id = id;
        this.flags = flags;
        this.isBotPublic = isBotPublic;
        this.name = name;
        this.termsOfServiceUrl = termsOfServiceUrl;
        this.privacyPolicyUrl = privacyPolicyUrl;
        this.owner = owner;
        this.team = team;
        this.tags = Collections.unmodifiableList(tags);
        this.redirectUris = Collections.unmodifiableList(redirectUris);
        this.interactionsEndpointUrl = interactionsEndpointUrl;
        this.roleConnectionsVerificationUrl = roleConnectionsVerificationUrl;
        this.customAuthUrl = customAuthUrl;
        this.defaultAuthUrlPerms = defaultAuthUrlPerms;
        this.defaultAuthUrlScopes = Collections.unmodifiableList(defaultAuthUrlScopes);
    }

    @Override
    public final boolean doesBotRequireCodeGrant() {
        return this.doesBotRequireCodeGrant;
    }

    public boolean equals(Object obj) {
        return obj instanceof ApplicationInfoImpl && this.id == ((ApplicationInfoImpl)obj).id;
    }

    @Override
    @Nonnull
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getTermsOfServiceUrl() {
        return this.termsOfServiceUrl;
    }

    @Override
    public String getPrivacyPolicyUrl() {
        return this.privacyPolicyUrl;
    }

    @Override
    public String getIconId() {
        return this.iconId;
    }

    @Override
    public String getIconUrl() {
        return this.iconId == null ? null : "https://cdn.discordapp.com/app-icons/" + this.id + '/' + this.iconId + ".png";
    }

    @Override
    @Nonnull
    public ApplicationTeam getTeam() {
        return this.team;
    }

    @Override
    @Nonnull
    public ApplicationInfo setRequiredScopes(@Nonnull Collection<String> scopes) {
        Checks.noneNull(scopes, "Scopes");
        this.scopes = String.join((CharSequence)"+", scopes);
        if (!this.scopes.contains("bot")) {
            this.scopes = this.scopes.isEmpty() ? "bot" : this.scopes + "+bot";
        }
        return this;
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    @Override
    @Nonnull
    public String getInviteUrl(String guildId, Collection<Permission> permissions) {
        StringBuilder builder = new StringBuilder("https://discord.com/oauth2/authorize?client_id=");
        builder.append(this.getId());
        builder.append("&scope=").append(this.scopes);
        if (permissions != null && !permissions.isEmpty()) {
            builder.append("&permissions=");
            builder.append(Permission.getRaw(permissions));
        }
        if (guildId != null) {
            builder.append("&guild_id=");
            builder.append(guildId);
        }
        return builder.toString();
    }

    @Override
    @Nonnull
    public JDA getJDA() {
        return this.api;
    }

    @Override
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Override
    @Nonnull
    public User getOwner() {
        return this.owner;
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    @Override
    public final boolean isBotPublic() {
        return this.isBotPublic;
    }

    @Override
    @Nonnull
    public List<String> getTags() {
        return this.tags;
    }

    @Override
    @Nonnull
    public List<String> getRedirectUris() {
        return this.redirectUris;
    }

    @Override
    @Nullable
    public String getInteractionsEndpointUrl() {
        return this.interactionsEndpointUrl;
    }

    @Override
    @Nullable
    public String getRoleConnectionsVerificationUrl() {
        return this.roleConnectionsVerificationUrl;
    }

    @Override
    @Nullable
    public String getCustomAuthorizationUrl() {
        return this.customAuthUrl;
    }

    @Override
    @Nonnull
    public EnumSet<Permission> getPermissions() {
        return Permission.getPermissions(this.defaultAuthUrlPerms);
    }

    @Override
    public long getPermissionsRaw() {
        return this.defaultAuthUrlPerms;
    }

    @Override
    public long getFlagsRaw() {
        return this.flags;
    }

    @Override
    @Nonnull
    public List<String> getScopes() {
        return this.defaultAuthUrlScopes;
    }

    public String toString() {
        return new EntityString(this).toString();
    }
}

