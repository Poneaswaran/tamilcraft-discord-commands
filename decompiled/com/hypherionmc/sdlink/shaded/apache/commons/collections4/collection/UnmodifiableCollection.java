/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.collection;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Unmodifiable;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.collection.AbstractCollectionDecorator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.UnmodifiableIterator;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

public final class UnmodifiableCollection<E>
extends AbstractCollectionDecorator<E>
implements Unmodifiable {
    private static final long serialVersionUID = -239892006883819945L;

    public static <T> Collection<T> unmodifiableCollection(Collection<? extends T> coll) {
        if (coll instanceof Unmodifiable) {
            Collection<? extends T> tmpColl = coll;
            return tmpColl;
        }
        return new UnmodifiableCollection<T>(coll);
    }

    private UnmodifiableCollection(Collection<? extends E> coll) {
        super(coll);
    }

    @Override
    public Iterator<E> iterator() {
        return UnmodifiableIterator.unmodifiableIterator(this.decorated().iterator());
    }

    @Override
    public boolean add(E object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> coll) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> coll) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> coll) {
        throw new UnsupportedOperationException();
    }
}

