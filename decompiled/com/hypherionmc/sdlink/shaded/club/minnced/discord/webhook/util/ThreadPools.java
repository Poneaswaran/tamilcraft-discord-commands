/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.club.minnced.discord.webhook.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public class ThreadPools {
    public static ScheduledExecutorService getDefaultPool(long id, ThreadFactory factory2, boolean isDaemon) {
        return Executors.newSingleThreadScheduledExecutor(factory2 == null ? new DefaultWebhookThreadFactory(id, isDaemon) : factory2);
    }

    public static final class DefaultWebhookThreadFactory
    implements ThreadFactory {
        private final long id;
        private final boolean isDaemon;

        public DefaultWebhookThreadFactory(long id, boolean isDaemon) {
            this.id = id;
            this.isDaemon = isDaemon;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "Webhook-RateLimit Thread WebhookID: " + this.id);
            thread.setDaemon(this.isDaemon);
            return thread;
        }
    }
}

