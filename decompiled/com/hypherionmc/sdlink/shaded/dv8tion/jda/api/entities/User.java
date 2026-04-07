/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.UserSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CacheRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ImageProxy;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.entities.UserSnowflakeImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.EntityString;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.awt.Color;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Unmodifiable;

public interface User
extends UserSnowflake {
    public static final Pattern USER_TAG = Pattern.compile("(.{2,32})#(\\d{4})");
    public static final String AVATAR_URL = "https://cdn.discordapp.com/avatars/%s/%s.%s";
    public static final String DEFAULT_AVATAR_URL = "https://cdn.discordapp.com/embed/avatars/%s.png";
    public static final String BANNER_URL = "https://cdn.discordapp.com/banners/%s/%s.%s";
    public static final int DEFAULT_ACCENT_COLOR_RAW = 0x1FFFFFFF;

    @Nonnull
    public static UserSnowflake fromId(long id) {
        return new UserSnowflakeImpl(id);
    }

    @Nonnull
    public static UserSnowflake fromId(@Nonnull String id) {
        return User.fromId(MiscUtil.parseSnowflake(id));
    }

    @Nonnull
    public String getName();

    @Nullable
    public String getGlobalName();

    @Nonnull
    default public String getEffectiveName() {
        String globalName = this.getGlobalName();
        return globalName != null ? globalName : this.getName();
    }

    @Nonnull
    public String getDiscriminator();

    @Nullable
    public String getAvatarId();

    @Nullable
    default public String getAvatarUrl() {
        String avatarId = this.getAvatarId();
        return avatarId == null ? null : String.format(AVATAR_URL, this.getId(), avatarId, avatarId.startsWith("a_") ? "gif" : "png");
    }

    @Nullable
    default public ImageProxy getAvatar() {
        String avatarUrl = this.getAvatarUrl();
        return avatarUrl == null ? null : new ImageProxy(avatarUrl);
    }

    @Nonnull
    default public String getEffectiveAvatarUrl() {
        String avatarUrl = this.getAvatarUrl();
        return avatarUrl == null ? this.getDefaultAvatarUrl() : avatarUrl;
    }

    @Nonnull
    default public ImageProxy getEffectiveAvatar() {
        ImageProxy avatar = this.getAvatar();
        return avatar == null ? this.getDefaultAvatar() : avatar;
    }

    @Nonnull
    @CheckReturnValue
    public CacheRestAction<Profile> retrieveProfile();

    @Nonnull
    public String getAsTag();

    public boolean hasPrivateChannel();

    @Nonnull
    @CheckReturnValue
    public CacheRestAction<PrivateChannel> openPrivateChannel();

    @Nonnull
    public @Unmodifiable List<Guild> getMutualGuilds();

    public boolean isBot();

    public boolean isSystem();

    @Nonnull
    public JDA getJDA();

    @Nonnull
    public EnumSet<UserFlag> getFlags();

    public int getFlagsRaw();

    public static enum UserFlag {
        STAFF(0, "Discord Employee"),
        PARTNER(1, "Partnered Server Owner"),
        HYPESQUAD(2, "HypeSquad Events"),
        BUG_HUNTER_LEVEL_1(3, "Bug Hunter Level 1"),
        HYPESQUAD_BRAVERY(6, "HypeSquad Bravery"),
        HYPESQUAD_BRILLIANCE(7, "HypeSquad Brilliance"),
        HYPESQUAD_BALANCE(8, "HypeSquad Balance"),
        EARLY_SUPPORTER(9, "Early Supporter"),
        TEAM_USER(10, "Team User"),
        BUG_HUNTER_LEVEL_2(14, "Bug Hunter Level 2"),
        VERIFIED_BOT(16, "Verified Bot"),
        VERIFIED_DEVELOPER(17, "Early Verified Bot Developer"),
        CERTIFIED_MODERATOR(18, "Discord Certified Moderator"),
        BOT_HTTP_INTERACTIONS(19, "HTTP Interactions Bot"),
        ACTIVE_DEVELOPER(22, "Active Developer"),
        UNKNOWN(-1, "Unknown");

        public static final UserFlag[] EMPTY_FLAGS;
        private final int offset;
        private final int raw;
        private final String name;

        private UserFlag(int offset, String name) {
            this.offset = offset;
            this.raw = 1 << offset;
            this.name = name;
        }

        @Nonnull
        public String getName() {
            return this.name;
        }

        public int getOffset() {
            return this.offset;
        }

        public int getRawValue() {
            return this.raw;
        }

        @Nonnull
        public static UserFlag getFromOffset(int offset) {
            for (UserFlag flag : UserFlag.values()) {
                if (flag.offset != offset) continue;
                return flag;
            }
            return UNKNOWN;
        }

        @Nonnull
        public static EnumSet<UserFlag> getFlags(int flags) {
            EnumSet<UserFlag> foundFlags = EnumSet.noneOf(UserFlag.class);
            if (flags == 0) {
                return foundFlags;
            }
            for (UserFlag flag : UserFlag.values()) {
                if (flag == UNKNOWN || (flags & flag.raw) != flag.raw) continue;
                foundFlags.add(flag);
            }
            return foundFlags;
        }

        public static int getRaw(UserFlag ... flags) {
            Checks.noneNull((Object[])flags, "UserFlags");
            int raw = 0;
            for (UserFlag flag : flags) {
                if (flag == null || flag == UNKNOWN) continue;
                raw |= flag.raw;
            }
            return raw;
        }

        public static int getRaw(@Nonnull Collection<UserFlag> flags) {
            Checks.notNull(flags, "Flag Collection");
            return UserFlag.getRaw(flags.toArray(EMPTY_FLAGS));
        }

        static {
            EMPTY_FLAGS = new UserFlag[0];
        }
    }

    public static class Profile {
        private final long userId;
        private final String bannerId;
        private final int accentColor;

        public Profile(long userId, String bannerId, int accentColor) {
            this.userId = userId;
            this.bannerId = bannerId;
            this.accentColor = accentColor;
        }

        @Nullable
        public String getBannerId() {
            return this.bannerId;
        }

        @Nullable
        public String getBannerUrl() {
            return this.bannerId == null ? null : String.format(User.BANNER_URL, Long.toUnsignedString(this.userId), this.bannerId, this.bannerId.startsWith("a_") ? "gif" : "png");
        }

        @Nullable
        public ImageProxy getBanner() {
            String bannerUrl = this.getBannerUrl();
            return bannerUrl == null ? null : new ImageProxy(bannerUrl);
        }

        @Nullable
        public Color getAccentColor() {
            return this.accentColor == 0x1FFFFFFF ? null : new Color(this.accentColor);
        }

        public int getAccentColorRaw() {
            return this.accentColor;
        }

        public String toString() {
            return new EntityString(this).addMetadata("userId", this.userId).addMetadata("bannerId", this.bannerId).addMetadata("accentColor", this.accentColor).toString();
        }
    }
}

