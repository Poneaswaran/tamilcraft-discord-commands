/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.InvalidTokenException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestRateLimiter;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.SessionController;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.OpeningHandshakeException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;

public class SessionControllerAdapter
implements SessionController {
    protected static final Logger log = JDALogger.getLog(SessionControllerAdapter.class);
    protected final Object lock = new Object();
    protected Queue<SessionController.SessionConnectNode> connectQueue = new ConcurrentLinkedQueue<SessionController.SessionConnectNode>();
    protected RestRateLimiter.GlobalRateLimit globalRatelimit = RestRateLimiter.GlobalRateLimit.create();
    protected Thread workerHandle;
    protected long lastConnect = 0L;

    @Override
    public void appendSession(@Nonnull SessionController.SessionConnectNode node) {
        this.removeSession(node);
        this.connectQueue.add(node);
        this.runWorker();
    }

    @Override
    public void removeSession(@Nonnull SessionController.SessionConnectNode node) {
        this.connectQueue.remove(node);
    }

    @Override
    @Nonnull
    public RestRateLimiter.GlobalRateLimit getRateLimitHandle() {
        return this.globalRatelimit;
    }

    @Override
    @Nonnull
    public SessionController.ShardedGateway getShardedGateway(@Nonnull JDA api) {
        return (SessionController.ShardedGateway)new RestActionImpl<SessionController.ShardedGateway>(api, Route.Misc.GATEWAY_BOT.compile(new String[0])){

            @Override
            public void handleResponse(Response response, Request<SessionController.ShardedGateway> request) {
                if (response.isOk()) {
                    DataObject object = response.getObject();
                    String url = object.getString("url");
                    int shards = object.getInt("shards");
                    int concurrency = object.getObject("session_start_limit").getInt("max_concurrency", 1);
                    request.onSuccess(new SessionController.ShardedGateway(url, shards, concurrency));
                } else if (response.code == 401) {
                    this.api.shutdownNow();
                    request.onFailure(new InvalidTokenException());
                } else {
                    request.onFailure(response);
                }
            }
        }.priority().complete();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void runWorker() {
        Object object = this.lock;
        synchronized (object) {
            if (this.workerHandle == null) {
                this.workerHandle = new QueueWorker();
                this.workerHandle.start();
            }
        }
    }

    protected class QueueWorker
    extends Thread {
        protected final long delay;

        public QueueWorker() {
            this(5);
        }

        public QueueWorker(int delay) {
            this(TimeUnit.SECONDS.toMillis(delay));
        }

        public QueueWorker(long delay) {
            super("SessionControllerAdapter-Worker");
            this.delay = delay;
            super.setUncaughtExceptionHandler(this::handleFailure);
        }

        protected void handleFailure(Thread thread, Throwable exception) {
            log.error("Worker has failed with throwable!", exception);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            try {
                long interval;
                if (this.delay > 0L && (interval = System.currentTimeMillis() - SessionControllerAdapter.this.lastConnect) < this.delay) {
                    Thread.sleep(this.delay - interval);
                }
            }
            catch (InterruptedException ex) {
                log.error("Unable to backoff", (Throwable)ex);
            }
            this.processQueue();
            Object object = SessionControllerAdapter.this.lock;
            synchronized (object) {
                SessionControllerAdapter.this.workerHandle = null;
                if (!SessionControllerAdapter.this.connectQueue.isEmpty()) {
                    SessionControllerAdapter.this.runWorker();
                }
            }
        }

        protected void processQueue() {
            boolean isMultiple;
            boolean bl = isMultiple = SessionControllerAdapter.this.connectQueue.size() > 1;
            while (!SessionControllerAdapter.this.connectQueue.isEmpty()) {
                SessionController.SessionConnectNode node = SessionControllerAdapter.this.connectQueue.poll();
                try {
                    node.run(isMultiple && SessionControllerAdapter.this.connectQueue.isEmpty());
                    isMultiple = true;
                    SessionControllerAdapter.this.lastConnect = System.currentTimeMillis();
                    if (SessionControllerAdapter.this.connectQueue.isEmpty()) break;
                    if (this.delay <= 0L) continue;
                    Thread.sleep(this.delay);
                }
                catch (IllegalStateException e) {
                    Throwable t = e.getCause();
                    if (t instanceof OpeningHandshakeException) {
                        log.error("Failed opening handshake, appending to queue. Message: {}", (Object)e.getMessage());
                    } else if (t != null && !JDA.Status.RECONNECT_QUEUED.name().equals(t.getMessage())) {
                        log.error("Failed to establish connection for a node, appending to queue", (Throwable)e);
                    } else {
                        log.error("Unexpected exception when running connect node", (Throwable)e);
                    }
                    SessionControllerAdapter.this.appendSession(node);
                }
                catch (InterruptedException e) {
                    log.error("Failed to run node", (Throwable)e);
                    SessionControllerAdapter.this.appendSession(node);
                    return;
                }
            }
        }
    }
}

