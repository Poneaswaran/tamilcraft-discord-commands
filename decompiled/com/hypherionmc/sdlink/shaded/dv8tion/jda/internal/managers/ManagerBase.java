/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.RateLimitedException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers.Manager;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.AuditableRestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public abstract class ManagerBase<M extends Manager<M>>
extends AuditableRestActionImpl<Void>
implements Manager<M> {
    private static boolean enablePermissionChecks = true;
    protected long set = 0L;

    public static void setPermissionChecksEnabled(boolean enable) {
        enablePermissionChecks = enable;
    }

    public static boolean isPermissionChecksEnabled() {
        return enablePermissionChecks;
    }

    protected ManagerBase(JDA api, Route.CompiledRoute route) {
        super(api, route);
    }

    @Override
    @Nonnull
    public M setCheck(BooleanSupplier checks) {
        return (M)((Manager)super.setCheck(checks));
    }

    @Override
    @Nonnull
    public M timeout(long timeout2, @Nonnull TimeUnit unit) {
        return (M)((Manager)super.timeout(timeout2, unit));
    }

    @Override
    @Nonnull
    public M deadline(long timestamp) {
        return (M)((Manager)super.deadline(timestamp));
    }

    @Override
    @Nonnull
    public M reset(long fields) {
        this.set &= fields ^ 0xFFFFFFFFFFFFFFFFL;
        return (M)this;
    }

    @Override
    @Nonnull
    public M reset(long ... fields) {
        Checks.notNull(fields, "Fields");
        if (fields.length == 0) {
            return (M)this;
        }
        if (fields.length == 1) {
            return this.reset(fields[0]);
        }
        long sum = fields[0];
        for (int i = 1; i < fields.length; ++i) {
            sum |= fields[i];
        }
        return this.reset(sum);
    }

    @Override
    @Nonnull
    public M reset() {
        this.set = 0L;
        return (M)this;
    }

    @Override
    public void queue(Consumer<? super Void> success, Consumer<? super Throwable> failure) {
        if (this.shouldUpdate()) {
            super.queue(success, failure);
        } else if (success != null) {
            success.accept(null);
        } else {
            ManagerBase.getDefaultSuccess().accept(null);
        }
    }

    @Override
    public Void complete(boolean shouldQueue) throws RateLimitedException {
        if (this.shouldUpdate()) {
            return (Void)super.complete(shouldQueue);
        }
        return null;
    }

    @Override
    protected BooleanSupplier finalizeChecks() {
        return enablePermissionChecks ? this::checkPermissions : super.finalizeChecks();
    }

    protected boolean shouldUpdate() {
        return this.set != 0L;
    }

    protected boolean shouldUpdate(long bit) {
        return (this.set & bit) != 0L;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected <E> void withLock(E object, Consumer<? super E> consumer) {
        E e = object;
        synchronized (e) {
            consumer.accept(object);
        }
    }

    protected boolean checkPermissions() {
        return true;
    }
}

