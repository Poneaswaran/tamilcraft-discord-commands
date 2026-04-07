/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.gnu.trove.iterator;

import com.hypherionmc.sdlink.shaded.gnu.trove.iterator.TAdvancingIterator;

public interface TIntObjectIterator<V>
extends TAdvancingIterator {
    public int key();

    public V value();

    public V setValue(V var1);
}

