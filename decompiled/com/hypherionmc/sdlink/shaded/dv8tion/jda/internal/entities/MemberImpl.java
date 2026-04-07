/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.OnlineStatus;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Activity;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ClientType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.GuildVoiceState;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.attribute.IPermissionContainer;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.DefaultGuildChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.GuildVoiceStateImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.MemberPresenceImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.channel.mixin.attribute.IPermissionContainerMixin;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.PermissionUtil;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.awt.Color;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class MemberImpl
implements Member {
    private final JDAImpl api;
    private final Set<Role> roles = ConcurrentHashMap.newKeySet();
    private final GuildVoiceState voiceState;
    private GuildImpl guild;
    private User user;
    private String nickname;
    private String avatarId;
    private long joinDate;
    private long boostDate;
    private long timeOutEnd;
    private boolean pending = false;
    private int flags;

    public MemberImpl(GuildImpl guild, User user) {
        this.api = (JDAImpl)user.getJDA();
        this.guild = guild;
        this.user = user;
        this.joinDate = 0L;
        boolean cacheState = this.api.isCacheFlagSet(CacheFlag.VOICE_STATE) || user.equals(this.api.getSelfUser());
        this.voiceState = cacheState ? new GuildVoiceStateImpl(this) : null;
    }

    public MemberPresenceImpl getPresence() {
        CacheView.SimpleCacheView<MemberPresenceImpl> presences = this.guild.getPresenceView();
        return presences == null ? null : (MemberPresenceImpl)presences.get(this.getIdLong());
    }

    @Override
    @Nonnull
    public User getUser() {
        User realUser = this.getJDA().getUserById(this.user.getIdLong());
        if (realUser != null) {
            this.user = realUser;
        }
        return this.user;
    }

    @Override
    @Nonnull
    public GuildImpl getGuild() {
        GuildImpl realGuild = (GuildImpl)this.api.getGuildById(this.guild.getIdLong());
        if (realGuild != null) {
            this.guild = realGuild;
        }
        return this.guild;
    }

    @Override
    @Nonnull
    public JDA getJDA() {
        return this.api;
    }

    @Override
    @Nonnull
    public OffsetDateTime getTimeJoined() {
        if (this.hasTimeJoined()) {
            return Helpers.toOffset(this.joinDate);
        }
        return this.getGuild().getTimeCreated();
    }

    @Override
    public boolean hasTimeJoined() {
        return this.joinDate != 0L;
    }

    @Override
    @Nullable
    public OffsetDateTime getTimeBoosted() {
        return this.isBoosting() ? Helpers.toOffset(this.boostDate) : null;
    }

    @Override
    public boolean isBoosting() {
        return this.boostDate != 0L;
    }

    @Override
    @Nullable
    public OffsetDateTime getTimeOutEnd() {
        return this.timeOutEnd != 0L ? Helpers.toOffset(this.timeOutEnd) : null;
    }

    @Override
    public GuildVoiceState getVoiceState() {
        return this.voiceState;
    }

    @Override
    @Nonnull
    public List<Activity> getActivities() {
        MemberPresenceImpl presence = this.getPresence();
        return presence == null ? Collections.emptyList() : presence.getActivities();
    }

    @Override
    @Nonnull
    public OnlineStatus getOnlineStatus() {
        MemberPresenceImpl presence = this.getPresence();
        return presence == null ? OnlineStatus.OFFLINE : presence.getOnlineStatus();
    }

    @Override
    @Nonnull
    public OnlineStatus getOnlineStatus(@Nonnull ClientType type) {
        Checks.notNull((Object)type, "Type");
        MemberPresenceImpl presence = this.getPresence();
        if (presence == null) {
            return OnlineStatus.OFFLINE;
        }
        OnlineStatus status = presence.getClientStatus().get((Object)type);
        return status == null ? OnlineStatus.OFFLINE : status;
    }

    @Override
    @Nonnull
    public EnumSet<ClientType> getActiveClients() {
        MemberPresenceImpl presence = this.getPresence();
        return presence == null ? EnumSet.noneOf(ClientType.class) : Helpers.copyEnumSet(ClientType.class, presence.getClientStatus().keySet());
    }

    @Override
    public String getNickname() {
        return this.nickname;
    }

    @Override
    public String getAvatarId() {
        return this.avatarId;
    }

    @Override
    @Nonnull
    public String getEffectiveName() {
        return this.nickname != null ? this.nickname : this.getUser().getEffectiveName();
    }

    @Override
    @Nonnull
    public List<Role> getRoles() {
        ArrayList<Role> roleList = new ArrayList<Role>(this.roles);
        roleList.sort(Comparator.reverseOrder());
        return Collections.unmodifiableList(roleList);
    }

    @Override
    public Color getColor() {
        int raw = this.getColorRaw();
        return raw != 0x1FFFFFFF ? new Color(raw) : null;
    }

    @Override
    public int getColorRaw() {
        for (Role r : this.getRoles()) {
            int colorRaw = r.getColorRaw();
            if (colorRaw == 0x1FFFFFFF) continue;
            return colorRaw;
        }
        return 0x1FFFFFFF;
    }

    @Override
    public int getFlagsRaw() {
        return this.flags;
    }

    @Override
    @Nonnull
    public EnumSet<Permission> getPermissions() {
        return Permission.getPermissions(PermissionUtil.getEffectivePermission(this));
    }

    @Override
    @Nonnull
    public EnumSet<Permission> getPermissions(@Nonnull GuildChannel channel) {
        Checks.notNull(channel, "Channel");
        if (!this.getGuild().equals(channel.getGuild())) {
            throw new IllegalArgumentException("Provided channel is not in the same guild as this member!");
        }
        return Permission.getPermissions(PermissionUtil.getEffectivePermission((GuildChannel)channel.getPermissionContainer(), this));
    }

    @Override
    @Nonnull
    public EnumSet<Permission> getPermissionsExplicit() {
        return Permission.getPermissions(PermissionUtil.getExplicitPermission(this));
    }

    @Override
    @Nonnull
    public EnumSet<Permission> getPermissionsExplicit(@Nonnull GuildChannel channel) {
        return Permission.getPermissions(PermissionUtil.getExplicitPermission((GuildChannel)channel.getPermissionContainer(), this));
    }

    @Override
    public boolean hasPermission(Permission ... permissions) {
        return PermissionUtil.checkPermission(this, permissions);
    }

    @Override
    public boolean hasPermission(@Nonnull Collection<Permission> permissions) {
        Checks.notNull(permissions, "Permission Collection");
        return this.hasPermission(permissions.toArray(Permission.EMPTY_PERMISSIONS));
    }

    @Override
    public boolean hasPermission(@Nonnull GuildChannel channel, Permission ... permissions) {
        return PermissionUtil.checkPermission(channel.getPermissionContainer(), this, permissions);
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
        long userPerms = PermissionUtil.getEffectivePermission((GuildChannel)targetChannel, this);
        if ((userPerms & Permission.MANAGE_PERMISSIONS.getRawValue()) == 0L) {
            return false;
        }
        long channelPermissions = PermissionUtil.getExplicitPermission((GuildChannel)targetChannel, this, false);
        boolean bl = hasLocalAdmin = (userPerms & Permission.ADMINISTRATOR.getRawValue() | channelPermissions & Permission.MANAGE_PERMISSIONS.getRawValue()) != 0L;
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
            if (((allow | deny) & (userPerms ^ 0xFFFFFFFFFFFFFFFFL)) == 0L) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean canSync(@Nonnull IPermissionContainer channel) {
        Checks.notNull(channel, "Channel");
        Checks.check(channel.getGuild().equals(this.getGuild()), "Channels must be from the same guild!");
        long userPerms = PermissionUtil.getEffectivePermission((GuildChannel)channel, this);
        if ((userPerms & Permission.MANAGE_PERMISSIONS.getRawValue()) == 0L) {
            return false;
        }
        long channelPermissions = PermissionUtil.getExplicitPermission((GuildChannel)channel, this, false);
        return (userPerms & Permission.ADMINISTRATOR.getRawValue() | channelPermissions & Permission.MANAGE_PERMISSIONS.getRawValue()) != 0L;
    }

    @Override
    public boolean canInteract(@Nonnull Member member) {
        return PermissionUtil.canInteract((Member)this, member);
    }

    @Override
    public boolean canInteract(@Nonnull Role role) {
        return PermissionUtil.canInteract((Member)this, role);
    }

    @Override
    public boolean canInteract(@Nonnull RichCustomEmoji emoji) {
        return PermissionUtil.canInteract((Member)this, emoji);
    }

    @Override
    public boolean isOwner() {
        return this.user.getIdLong() == this.getGuild().getOwnerIdLong();
    }

    @Override
    public boolean isPending() {
        return this.pending;
    }

    @Override
    public long getIdLong() {
        return this.user.getIdLong();
    }

    @Override
    @Nonnull
    public String getAsMention() {
        return "<@" + this.user.getId() + '>';
    }

    @Override
    @Nullable
    public DefaultGuildChannelUnion getDefaultChannel() {
        return Stream.concat(this.getGuild().getTextChannelCache().stream(), this.getGuild().getNewsChannelCache().stream()).filter(c -> this.hasPermission((GuildChannel)c, Permission.VIEW_CHANNEL)).min(Comparator.naturalOrder()).orElse(null);
    }

    @Override
    @Nonnull
    public String getDefaultAvatarId() {
        return this.user.getDefaultAvatarId();
    }

    public MemberImpl setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public MemberImpl setAvatarId(String avatarId) {
        this.avatarId = avatarId;
        return this;
    }

    public MemberImpl setJoinDate(long joinDate) {
        this.joinDate = joinDate;
        return this;
    }

    public MemberImpl setBoostDate(long boostDate) {
        this.boostDate = boostDate;
        return this;
    }

    public MemberImpl setTimeOutEnd(long time) {
        this.timeOutEnd = time;
        return this;
    }

    public MemberImpl setPending(boolean pending) {
        this.pending = pending;
        return this;
    }

    public MemberImpl setFlags(int flags) {
        this.flags = flags;
        return this;
    }

    public Set<Role> getRoleSet() {
        return this.roles;
    }

    public long getBoostDateRaw() {
        return this.boostDate;
    }

    public long getTimeOutEndRaw() {
        return this.timeOutEnd;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MemberImpl)) {
            return false;
        }
        MemberImpl oMember = (MemberImpl)o;
        return oMember.user.getIdLong() == this.user.getIdLong() && oMember.guild.getIdLong() == this.guild.getIdLong();
    }

    public int hashCode() {
        return Objects.hash(this.guild.getIdLong(), this.user.getIdLong());
    }

    public String toString() {
        return new EntityString(this).setName(this.getEffectiveName()).addMetadata("user", this.getUser()).addMetadata("guild", this.getGuild()).toString();
    }
}

