/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.Channel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.ChannelType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.ClosableIterator;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.LockIterator;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.cache.ChannelCacheView;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.UnlockHook;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.cache.ReadWriteLockCache;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TLongObjectMap;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.hash.TLongObjectHashMap;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChannelCacheViewImpl<T extends Channel>
extends ReadWriteLockCache<T>
implements ChannelCacheView<T> {
    protected final EnumMap<ChannelType, TLongObjectMap<T>> caches = new EnumMap(ChannelType.class);

    public ChannelCacheViewImpl(Class<T> type) {
        for (ChannelType channelType : ChannelType.values()) {
            channelType = this.normalizeKey(channelType);
            Class<? extends Channel> clazz = channelType.getInterface();
            if (channelType == ChannelType.UNKNOWN || !type.isAssignableFrom(clazz)) continue;
            this.caches.put(channelType, new TLongObjectHashMap());
        }
    }

    protected ChannelType normalizeKey(ChannelType type) {
        return type.isThread() ? ChannelType.GUILD_PUBLIC_THREAD : type;
    }

    @Nullable
    protected <C extends T> TLongObjectMap<C> getMap(@Nonnull ChannelType type) {
        return this.caches.get((Object)this.normalizeKey(type));
    }

    @Nullable
    public <C extends T> C put(C element) {
        try (UnlockHook hook = this.writeLock();){
            Channel channel = (Channel)this.getMap(element.getType()).put(element.getIdLong(), element);
            return (C)channel;
        }
    }

    @Nullable
    public <C extends T> C remove(ChannelType type, long id) {
        try (UnlockHook hook = this.writeLock();){
            Channel removed;
            Channel channel = removed = (Channel)this.getMap(type).remove(id);
            return (C)channel;
        }
    }

    public <C extends T> C remove(C channel) {
        return this.remove(channel.getType(), channel.getIdLong());
    }

    public <C extends T> void removeIf(Class<C> typeFilter, Predicate<? super C> predicate) {
        try (UnlockHook hook = this.writeLock();){
            ((FilteredCacheView)this.ofType((Class)typeFilter)).removeIf(predicate);
        }
    }

    public void clear() {
        try (UnlockHook hook = this.writeLock();){
            this.caches.values().forEach(TLongObjectMap::clear);
        }
    }

    @Override
    @Nonnull
    public <C extends T> FilteredCacheView<C> ofType(@Nonnull Class<C> type) {
        return new FilteredCacheView<C>(type);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        try (UnlockHook hook = this.readLock();){
            for (TLongObjectMap<T> cache : this.caches.values()) {
                cache.valueCollection().forEach(action);
            }
        }
    }

    @Override
    @Nonnull
    public List<T> asList() {
        List list = this.getCachedList();
        if (list == null) {
            list = this.cache(this.applyStream(stream -> stream.collect(Collectors.toList())));
        }
        return list;
    }

    @Override
    @Nonnull
    public Set<T> asSet() {
        Set set = this.getCachedSet();
        if (set == null) {
            set = this.cache(this.applyStream(stream -> stream.collect(Collectors.toSet())));
        }
        return set;
    }

    @Override
    @Nonnull
    public ClosableIterator<T> lockedIterator() {
        ReentrantReadWriteLock.ReadLock readLock = this.lock.readLock();
        MiscUtil.tryLock(readLock);
        try {
            Iterator directIterator = this.caches.values().stream().flatMap(map -> map.valueCollection().stream()).iterator();
            return new LockIterator(directIterator, readLock);
        }
        catch (Throwable t) {
            readLock.unlock();
            throw t;
        }
    }

    @Override
    public long size() {
        try (UnlockHook hook = this.readLock();){
            long l = this.caches.values().stream().mapToLong(TLongObjectMap::size).sum();
            return l;
        }
    }

    @Override
    public boolean isEmpty() {
        try (UnlockHook hook = this.readLock();){
            boolean bl = this.caches.values().stream().allMatch(TLongObjectMap::isEmpty);
            return bl;
        }
    }

    @Override
    @Nonnull
    public List<T> getElementsByName(@Nonnull String name, boolean ignoreCase) {
        Checks.notEmpty(name, "Name");
        return this.applyStream(stream -> stream.filter(channel -> Helpers.equals(channel.getName(), name, ignoreCase)).collect(Helpers.toUnmodifiableList()));
    }

    @Override
    @Nonnull
    public Stream<T> stream() {
        return this.asList().stream();
    }

    @Override
    @Nonnull
    public Stream<T> parallelStream() {
        return this.asList().parallelStream();
    }

    @Override
    @Nullable
    public T getElementById(long id) {
        try (UnlockHook hook = this.readLock();){
            for (TLongObjectMap<T> cache : this.caches.values()) {
                Channel element = (Channel)cache.get(id);
                if (element == null) continue;
                Channel channel = element;
                return (T)channel;
            }
            Iterator<TLongObjectMap<T>> iterator2 = null;
            return (T)iterator2;
        }
    }

    @Override
    public T getElementById(@Nonnull ChannelType type, long id) {
        Checks.notNull((Object)type, "ChannelType");
        try (UnlockHook hook = this.readLock();){
            TLongObjectMap map = this.getMap(type);
            Channel channel = map == null ? null : (Channel)map.get(id);
            return (T)channel;
        }
    }

    @Override
    @Nonnull
    public Iterator<T> iterator() {
        return this.stream().iterator();
    }

    public class FilteredCacheView<C extends T>
    implements ChannelCacheView<C> {
        protected final Class<C> type;
        protected final List<TLongObjectMap<C>> filteredMaps;

        protected FilteredCacheView(Class<C> type) {
            Checks.notNull(type, "Type");
            this.type = type;
            this.filteredMaps = ChannelCacheViewImpl.this.caches.entrySet().stream().filter(entry -> entry.getKey() != null && type.isAssignableFrom(((ChannelType)((Object)((Object)entry.getKey()))).getInterface())).map(entry -> (TLongObjectMap)entry.getValue()).collect(Collectors.toList());
        }

        protected void removeIf(Predicate<? super C> filter) {
            this.filteredMaps.forEach(map -> map.valueCollection().removeIf(filter));
        }

        @Override
        @Nonnull
        public List<C> asList() {
            return this.applyStream(stream -> stream.collect(Helpers.toUnmodifiableList()));
        }

        @Override
        @Nonnull
        public Set<C> asSet() {
            return this.applyStream(stream -> stream.collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet)));
        }

        @Override
        @Nonnull
        public ClosableIterator<C> lockedIterator() {
            ReentrantReadWriteLock.ReadLock readLock = ChannelCacheViewImpl.this.lock.readLock();
            MiscUtil.tryLock(readLock);
            try {
                Iterator directIterator = this.filteredMaps.stream().flatMap(map -> map.valueCollection().stream()).iterator();
                return new LockIterator(directIterator, readLock);
            }
            catch (Throwable t) {
                readLock.unlock();
                throw t;
            }
        }

        @Override
        public long size() {
            try (UnlockHook hook = ChannelCacheViewImpl.this.readLock();){
                long l = this.filteredMaps.stream().mapToLong(TLongObjectMap::size).sum();
                return l;
            }
        }

        @Override
        public boolean isEmpty() {
            try (UnlockHook hook = ChannelCacheViewImpl.this.readLock();){
                boolean bl = this.filteredMaps.stream().allMatch(TLongObjectMap::isEmpty);
                return bl;
            }
        }

        @Override
        @Nonnull
        public List<C> getElementsByName(@Nonnull String name, boolean ignoreCase) {
            Checks.notEmpty(name, "Name");
            return this.applyStream(stream -> stream.filter(channel -> Helpers.equals(channel.getName(), name, ignoreCase)).collect(Helpers.toUnmodifiableList()));
        }

        @Override
        @Nonnull
        public Stream<C> stream() {
            return this.asList().stream();
        }

        @Override
        @Nonnull
        public Stream<C> parallelStream() {
            return this.asList().parallelStream();
        }

        @Override
        @Nonnull
        public <C1 extends C> ChannelCacheView<C1> ofType(@Nonnull Class<C1> type) {
            return ChannelCacheViewImpl.this.ofType(type);
        }

        @Override
        @Nullable
        public C getElementById(@Nonnull ChannelType type, long id) {
            Object channel = ChannelCacheViewImpl.this.getElementById(type, id);
            return (C)(this.type.isInstance(channel) ? (Channel)this.type.cast(channel) : null);
        }

        @Override
        @Nullable
        public C getElementById(long id) {
            try (UnlockHook hook = ChannelCacheViewImpl.this.readLock();){
                Channel channel = this.filteredMaps.stream().map(it -> (Channel)it.get(id)).filter(Objects::nonNull).findFirst().orElse(null);
                return (C)channel;
            }
        }

        @Override
        @Nonnull
        public Iterator<C> iterator() {
            return this.asList().iterator();
        }
    }
}

