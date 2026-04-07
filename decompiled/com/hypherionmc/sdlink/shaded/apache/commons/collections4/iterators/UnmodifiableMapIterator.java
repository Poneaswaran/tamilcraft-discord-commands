/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.MapIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Unmodifiable;

public final class UnmodifiableMapIterator<K, V>
implements MapIterator<K, V>,
Unmodifiable {
    private final MapIterator<? extends K, ? extends V> iterator;

    public static <K, V> MapIterator<K, V> unmodifiableMapIterator(MapIterator<? extends K, ? extends V> iterator2) {
        if (iterator2 == null) {
            throw new NullPointerException("MapIterator must not be null");
        }
        if (iterator2 instanceof Unmodifiable) {
            MapIterator<? extends K, ? extends V> tmpIterator = iterator2;
            return tmpIterator;
        }
        return new UnmodifiableMapIterator<K, V>(iterator2);
    }

    private UnmodifiableMapIterator(MapIterator<? extends K, ? extends V> iterator2) {
        this.iterator = iterator2;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public K next() {
        return this.iterator.next();
    }

    @Override
    public K getKey() {
        return this.iterator.getKey();
    }

    @Override
    public V getValue() {
        return this.iterator.getValue();
    }

    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException("setValue() is not supported");
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() is not supported");
    }
}

