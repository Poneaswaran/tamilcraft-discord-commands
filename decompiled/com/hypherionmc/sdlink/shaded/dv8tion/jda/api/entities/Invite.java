/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.GuildWelcomeScreen;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ImageProxy;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.InviteImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

public interface Invite {
    @Nonnull
    @CheckReturnValue
    public static RestAction<Invite> resolve(@Nonnull JDA api, @Nonnull String code) {
        return Invite.resolve(api, code, false);
    }

    @Nonnull
    @CheckReturnValue
    public static RestAction<Invite> resolve(@Nonnull JDA api, @Nonnull String code, boolean withCounts) {
        return InviteImpl.resolve(api, code, withCounts);
    }

    @Nonnull
    @CheckReturnValue
    public AuditableRestAction<Void> delete();

    @Nonnull
    @CheckReturnValue
    public RestAction<Invite> expand();

    @Nonnull
    public InviteType getType();

    @Nonnull
    public TargetType getTargetType();

    @Nullable
    public Channel getChannel();

    @Nullable
    public Group getGroup();

    @Nullable
    public InviteTarget getTarget();

    @Nonnull
    public String getCode();

    @Nonnull
    default public String getUrl() {
        return "https://discord.gg/" + this.getCode();
    }

    @Nullable
    public Guild getGuild();

    @Nullable
    public User getInviter();

    @Nonnull
    public JDA getJDA();

    public int getMaxAge();

    public int getMaxUses();

    @Nonnull
    public OffsetDateTime getTimeCreated();

    public int getUses();

    public boolean isExpanded();

    public boolean isTemporary();

    public static enum TargetType {
        NONE(0),
        STREAM(1),
        EMBEDDED_APPLICATION(2),
        ROLE_SUBSCRIPTIONS_PURCHASE(3),
        UNKNOWN(-1);

        private final int id;

        private TargetType(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        @Nonnull
        public static TargetType fromId(int id) {
            for (TargetType type : TargetType.values()) {
                if (type.id != id) continue;
                return type;
            }
            return UNKNOWN;
        }
    }

    public static enum InviteType {
        GUILD,
        GROUP,
        UNKNOWN;

    }

    public static interface EmbeddedApplication
    extends ISnowflake {
        @Nonnull
        public String getName();

        @Nonnull
        public String getDescription();

        @Nullable
        public String getSummary();

        @Nullable
        public String getIconId();

        @Nullable
        public String getIconUrl();

        @Nullable
        default public ImageProxy getIcon() {
            String iconUrl = this.getIconUrl();
            return iconUrl == null ? null : new ImageProxy(iconUrl);
        }

        public int getMaxParticipants();
    }

    public static interface InviteTarget {
        @Nonnull
        public TargetType getType();

        @Nonnull
        public String getId();

        public long getIdLong();

        @Nullable
        public User getUser();

        @Nullable
        public EmbeddedApplication getApplication();
    }

    public static interface Group
    extends ISnowflake {
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
        public String getName();

        @Nullable
        public List<String> getUsers();
    }

    public static interface Guild
    extends ISnowflake {
        @Nullable
        public String getIconId();

        @Nullable
        public String getIconUrl();

        @Nullable
        default public ImageProxy getIcon() {
            String iconUrl = this.getIconUrl();
            return iconUrl == null ? null : new ImageProxy(iconUrl);
        }

        @Nonnull
        public String getName();

        @Nullable
        public String getSplashId();

        @Nullable
        public String getSplashUrl();

        @Nullable
        default public ImageProxy getSplash() {
            String splashUrl = this.getSplashUrl();
            return splashUrl == null ? null : new ImageProxy(splashUrl);
        }

        @Nonnull
        public Guild.VerificationLevel getVerificationLevel();

        public int getOnlineCount();

        public int getMemberCount();

        @Nonnull
        public Set<String> getFeatures();

        @Nullable
        public GuildWelcomeScreen getWelcomeScreen();
    }

    public static interface Channel
    extends ISnowflake {
        @Nonnull
        public String getName();

        @Nonnull
        public ChannelType getType();
    }
}

