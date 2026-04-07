/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.ISnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Member;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Role;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ClosableIterator;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.CacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.MemberCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.SnowflakeCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.UnifiedMemberCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.ChainedClosableIterator;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class UnifiedCacheViewImpl<T, E extends CacheView<T>>
implements CacheView<T> {
    protected final Supplier<? extends Stream<? extends E>> generator;

    public UnifiedCacheViewImpl(Supplier<? extends Stream<? extends E>> generator) {
        this.generator = generator;
    }

    @Override
    public long size() {
        return this.distinctStream().mapToLong(CacheView::size).sum();
    }

    @Override
    public boolean isEmpty() {
        return this.distinctStream().allMatch(CacheView::isEmpty);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        try (ClosableIterator it = this.lockedIterator();){
            while (it.hasNext()) {
                action.accept(it.next());
            }
        }
    }

    @Override
    @Nonnull
    public List<T> asList() {
        LinkedList list = new LinkedList();
        this.forEach(list::add);
        return Collections.unmodifiableList(list);
    }

    @Override
    @Nonnull
    public Set<T> asSet() {
        try (ClosableIterator it = this.lockedIterator();){
            while (((ChainedClosableIterator)it).hasNext()) {
                ((ChainedClosableIterator)it).next();
            }
            Set set = Collections.unmodifiableSet(((ChainedClosableIterator)it).getItems());
            return set;
        }
    }

    @Override
    @Nonnull
    public ChainedClosableIterator<T> lockedIterator() {
        Iterator gen = this.generator.get().iterator();
        return new ChainedClosableIterator(gen);
    }

    @Override
    @Nonnull
    public List<T> getElementsByName(@Nonnull String name, boolean ignoreCase) {
        return this.distinctStream().flatMap(view -> view.getElementsByName(name, ignoreCase).stream()).distinct().collect(Helpers.toUnmodifiableList());
    }

    @Override
    @Nonnull
    public Stream<T> stream() {
        return this.distinctStream().flatMap(CacheView::stream).distinct();
    }

    @Override
    @Nonnull
    public Stream<T> parallelStream() {
        return this.distinctStream().flatMap(CacheView::parallelStream).distinct();
    }

    @Override
    @Nonnull
    public Iterator<T> iterator() {
        return this.stream().iterator();
    }

    protected Stream<? extends E> distinctStream() {
        return this.generator.get().distinct();
    }

    public static class UnifiedMemberCacheViewImpl
    extends UnifiedCacheViewImpl<Member, MemberCacheView>
    implements UnifiedMemberCacheView {
        public UnifiedMemberCacheViewImpl(Supplier<? extends Stream<? extends MemberCacheView>> generator) {
            super(generator);
        }

        @Override
        @Nonnull
        public List<Member> getElementsById(long id) {
            return this.distinctStream().map(view -> view.getElementById(id)).filter(Objects::nonNull).collect(Helpers.toUnmodifiableList());
        }

        @Override
        @Nonnull
        public List<Member> getElementsByUsername(@Nonnull String name, boolean ignoreCase) {
            return this.distinctStream().flatMap(view -> view.getElementsByUsername(name, ignoreCase).stream()).collect(Helpers.toUnmodifiableList());
        }

        @Override
        @Nonnull
        public List<Member> getElementsByNickname(@Nullable String name, boolean ignoreCase) {
            return this.distinctStream().flatMap(view -> view.getElementsByNickname(name, ignoreCase).stream()).collect(Helpers.toUnmodifiableList());
        }

        @Override
        @Nonnull
        public List<Member> getElementsWithRoles(Role ... roles) {
            return this.distinctStream().flatMap(view -> view.getElementsWithRoles(roles).stream()).collect(Helpers.toUnmodifiableList());
        }

        @Override
        @Nonnull
        public List<Member> getElementsWithRoles(@Nonnull Collection<Role> roles) {
            return this.distinctStream().flatMap(view -> view.getElementsWithRoles(roles).stream()).collect(Helpers.toUnmodifiableList());
        }
    }

    public static class UnifiedSnowflakeCacheView<T extends ISnowflake>
    extends UnifiedCacheViewImpl<T, SnowflakeCacheView<T>>
    implements SnowflakeCacheView<T> {
        public UnifiedSnowflakeCacheView(Supplier<? extends Stream<? extends SnowflakeCacheView<T>>> generator) {
            super(generator);
        }

        @Override
        public T getElementById(long id) {
            return (T)((ISnowflake)((Stream)this.generator.get()).map(view -> view.getElementById(id)).filter(Objects::nonNull).findFirst().orElse(null));
        }
    }
}

