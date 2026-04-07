/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.ResettableIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.ListIteratorWrapper;
import java.util.Iterator;

public class IteratorIterable<E>
implements Iterable<E> {
    private final Iterator<? extends E> iterator;
    private final Iterator<E> typeSafeIterator;

    private static <E> Iterator<E> createTypesafeIterator(final Iterator<? extends E> iterator2) {
        return new Iterator<E>(){

            @Override
            public boolean hasNext() {
                return iterator2.hasNext();
            }

            @Override
            public E next() {
                return iterator2.next();
            }

            @Override
            public void remove() {
                iterator2.remove();
            }
        };
    }

    public IteratorIterable(Iterator<? extends E> iterator2) {
        this(iterator2, false);
    }

    public IteratorIterable(Iterator<? extends E> iterator2, boolean multipleUse) {
        this.iterator = multipleUse && !(iterator2 instanceof ResettableIterator) ? new ListIteratorWrapper<E>(iterator2) : iterator2;
        this.typeSafeIterator = IteratorIterable.createTypesafeIterator(this.iterator);
    }

    @Override
    public Iterator<E> iterator() {
        if (this.iterator instanceof ResettableIterator) {
            ((ResettableIterator)this.iterator).reset();
        }
        return this.typeSafeIterator;
    }
}

