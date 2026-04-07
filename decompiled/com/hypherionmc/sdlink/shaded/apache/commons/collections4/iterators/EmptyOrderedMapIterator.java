/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.OrderedMapIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.ResettableIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.AbstractEmptyMapIterator;

public class EmptyOrderedMapIterator<K, V>
extends AbstractEmptyMapIterator<K, V>
implements OrderedMapIterator<K, V>,
ResettableIterator<K> {
    public static final OrderedMapIterator INSTANCE = new EmptyOrderedMapIterator();

    public static <K, V> OrderedMapIterator<K, V> emptyOrderedMapIterator() {
        return INSTANCE;
    }

    protected EmptyOrderedMapIterator() {
    }
}

