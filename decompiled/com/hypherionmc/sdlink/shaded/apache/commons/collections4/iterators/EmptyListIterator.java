/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.ResettableListIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.AbstractEmptyIterator;
import java.util.ListIterator;

public class EmptyListIterator<E>
extends AbstractEmptyIterator<E>
implements ResettableListIterator<E> {
    public static final ResettableListIterator RESETTABLE_INSTANCE = new EmptyListIterator();
    public static final ListIterator INSTANCE = RESETTABLE_INSTANCE;

    public static <E> ResettableListIterator<E> resettableEmptyListIterator() {
        return RESETTABLE_INSTANCE;
    }

    public static <E> ListIterator<E> emptyListIterator() {
        return INSTANCE;
    }

    protected EmptyListIterator() {
    }
}

