/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ClosableIterator;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.ChannelCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.ChainedClosableIterator;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnifiedChannelCacheView<C extends Channel>
implements ChannelCacheView<C> {
    private final Supplier<Stream<ChannelCacheView<C>>> supplier;

    public UnifiedChannelCacheView(Supplier<Stream<ChannelCacheView<C>>> supplier) {
        this.supplier = supplier;
    }

    @Override
    public void forEach(Consumer<? super C> action) {
        Objects.requireNonNull(action, "Consumer");
        try (ClosableIterator<C> iterator2 = this.lockedIterator();){
            while (iterator2.hasNext()) {
                action.accept(iterator2.next());
            }
        }
    }

    @Override
    @Nonnull
    public List<C> asList() {
        return this.stream().collect(Helpers.toUnmodifiableList());
    }

    @Override
    @Nonnull
    public Set<C> asSet() {
        return this.stream().collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
    }

    @Override
    @Nonnull
    public ClosableIterator<C> lockedIterator() {
        return new ChainedClosableIterator(this.supplier.get().iterator());
    }

    @Override
    public long size() {
        return this.supplier.get().mapToLong(CacheView::size).sum();
    }

    @Override
    public boolean isEmpty() {
        return this.supplier.get().allMatch(CacheView::isEmpty);
    }

    @Override
    @Nonnull
    public List<C> getElementsByName(@Nonnull String name, boolean ignoreCase) {
        return this.supplier.get().flatMap(view -> view.getElementsByName(name, ignoreCase).stream()).collect(Helpers.toUnmodifiableList());
    }

    @Override
    @Nonnull
    public Stream<C> stream() {
        return this.supplier.get().flatMap(CacheView::stream);
    }

    @Override
    @Nonnull
    public Stream<C> parallelStream() {
        return ((Stream)this.supplier.get().parallel()).flatMap(CacheView::parallelStream);
    }

    @Override
    @Nonnull
    public <T extends C> ChannelCacheView<T> ofType(@Nonnull Class<T> type) {
        Checks.notNull(type, "Type");
        return new UnifiedChannelCacheView<C>(() -> this.supplier.get().map(view -> view.ofType(type)));
    }

    @Override
    @Nullable
    public C getElementById(@Nonnull ChannelType type, long id) {
        return (C)((Channel)this.supplier.get().map(view -> view.getElementById(type, id)).filter(Objects::nonNull).findFirst().orElse(null));
    }

    @Override
    @Nullable
    public C getElementById(long id) {
        return (C)((Channel)this.supplier.get().map(view -> (Channel)view.getElementById(id)).filter(Objects::nonNull).findFirst().orElse(null));
    }

    @Override
    @Nonnull
    public Iterator<C> iterator() {
        return this.stream().iterator();
    }
}

