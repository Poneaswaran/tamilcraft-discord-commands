/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Get;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.MapIterator;

public interface IterableGet<K, V>
extends Get<K, V> {
    public MapIterator<K, V> mapIterator();
}

