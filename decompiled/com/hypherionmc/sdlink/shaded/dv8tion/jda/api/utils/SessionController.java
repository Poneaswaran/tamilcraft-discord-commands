/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestRateLimiter;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public interface SessionController {
    public static final int IDENTIFY_DELAY = 5;

    default public void setConcurrency(int level) {
    }

    public void appendSession(@Nonnull SessionConnectNode var1);

    public void removeSession(@Nonnull SessionConnectNode var1);

    @Nonnull
    default public RestRateLimiter.GlobalRateLimit getRateLimitHandle() {
        return RestRateLimiter.GlobalRateLimit.create();
    }

    @Nonnull
    default public String getGateway() {
        return "wss://gateway.discord.gg/";
    }

    @Nonnull
    public ShardedGateway getShardedGateway(@Nonnull JDA var1);

    public static interface SessionConnectNode {
        public boolean isReconnect();

        @Nonnull
        public JDA getJDA();

        @Nonnull
        public JDA.ShardInfo getShardInfo();

        public void run(boolean var1) throws InterruptedException;
    }

    public static class ShardedGateway {
        private final String url;
        private final int shardTotal;
        private final int concurrency;

        public ShardedGateway(String url, int shardTotal) {
            this(url, shardTotal, 1);
        }

        public ShardedGateway(String url, int shardTotal, int concurrency) {
            this.url = url;
            this.shardTotal = shardTotal;
            this.concurrency = concurrency;
        }

        @Nonnull
        public String getUrl() {
            return this.url;
        }

        public int getShardTotal() {
            return this.shardTotal;
        }

        public int getConcurrency() {
            return this.concurrency;
        }
    }
}

