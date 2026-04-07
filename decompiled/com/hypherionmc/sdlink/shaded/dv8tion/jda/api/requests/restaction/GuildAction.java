/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Icon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.SerializableData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.PermOverrideData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.awt.Color;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public interface GuildAction
extends RestAction<Void> {
    @Nonnull
    @CheckReturnValue
    public GuildAction setCheck(@Nullable BooleanSupplier var1);

    @Nonnull
    @CheckReturnValue
    public GuildAction timeout(long var1, @Nonnull TimeUnit var3);

    @Nonnull
    @CheckReturnValue
    public GuildAction deadline(long var1);

    @Nonnull
    @CheckReturnValue
    public GuildAction setIcon(@Nullable Icon var1);

    @Nonnull
    @CheckReturnValue
    public GuildAction setName(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    public GuildAction setVerificationLevel(@Nullable Guild.VerificationLevel var1);

    @Nonnull
    @CheckReturnValue
    public GuildAction setNotificationLevel(@Nullable Guild.NotificationLevel var1);

    @Nonnull
    @CheckReturnValue
    public GuildAction setExplicitContentLevel(@Nullable Guild.ExplicitContentLevel var1);

    @Nonnull
    @CheckReturnValue
    public GuildAction addChannel(@Nonnull ChannelData var1);

    @Nonnull
    @CheckReturnValue
    public ChannelData getChannel(int var1);

    @Nonnull
    @CheckReturnValue
    public ChannelData removeChannel(int var1);

    @Nonnull
    @CheckReturnValue
    public GuildAction removeChannel(@Nonnull ChannelData var1);

    @Nonnull
    @CheckReturnValue
    public ChannelData newChannel(@Nonnull ChannelType var1, @Nonnull String var2);

    @Nonnull
    @CheckReturnValue
    public RoleData getPublicRole();

    @Nonnull
    @CheckReturnValue
    public RoleData getRole(int var1);

    @Nonnull
    @CheckReturnValue
    public RoleData newRole();

    public static class ChannelData
    implements SerializableData {
        protected final ChannelType type;
        protected final String name;
        protected final Set<PermOverrideData> overrides = new HashSet<PermOverrideData>();
        protected Integer position;
        protected String topic;
        protected Boolean nsfw;
        protected Integer bitrate;
        protected Integer userlimit;

        public ChannelData(ChannelType type, String name) {
            Checks.notBlank(name, "Name");
            Checks.check(type == ChannelType.TEXT || type == ChannelType.VOICE || type == ChannelType.STAGE, "Can only create channels of type TEXT, STAGE, or VOICE in GuildAction!");
            Checks.check(name.length() >= 2 && name.length() <= 100, "Channel name has to be between 2-100 characters long!");
            this.type = type;
            this.name = name;
        }

        @Nonnull
        public ChannelData setTopic(@Nullable String topic) {
            if (topic != null && topic.length() > 1024) {
                throw new IllegalArgumentException("Channel Topic must not be greater than 1024 in length!");
            }
            this.topic = topic;
            return this;
        }

        @Nonnull
        public ChannelData setNSFW(@Nullable Boolean nsfw) {
            this.nsfw = nsfw;
            return this;
        }

        @Nonnull
        public ChannelData setBitrate(@Nullable Integer bitrate) {
            if (bitrate != null) {
                Checks.check(bitrate >= 8000, "Bitrate must be greater than 8000.");
                Checks.check(bitrate <= 96000, "Bitrate must be less than 96000.");
            }
            this.bitrate = bitrate;
            return this;
        }

        @Nonnull
        public ChannelData setUserlimit(@Nullable Integer userlimit) {
            if (userlimit != null && (userlimit < 0 || userlimit > 99)) {
                throw new IllegalArgumentException("Userlimit must be between 0-99!");
            }
            this.userlimit = userlimit;
            return this;
        }

        @Nonnull
        public ChannelData setPosition(@Nullable Integer position) {
            this.position = position;
            return this;
        }

        @Nonnull
        public ChannelData addPermissionOverride(@Nonnull RoleData role, long allow, long deny) {
            Checks.notNull(role, "Role");
            this.overrides.add(new PermOverrideData(0, role.id, allow, deny));
            return this;
        }

        @Nonnull
        public ChannelData addPermissionOverride(@Nonnull RoleData role, @Nullable Collection<Permission> allow, @Nullable Collection<Permission> deny) {
            long allowRaw = 0L;
            long denyRaw = 0L;
            if (allow != null) {
                Checks.noneNull(allow, "Granted Permissions");
                allowRaw = Permission.getRaw(allow);
            }
            if (deny != null) {
                Checks.noneNull(deny, "Denied Permissions");
                denyRaw = Permission.getRaw(deny);
            }
            return this.addPermissionOverride(role, allowRaw, denyRaw);
        }

        @Override
        @Nonnull
        public DataObject toData() {
            DataObject o = DataObject.empty();
            o.put("name", this.name);
            o.put("type", this.type.getId());
            if (this.topic != null) {
                o.put("topic", this.topic);
            }
            if (this.nsfw != null) {
                o.put("nsfw", this.nsfw);
            }
            if (this.bitrate != null) {
                o.put("bitrate", this.bitrate);
            }
            if (this.userlimit != null) {
                o.put("user_limit", this.userlimit);
            }
            if (this.position != null) {
                o.put("position", this.position);
            }
            if (!this.overrides.isEmpty()) {
                o.put("permission_overwrites", this.overrides);
            }
            return o;
        }
    }

    public static class RoleData
    implements SerializableData {
        protected final long id;
        protected final boolean isPublicRole;
        protected Long permissions;
        protected String name;
        protected Integer color;
        protected Integer position;
        protected Boolean mentionable;
        protected Boolean hoisted;

        public RoleData(long id) {
            this.id = id;
            this.isPublicRole = id == 0L;
        }

        @Nonnull
        public RoleData setPermissionsRaw(@Nullable Long rawPermissions) {
            this.permissions = rawPermissions;
            return this;
        }

        @Nonnull
        public RoleData addPermissions(Permission ... permissions) {
            Checks.notNull(permissions, "Permissions");
            for (Permission perm : permissions) {
                Checks.notNull((Object)perm, "Permissions");
            }
            if (this.permissions == null) {
                this.permissions = 0L;
            }
            this.permissions = this.permissions | Permission.getRaw(permissions);
            return this;
        }

        @Nonnull
        public RoleData addPermissions(@Nonnull Collection<Permission> permissions) {
            Checks.noneNull(permissions, "Permissions");
            if (this.permissions == null) {
                this.permissions = 0L;
            }
            this.permissions = this.permissions | Permission.getRaw(permissions);
            return this;
        }

        @Nonnull
        public RoleData setName(@Nullable String name) {
            this.checkPublic("name");
            this.name = name;
            return this;
        }

        @Nonnull
        public RoleData setColor(@Nullable Color color) {
            this.checkPublic("color");
            this.color = color == null ? null : Integer.valueOf(color.getRGB());
            return this;
        }

        @Nonnull
        public RoleData setColor(@Nullable Integer color) {
            this.checkPublic("color");
            this.color = color;
            return this;
        }

        @Nonnull
        public RoleData setPosition(@Nullable Integer position) {
            this.checkPublic("position");
            this.position = position;
            return this;
        }

        @Nonnull
        public RoleData setMentionable(@Nullable Boolean mentionable) {
            this.checkPublic("mentionable");
            this.mentionable = mentionable;
            return this;
        }

        @Nonnull
        public RoleData setHoisted(@Nullable Boolean hoisted) {
            this.checkPublic("hoisted");
            this.hoisted = hoisted;
            return this;
        }

        @Override
        @Nonnull
        public DataObject toData() {
            DataObject o = DataObject.empty().put("id", Long.toUnsignedString(this.id));
            if (this.permissions != null) {
                o.put("permissions", this.permissions);
            }
            if (this.position != null) {
                o.put("position", this.position);
            }
            if (this.name != null) {
                o.put("name", this.name);
            }
            if (this.color != null) {
                o.put("color", this.color & 0xFFFFFF);
            }
            if (this.mentionable != null) {
                o.put("mentionable", this.mentionable);
            }
            if (this.hoisted != null) {
                o.put("hoist", this.hoisted);
            }
            return o;
        }

        protected void checkPublic(String comment) {
            if (this.isPublicRole) {
                throw new IllegalStateException("Cannot modify " + comment + " for the public role!");
            }
        }
    }
}

