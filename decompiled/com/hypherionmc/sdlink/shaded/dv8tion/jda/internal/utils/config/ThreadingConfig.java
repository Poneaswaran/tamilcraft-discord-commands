/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.config;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.concurrent.CountingThreadFactory;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class ThreadingConfig {
    private final Object audioLock = new Object();
    private ScheduledExecutorService rateLimitScheduler;
    private ExecutorService rateLimitElastic;
    private ScheduledExecutorService gatewayPool;
    private ExecutorService callbackPool = ForkJoinPool.commonPool();
    private ExecutorService eventPool;
    private ScheduledExecutorService audioPool;
    private boolean shutdownRateLimitScheduler = true;
    private boolean shutdownRateLimitElastic = true;
    private boolean shutdownGatewayPool = true;
    private boolean shutdownCallbackPool = false;
    private boolean shutdownEventPool;
    private boolean shutdownAudioPool = true;

    public void setRateLimitScheduler(@Nullable ScheduledExecutorService executor, boolean shutdown) {
        this.rateLimitScheduler = executor;
        this.shutdownRateLimitScheduler = shutdown;
    }

    public void setRateLimitElastic(@Nullable ExecutorService executor, boolean shutdown) {
        this.rateLimitElastic = executor;
        this.shutdownRateLimitElastic = shutdown;
    }

    public void setGatewayPool(@Nullable ScheduledExecutorService executor, boolean shutdown) {
        this.gatewayPool = executor;
        this.shutdownGatewayPool = shutdown;
    }

    public void setCallbackPool(@Nullable ExecutorService executor, boolean shutdown) {
        this.callbackPool = executor == null ? ForkJoinPool.commonPool() : executor;
        this.shutdownCallbackPool = shutdown;
    }

    public void setEventPool(@Nullable ExecutorService executor, boolean shutdown) {
        this.eventPool = executor;
        this.shutdownEventPool = shutdown;
    }

    public void setAudioPool(@Nullable ScheduledExecutorService executor, boolean shutdown) {
        this.audioPool = executor;
        this.shutdownAudioPool = shutdown;
    }

    public void init(@Nonnull Supplier<String> identifier) {
        if (this.rateLimitScheduler == null) {
            this.rateLimitScheduler = ThreadingConfig.newScheduler(2, identifier, "RateLimit-Scheduler", false);
        }
        if (this.gatewayPool == null) {
            this.gatewayPool = ThreadingConfig.newScheduler(1, identifier, "Gateway");
        }
        if (this.rateLimitElastic == null) {
            this.rateLimitElastic = Executors.newCachedThreadPool(new CountingThreadFactory(identifier, "RateLimit-Elastic", false));
            if (this.rateLimitElastic instanceof ThreadPoolExecutor) {
                ((ThreadPoolExecutor)this.rateLimitElastic).setCorePoolSize(1);
                ((ThreadPoolExecutor)this.rateLimitElastic).setKeepAliveTime(2L, TimeUnit.MINUTES);
            }
        }
    }

    public void shutdown() {
        if (this.shutdownCallbackPool) {
            this.callbackPool.shutdown();
        }
        if (this.shutdownGatewayPool) {
            this.gatewayPool.shutdown();
        }
        if (this.shutdownEventPool && this.eventPool != null) {
            this.eventPool.shutdown();
        }
        if (this.shutdownAudioPool && this.audioPool != null) {
            this.audioPool.shutdown();
        }
    }

    public void shutdownRequester() {
        if (this.shutdownRateLimitScheduler) {
            this.rateLimitScheduler.shutdown();
        }
        if (this.shutdownRateLimitElastic) {
            this.rateLimitElastic.shutdown();
        }
    }

    public void shutdownNow() {
        if (this.shutdownCallbackPool) {
            this.callbackPool.shutdownNow();
        }
        if (this.shutdownGatewayPool) {
            this.gatewayPool.shutdownNow();
        }
        if (this.shutdownRateLimitScheduler) {
            this.rateLimitScheduler.shutdownNow();
        }
        if (this.shutdownRateLimitElastic) {
            this.rateLimitElastic.shutdownNow();
        }
        if (this.shutdownEventPool && this.eventPool != null) {
            this.eventPool.shutdownNow();
        }
        if (this.shutdownAudioPool && this.audioPool != null) {
            this.audioPool.shutdownNow();
        }
    }

    @Nonnull
    public ScheduledExecutorService getRateLimitScheduler() {
        return this.rateLimitScheduler;
    }

    @Nonnull
    public ExecutorService getRateLimitElastic() {
        return this.rateLimitElastic;
    }

    @Nonnull
    public ScheduledExecutorService getGatewayPool() {
        return this.gatewayPool;
    }

    @Nonnull
    public ExecutorService getCallbackPool() {
        return this.callbackPool;
    }

    @Nullable
    public ExecutorService getEventPool() {
        return this.eventPool;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public ScheduledExecutorService getAudioPool(@Nonnull Supplier<String> identifier) {
        ScheduledExecutorService pool = this.audioPool;
        if (pool == null) {
            Object object = this.audioLock;
            synchronized (object) {
                pool = this.audioPool;
                if (pool == null) {
                    pool = this.audioPool = ThreadingConfig.newScheduler(1, identifier, "AudioLifeCycle");
                }
            }
        }
        return pool;
    }

    public boolean isShutdownRateLimitScheduler() {
        return this.shutdownRateLimitScheduler;
    }

    public boolean isShutdownRateLimitElastic() {
        return this.shutdownRateLimitElastic;
    }

    public boolean isShutdownGatewayPool() {
        return this.shutdownGatewayPool;
    }

    public boolean isShutdownCallbackPool() {
        return this.shutdownCallbackPool;
    }

    public boolean isShutdownEventPool() {
        return this.shutdownEventPool;
    }

    public boolean isShutdownAudioPool() {
        return this.shutdownAudioPool;
    }

    @Nonnull
    public static ScheduledThreadPoolExecutor newScheduler(int coreSize, Supplier<String> identifier, String baseName) {
        return ThreadingConfig.newScheduler(coreSize, identifier, baseName, true);
    }

    @Nonnull
    public static ScheduledThreadPoolExecutor newScheduler(int coreSize, Supplier<String> identifier, String baseName, boolean daemon) {
        return new ScheduledThreadPoolExecutor(coreSize, new CountingThreadFactory(identifier, baseName, daemon));
    }

    @Nonnull
    public static ThreadingConfig getDefault() {
        return new ThreadingConfig();
    }
}

