/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.IterableMap;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.OrderedMapIterator;

public interface OrderedMap<K, V>
extends IterableMap<K, V> {
    @Override
    public OrderedMapIterator<K, V> mapIterator();

    public K firstKey();

    public K lastKey();

    public K nextKey(K var1);

    public K previousKey(K var1);
}

