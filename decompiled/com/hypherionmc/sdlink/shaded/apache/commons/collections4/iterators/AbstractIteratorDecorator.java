/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.AbstractUntypedIteratorDecorator;
import java.util.Iterator;

public abstract class AbstractIteratorDecorator<E>
extends AbstractUntypedIteratorDecorator<E, E> {
    protected AbstractIteratorDecorator(Iterator<E> iterator2) {
        super(iterator2);
    }

    @Override
    public E next() {
        return (E)this.getIterator().next();
    }
}

