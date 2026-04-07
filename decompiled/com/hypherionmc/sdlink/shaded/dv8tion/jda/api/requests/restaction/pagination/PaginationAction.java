/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Blocking
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.pagination;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.RestAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.Procedure;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.CheckReturnValue;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.Unmodifiable;

public interface PaginationAction<T, M extends PaginationAction<T, M>>
extends RestAction<List<T>>,
Iterable<T> {
    @Nonnull
    @CheckReturnValue
    public M skipTo(long var1);

    public long getLastKey();

    @Nonnull
    @CheckReturnValue
    public M setCheck(@Nullable BooleanSupplier var1);

    @Nonnull
    @CheckReturnValue
    public M timeout(long var1, @Nonnull TimeUnit var3);

    @Nonnull
    @CheckReturnValue
    public M deadline(long var1);

    @Nonnull
    default public EnumSet<PaginationOrder> getSupportedOrders() {
        return EnumSet.allOf(PaginationOrder.class);
    }

    @Nonnull
    public PaginationOrder getOrder();

    @Nonnull
    @CheckReturnValue
    public M order(@Nonnull PaginationOrder var1);

    @Nonnull
    @CheckReturnValue
    default public M reverse() {
        if (this.getOrder() == PaginationOrder.BACKWARD) {
            return this.order(PaginationOrder.FORWARD);
        }
        return this.order(PaginationOrder.BACKWARD);
    }

    public int cacheSize();

    public boolean isEmpty();

    @Nonnull
    public @Unmodifiable List<T> getCached();

    @Nonnull
    public T getLast();

    @Nonnull
    public T getFirst();

    @Nonnull
    @CheckReturnValue
    public M limit(int var1);

    @Nonnull
    @CheckReturnValue
    public M cache(boolean var1);

    public boolean isCacheEnabled();

    public int getMaxLimit();

    public int getMinLimit();

    public int getLimit();

    @Nonnull
    @CheckReturnValue
    default public CompletableFuture<List<T>> takeWhileAsync(@Nonnull Predicate<? super T> rule) {
        Checks.notNull(rule, "Rule");
        return this.takeUntilAsync(rule.negate());
    }

    @Nonnull
    @CheckReturnValue
    default public CompletableFuture<List<T>> takeWhileAsync(int limit, @Nonnull Predicate<? super T> rule) {
        Checks.notNull(rule, "Rule");
        return this.takeUntilAsync(limit, rule.negate());
    }

    @Nonnull
    @CheckReturnValue
    default public CompletableFuture<List<T>> takeUntilAsync(@Nonnull Predicate<? super T> rule) {
        return this.takeUntilAsync(0, rule);
    }

    @Nonnull
    @CheckReturnValue
    default public CompletableFuture<List<T>> takeUntilAsync(int limit, @Nonnull Predicate<? super T> rule) {
        Checks.notNull(rule, "Rule");
        Checks.notNegative(limit, "Limit");
        ArrayList result = new ArrayList();
        CompletableFuture future = new CompletableFuture();
        CompletableFuture<?> handle = this.forEachAsync(element -> {
            if (rule.test(element)) {
                return false;
            }
            result.add(element);
            return limit == 0 || limit > result.size();
        });
        handle.whenComplete((r, t) -> {
            if (t != null) {
                future.completeExceptionally((Throwable)t);
            } else {
                future.complete(result);
            }
        });
        return future;
    }

    @Nonnull
    @CheckReturnValue
    public CompletableFuture<List<T>> takeAsync(int var1);

    @Nonnull
    @CheckReturnValue
    public CompletableFuture<List<T>> takeRemainingAsync(int var1);

    @Nonnull
    @CheckReturnValue
    default public CompletableFuture<?> forEachAsync(@Nonnull Procedure<? super T> action) {
        return this.forEachAsync(action, RestActionImpl.getDefaultFailure());
    }

    @Nonnull
    @CheckReturnValue
    public CompletableFuture<?> forEachAsync(@Nonnull Procedure<? super T> var1, @Nonnull Consumer<? super Throwable> var2);

    @Nonnull
    @CheckReturnValue
    default public CompletableFuture<?> forEachRemainingAsync(@Nonnull Procedure<? super T> action) {
        return this.forEachRemainingAsync(action, RestActionImpl.getDefaultFailure());
    }

    @Nonnull
    @CheckReturnValue
    public CompletableFuture<?> forEachRemainingAsync(@Nonnull Procedure<? super T> var1, @Nonnull Consumer<? super Throwable> var2);

    @Blocking
    public void forEachRemaining(@Nonnull Procedure<? super T> var1);

    @Override
    @Nonnull
    @Blocking
    default public Spliterator<T> spliterator() {
        return Spliterators.spliteratorUnknownSize(this.iterator(), 1024);
    }

    @Nonnull
    @Blocking
    default public Stream<T> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    @Nonnull
    @Blocking
    default public Stream<T> parallelStream() {
        return StreamSupport.stream(this.spliterator(), true);
    }

    @Override
    @Nonnull
    @Blocking
    public PaginationIterator<T> iterator();

    public static enum PaginationOrder {
        BACKWARD("before"),
        FORWARD("after");

        private final String key;

        private PaginationOrder(String key) {
            this.key = key;
        }

        @Nonnull
        public String getKey() {
            return this.key;
        }
    }

    public static class PaginationIterator<E>
    implements Iterator<E> {
        protected Queue<E> items;
        protected final Supplier<List<E>> supply;

        public PaginationIterator(Collection<E> queue, Supplier<List<E>> supply) {
            this.items = new LinkedList<E>(queue);
            this.supply = supply;
        }

        @Override
        public boolean hasNext() {
            if (this.items == null) {
                return false;
            }
            if (!this.hitEnd()) {
                return true;
            }
            if (this.items.addAll(this.supply.get())) {
                return true;
            }
            this.items = null;
            return false;
        }

        @Override
        @Nonnull
        public E next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("Reached End of pagination task!");
            }
            return this.items.poll();
        }

        protected boolean hitEnd() {
            return this.items.isEmpty();
        }
    }
}

