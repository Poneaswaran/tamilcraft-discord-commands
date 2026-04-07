/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.ResettableIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.AbstractEmptyIterator;
import java.util.Iterator;

public class EmptyIterator<E>
extends AbstractEmptyIterator<E>
implements ResettableIterator<E> {
    public static final ResettableIterator RESETTABLE_INSTANCE = new EmptyIterator();
    public static final Iterator INSTANCE = RESETTABLE_INSTANCE;

    public static <E> ResettableIterator<E> resettableEmptyIterator() {
        return RESETTABLE_INSTANCE;
    }

    public static <E> Iterator<E> emptyIterator() {
        return INSTANCE;
    }

    protected EmptyIterator() {
    }
}

