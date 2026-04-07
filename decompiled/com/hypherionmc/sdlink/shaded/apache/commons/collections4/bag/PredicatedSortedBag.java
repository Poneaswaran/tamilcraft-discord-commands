/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.bag;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Predicate;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.SortedBag;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.bag.PredicatedBag;
import java.util.Comparator;

public class PredicatedSortedBag<E>
extends PredicatedBag<E>
implements SortedBag<E> {
    private static final long serialVersionUID = 3448581314086406616L;

    public static <E> PredicatedSortedBag<E> predicatedSortedBag(SortedBag<E> bag, Predicate<? super E> predicate) {
        return new PredicatedSortedBag<E>(bag, predicate);
    }

    protected PredicatedSortedBag(SortedBag<E> bag, Predicate<? super E> predicate) {
        super(bag, predicate);
    }

    @Override
    protected SortedBag<E> decorated() {
        return (SortedBag)super.decorated();
    }

    @Override
    public E first() {
        return this.decorated().first();
    }

    @Override
    public E last() {
        return this.decorated().last();
    }

    @Override
    public Comparator<? super E> comparator() {
        return this.decorated().comparator();
    }
}

