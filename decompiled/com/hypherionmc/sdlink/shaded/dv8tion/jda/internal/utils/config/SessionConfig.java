/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.config;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.hooks.VoiceDispatchInterceptor;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ConcurrentSessionController;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.SessionController;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.config.flags.ConfigFlag;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocketFactory;
import com.hypherionmc.sdlink.shaded.okhttp3.OkHttpClient;
import java.util.EnumSet;

public class SessionConfig {
    private final SessionController sessionController;
    private final OkHttpClient httpClient;
    private final WebSocketFactory webSocketFactory;
    private final VoiceDispatchInterceptor interceptor;
    private final int largeThreshold;
    private EnumSet<ConfigFlag> flags;
    private int maxReconnectDelay;

    public SessionConfig(@Nullable SessionController sessionController, @Nullable OkHttpClient httpClient, @Nullable WebSocketFactory webSocketFactory, @Nullable VoiceDispatchInterceptor interceptor, EnumSet<ConfigFlag> flags, int maxReconnectDelay, int largeThreshold) {
        this.sessionController = sessionController == null ? new ConcurrentSessionController() : sessionController;
        this.httpClient = httpClient;
        this.webSocketFactory = webSocketFactory == null ? SessionConfig.newWebSocketFactory() : webSocketFactory;
        this.interceptor = interceptor;
        this.flags = flags;
        this.maxReconnectDelay = maxReconnectDelay;
        this.largeThreshold = largeThreshold;
    }

    private static WebSocketFactory newWebSocketFactory() {
        return new WebSocketFactory().setConnectionTimeout(10000);
    }

    public void setAutoReconnect(boolean autoReconnect) {
        if (autoReconnect) {
            this.flags.add(ConfigFlag.AUTO_RECONNECT);
        } else {
            this.flags.remove((Object)ConfigFlag.AUTO_RECONNECT);
        }
    }

    @Nonnull
    public SessionController getSessionController() {
        return this.sessionController;
    }

    @Nullable
    public OkHttpClient getHttpClient() {
        return this.httpClient;
    }

    @Nonnull
    public WebSocketFactory getWebSocketFactory() {
        return this.webSocketFactory;
    }

    @Nullable
    public VoiceDispatchInterceptor getVoiceDispatchInterceptor() {
        return this.interceptor;
    }

    public boolean isAutoReconnect() {
        return this.flags.contains((Object)ConfigFlag.AUTO_RECONNECT);
    }

    public boolean isRetryOnTimeout() {
        return this.flags.contains((Object)ConfigFlag.RETRY_TIMEOUT);
    }

    public boolean isBulkDeleteSplittingEnabled() {
        return this.flags.contains((Object)ConfigFlag.BULK_DELETE_SPLIT);
    }

    public boolean isRawEvents() {
        return this.flags.contains((Object)ConfigFlag.RAW_EVENTS);
    }

    public boolean isEventPassthrough() {
        return this.flags.contains((Object)ConfigFlag.EVENT_PASSTHROUGH);
    }

    public boolean isRelativeRateLimit() {
        return this.flags.contains((Object)ConfigFlag.USE_RELATIVE_RATELIMIT);
    }

    public int getMaxReconnectDelay() {
        return this.maxReconnectDelay;
    }

    public int getLargeThreshold() {
        return this.largeThreshold;
    }

    public EnumSet<ConfigFlag> getFlags() {
        return this.flags;
    }

    @Nonnull
    public static SessionConfig getDefault() {
        return new SessionConfig(null, new OkHttpClient(), null, null, ConfigFlag.getDefault(), 900, 250);
    }
}

