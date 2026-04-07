/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Predicate;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Transformer;
import java.io.Serializable;

public class PredicateTransformer<T>
implements Transformer<T, Boolean>,
Serializable {
    private static final long serialVersionUID = 5278818408044349346L;
    private final Predicate<? super T> iPredicate;

    public static <T> Transformer<T, Boolean> predicateTransformer(Predicate<? super T> predicate) {
        if (predicate == null) {
            throw new IllegalArgumentException("Predicate must not be null");
        }
        return new PredicateTransformer<T>(predicate);
    }

    public PredicateTransformer(Predicate<? super T> predicate) {
        this.iPredicate = predicate;
    }

    @Override
    public Boolean transform(T input) {
        return this.iPredicate.evaluate(input);
    }

    public Predicate<? super T> getPredicate() {
        return this.iPredicate;
    }
}

