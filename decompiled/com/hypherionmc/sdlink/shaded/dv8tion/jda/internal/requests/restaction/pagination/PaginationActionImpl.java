/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.pagination;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination.PaginationAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.Procedure;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public abstract class PaginationActionImpl<T, M extends PaginationAction<T, M>>
extends RestActionImpl<List<T>>
implements PaginationAction<T, M> {
    protected final List<T> cached = new CopyOnWriteArrayList<T>();
    protected final int maxLimit;
    protected final int minLimit;
    protected final AtomicInteger limit;
    protected PaginationAction.PaginationOrder order = PaginationAction.PaginationOrder.BACKWARD;
    protected volatile long iteratorIndex = 0L;
    protected volatile long lastKey = 0L;
    protected volatile T last = null;
    protected volatile boolean useCache = true;

    public PaginationActionImpl(JDA api, Route.CompiledRoute route, int minLimit, int maxLimit, int initialLimit) {
        super(api, route);
        this.maxLimit = maxLimit;
        this.minLimit = minLimit;
        this.limit = new AtomicInteger(initialLimit);
    }

    public PaginationActionImpl(JDA api) {
        super(api, null);
        this.maxLimit = 0;
        this.minLimit = 0;
        this.limit = new AtomicInteger(0);
    }

    @Override
    @Nonnull
    public M skipTo(long id) {
        int cmp;
        if (!this.cached.isEmpty() && (cmp = Long.compareUnsigned(this.lastKey, id)) < 0) {
            throw new IllegalArgumentException("Cannot jump to that id, it is newer than the current oldest element.");
        }
        if (this.lastKey != id) {
            this.last = null;
        }
        this.iteratorIndex = id;
        this.lastKey = id;
        return (M)this;
    }

    @Override
    public long getLastKey() {
        return this.lastKey;
    }

    @Override
    @Nonnull
    public PaginationAction.PaginationOrder getOrder() {
        return this.order;
    }

    @Override
    @Nonnull
    public M order(@Nonnull PaginationAction.PaginationOrder order) {
        Checks.notNull((Object)order, "PaginationOrder");
        if (order != this.order) {
            if (!this.isEmpty()) {
                throw new IllegalStateException("Cannot change pagination order after retrieving.");
            }
            if (!this.getSupportedOrders().contains((Object)order)) {
                throw new IllegalArgumentException("Cannot use PaginationOrder." + (Object)((Object)order) + " for this pagination endpoint.");
            }
        }
        this.order = order;
        return (M)this;
    }

    @Override
    @Nonnull
    public M setCheck(BooleanSupplier checks) {
        return (M)((PaginationAction)super.setCheck(checks));
    }

    @Override
    @Nonnull
    public M timeout(long timeout2, @Nonnull TimeUnit unit) {
        return (M)((PaginationAction)super.timeout(timeout2, unit));
    }

    @Override
    @Nonnull
    public M deadline(long timestamp) {
        return (M)((PaginationAction)super.deadline(timestamp));
    }

    @Override
    public int cacheSize() {
        return this.cached.size();
    }

    @Override
    public boolean isEmpty() {
        return this.cached.isEmpty();
    }

    @Override
    @Nonnull
    public List<T> getCached() {
        return Collections.unmodifiableList(this.cached);
    }

    @Override
    @Nonnull
    public T getLast() {
        T last = this.last;
        if (last == null) {
            throw new NoSuchElementException("No entities have been retrieved yet.");
        }
        return last;
    }

    @Override
    @Nonnull
    public T getFirst() {
        if (this.cached.isEmpty()) {
            throw new NoSuchElementException("No entities have been retrieved yet.");
        }
        return this.cached.get(0);
    }

    @Override
    @Nonnull
    public M limit(int limit) {
        Checks.check(this.maxLimit == 0 || limit <= this.maxLimit, "Limit must not exceed %d!", (Object)this.maxLimit);
        Checks.check(this.minLimit == 0 || limit >= this.minLimit, "Limit must be greater or equal to %d", (Object)this.minLimit);
        this.limit.set(limit);
        return (M)this;
    }

    @Override
    @Nonnull
    public M cache(boolean enableCache) {
        this.useCache = enableCache;
        return (M)this;
    }

    @Override
    public boolean isCacheEnabled() {
        return this.useCache;
    }

    @Override
    public final int getMaxLimit() {
        return this.maxLimit;
    }

    @Override
    public final int getMinLimit() {
        return this.minLimit;
    }

    @Override
    public final int getLimit() {
        return this.limit.get();
    }

    @Override
    @Nonnull
    public CompletableFuture<List<T>> takeAsync(int amount) {
        return this.takeAsync0(amount, (task, list) -> this.forEachAsync(val -> {
            list.add(val);
            return list.size() < amount;
        }, task::completeExceptionally));
    }

    @Override
    @Nonnull
    public CompletableFuture<List<T>> takeRemainingAsync(int amount) {
        return this.takeAsync0(amount, (task, list) -> this.forEachRemainingAsync(val -> {
            list.add(val);
            return list.size() < amount;
        }, task::completeExceptionally));
    }

    private CompletableFuture<List<T>> takeAsync0(int amount, BiFunction<CompletableFuture<?>, List<T>, CompletableFuture<?>> converter) {
        CompletableFuture task = new CompletableFuture();
        ArrayList list = new ArrayList(amount);
        CompletableFuture<?> promise = converter.apply(task, list);
        promise.thenRun(() -> task.complete(list));
        return task;
    }

    @Override
    @Nonnull
    public PaginationAction.PaginationIterator<T> iterator() {
        return new PaginationAction.PaginationIterator<T>(this.cached, this::getNextChunk);
    }

    @Override
    @Nonnull
    public CompletableFuture<?> forEachAsync(@Nonnull Procedure<? super T> action, @Nonnull Consumer<? super Throwable> failure) {
        Checks.notNull(action, "Procedure");
        Checks.notNull(failure, "Failure Consumer");
        CompletableFuture task = new CompletableFuture();
        ChainedConsumer acceptor = new ChainedConsumer(task, action, throwable -> {
            task.completeExceptionally((Throwable)throwable);
            failure.accept((Throwable)throwable);
        });
        try {
            acceptor.accept(this.cached);
        }
        catch (Exception ex) {
            failure.accept(ex);
            task.completeExceptionally(ex);
        }
        return task;
    }

    @Override
    @Nonnull
    public CompletableFuture<?> forEachRemainingAsync(@Nonnull Procedure<? super T> action, @Nonnull Consumer<? super Throwable> failure) {
        Checks.notNull(action, "Procedure");
        Checks.notNull(failure, "Failure Consumer");
        CompletableFuture task = new CompletableFuture();
        ChainedConsumer acceptor = new ChainedConsumer(task, action, throwable -> {
            task.completeExceptionally((Throwable)throwable);
            failure.accept((Throwable)throwable);
        });
        try {
            acceptor.accept(this.getRemainingCache());
        }
        catch (Exception ex) {
            failure.accept(ex);
            task.completeExceptionally(ex);
        }
        return task;
    }

    @Override
    public void forEachRemaining(@Nonnull Procedure<? super T> action) {
        Checks.notNull(action, "Procedure");
        LinkedList<T> queue = new LinkedList<T>();
        while (queue.addAll(this.getNextChunk())) {
            while (!queue.isEmpty()) {
                Object it = queue.poll();
                if (action.execute(it)) continue;
                this.updateIndex(it);
                return;
            }
        }
    }

    @Nonnull
    protected String getPaginationLastEvaluatedKey(long lastId, T last) {
        return Long.toUnsignedString(lastId);
    }

    @Override
    protected Route.CompiledRoute finalizeRoute() {
        Route.CompiledRoute route = super.finalizeRoute();
        String limit = String.valueOf(this.getLimit());
        long localLastKey = this.lastKey;
        route = route.withQueryParams("limit", limit);
        if (localLastKey != 0L) {
            route = route.withQueryParams(this.order.getKey(), this.getPaginationLastEvaluatedKey(localLastKey, this.last));
        } else if (this.order == PaginationAction.PaginationOrder.FORWARD) {
            route = route.withQueryParams("after", this.getPaginationLastEvaluatedKey(0L, this.last));
        }
        return route;
    }

    protected List<T> getRemainingCache() {
        int index = this.getIteratorIndex();
        if (this.useCache && index > -1 && index < this.cached.size()) {
            return this.cached.subList(index, this.cached.size());
        }
        return Collections.emptyList();
    }

    public List<T> getNextChunk() {
        List list = this.getRemainingCache();
        if (!list.isEmpty()) {
            return list;
        }
        int current = this.limit.getAndSet(this.getMaxLimit());
        list = (List)this.complete();
        this.limit.set(current);
        return list;
    }

    protected abstract long getKey(T var1);

    protected int getIteratorIndex() {
        for (int i = 0; i < this.cached.size(); ++i) {
            if (this.getKey(this.cached.get(i)) != this.iteratorIndex) continue;
            return i + 1;
        }
        return -1;
    }

    protected void updateIndex(T it) {
        long key;
        this.iteratorIndex = key = this.getKey(it);
        if (!this.useCache) {
            this.lastKey = key;
            this.last = it;
        }
    }

    protected class ChainedConsumer
    implements Consumer<List<T>> {
        protected final CompletableFuture<?> task;
        protected final Procedure<? super T> action;
        protected final Consumer<Throwable> throwableConsumer;
        protected boolean initial = true;

        protected ChainedConsumer(CompletableFuture<?> task, Procedure<? super T> action, Consumer<Throwable> throwableConsumer) {
            this.task = task;
            this.action = action;
            this.throwableConsumer = throwableConsumer;
        }

        @Override
        public void accept(List<T> list) {
            if (list.isEmpty() && !this.initial) {
                this.task.complete(null);
                return;
            }
            this.initial = false;
            Object previous = null;
            for (Object it : list) {
                if (this.task.isCancelled()) {
                    if (previous != null) {
                        PaginationActionImpl.this.updateIndex(previous);
                    }
                    return;
                }
                if (this.action.execute(it)) {
                    previous = it;
                    continue;
                }
                PaginationActionImpl.this.updateIndex(it);
                this.task.complete(null);
                return;
            }
            int currentLimit = PaginationActionImpl.this.limit.getAndSet(PaginationActionImpl.this.maxLimit);
            PaginationActionImpl.this.queue(this, this.throwableConsumer);
            PaginationActionImpl.this.limit.set(currentLimit);
        }
    }
}

