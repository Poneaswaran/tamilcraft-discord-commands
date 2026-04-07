/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Predicate;

public interface PredicateDecorator<T>
extends Predicate<T> {
    public Predicate<? super T>[] getPredicates();
}

