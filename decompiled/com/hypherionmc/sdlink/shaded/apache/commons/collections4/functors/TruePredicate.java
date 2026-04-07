/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Predicate;
import java.io.Serializable;

public final class TruePredicate<T>
implements Predicate<T>,
Serializable {
    private static final long serialVersionUID = 3374767158756189740L;
    public static final Predicate INSTANCE = new TruePredicate();

    public static <T> Predicate<T> truePredicate() {
        return INSTANCE;
    }

    private TruePredicate() {
    }

    @Override
    public boolean evaluate(T object) {
        return true;
    }

    private Object readResolve() {
        return INSTANCE;
    }
}

