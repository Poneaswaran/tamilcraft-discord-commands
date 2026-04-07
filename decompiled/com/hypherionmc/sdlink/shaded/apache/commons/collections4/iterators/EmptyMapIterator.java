/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.MapIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.ResettableIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.AbstractEmptyMapIterator;

public class EmptyMapIterator<K, V>
extends AbstractEmptyMapIterator<K, V>
implements MapIterator<K, V>,
ResettableIterator<K> {
    public static final MapIterator INSTANCE = new EmptyMapIterator();

    public static <K, V> MapIterator<K, V> emptyMapIterator() {
        return INSTANCE;
    }

    protected EmptyMapIterator() {
    }
}

