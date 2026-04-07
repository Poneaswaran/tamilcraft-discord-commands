/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.GuildWelcomeScreen;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Invite;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.EntityBuilder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.CompletedRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

public class InviteImpl
implements Invite {
    private final JDAImpl api;
    private final Invite.Channel channel;
    private final String code;
    private final boolean expanded;
    private final Invite.Guild guild;
    private final Invite.Group group;
    private final Invite.InviteTarget target;
    private final User inviter;
    private final int maxAge;
    private final int maxUses;
    private final boolean temporary;
    private final OffsetDateTime timeCreated;
    private final int uses;
    private final Invite.InviteType type;

    public InviteImpl(JDAImpl api, String code, boolean expanded, User inviter, int maxAge, int maxUses, boolean temporary, OffsetDateTime timeCreated, int uses, Invite.Channel channel, Invite.Guild guild, Invite.Group group, Invite.InviteTarget target, Invite.InviteType type) {
        this.api = api;
        this.code = code;
        this.expanded = expanded;
        this.inviter = inviter;
        this.maxAge = maxAge;
        this.maxUses = maxUses;
        this.temporary = temporary;
        this.timeCreated = timeCreated;
        this.uses = uses;
        this.channel = channel;
        this.guild = guild;
        this.group = group;
        this.target = target;
        this.type = type;
    }

    public static RestAction<Invite> resolve(JDA api, String code, boolean withCounts) {
        Checks.notNull(code, "code");
        Checks.notNull(api, "api");
        Route.CompiledRoute route = Route.Invites.GET_INVITE.compile(code);
        if (withCounts) {
            route = route.withQueryParams("with_counts", "true");
        }
        JDAImpl jda = (JDAImpl)api;
        return new RestActionImpl<Invite>(api, route, (response, request) -> jda.getEntityBuilder().createInvite(response.getObject()));
    }

    @Override
    @Nonnull
    public AuditableRestAction<Void> delete() {
        Route.CompiledRoute route = Route.Invites.DELETE_INVITE.compile(this.code);
        return new AuditableRestActionImpl<Void>(this.api, route);
    }

    @Override
    @Nonnull
    public RestAction<Invite> expand() {
        Route.CompiledRoute route;
        if (this.expanded) {
            return new CompletedRestAction<Invite>((JDA)this.getJDA(), this);
        }
        if (this.type != Invite.InviteType.GUILD) {
            throw new IllegalStateException("Only guild invites can be expanded");
        }
        Guild guild = this.api.getGuildById(this.guild.getIdLong());
        if (guild == null) {
            throw new UnsupportedOperationException("You're not in the guild this invite points to");
        }
        Member member = guild.getSelfMember();
        GuildChannel channel = guild.getChannelById(GuildChannel.class, this.channel.getIdLong());
        if (channel == null) {
            throw new UnsupportedOperationException("Cannot expand invite without known channel. Channel ID: " + this.channel.getId());
        }
        if (member.hasPermission(channel, Permission.MANAGE_CHANNEL)) {
            route = Route.Invites.GET_CHANNEL_INVITES.compile(channel.getId());
        } else if (member.hasPermission(Permission.MANAGE_SERVER)) {
            route = Route.Invites.GET_GUILD_INVITES.compile(guild.getId());
        } else {
            throw new InsufficientPermissionException(channel, Permission.MANAGE_CHANNEL, "You don't have the permission to view the full invite info");
        }
        return new RestActionImpl<Invite>((JDA)this.api, route, (response, request) -> {
            EntityBuilder entityBuilder = this.api.getEntityBuilder();
            DataArray array = response.getArray();
            for (int i = 0; i < array.length(); ++i) {
                DataObject object = array.getObject(i);
                if (!this.code.equals(object.getString("code"))) continue;
                return entityBuilder.createInvite(object);
            }
            throw new IllegalStateException("Missing the invite in the channel/guild invite list");
        });
    }

    @Override
    @Nonnull
    public Invite.InviteType getType() {
        return this.type;
    }

    @Override
    @Nonnull
    public Invite.TargetType getTargetType() {
        return this.target == null ? Invite.TargetType.NONE : this.target.getType();
    }

    @Override
    public Invite.Channel getChannel() {
        return this.channel;
    }

    @Override
    @Nonnull
    public String getCode() {
        return this.code;
    }

    @Override
    public Invite.Guild getGuild() {
        return this.guild;
    }

    @Override
    public Invite.Group getGroup() {
        return this.group;
    }

    @Override
    @Nullable
    public Invite.InviteTarget getTarget() {
        return this.target;
    }

    @Override
    public User getInviter() {
        return this.inviter;
    }

    @Override
    @Nonnull
    public JDAImpl getJDA() {
        return this.api;
    }

    @Override
    public int getMaxAge() {
        if (!this.expanded) {
            throw new IllegalStateException("Only valid for expanded invites");
        }
        return this.maxAge;
    }

    @Override
    public int getMaxUses() {
        if (!this.expanded) {
            throw new IllegalStateException("Only valid for expanded invites");
        }
        return this.maxUses;
    }

    @Override
    @Nonnull
    public OffsetDateTime getTimeCreated() {
        if (!this.expanded) {
            throw new IllegalStateException("Only valid for expanded invites");
        }
        return this.timeCreated;
    }

    @Override
    public int getUses() {
        if (!this.expanded) {
            throw new IllegalStateException("Only valid for expanded invites");
        }
        return this.uses;
    }

    @Override
    public boolean isExpanded() {
        return this.expanded;
    }

    @Override
    public boolean isTemporary() {
        if (!this.expanded) {
            throw new IllegalStateException("Only valid for expanded invites");
        }
        return this.temporary;
    }

    public int hashCode() {
        return this.code.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof InviteImpl)) {
            return false;
        }
        InviteImpl impl = (InviteImpl)obj;
        return impl.code.equals(this.code);
    }

    public String toString() {
        return new EntityString(this).addMetadata("code", this.code).toString();
    }

    public static class EmbeddedApplicationImpl
    implements Invite.EmbeddedApplication {
        private final String iconId;
        private final String name;
        private final String description;
        private final String summary;
        private final long id;
        private final int maxParticipants;

        public EmbeddedApplicationImpl(String iconId, String name, String description, String summary, long id, int maxParticipants) {
            this.iconId = iconId;
            this.name = name;
            this.description = description;
            this.summary = summary;
            this.id = id;
            this.maxParticipants = maxParticipants;
        }

        @Override
        public long getIdLong() {
            return this.id;
        }

        @Override
        @Nonnull
        public String getName() {
            return this.name;
        }

        @Override
        @Nonnull
        public String getDescription() {
            return this.description;
        }

        @Override
        @Nullable
        public String getSummary() {
            return this.summary;
        }

        @Override
        @Nullable
        public String getIconId() {
            return this.iconId;
        }

        @Override
        @Nullable
        public String getIconUrl() {
            return this.iconId == null ? null : "https://cdn.discordapp.com/app-icons/" + this.id + '/' + this.iconId + ".png";
        }

        @Override
        public int getMaxParticipants() {
            return this.maxParticipants;
        }

        public String toString() {
            return new EntityString(this).setName(this.name).toString();
        }
    }

    public static class InviteTargetImpl
    implements Invite.InviteTarget {
        private final Invite.TargetType type;
        private final Invite.EmbeddedApplication targetApplication;
        private final User targetUser;

        public InviteTargetImpl(Invite.TargetType type, Invite.EmbeddedApplication targetApplication, User targetUser) {
            this.type = type;
            this.targetApplication = targetApplication;
            this.targetUser = targetUser;
        }

        @Override
        @Nonnull
        public Invite.TargetType getType() {
            return this.type;
        }

        @Override
        @Nonnull
        public String getId() {
            return this.getTargetEntity().getId();
        }

        @Override
        public long getIdLong() {
            return this.getTargetEntity().getIdLong();
        }

        @Override
        @Nullable
        public User getUser() {
            return this.targetUser;
        }

        @Override
        @Nullable
        public Invite.EmbeddedApplication getApplication() {
            return this.targetApplication;
        }

        public String toString() {
            return new EntityString(this).setType(this.getType()).addMetadata("target", this.getTargetEntity()).toString();
        }

        @Nonnull
        private ISnowflake getTargetEntity() {
            if (this.targetUser != null) {
                return this.targetUser;
            }
            if (this.targetApplication != null) {
                return this.targetApplication;
            }
            throw new IllegalStateException("No target entity");
        }
    }

    public static class GroupImpl
    implements Invite.Group {
        private final String iconId;
        private final String name;
        private final long id;
        private final List<String> users;

        public GroupImpl(String iconId, String name, long id, List<String> users) {
            this.iconId = iconId;
            this.name = name;
            this.id = id;
            this.users = users;
        }

        @Override
        public String getIconId() {
            return this.iconId;
        }

        @Override
        public String getIconUrl() {
            return this.iconId == null ? null : "https://cdn.discordapp.com/channel-icons/" + this.id + "/" + this.iconId + ".png";
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public long getIdLong() {
            return this.id;
        }

        @Override
        public List<String> getUsers() {
            return this.users;
        }

        public String toString() {
            return new EntityString(this).setName(this.name).toString();
        }
    }

    public static class GuildImpl
    implements Invite.Guild {
        private final String iconId;
        private final String name;
        private final String splashId;
        private final int presenceCount;
        private final int memberCount;
        private final long id;
        private final Guild.VerificationLevel verificationLevel;
        private final Set<String> features;
        private final GuildWelcomeScreen welcomeScreen;

        public GuildImpl(long id, String iconId, String name, String splashId, Guild.VerificationLevel verificationLevel, int presenceCount, int memberCount, Set<String> features, GuildWelcomeScreen welcomeScreen) {
            this.id = id;
            this.iconId = iconId;
            this.name = name;
            this.splashId = splashId;
            this.verificationLevel = verificationLevel;
            this.presenceCount = presenceCount;
            this.memberCount = memberCount;
            this.features = features;
            this.welcomeScreen = welcomeScreen;
        }

        public GuildImpl(Guild guild) {
            this(guild.getIdLong(), guild.getIconId(), guild.getName(), guild.getSplashId(), guild.getVerificationLevel(), -1, -1, guild.getFeatures(), null);
        }

        @Override
        public String getIconId() {
            return this.iconId;
        }

        @Override
        public String getIconUrl() {
            return this.iconId == null ? null : "https://cdn.discordapp.com/icons/" + this.id + "/" + this.iconId + ".png";
        }

        @Override
        public long getIdLong() {
            return this.id;
        }

        @Override
        @Nonnull
        public String getName() {
            return this.name;
        }

        @Override
        public String getSplashId() {
            return this.splashId;
        }

        @Override
        public String getSplashUrl() {
            return this.splashId == null ? null : "https://cdn.discordapp.com/splashes/" + this.id + "/" + this.splashId + ".png";
        }

        @Override
        @Nonnull
        public Guild.VerificationLevel getVerificationLevel() {
            return this.verificationLevel;
        }

        @Override
        public int getOnlineCount() {
            return this.presenceCount;
        }

        @Override
        public int getMemberCount() {
            return this.memberCount;
        }

        @Override
        @Nonnull
        public Set<String> getFeatures() {
            return this.features;
        }

        @Override
        @Nullable
        public GuildWelcomeScreen getWelcomeScreen() {
            return this.welcomeScreen;
        }

        public String toString() {
            return new EntityString(this).setName(this.name).toString();
        }
    }

    public static class ChannelImpl
    implements Invite.Channel {
        private final long id;
        private final String name;
        private final ChannelType type;

        public ChannelImpl(long id, String name, ChannelType type) {
            this.id = id;
            this.name = name;
            this.type = type;
        }

        public ChannelImpl(GuildChannel channel) {
            this(channel.getIdLong(), channel.getName(), channel.getType());
        }

        @Override
        public long getIdLong() {
            return this.id;
        }

        @Override
        @Nonnull
        public String getName() {
            return this.name;
        }

        @Override
        @Nonnull
        public ChannelType getType() {
            return this.type;
        }

        public String toString() {
            return new EntityString(this).setType(this.getType()).setName(this.name).toString();
        }
    }
}

