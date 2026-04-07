/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.OrderedIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.ResettableIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.AbstractEmptyIterator;

public class EmptyOrderedIterator<E>
extends AbstractEmptyIterator<E>
implements OrderedIterator<E>,
ResettableIterator<E> {
    public static final OrderedIterator INSTANCE = new EmptyOrderedIterator();

    public static <E> OrderedIterator<E> emptyOrderedIterator() {
        return INSTANCE;
    }

    protected EmptyOrderedIterator() {
    }
}

