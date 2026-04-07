/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.concurrent;

import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

public class CountingThreadFactory
implements ThreadFactory {
    private final Supplier<String> identifier;
    private final AtomicLong count = new AtomicLong(1L);
    private final boolean daemon;

    public CountingThreadFactory(@Nonnull Supplier<String> identifier, @Nonnull String specifier) {
        this(identifier, specifier, true);
    }

    public CountingThreadFactory(@Nonnull Supplier<String> identifier, @Nonnull String specifier, boolean daemon) {
        this.identifier = () -> (String)identifier.get() + " " + specifier;
        this.daemon = daemon;
    }

    @Override
    @Nonnull
    public Thread newThread(@Nonnull Runnable r) {
        Thread thread = new Thread(r, this.identifier.get() + "-Worker " + this.count.getAndIncrement());
        thread.setDaemon(this.daemon);
        return thread;
    }
}

