/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.SortedChannelCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.UnlockHook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.ChannelCacheViewImpl;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.Spliterator;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SortedChannelCacheViewImpl<T extends Channel & Comparable<? super T>>
extends ChannelCacheViewImpl<T>
implements SortedChannelCacheView<T> {
    public SortedChannelCacheViewImpl(Class<T> type) {
        super(type);
    }

    @Override
    @Nonnull
    public <C extends T> SortedFilteredCacheView<C> ofType(@Nonnull Class<C> type) {
        return new SortedFilteredCacheView<C>(type);
    }

    @Override
    @Nonnull
    public List<T> asList() {
        List<Object> list = this.getCachedList();
        if (list == null) {
            list = this.cache(new ArrayList(this.asSet()));
        }
        return list;
    }

    @Override
    @Nonnull
    public NavigableSet<T> asSet() {
        NavigableSet set = (NavigableSet)this.getCachedSet();
        if (set == null) {
            set = this.cache(this.applyStream(stream -> stream.collect(Collectors.toCollection(TreeSet::new))));
        }
        return set;
    }

    @Override
    public void forEachUnordered(@Nonnull Consumer<? super T> action) {
        super.forEach(action);
    }

    @Override
    public void forEach(@Nonnull Consumer<? super T> action) {
        this.asSet().forEach(action);
    }

    @Override
    @Nonnull
    public List<T> getElementsByName(@Nonnull String name) {
        List elements = super.getElementsByName(name);
        elements.sort(Comparator.naturalOrder());
        return elements;
    }

    @Override
    @Nonnull
    public Stream<T> streamUnordered() {
        try (UnlockHook hook = this.readLock();){
            Stream stream = this.caches.values().stream().flatMap(cache -> cache.valueCollection().stream()).collect(Collectors.toList()).stream();
            return stream;
        }
    }

    @Override
    @Nonnull
    public Stream<T> parallelStreamUnordered() {
        return (Stream)this.streamUnordered().parallel();
    }

    @Override
    public Spliterator<T> spliterator() {
        return this.asSet().spliterator();
    }

    @Override
    @Nonnull
    public Iterator<T> iterator() {
        return this.asSet().iterator();
    }

    public class SortedFilteredCacheView<C extends T>
    extends ChannelCacheViewImpl.FilteredCacheView<C>
    implements SortedChannelCacheView<C> {
        protected SortedFilteredCacheView(Class<C> type) {
            super(SortedChannelCacheViewImpl.this, type);
        }

        @Override
        @Nonnull
        public List<C> asList() {
            return this.applyStream(stream -> stream.sorted().collect(Helpers.toUnmodifiableList()));
        }

        @Override
        @Nonnull
        public NavigableSet<C> asSet() {
            return this.applyStream(stream -> stream.collect(Collectors.collectingAndThen(Collectors.toCollection(TreeSet::new), Collections::unmodifiableNavigableSet)));
        }

        @Override
        @Nonnull
        public List<C> getElementsByName(@Nonnull String name, boolean ignoreCase) {
            Checks.notEmpty(name, "Name");
            return this.applyStream(stream -> stream.filter(it -> Helpers.equals(name, it.getName(), ignoreCase)).sorted().collect(Helpers.toUnmodifiableList()));
        }

        @Override
        @Nonnull
        public Stream<C> streamUnordered() {
            List elements = this.applyStream(stream -> stream.filter(x$0 -> this.type.isInstance(x$0)).collect(Collectors.toList()));
            return elements.stream();
        }

        @Override
        @Nonnull
        public Stream<C> parallelStreamUnordered() {
            return (Stream)this.stream().parallel();
        }

        @Override
        @Nonnull
        public <C1 extends C> SortedChannelCacheView<C1> ofType(@Nonnull Class<C1> type) {
            return SortedChannelCacheViewImpl.this.ofType(type);
        }

        @Override
        public void forEachUnordered(@Nonnull Consumer<? super C> action) {
            super.forEach(action);
        }

        @Override
        public void forEach(Consumer<? super C> action) {
            this.stream().forEach(action);
        }
    }
}

