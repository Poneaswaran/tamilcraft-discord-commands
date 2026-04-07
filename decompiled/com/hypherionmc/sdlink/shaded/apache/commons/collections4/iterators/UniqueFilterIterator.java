/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.UniquePredicate;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.FilterIterator;
import java.util.Iterator;

public class UniqueFilterIterator<E>
extends FilterIterator<E> {
    public UniqueFilterIterator(Iterator<? extends E> iterator2) {
        super(iterator2, UniquePredicate.uniquePredicate());
    }
}

