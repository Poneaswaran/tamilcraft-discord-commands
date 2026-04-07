/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.core.managers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class SpamManager {
    private final ConcurrentHashMap<String, List<Long>> messageTimestamps = new ConcurrentHashMap();
    private final Set<String> blockedMessages = new HashSet<String>();
    private final int threshold;
    private final int timeWindowMillis;
    private final int blockMillis;
    private final ScheduledExecutorService executor;

    public SpamManager(int threshold, int timeWindowMillis, int blockMillis, ScheduledExecutorService executor) {
        this.threshold = threshold;
        this.timeWindowMillis = timeWindowMillis;
        this.blockMillis = blockMillis;
        this.executor = executor;
        this.startSpamChecker();
    }

    public void receiveMessage(String message) {
        long currentTime = System.currentTimeMillis();
        this.messageTimestamps.compute(message, (msg, timestamps) -> {
            if (timestamps == null) {
                timestamps = new ArrayList<Long>();
            }
            timestamps.add(currentTime);
            return new ArrayList<Long>(timestamps.stream().filter(timestamp -> currentTime - timestamp <= (long)this.timeWindowMillis).toList());
        });
        if (this.messageTimestamps.get(message).size() >= this.threshold) {
            this.blockedMessages.add(message);
        }
    }

    public boolean isBlocked(String message) {
        return this.blockedMessages.contains(message);
    }

    private void startSpamChecker() {
        this.executor.scheduleAtFixedRate(() -> {
            long currentTime = System.currentTimeMillis();
            this.blockedMessages.removeIf(message -> {
                ArrayList<Long> timestamps = this.messageTimestamps.getOrDefault(message, new ArrayList());
                timestamps = new ArrayList<Long>(timestamps.stream().filter(timestamp -> currentTime - timestamp <= (long)this.timeWindowMillis).toList());
                this.messageTimestamps.put((String)message, (List<Long>)timestamps);
                return timestamps.size() < this.threshold;
            });
        }, this.blockMillis, this.blockMillis, TimeUnit.MILLISECONDS);
    }
}

