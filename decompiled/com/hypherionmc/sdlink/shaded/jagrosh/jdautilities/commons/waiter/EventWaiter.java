/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.commons.waiter;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.Event;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.GenericEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.session.ShutdownEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.hooks.EventListener;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.hooks.SubscribeEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventWaiter
implements EventListener {
    private static final Logger LOG = LoggerFactory.getLogger(EventWaiter.class);
    private final HashMap<Class<?>, Set<WaitingEvent>> waitingEvents;
    private final ScheduledExecutorService threadpool;
    private final boolean shutdownAutomatically;

    public EventWaiter() {
        this(Executors.newSingleThreadScheduledExecutor(), true);
    }

    public EventWaiter(ScheduledExecutorService threadpool, boolean shutdownAutomatically) {
        Checks.notNull(threadpool, "ScheduledExecutorService");
        Checks.check(!threadpool.isShutdown(), "Cannot construct EventWaiter with a closed ScheduledExecutorService!");
        this.waitingEvents = new HashMap();
        this.threadpool = threadpool;
        this.shutdownAutomatically = shutdownAutomatically;
    }

    public boolean isShutdown() {
        return this.threadpool.isShutdown();
    }

    public <T extends Event> void waitForEvent(Class<T> classType, Predicate<T> condition, Consumer<T> action) {
        this.waitForEvent(classType, condition, action, -1L, null, null);
    }

    public <T extends Event> void waitForEvent(Class<T> classType, Predicate<T> condition, Consumer<T> action, long timeout2, TimeUnit unit, Runnable timeoutAction) {
        Checks.check(!this.isShutdown(), "Attempted to register a WaitingEvent while the EventWaiter's threadpool was already shut down!");
        Checks.notNull(classType, "The provided class type");
        Checks.notNull(condition, "The provided condition predicate");
        Checks.notNull(action, "The provided action consumer");
        WaitingEvent we = new WaitingEvent(condition, action);
        Set set = this.waitingEvents.computeIfAbsent(classType, c -> ConcurrentHashMap.newKeySet());
        set.add(we);
        if (timeout2 > 0L && unit != null) {
            this.threadpool.schedule(() -> {
                try {
                    if (set.remove(we) && timeoutAction != null) {
                        timeoutAction.run();
                    }
                }
                catch (Exception ex) {
                    LOG.error("Failed to run timeoutAction", (Throwable)ex);
                }
            }, timeout2, unit);
        }
    }

    @Override
    @SubscribeEvent
    public final void onEvent(GenericEvent event) {
        for (Class<?> c = event.getClass(); c != null; c = c.getSuperclass()) {
            Set<WaitingEvent> set = this.waitingEvents.get(c);
            if (set != null) {
                set.removeIf(wEvent -> wEvent.attempt(event));
            }
            if (!(event instanceof ShutdownEvent) || !this.shutdownAutomatically) continue;
            this.threadpool.shutdown();
        }
    }

    public void shutdown() {
        if (this.shutdownAutomatically) {
            throw new UnsupportedOperationException("Shutting down EventWaiters that are set to automatically close is unsupported!");
        }
        this.threadpool.shutdown();
    }

    private class WaitingEvent<T extends GenericEvent> {
        final Predicate<T> condition;
        final Consumer<T> action;

        WaitingEvent(Predicate<T> condition, Consumer<T> action) {
            this.condition = condition;
            this.action = action;
        }

        boolean attempt(T event) {
            if (this.condition.test(event)) {
                this.action.accept(event);
                return true;
            }
            return false;
        }
    }
}

