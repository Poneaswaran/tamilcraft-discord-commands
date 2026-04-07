/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators;

import java.util.Iterator;

public abstract class AbstractUntypedIteratorDecorator<I, O>
implements Iterator<O> {
    private final Iterator<I> iterator;

    protected AbstractUntypedIteratorDecorator(Iterator<I> iterator2) {
        if (iterator2 == null) {
            throw new NullPointerException("Iterator must not be null");
        }
        this.iterator = iterator2;
    }

    protected Iterator<I> getIterator() {
        return this.iterator;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public void remove() {
        this.iterator.remove();
    }
}

