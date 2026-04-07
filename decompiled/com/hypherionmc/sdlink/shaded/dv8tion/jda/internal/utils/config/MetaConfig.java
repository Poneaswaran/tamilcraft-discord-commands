/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.config;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheFlag;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.config.flags.ConfigFlag;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MetaConfig {
    private static final MetaConfig defaultConfig = new MetaConfig(2048, null, EnumSet.allOf(CacheFlag.class), ConfigFlag.getDefault());
    private final ConcurrentMap<String, String> mdcContextMap;
    private final EnumSet<CacheFlag> cacheFlags;
    private final boolean enableMDC;
    private final boolean useShutdownHook;
    private final int maxBufferSize;

    public MetaConfig(int maxBufferSize, @Nullable ConcurrentMap<String, String> mdcContextMap, @Nullable EnumSet<CacheFlag> cacheFlags, EnumSet<ConfigFlag> flags) {
        this.maxBufferSize = maxBufferSize;
        this.cacheFlags = cacheFlags == null ? EnumSet.allOf(CacheFlag.class) : cacheFlags;
        this.enableMDC = flags.contains((Object)ConfigFlag.MDC_CONTEXT);
        this.mdcContextMap = this.enableMDC ? (mdcContextMap == null ? new ConcurrentHashMap() : mdcContextMap) : null;
        this.useShutdownHook = flags.contains((Object)ConfigFlag.SHUTDOWN_HOOK);
    }

    @Nullable
    public ConcurrentMap<String, String> getMdcContextMap() {
        return this.mdcContextMap;
    }

    @Nonnull
    public EnumSet<CacheFlag> getCacheFlags() {
        return this.cacheFlags;
    }

    public boolean isEnableMDC() {
        return this.enableMDC;
    }

    public boolean isUseShutdownHook() {
        return this.useShutdownHook;
    }

    public int getMaxBufferSize() {
        return this.maxBufferSize;
    }

    @Nonnull
    public static MetaConfig getDefault() {
        return defaultConfig;
    }
}

