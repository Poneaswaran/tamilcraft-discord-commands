/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.RoleIcon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.HierarchyException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.RoleManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.RoleAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IPermissionContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.RoleManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.PermissionUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.SortedSnowflakeCacheViewImpl;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.awt.Color;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Objects;

public class RoleImpl
implements Role {
    private final long id;
    private final JDAImpl api;
    private Guild guild;
    private RoleTagsImpl tags;
    private String name;
    private boolean managed;
    private boolean hoisted;
    private boolean mentionable;
    private long rawPermissions;
    private int color;
    private int rawPosition;
    private int frozenPosition = Integer.MIN_VALUE;
    private RoleIcon icon;

    public RoleImpl(long id, Guild guild) {
        this.id = id;
        this.api = (JDAImpl)guild.getJDA();
        this.guild = guild;
        this.tags = this.api.isCacheFlagSet(CacheFlag.ROLE_TAGS) ? new RoleTagsImpl() : null;
    }

    @Override
    public int getPosition() {
        if (this.frozenPosition > Integer.MIN_VALUE) {
            return this.frozenPosition;
        }
        Guild guild = this.getGuild();
        if (this.equals(guild.getPublicRole())) {
            return -1;
        }
        int i = guild.getRoles().size() - 2;
        for (Role r : guild.getRoles()) {
            if (this.equals(r)) {
                return i;
            }
            --i;
        }
        throw new IllegalStateException("Somehow when determining position we never found the role in the Guild's roles? wtf?");
    }

    @Override
    public int getPositionRaw() {
        return this.rawPosition;
    }

    @Override
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isManaged() {
        return this.managed;
    }

    @Override
    public boolean isHoisted() {
        return this.hoisted;
    }

    @Override
    public boolean isMentionable() {
        return this.mentionable;
    }

    @Override
    public long getPermissionsRaw() {
        return this.rawPermissions;
    }

    @Override
    @Nonnull
    public EnumSet<Permission> getPermissions() {
        return Permission.getPermissions(this.rawPermissions);
    }

    @Override
    @Nonnull
    public EnumSet<Permission> getPermissions(@Nonnull GuildChannel channel) {
        return Permission.getPermissions(PermissionUtil.getEffectivePermission((GuildChannel)channel.getPermissionContainer(), this));
    }

    @Override
    @Nonnull
    public EnumSet<Permission> getPermissionsExplicit() {
        return this.getPermissions();
    }

    @Override
    @Nonnull
    public EnumSet<Permission> getPermissionsExplicit(@Nonnull GuildChannel channel) {
        return Permission.getPermissions(PermissionUtil.getExplicitPermission((GuildChannel)channel.getPermissionContainer(), this));
    }

    @Override
    public Color getColor() {
        return this.color != 0x1FFFFFFF ? new Color(this.color) : null;
    }

    @Override
    public int getColorRaw() {
        return this.color;
    }

    @Override
    public boolean isPublicRole() {
        return this.equals(this.getGuild().getPublicRole());
    }

    @Override
    public boolean hasPermission(Permission ... permissions) {
        long effectivePerms = this.rawPermissions | this.getGuild().getPublicRole().getPermissionsRaw();
        for (Permission perm : permissions) {
            long rawValue = perm.getRawValue();
            if ((effectivePerms & rawValue) == rawValue) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean hasPermission(@Nonnull Collection<Permission> permissions) {
        Checks.notNull(permissions, "Permission Collection");
        return this.hasPermission(permissions.toArray(Permission.EMPTY_PERMISSIONS));
    }

    @Override
    public boolean hasPermission(@Nonnull GuildChannel channel, Permission ... permissions) {
        long effectivePerms = PermissionUtil.getEffectivePermission((GuildChannel)channel.getPermissionContainer(), this);
        for (Permission perm : permissions) {
            long rawValue = perm.getRawValue();
            if ((effectivePerms & rawValue) == rawValue) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean hasPermission(@Nonnull GuildChannel channel, @Nonnull Collection<Permission> permissions) {
        Checks.notNull(permissions, "Permission Collection");
        return this.hasPermission(channel, permissions.toArray(Permission.EMPTY_PERMISSIONS));
    }

    @Override
    public boolean canSync(@Nonnull IPermissionContainer targetChannel, @Nonnull IPermissionContainer syncSource) {
        boolean hasLocalAdmin;
        Checks.notNull(targetChannel, "Channel");
        Checks.notNull(syncSource, "Channel");
        Checks.check(targetChannel.getGuild().equals(this.getGuild()), "Channels must be from the same guild!");
        Checks.check(syncSource.getGuild().equals(this.getGuild()), "Channels must be from the same guild!");
        long rolePerms = PermissionUtil.getEffectivePermission((GuildChannel)targetChannel, this);
        if ((rolePerms & Permission.MANAGE_PERMISSIONS.getRawValue()) == 0L) {
            return false;
        }
        long channelPermissions = PermissionUtil.getExplicitPermission((GuildChannel)targetChannel, this, false);
        boolean bl = hasLocalAdmin = (rolePerms & Permission.ADMINISTRATOR.getRawValue() | channelPermissions & Permission.MANAGE_PERMISSIONS.getRawValue()) != 0L;
        if (hasLocalAdmin) {
            return true;
        }
        TLongObjectMap<PermissionOverride> existingOverrides = ((IPermissionContainerMixin)targetChannel).getPermissionOverrideMap();
        for (PermissionOverride override : syncSource.getPermissionOverrides()) {
            PermissionOverride existing = existingOverrides.get(override.getIdLong());
            long allow = override.getAllowedRaw();
            long deny = override.getDeniedRaw();
            if (existing != null) {
                allow ^= existing.getAllowedRaw();
                deny ^= existing.getDeniedRaw();
            }
            if (((allow | deny) & (rolePerms ^ 0xFFFFFFFFFFFFFFFFL)) == 0L) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean canSync(@Nonnull IPermissionContainer channel) {
        Checks.notNull(channel, "Channel");
        Checks.check(channel.getGuild().equals(this.getGuild()), "Channels must be from the same guild!");
        long rolePerms = PermissionUtil.getEffectivePermission((GuildChannel)channel, this);
        if ((rolePerms & Permission.MANAGE_PERMISSIONS.getRawValue()) == 0L) {
            return false;
        }
        long channelPermissions = PermissionUtil.getExplicitPermission((GuildChannel)channel, this, false);
        return (rolePerms & Permission.ADMINISTRATOR.getRawValue() | channelPermissions & Permission.MANAGE_PERMISSIONS.getRawValue()) != 0L;
    }

    @Override
    public boolean canInteract(@Nonnull Role role) {
        return PermissionUtil.canInteract(this, role);
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        Guild realGuild = this.api.getGuildById(this.guild.getIdLong());
        if (realGuild != null) {
            this.guild = realGuild;
        }
        return this.guild;
    }

    @Override
    @Nonnull
    public RoleAction createCopy(@Nonnull Guild guild) {
        Checks.notNull(guild, "Guild");
        return guild.createRole().setColor(this.color).setHoisted(this.hoisted).setMentionable(this.mentionable).setName(this.name).setPermissions(this.rawPermissions).setIcon(this.icon == null ? null : this.icon.getEmoji());
    }

    @Override
    @Nonnull
    public RoleManager getManager() {
        return new RoleManagerImpl(this);
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> delete() {
        Guild guild = this.getGuild();
        if (!guild.getSelfMember().hasPermission(Permission.MANAGE_ROLES)) {
            throw new InsufficientPermissionException(guild, Permission.MANAGE_ROLES);
        }
        if (!PermissionUtil.canInteract(guild.getSelfMember(), (Role)this)) {
            throw new HierarchyException("Can't delete role >= highest self-role");
        }
        if (this.managed) {
            throw new UnsupportedOperationException("Cannot delete a Role that is managed. ");
        }
        Route.CompiledRoute route = Route.Roles.DELETE_ROLE.compile(guild.getId(), this.getId());
        return new AuditableRestActionImpl<Void>(this.getJDA(), route);
    }

    @Override
    @Nonnull
    public JDA getJDA() {
        return this.api;
    }

    @Override
    @Nonnull
    public Role.RoleTags getTags() {
        return this.tags == null ? RoleTagsImpl.EMPTY : this.tags;
    }

    @Override
    @Nullable
    public RoleIcon getIcon() {
        return this.icon;
    }

    @Override
    @Nonnull
    public String getAsMention() {
        return this.isPublicRole() ? "@everyone" : "<@&" + this.getId() + '>';
    }

    @Override
    public long getIdLong() {
        return this.id;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        Role oRole = (Role)o;
        return this.getIdLong() == oRole.getIdLong();
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    public String toString() {
        return new EntityString(this).setName(this.getName()).toString();
    }

    @Override
    public int compareTo(@Nonnull Role r) {
        if (this == r) {
            return 0;
        }
        if (!(r instanceof RoleImpl)) {
            throw new IllegalArgumentException("Cannot compare different role implementations");
        }
        RoleImpl impl = (RoleImpl)r;
        if (this.guild.getIdLong() != impl.guild.getIdLong()) {
            throw new IllegalArgumentException("Cannot compare roles that aren't from the same guild!");
        }
        if (this.getPositionRaw() != r.getPositionRaw()) {
            return this.getPositionRaw() - r.getPositionRaw();
        }
        OffsetDateTime thisTime = this.getTimeCreated();
        OffsetDateTime rTime = r.getTimeCreated();
        return rTime.compareTo(thisTime);
    }

    public RoleImpl setName(String name) {
        this.name = name;
        return this;
    }

    public RoleImpl setColor(int color) {
        this.color = color;
        return this;
    }

    public RoleImpl setManaged(boolean managed) {
        this.managed = managed;
        return this;
    }

    public RoleImpl setHoisted(boolean hoisted) {
        this.hoisted = hoisted;
        return this;
    }

    public RoleImpl setMentionable(boolean mentionable) {
        this.mentionable = mentionable;
        return this;
    }

    public RoleImpl setRawPermissions(long rawPermissions) {
        this.rawPermissions = rawPermissions;
        return this;
    }

    public RoleImpl setRawPosition(int rawPosition) {
        SortedSnowflakeCacheViewImpl roleCache = (SortedSnowflakeCacheViewImpl)this.getGuild().getRoleCache();
        roleCache.clearCachedLists();
        this.rawPosition = rawPosition;
        return this;
    }

    public RoleImpl setTags(DataObject tags) {
        if (this.tags == null) {
            return this;
        }
        this.tags = new RoleTagsImpl(tags);
        return this;
    }

    public RoleImpl setIcon(RoleIcon icon) {
        this.icon = icon;
        return this;
    }

    public void freezePosition() {
        this.frozenPosition = this.getPosition();
    }

    public static class RoleTagsImpl
    implements Role.RoleTags {
        public static final Role.RoleTags EMPTY = new RoleTagsImpl();
        private final long botId;
        private final long integrationId;
        private final long subscriptionListingId;
        private final boolean premiumSubscriber;
        private final boolean availableForPurchase;
        private final boolean isGuildConnections;

        public RoleTagsImpl() {
            this.botId = 0L;
            this.integrationId = 0L;
            this.subscriptionListingId = 0L;
            this.premiumSubscriber = false;
            this.availableForPurchase = false;
            this.isGuildConnections = false;
        }

        public RoleTagsImpl(DataObject tags) {
            this.botId = tags.getUnsignedLong("bot_id", 0L);
            this.integrationId = tags.getUnsignedLong("integration_id", 0L);
            this.subscriptionListingId = tags.getUnsignedLong("subscription_listing_id", 0L);
            this.premiumSubscriber = tags.hasKey("premium_subscriber");
            this.availableForPurchase = tags.hasKey("available_for_purchase");
            this.isGuildConnections = tags.hasKey("guild_connections");
        }

        @Override
        public boolean isBot() {
            return this.botId != 0L;
        }

        @Override
        public long getBotIdLong() {
            return this.botId;
        }

        @Override
        public boolean isBoost() {
            return this.premiumSubscriber;
        }

        @Override
        public boolean isIntegration() {
            return this.integrationId != 0L;
        }

        @Override
        public long getIntegrationIdLong() {
            return this.integrationId;
        }

        @Override
        public long getSubscriptionIdLong() {
            return this.subscriptionListingId;
        }

        @Override
        public boolean isAvailableForPurchase() {
            return this.availableForPurchase;
        }

        @Override
        public boolean isLinkedRole() {
            return this.isGuildConnections;
        }

        public int hashCode() {
            return Objects.hash(this.botId, this.integrationId, this.premiumSubscriber, this.availableForPurchase, this.subscriptionListingId, this.isGuildConnections);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof RoleTagsImpl)) {
                return false;
            }
            RoleTagsImpl other = (RoleTagsImpl)obj;
            return this.botId == other.botId && this.integrationId == other.integrationId && this.premiumSubscriber == other.premiumSubscriber && this.availableForPurchase == other.availableForPurchase && this.subscriptionListingId == other.subscriptionListingId && this.isGuildConnections == other.isGuildConnections;
        }

        public String toString() {
            return new EntityString(this).addMetadata("bot", this.getBotId()).addMetadata("integration", this.getIntegrationId()).addMetadata("subscriptionListing", this.getSubscriptionId()).addMetadata("isBoost", this.isBoost()).addMetadata("isAvailableForPurchase", this.isAvailableForPurchase()).addMetadata("isGuildConnections", this.isLinkedRole()).toString();
        }
    }
}

