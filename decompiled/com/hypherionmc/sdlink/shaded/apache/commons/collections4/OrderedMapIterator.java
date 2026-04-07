/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.MapIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.OrderedIterator;

public interface OrderedMapIterator<K, V>
extends MapIterator<K, V>,
OrderedIterator<K> {
    @Override
    public boolean hasPrevious();

    @Override
    public K previous();
}

