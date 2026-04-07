/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Icon;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.Manager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;

public interface GuildManager
extends Manager<GuildManager> {
    public static final long NAME = 1L;
    public static final long ICON = 2L;
    public static final long SPLASH = 4L;
    public static final long AFK_CHANNEL = 8L;
    public static final long AFK_TIMEOUT = 16L;
    public static final long SYSTEM_CHANNEL = 32L;
    public static final long MFA_LEVEL = 64L;
    public static final long NOTIFICATION_LEVEL = 128L;
    public static final long EXPLICIT_CONTENT_LEVEL = 256L;
    public static final long VERIFICATION_LEVEL = 512L;
    public static final long BANNER = 1024L;
    public static final long DESCRIPTION = 2048L;
    public static final long RULES_CHANNEL = 4096L;
    public static final long COMMUNITY_UPDATES_CHANNEL = 8192L;
    public static final long BOOST_PROGRESS_BAR_ENABLED = 16384L;
    public static final long FEATURES = 32768L;
    public static final long SAFETY_ALERTS_CHANNEL = 65536L;

    @Override
    @Nonnull
    @CheckReturnValue
    public GuildManager reset(long var1);

    @Override
    @Nonnull
    @CheckReturnValue
    public GuildManager reset(long ... var1);

    @Nonnull
    public Guild getGuild();

    @Nonnull
    @CheckReturnValue
    public GuildManager setName(@Nonnull String var1);

    @Nonnull
    @CheckReturnValue
    public GuildManager setIcon(@Nullable Icon var1);

    @Nonnull
    @CheckReturnValue
    public GuildManager setSplash(@Nullable Icon var1);

    @Nonnull
    @CheckReturnValue
    public GuildManager setAfkChannel(@Nullable VoiceChannel var1);

    @Nonnull
    @CheckReturnValue
    public GuildManager setSystemChannel(@Nullable TextChannel var1);

    @Nonnull
    @CheckReturnValue
    public GuildManager setRulesChannel(@Nullable TextChannel var1);

    @Nonnull
    @CheckReturnValue
    public GuildManager setCommunityUpdatesChannel(@Nullable TextChannel var1);

    @Nonnull
    @CheckReturnValue
    public GuildManager setSafetyAlertsChannel(@Nullable TextChannel var1);

    @Nonnull
    @CheckReturnValue
    public GuildManager setAfkTimeout(@Nonnull Guild.Timeout var1);

    @Nonnull
    @CheckReturnValue
    public GuildManager setVerificationLevel(@Nonnull Guild.VerificationLevel var1);

    @Nonnull
    @CheckReturnValue
    public GuildManager setDefaultNotificationLevel(@Nonnull Guild.NotificationLevel var1);

    @Nonnull
    @CheckReturnValue
    public GuildManager setRequiredMFALevel(@Nonnull Guild.MFALevel var1);

    @Nonnull
    @CheckReturnValue
    public GuildManager setExplicitContentLevel(@Nonnull Guild.ExplicitContentLevel var1);

    @Nonnull
    @CheckReturnValue
    public GuildManager setBanner(@Nullable Icon var1);

    @Nonnull
    @CheckReturnValue
    public GuildManager setDescription(@Nullable String var1);

    @Nonnull
    @CheckReturnValue
    public GuildManager setBoostProgressBarEnabled(boolean var1);

    @Nonnull
    @CheckReturnValue
    public GuildManager setFeatures(@Nonnull Collection<String> var1);

    @Nonnull
    @CheckReturnValue
    public GuildManager addFeatures(@Nonnull Collection<String> var1);

    @Nonnull
    @CheckReturnValue
    default public GuildManager addFeatures(String ... features) {
        Checks.noneNull(features, "Features");
        return this.addFeatures(Arrays.asList(features));
    }

    @Nonnull
    @CheckReturnValue
    public GuildManager removeFeatures(@Nonnull Collection<String> var1);

    @Nonnull
    @CheckReturnValue
    default public GuildManager removeFeatures(String ... features) {
        Checks.noneNull(features, "Features");
        return this.removeFeatures(Arrays.asList(features));
    }

    @Nonnull
    @CheckReturnValue
    default public GuildManager setInvitesDisabled(boolean disabled) {
        if (disabled) {
            return this.addFeatures("INVITES_DISABLED");
        }
        return this.removeFeatures("INVITES_DISABLED");
    }
}

