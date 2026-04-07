/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.GatewayIntent;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.EnumSet;

public enum CacheFlag {
    ACTIVITY(GatewayIntent.GUILD_PRESENCES),
    VOICE_STATE(GatewayIntent.GUILD_VOICE_STATES),
    EMOJI(GatewayIntent.GUILD_EXPRESSIONS),
    STICKER(GatewayIntent.GUILD_EXPRESSIONS),
    CLIENT_STATUS(GatewayIntent.GUILD_PRESENCES),
    MEMBER_OVERRIDES,
    ROLE_TAGS,
    FORUM_TAGS,
    ONLINE_STATUS(GatewayIntent.GUILD_PRESENCES),
    SCHEDULED_EVENTS(GatewayIntent.SCHEDULED_EVENTS);

    private static final EnumSet<CacheFlag> privileged;
    private final GatewayIntent requiredIntent;

    private CacheFlag() {
        this(null);
    }

    private CacheFlag(GatewayIntent requiredIntent) {
        this.requiredIntent = requiredIntent;
    }

    @Nullable
    public GatewayIntent getRequiredIntent() {
        return this.requiredIntent;
    }

    public boolean isPresence() {
        return this.requiredIntent == GatewayIntent.GUILD_PRESENCES;
    }

    @Nonnull
    public static EnumSet<CacheFlag> getPrivileged() {
        return EnumSet.copyOf(privileged);
    }

    static {
        privileged = EnumSet.of(ACTIVITY, CLIENT_STATUS, ONLINE_STATUS);
    }
}

