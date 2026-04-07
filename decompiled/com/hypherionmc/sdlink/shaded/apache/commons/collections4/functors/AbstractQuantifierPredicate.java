/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Predicate;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.FunctorUtils;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.PredicateDecorator;
import java.io.Serializable;

public abstract class AbstractQuantifierPredicate<T>
implements PredicateDecorator<T>,
Serializable {
    private static final long serialVersionUID = -3094696765038308799L;
    protected final Predicate<? super T>[] iPredicates;

    public AbstractQuantifierPredicate(Predicate<? super T> ... predicates) {
        this.iPredicates = predicates;
    }

    @Override
    public Predicate<? super T>[] getPredicates() {
        return FunctorUtils.copy(this.iPredicates);
    }
}

