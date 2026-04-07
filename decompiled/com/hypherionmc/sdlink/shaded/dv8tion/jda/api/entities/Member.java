/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.annotations.Incubating;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.OnlineStatus;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Activity;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ClientType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.GuildVoiceState;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IMentionable;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.IPermissionHolder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.UserSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.DefaultGuildChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ImageProxy;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.awt.Color;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.Unmodifiable;

public interface Member
extends IMentionable,
IPermissionHolder,
UserSnowflake {
    public static final String AVATAR_URL = "https://cdn.discordapp.com/guilds/%s/users/%s/avatars/%s.%s";
    public static final int MAX_TIME_OUT_LENGTH = 28;

    @Nonnull
    public User getUser();

    @Override
    @Nonnull
    public Guild getGuild();

    @Nonnull
    public JDA getJDA();

    @Nonnull
    public OffsetDateTime getTimeJoined();

    public boolean hasTimeJoined();

    @Nullable
    public OffsetDateTime getTimeBoosted();

    public boolean isBoosting();

    @Nullable
    public OffsetDateTime getTimeOutEnd();

    default public boolean isTimedOut() {
        return this.getTimeOutEnd() != null && this.getTimeOutEnd().isAfter(OffsetDateTime.now());
    }

    @Nullable
    public GuildVoiceState getVoiceState();

    @Nonnull
    public @Unmodifiable List<Activity> getActivities();

    @Nonnull
    public OnlineStatus getOnlineStatus();

    @Nonnull
    public OnlineStatus getOnlineStatus(@Nonnull ClientType var1);

    @Nonnull
    public EnumSet<ClientType> getActiveClients();

    @Nullable
    public String getNickname();

    @Nonnull
    public String getEffectiveName();

    @Nullable
    public String getAvatarId();

    @Nullable
    default public String getAvatarUrl() {
        String avatarId = this.getAvatarId();
        return avatarId == null ? null : String.format(AVATAR_URL, this.getGuild().getId(), this.getId(), avatarId, avatarId.startsWith("a_") ? "gif" : "png");
    }

    @Nullable
    default public ImageProxy getAvatar() {
        String avatarUrl = this.getAvatarUrl();
        return avatarUrl == null ? null : new ImageProxy(avatarUrl);
    }

    @Nonnull
    default public String getEffectiveAvatarUrl() {
        String avatarUrl = this.getAvatarUrl();
        return avatarUrl == null ? this.getUser().getEffectiveAvatarUrl() : avatarUrl;
    }

    @Nonnull
    default public ImageProxy getEffectiveAvatar() {
        ImageProxy avatar = this.getAvatar();
        return avatar == null ? this.getUser().getEffectiveAvatar() : avatar;
    }

    @Nonnull
    public @Unmodifiable List<Role> getRoles();

    @Nullable
    public Color getColor();

    public int getColorRaw();

    public int getFlagsRaw();

    @Nonnull
    default public EnumSet<MemberFlag> getFlags() {
        return MemberFlag.fromRaw(this.getFlagsRaw());
    }

    public boolean canInteract(@Nonnull Member var1);

    public boolean canInteract(@Nonnull Role var1);

    public boolean canInteract(@Nonnull RichCustomEmoji var1);

    public boolean isOwner();

    @Incubating
    public boolean isPending();

    @Nullable
    public DefaultGuildChannelUnion getDefaultChannel();

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Void> ban(int deletionTimeframe, @Nonnull TimeUnit unit) {
        return this.getGuild().ban(this, deletionTimeframe, unit);
    }

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Void> kick() {
        return this.getGuild().kick(this);
    }

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Void> timeoutFor(long amount, @Nonnull TimeUnit unit) {
        return this.getGuild().timeoutFor(this, amount, unit);
    }

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Void> timeoutFor(@Nonnull Duration duration) {
        return this.getGuild().timeoutFor(this, duration);
    }

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Void> timeoutUntil(@Nonnull TemporalAccessor temporal) {
        return this.getGuild().timeoutUntil(this, temporal);
    }

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Void> removeTimeout() {
        return this.getGuild().removeTimeout(this);
    }

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Void> mute(boolean mute) {
        return this.getGuild().mute(this, mute);
    }

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Void> deafen(boolean deafen) {
        return this.getGuild().deafen(this, deafen);
    }

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Void> modifyNickname(@Nullable String nickname) {
        return this.getGuild().modifyNickname(this, nickname);
    }

    @Nonnull
    @CheckReturnValue
    default public AuditableRestAction<Void> modifyFlags(@Nonnull Collection<MemberFlag> newFlags) {
        Checks.noneNull(newFlags, "Flags");
        if (!this.getGuild().getSelfMember().hasPermission(Permission.MODERATE_MEMBERS)) {
            throw new InsufficientPermissionException(this.getGuild(), Permission.MODERATE_MEMBERS);
        }
        int flags = this.getFlagsRaw();
        EnumSet<MemberFlag> updated = Helpers.copyEnumSet(MemberFlag.class, newFlags);
        for (MemberFlag flag : MemberFlag.values()) {
            if (!flag.modifiable) continue;
            if (updated.contains((Object)flag)) {
                flags |= flag.raw;
                continue;
            }
            flags &= ~flag.raw;
        }
        DataObject body = DataObject.empty().put("flags", flags);
        Route.CompiledRoute route = Route.Guilds.MODIFY_MEMBER.compile(this.getGuild().getId(), this.getId());
        return new AuditableRestActionImpl<Void>(this.getJDA(), route, body);
    }

    public static enum MemberFlag {
        DID_REJOIN(1, false),
        COMPLETED_ONBOARDING(2, false),
        BYPASSES_VERIFICATION(4, true),
        STARTED_ONBOARDING(8, false);

        private final int raw;
        private final boolean modifiable;

        private MemberFlag(int raw, boolean modifiable) {
            this.raw = raw;
            this.modifiable = modifiable;
        }

        public int getRaw() {
            return this.raw;
        }

        public boolean isModifiable() {
            return this.modifiable;
        }

        @Nonnull
        public static EnumSet<MemberFlag> fromRaw(int raw) {
            EnumSet<MemberFlag> flags = EnumSet.noneOf(MemberFlag.class);
            for (MemberFlag flag : MemberFlag.values()) {
                if ((raw & flag.raw) != flag.raw) continue;
                flags.add(flag);
            }
            return flags;
        }

        public static int toRaw(@Nonnull Collection<MemberFlag> flags) {
            Checks.noneNull(flags, "Flags");
            int raw = 0;
            for (MemberFlag flag : flags) {
                raw |= flag.raw;
            }
            return raw;
        }
    }
}

