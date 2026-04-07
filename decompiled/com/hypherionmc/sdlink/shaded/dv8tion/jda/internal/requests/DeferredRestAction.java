/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.RateLimitedException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.CacheRestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DeferredRestAction<T, R extends RestAction<T>>
implements AuditableRestAction<T>,
CacheRestAction<T> {
    private final JDA api;
    private final Class<T> type;
    private final Supplier<T> valueSupplier;
    private final Supplier<R> actionSupplier;
    private boolean useCache = true;
    private String reason;
    private long deadline = -1L;
    private BooleanSupplier isAction;
    private BooleanSupplier transitiveChecks;

    public DeferredRestAction(JDA api, Supplier<R> actionSupplier) {
        this(api, null, null, actionSupplier);
    }

    public DeferredRestAction(JDA api, Class<T> type, Supplier<T> valueSupplier, Supplier<R> actionSupplier) {
        this.api = api;
        this.type = type;
        this.valueSupplier = valueSupplier;
        this.actionSupplier = actionSupplier;
    }

    @Override
    @Nonnull
    public JDA getJDA() {
        return this.api;
    }

    @Override
    @Nonnull
    public AuditableRestAction<T> reason(String reason) {
        this.reason = reason;
        return this;
    }

    @Override
    @Nonnull
    public DeferredRestAction<T, R> setCheck(BooleanSupplier checks) {
        this.transitiveChecks = checks;
        return this;
    }

    @Override
    @Nullable
    public BooleanSupplier getCheck() {
        return this.transitiveChecks;
    }

    @Override
    @Nonnull
    public DeferredRestAction<T, R> timeout(long timeout2, @Nonnull TimeUnit unit) {
        Checks.notNull((Object)unit, "TimeUnit");
        return this.deadline(timeout2 <= 0L ? 0L : System.currentTimeMillis() + unit.toMillis(timeout2));
    }

    @Override
    @Nonnull
    public DeferredRestAction<T, R> deadline(long timestamp) {
        this.deadline = timestamp;
        return this;
    }

    @Override
    @Nonnull
    public CacheRestAction<T> useCache(boolean useCache) {
        this.useCache = useCache;
        return this;
    }

    public AuditableRestAction<T> setCacheCheck(BooleanSupplier checks) {
        this.isAction = checks;
        return this;
    }

    @Override
    public void queue(Consumer<? super T> success, Consumer<? super Throwable> failure) {
        Consumer<Object> finalSuccess = success != null ? success : RestAction.getDefaultSuccess();
        if (this.type == null) {
            BooleanSupplier checks = this.isAction;
            if (checks != null && checks.getAsBoolean()) {
                this.getAction().queue(success, failure);
            } else {
                finalSuccess.accept(null);
            }
            return;
        }
        T value = this.valueSupplier.get();
        if (!this.useCache || value == null) {
            this.getAction().queue(success, failure);
        } else {
            finalSuccess.accept(value);
        }
    }

    @Override
    @Nonnull
    public CompletableFuture<T> submit(boolean shouldQueue) {
        if (this.type == null) {
            BooleanSupplier checks = this.isAction;
            if (checks != null && checks.getAsBoolean()) {
                return this.getAction().submit(shouldQueue);
            }
            return CompletableFuture.completedFuture(null);
        }
        T value = this.valueSupplier.get();
        if (this.useCache && value != null) {
            return CompletableFuture.completedFuture(value);
        }
        return this.getAction().submit(shouldQueue);
    }

    @Override
    public T complete(boolean shouldQueue) throws RateLimitedException {
        if (this.type == null) {
            BooleanSupplier checks = this.isAction;
            if (checks != null && checks.getAsBoolean()) {
                return this.getAction().complete(shouldQueue);
            }
            return null;
        }
        T value = this.valueSupplier.get();
        if (this.useCache && value != null) {
            return value;
        }
        return this.getAction().complete(shouldQueue);
    }

    private R getAction() {
        RestAction action = (RestAction)this.actionSupplier.get();
        action.setCheck(this.transitiveChecks);
        if (this.deadline >= 0L) {
            action.deadline(this.deadline);
        }
        if (action instanceof AuditableRestAction && this.reason != null) {
            ((AuditableRestAction)action).reason(this.reason);
        }
        return (R)action;
    }
}

