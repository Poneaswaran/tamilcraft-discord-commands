/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.hooks;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.GenericEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.hooks.IEventManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.hooks.InterfacedEventManager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

public class EventManagerProxy
implements IEventManager {
    private final ExecutorService executor;
    private IEventManager subject;

    public EventManagerProxy(IEventManager subject, ExecutorService executor) {
        this.subject = subject;
        this.executor = executor;
    }

    public void setSubject(IEventManager subject) {
        this.subject = subject == null ? new InterfacedEventManager() : subject;
    }

    public IEventManager getSubject() {
        return this.subject;
    }

    @Override
    public void register(@Nonnull Object listener) {
        this.subject.register(listener);
    }

    @Override
    public void unregister(@Nonnull Object listener) {
        this.subject.unregister(listener);
    }

    @Override
    public void handle(@Nonnull GenericEvent event) {
        try {
            if (this.executor != null && !this.executor.isShutdown()) {
                this.executor.execute(() -> this.handleInternally(event));
            } else {
                this.handleInternally(event);
            }
        }
        catch (RejectedExecutionException ex) {
            JDAImpl.LOG.warn("Event-Pool rejected event execution! Running on handling thread instead...");
            this.handleInternally(event);
        }
        catch (Exception ex) {
            JDAImpl.LOG.error("Encountered exception trying to schedule event", (Throwable)ex);
        }
    }

    private void handleInternally(@Nonnull GenericEvent event) {
        try {
            this.subject.handle(event);
        }
        catch (RuntimeException e) {
            JDAImpl.LOG.error("The EventManager.handle() call had an uncaught exception", (Throwable)e);
        }
    }

    @Override
    @Nonnull
    public List<Object> getRegisteredListeners() {
        return this.subject.getRegisteredListeners();
    }
}

