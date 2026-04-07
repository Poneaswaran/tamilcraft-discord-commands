/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Unmodifiable;
import java.util.Iterator;

public final class UnmodifiableIterator<E>
implements Iterator<E>,
Unmodifiable {
    private final Iterator<? extends E> iterator;

    public static <E> Iterator<E> unmodifiableIterator(Iterator<? extends E> iterator2) {
        if (iterator2 == null) {
            throw new NullPointerException("Iterator must not be null");
        }
        if (iterator2 instanceof Unmodifiable) {
            Iterator<? extends E> tmpIterator = iterator2;
            return tmpIterator;
        }
        return new UnmodifiableIterator<E>(iterator2);
    }

    private UnmodifiableIterator(Iterator<? extends E> iterator2) {
        this.iterator = iterator2;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public E next() {
        return this.iterator.next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() is not supported");
    }
}

