/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDAInfo;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestRateLimiter;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.SequentialRestRateLimiter;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.okhttp3.Request;
import java.util.function.Consumer;
import java.util.function.Function;

public class RestConfig {
    public static final String USER_AGENT = "DiscordBot (https://github.com/discord-jda/JDA, " + JDAInfo.VERSION + ")";
    public static final String DEFAULT_BASE_URL = "https://discord.com/api/v10/";
    private String userAgent = USER_AGENT;
    private String baseUrl = "https://discord.com/api/v10/";
    private boolean relativeRateLimit = true;
    private Consumer<? super Request.Builder> customBuilder;
    private Function<? super RestRateLimiter.RateLimitConfig, ? extends RestRateLimiter> rateLimiter = SequentialRestRateLimiter::new;

    @Nonnull
    public RestConfig setRelativeRateLimit(boolean relativeRateLimit) {
        this.relativeRateLimit = relativeRateLimit;
        return this;
    }

    @Nonnull
    public RestConfig setRateLimiterFactory(@Nonnull Function<? super RestRateLimiter.RateLimitConfig, ? extends RestRateLimiter> rateLimiter) {
        Checks.notNull(rateLimiter, "RateLimiter");
        this.rateLimiter = rateLimiter;
        return this;
    }

    @Nonnull
    public RestConfig setBaseUrl(@Nonnull String baseUrl) {
        Checks.notEmpty(baseUrl, "URL");
        Checks.check(baseUrl.length() > 4 && baseUrl.substring(0, 4).equalsIgnoreCase("http"), "URL must be HTTP");
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
        return this;
    }

    @Nonnull
    public RestConfig setUserAgentSuffix(@Nullable String suffix) {
        this.userAgent = suffix == null || Helpers.isBlank(suffix) ? USER_AGENT : USER_AGENT + " " + suffix;
        return this;
    }

    @Nonnull
    public RestConfig setCustomBuilder(@Nullable Consumer<? super Request.Builder> customBuilder) {
        this.customBuilder = customBuilder;
        return this;
    }

    @Nonnull
    public String getUserAgent() {
        return this.userAgent;
    }

    @Nonnull
    public String getBaseUrl() {
        return this.baseUrl;
    }

    @Nonnull
    public Function<? super RestRateLimiter.RateLimitConfig, ? extends RestRateLimiter> getRateLimiterFactory() {
        return this.rateLimiter;
    }

    @Nullable
    public Consumer<? super Request.Builder> getCustomBuilder() {
        return this.customBuilder;
    }

    public boolean isRelativeRateLimit() {
        return this.relativeRateLimit;
    }
}

