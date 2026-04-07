/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Bag;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Predicate;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.SortedBag;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Transformer;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.bag.CollectionBag;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.bag.HashBag;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.bag.PredicatedBag;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.bag.PredicatedSortedBag;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.bag.SynchronizedBag;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.bag.SynchronizedSortedBag;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.bag.TransformedBag;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.bag.TransformedSortedBag;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.bag.TreeBag;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.bag.UnmodifiableBag;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.bag.UnmodifiableSortedBag;

public class BagUtils {
    public static final Bag EMPTY_BAG = UnmodifiableBag.unmodifiableBag(new HashBag());
    public static final Bag EMPTY_SORTED_BAG = UnmodifiableSortedBag.unmodifiableSortedBag(new TreeBag());

    private BagUtils() {
    }

    public static <E> Bag<E> synchronizedBag(Bag<E> bag) {
        return SynchronizedBag.synchronizedBag(bag);
    }

    public static <E> Bag<E> unmodifiableBag(Bag<? extends E> bag) {
        return UnmodifiableBag.unmodifiableBag(bag);
    }

    public static <E> Bag<E> predicatedBag(Bag<E> bag, Predicate<? super E> predicate) {
        return PredicatedBag.predicatedBag(bag, predicate);
    }

    public static <E> Bag<E> transformingBag(Bag<E> bag, Transformer<? super E, ? extends E> transformer) {
        return TransformedBag.transformingBag(bag, transformer);
    }

    public static <E> Bag<E> collectionBag(Bag<E> bag) {
        return CollectionBag.collectionBag(bag);
    }

    public static <E> SortedBag<E> synchronizedSortedBag(SortedBag<E> bag) {
        return SynchronizedSortedBag.synchronizedSortedBag(bag);
    }

    public static <E> SortedBag<E> unmodifiableSortedBag(SortedBag<E> bag) {
        return UnmodifiableSortedBag.unmodifiableSortedBag(bag);
    }

    public static <E> SortedBag<E> predicatedSortedBag(SortedBag<E> bag, Predicate<? super E> predicate) {
        return PredicatedSortedBag.predicatedSortedBag(bag, predicate);
    }

    public static <E> SortedBag<E> transformingSortedBag(SortedBag<E> bag, Transformer<? super E, ? extends E> transformer) {
        return TransformedSortedBag.transformingSortedBag(bag, transformer);
    }

    public static <E> Bag<E> emptyBag() {
        return EMPTY_BAG;
    }

    public static <E> SortedBag<E> emptySortedBag() {
        return (SortedBag)EMPTY_SORTED_BAG;
    }
}

