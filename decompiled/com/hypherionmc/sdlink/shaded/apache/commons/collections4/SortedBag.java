/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Bag;
import java.util.Comparator;

public interface SortedBag<E>
extends Bag<E> {
    public Comparator<? super E> comparator();

    public E first();

    public E last();
}

