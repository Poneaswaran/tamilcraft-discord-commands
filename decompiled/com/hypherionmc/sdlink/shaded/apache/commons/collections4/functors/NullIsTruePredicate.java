/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Predicate;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.PredicateDecorator;
import java.io.Serializable;

public final class NullIsTruePredicate<T>
implements PredicateDecorator<T>,
Serializable {
    private static final long serialVersionUID = -7625133768987126273L;
    private final Predicate<? super T> iPredicate;

    public static <T> Predicate<T> nullIsTruePredicate(Predicate<? super T> predicate) {
        if (predicate == null) {
            throw new NullPointerException("Predicate must not be null");
        }
        return new NullIsTruePredicate<T>(predicate);
    }

    public NullIsTruePredicate(Predicate<? super T> predicate) {
        this.iPredicate = predicate;
    }

    @Override
    public boolean evaluate(T object) {
        if (object == null) {
            return true;
        }
        return this.iPredicate.evaluate(object);
    }

    @Override
    public Predicate<? super T>[] getPredicates() {
        return new Predicate[]{this.iPredicate};
    }
}

