/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Predicate;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.AbstractQuantifierPredicate;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.FunctorUtils;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.TruePredicate;
import java.util.Collection;

public final class NonePredicate<T>
extends AbstractQuantifierPredicate<T> {
    private static final long serialVersionUID = 2007613066565892961L;

    public static <T> Predicate<T> nonePredicate(Predicate<? super T> ... predicates) {
        FunctorUtils.validate(predicates);
        if (predicates.length == 0) {
            return TruePredicate.truePredicate();
        }
        return new NonePredicate<T>(FunctorUtils.copy(predicates));
    }

    public static <T> Predicate<T> nonePredicate(Collection<? extends Predicate<? super T>> predicates) {
        Predicate<T>[] preds = FunctorUtils.validate(predicates);
        if (preds.length == 0) {
            return TruePredicate.truePredicate();
        }
        return new NonePredicate(preds);
    }

    public NonePredicate(Predicate<? super T> ... predicates) {
        super(predicates);
    }

    @Override
    public boolean evaluate(T object) {
        for (Predicate iPredicate : this.iPredicates) {
            if (!iPredicate.evaluate(object)) continue;
            return false;
        }
        return true;
    }
}

