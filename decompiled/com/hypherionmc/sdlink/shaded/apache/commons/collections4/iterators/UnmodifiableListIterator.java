/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Unmodifiable;
import java.util.ListIterator;

public final class UnmodifiableListIterator<E>
implements ListIterator<E>,
Unmodifiable {
    private final ListIterator<? extends E> iterator;

    public static <E> ListIterator<E> umodifiableListIterator(ListIterator<? extends E> iterator2) {
        if (iterator2 == null) {
            throw new NullPointerException("ListIterator must not be null");
        }
        if (iterator2 instanceof Unmodifiable) {
            ListIterator<? extends E> tmpIterator = iterator2;
            return tmpIterator;
        }
        return new UnmodifiableListIterator<E>(iterator2);
    }

    private UnmodifiableListIterator(ListIterator<? extends E> iterator2) {
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
    public int nextIndex() {
        return this.iterator.nextIndex();
    }

    @Override
    public boolean hasPrevious() {
        return this.iterator.hasPrevious();
    }

    @Override
    public E previous() {
        return this.iterator.previous();
    }

    @Override
    public int previousIndex() {
        return this.iterator.previousIndex();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() is not supported");
    }

    @Override
    public void set(E obj) {
        throw new UnsupportedOperationException("set() is not supported");
    }

    @Override
    public void add(E obj) {
        throw new UnsupportedOperationException("add() is not supported");
    }
}

